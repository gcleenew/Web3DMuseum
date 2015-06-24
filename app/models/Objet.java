package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.annotation.Sql;


@Entity
public class Objet extends Model {

  @Id
  @Constraints.Min(10)
  public int id;

  @Constraints.Required
  public String nom;

  @Constraints.Required
  public String reference;

  @Constraints.Required
  @Column(columnDefinition = "TEXT")
  public String description;

  public String type_objet;

  public String matiere;

  public Double largeur;

  public Double longueur;

  public Double hauteur;

  public Double poids;

  public String localisationActuelle;

  public String localisationOrigine;

  public String archeologue;

  public Date dateDecouverte;

  public String civilisation;

  public String model3D;

  public int compositeParentId;

  @ManyToMany
  @JoinTable(name="statistique_objet",
   joinColumns=@JoinColumn(name="objet1"),
   inverseJoinColumns=@JoinColumn(name="objet2")
  )
  public List<Objet> statEnfants;

  @ManyToMany
  @JoinTable(name="statistique_objet",
   joinColumns=@JoinColumn(name="objet2"),
   inverseJoinColumns=@JoinColumn(name="objet1")
  )
  public List<Objet> statParents;

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  public List<Favori> favoris = new ArrayList<Favori>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  public List<Historique> historiques = new ArrayList<Historique>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  public List<Image> images = new ArrayList<Image>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  public List<Video> videos = new ArrayList<Video>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  public List<Audio> audios = new ArrayList<Audio>();

  public List<PropositionModification> propositionModifications = new ArrayList<PropositionModification>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  public List<FaitHistorique> faitHistoriques = new ArrayList<FaitHistorique>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  public List<Commentaire> commentaires = new ArrayList<Commentaire>();

  @Formats.DateTime(pattern="dd/MM/yyyy")
  public Date creationDate = new Date();

  public static Finder<Integer,Objet> find = new Finder<Integer,Objet>(
    Integer.class, Objet.class
  );

  public String getField(String field)
  {
    if (field.equals("description")) {
      return description;
    }
    else if (field.equals("type_objet")) {
      return type_objet;
    }
    else if (field.equals("matiere")) {
      return matiere;
    }
    else if (field.equals("localisationActuelle")) {
      return localisationActuelle;
    }
    else if (field.equals("localisationOrigine")) {
      return localisationOrigine;
    }
    else if (field.equals("archeologue")) {
      return archeologue;
    }
    else if (field.equals("civilisation")) {
      return civilisation;
    }
    return null;
  }

}
