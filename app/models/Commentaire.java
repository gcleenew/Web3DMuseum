package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class Commentaire extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;

  @Constraints.Required
  @Column(columnDefinition = "TEXT")
  public String contenu;

  public boolean valide;
  
  @Formats.DateTime(pattern="dd/MM/yyyy")
  public Date creationDate = new Date();

  @ManyToOne
  public Utilisateur utilisateur;

  @ManyToOne
  public Objet objet;
  
  public static Finder<Long,Commentaire> find = new Finder<Long,Commentaire>(
    Long.class, Commentaire.class
  ); 

}