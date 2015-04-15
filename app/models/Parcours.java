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
  public List<ParcoursObjet> parcoursObjets = new ArrayList<ParcoursObjet>();
  
  public static Finder<Long,Parcours> find = new Finder<Long,Parcours>(
    Long.class, Parcours.class
  ); 
}