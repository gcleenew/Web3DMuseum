/*
 * Copyright 2013 Damien Lecan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.play2war.plugin

import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.util.jar.Manifest

import scala.collection.immutable.Stream.consWrapper

import com.github.play2war.plugin.Play2WarKeys._

import com.typesafe.sbt.packager.Keys.projectDependencyArtifacts

import play.sbt.PlayImport.PlayKeys.playPackageAssets

import sbt.ConfigKey.configurationToKey
import sbt.Keys._
import sbt._

trait Play2WarCommands {

  val manifestRegex = """(?i).*META-INF[/\\]MANIFEST.MF"""

  def getFiles(root: File, skipHidden: Boolean = false): Stream[File] =
    if (!root.exists || (skipHidden && root.isHidden) ||
      manifestRegex.r.pattern.matcher(root.getAbsolutePath).matches()) {
      Stream.empty
    } else {
      root #:: (
        root.listFiles match {
          case null => Stream.empty
          case files => files.toStream.flatMap(getFiles(_, skipHidden))
        })
    }

  val warTask: Def.Initialize[Task[sbt.File]] = Def.task[sbt.File] {
    val s = streams.value

    // projectJars contains the jar of the projects and all sub-projects
    val projectBinJars = (projectDependencyArtifacts in Runtime).value
    val projectAssets = Attributed(playPackageAssets.value)(AttributeMap.empty)
    val projectJars = projectBinJars :+ projectAssets
    s.log.debug(s"projectJars = ${projectJars.mkString(", ")}, projectBinJars = ${projectBinJars.mkString(", ")}, projectAssets = $projectAssets")

    val dependencies = (dependencyClasspath in Runtime).value
    val unmanagedDependencies = (unmanagedClasspath in Runtime).value
    val id = normalizedName.value
    //val packaged = com.typesafe.sbt.packager.Keys.dist.value
    s.log.info("Build WAR package for servlet container: " + servletVersion.value)

    if (dependencies.exists(_.data.name.contains("play2-war-core-common"))) {
      s.log.debug("play2-war-core-common found in dependencies!")
    } else {
      s.log.error("play2-war-core-common not found in dependencies!")
      throw new IllegalArgumentException("play2-war-core-common not found in dependencies!")
    }

    val warDir = target.value
    val packageName = targetName.value.getOrElse(id + "-" + version.value)
    val war = warDir / (packageName + ".war")
    val manifestString = "Manifest-Version: 1.0\n"

    s.log.info("Packaging " + war.getCanonicalPath + " ...")

    IO.createDirectory(warDir)

    val allFilteredArtifacts = defaultFilteredArtifacts.value ++ filteredArtifacts.value

    allFilteredArtifacts.foreach {
      case (groupId, artifactId) =>
        s.log.debug("Ignoring dependency " + groupId + " -> " + artifactId)
    }

    val files: Traversable[(File, String)] = dependencies.
      filter(_.data.ext == "jar").flatMap { dependency =>
      val filename = for {
        module <- dependency.get(AttributeKey[ModuleID]("module-id"))
        artifact <- dependency.get(AttributeKey[Artifact]("artifact"))
        if !allFilteredArtifacts.contains((module.organization, module.name))
      } yield {
        // groupId.artifactId-version[-classifier].extension
        module.organization + "." + module.name + "-" + module.revision + artifact.classifier.fold(""){ "-" + _ } + "." + artifact.extension
      }
      filename.map { fName =>
        val path = "WEB-INF/lib/" + fName
        dependency.data -> path
      }
    } ++ unmanagedDependencies.map { unmanaged =>
      val path = "WEB-INF/lib/" + unmanaged.data.getName
      unmanaged.data -> path
    } ++ {

      // project jars
      if (explodedJar.value) {
        s.log.info("Main artifacts " + projectJars.map(_.data.getName).mkString("'", " ", "'") + " will be packaged exploded")

        val explodedJarDir = target.value / "exploded"

        IO.delete(explodedJarDir)
        IO.createDirectory(explodedJarDir)

        projectJars.map(_.data).flatMap { jar =>
          IO.unzip(jar, explodedJarDir).map {
            file =>
              val partialPath = IO.relativize(explodedJarDir, file).getOrElse(file.getName)

              file -> ("WEB-INF/classes/" + partialPath)
          }
        }
      } else {
        projectJars.map { projectJar =>
          val path = "WEB-INF/lib/" + projectJar.data.getName
          projectJar.data -> path
        }
      }
    }

    files.foreach { case (file, path) =>
      s.log.debug("Embedding file " + file + " -> " + path)
    }

    val webxmlFolder = webappResource.value / "WEB-INF"
    val webxml = webxmlFolder / "web.xml"

    // Web.xml generation
    servletVersion.value match {
      case "2.5" => {

        if (webxml.exists) {
          s.log.info("WEB-INF/web.xml found.")
        } else {
          s.log.info("WEB-INF/web.xml not found, generate it in " + webxmlFolder)
          IO.write(webxml,
            """<?xml version="1.0" ?>
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
        version="2.5">

  <display-name>Play! """ + id + """</display-name>

  <listener>
      <listener-class>play.core.server.servlet25.Play2Servlet</listener-class>
  </listener>

  <servlet>
    <servlet-name>play</servlet-name>
    <servlet-class>play.core.server.servlet25.Play2Servlet</servlet-class>
  </servlet>

  <servlet-mapping>
    <servlet-name>play</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>

</web-app>
                                 """ /* */)
        }

      }

      case "3.0" => handleWebXmlFileOnServlet3x(webxml, s, disableWarningWhenWebxmlFileFound.value, "3.0")
      case "3.1" => handleWebXmlFileOnServlet3x(webxml, s, disableWarningWhenWebxmlFileFound.value, "3.1")

      case unknown => {
        s.log.warn("Unknown servlet container version: " + unknown + ". Force default 3.0 version")
        handleWebXmlFileOnServlet3x(webxml, s, disableWarningWhenWebxmlFileFound.value, "3.0")
      }
    }

    // Webapp resources
    val webappR = webappResource.value
    s.log.debug("Webapp resources directory: " + webappR.getAbsolutePath)

    val filesToInclude = getFiles(webappR).filter(f => f.isFile)

    val additionnalResources = filesToInclude.map {
      f =>
        f -> Path.relativizeFile(webappR, f).get.getPath
    }

    additionnalResources.foreach {
      r =>
        s.log.debug("Embedding " + r._1 + " -> /" + r._2)
    }

    val metaInfFolder = webappR / "META-INF"
    val manifest = if (metaInfFolder.exists()) {
      val option = metaInfFolder.listFiles.find(f =>
        manifestRegex.r.pattern.matcher(f.getAbsolutePath).matches())
      if (option.isDefined) {
        new Manifest(new FileInputStream(option.get))
      }
      else {
        new Manifest(new ByteArrayInputStream(manifestString.getBytes))
      }
    }
    else {
      new Manifest(new ByteArrayInputStream(manifestString.getBytes))
    }

    // Package final jar
    val jarContent = (files ++ additionnalResources).toSet

    IO.jar(jarContent, war, manifest)

    s.log.info("Packaging done.")

    war
  }

  def handleWebXmlFileOnServlet3x(webxml: File, s: TaskStreams, disableWarn: Boolean, containerVersion: String) = {
    if (webxml.exists && !disableWarn) {
      s.log.warn(s"WEB-INF/web.xml found! As WAR package will be built for servlet $containerVersion containers, check if this web.xml file is compatible with.")
    }
  }
}
