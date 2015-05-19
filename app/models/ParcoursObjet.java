package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class ParcoursObjet extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;

  @ManyToOne
  public Objet objet;

  @ManyToOne
  public Parcours parcours;
  
  public static Finder<Long,ParcoursObjet> find = new Finder<Long,ParcoursObjet>(
    Long.class, ParcoursObjet.class
  ); 

  
}