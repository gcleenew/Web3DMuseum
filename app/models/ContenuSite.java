package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class ContenuSite extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;
  
  @Constraints.Required
  @Column(columnDefinition = "TEXT")
  public String contenu;
  
  public static Finder<Long,ContenuSite> find = new Finder<Long,ContenuSite>(
    Long.class, ContenuSite.class
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
}