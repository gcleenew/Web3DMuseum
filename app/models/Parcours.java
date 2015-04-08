package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class Parcours extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;

  @Constraints.Required
  public String nom;

  @OneToMany(cascade=CascadeType.ALL, mappedBy="parcours")
  List<ParcoursObjet> parcoursObjets = new ArrayList<ParcoursObjet>();
  
  public static Finder<Long,Parcours> find = new Finder<Long,Parcours>(
    Long.class, Parcours.class
  ); 

  
  public Long getId() {
      return id;
  }
  
  public void setId(Long id) {
      this.id = id;
  }

  
  public String getNom() {
      return nom;
  }
  
  public void setNom(String nom) {
      this.nom = nom;
  }

  
  public List<ParcoursObjet> getParcoursObjets() {
      return parcoursObjets;
  }
  
  public void setParcoursObjets(List<ParcoursObjet> parcoursObjets) {
      this.parcoursObjets = parcoursObjets;
  }

}