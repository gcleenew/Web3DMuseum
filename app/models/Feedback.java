package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class Feedback extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;
  
  @Constraints.Required
  @Column(columnDefinition = "TEXT")
  public String contenu;
  
  @Constraints.Required
  public String email;
  
  @Formats.DateTime(pattern="dd/MM/yyyy")
  public Date creationDate = new Date();
  
  public static Finder<Long,Feedback> find = new Finder<Long,Feedback>(
    Long.class, Feedback.class
  ); 

  public void createFeedback( String email, String contenu){
    creationDate = new Date();
    
    // TO DO
  }
}