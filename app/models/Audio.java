package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class Audio extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;
  
  @Constraints.Required
  public String lien;

  @ManyToOne
  private Objet objet;
  
  public static Finder<Long,Audio> find = new Finder<Long,Audio>(
    Long.class, Audio.class
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
  
  public void setLien(String lien) {
      this.lien = lien;
  }
  
  public Objet getObjet() {
      return objet;
  }
  
  public void setObjet(Objet objet) {
      this.objet = objet;
  }
  
}