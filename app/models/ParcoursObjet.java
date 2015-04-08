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
  private Objet objet;

  @ManyToOne
  private Parcours parcours;
  
  public static Finder<Long,ParcoursObjet> find = new Finder<Long,ParcoursObjet>(
    Long.class, ParcoursObjet.class
  ); 

  
  public Long getId() {
      return id;
  }
  
  public void setId(Long id) {
      this.id = id;
  }
  
  public Objet getObjet() {
      return objet;
  }
  
  public void setObjet(Objet objet) {
      this.objet = objet;
  }
  
  public Parcours getParcours() {
      return parcours;
  }
  
  public void setParcours(Parcours parcours) {
      this.parcours = parcours;
  }

}