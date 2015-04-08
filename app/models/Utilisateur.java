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
  public Date creationDate = new Date();
  
  public static Finder<Long,Utilisateur> find = new Finder<Long,Utilisateur>(
    Long.class, Utilisateur.class
  ); 

  public Long getId() {
      return id;
  }
  
  public void setId(Long id) {
      this.id = id;
  }

  public String getEmail() {
      return email;
  }
  
  public void setEmail(String email) {
      this.email = email;
  }

  public String getPassword() {
      return password;
  }
  
  public void setPassword(String password) {
      this.password = password;
  }

  public String getUsername() {
      return username;
  }
  
  public void setUsername(String username) {
      this.username = username;
  }

  public String getRights() {
      return rights;
  }
  
  public void setRights(String rights) {
      this.rights = rights;
  }

  public Date getCreationDate() {
      return creationDate;
  }
  
  public void setCreationDate(Date creationDate) {
      this.creationDate = creationDate;
  }

}