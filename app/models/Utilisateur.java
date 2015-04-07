package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class Utilisateur extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;

  @Constraints.Required
  public String email;

  @Constraints.Required
  public String password;

  @Constraints.Required
  public String username;

  public String rights;

  @Formats.DateTime(pattern="dd/MM/yyyy")
  public Date dueDate = new Date();
  
  public static Finder<Long,Utilisateur> find = new Finder<Long,Utilisateur>(
    Long.class, Utilisateur.class
  ); 

}