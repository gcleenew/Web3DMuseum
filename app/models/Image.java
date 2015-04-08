package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class Image extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;
  
  @Constraints.Required
  public String lien;

  @ManyToOne
  private Objet objet;
  
  public static Finder<Long,Image> find = new Finder<Long,Image>(
    Long.class, Image.class
  ); 

  
  public Long getId() {
      return id;
  }
  
  public void setId(Long id) {
      this.id = id;
  }
  
  public String getLien() {
      return lien;
  }
  
  public void setLein(String lien) {
      this.lien = lien;
  }
  
  public Objet getObjet() {
      return objet;
  }
  
  public void setObjet(Objet objet) {
      this.objet = objet;
  }

}