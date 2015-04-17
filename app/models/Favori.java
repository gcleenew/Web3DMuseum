package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class Favori extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;

  @ManyToOne
  public Utilisateur utilisateur;

  @ManyToOne
  public Objet objet;
  
  public static Finder<Long,Favori> find = new Finder<Long,Favori>(
    Long.class, Favori.class
  ); 
}