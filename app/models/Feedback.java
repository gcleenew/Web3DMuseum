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

  
  public Long getId() {
      return id;
  }
  
  public void setId(Long id) {
      this.id = id;
  }

  
  public String getContenu() {
      return contenu;
  }
  
  public void setContenu(String contenu) {
      this.contenu = contenu;
  }

  
  public String getEmail() {
      return email;
  }
  
  public void setEmail(String email) {
      this.email = email;
  }

  
  public Date getCreationDate() {
      return creationDate;
  }
  
  public void setCreationDate(Date creationDate) {
      this.creationDate = creationDate;
  }

}