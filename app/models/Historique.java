package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class Historique extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;

  @ManyToOne
  private Utilisateur utilisateur;

  @ManyToOne
  private Objet objet;
  
  public static Finder<Long,Historique> find = new Finder<Long,Historique>(
    Long.class, Historique.class
  ); 

  
  public Long getId() {
      return id;
  }
  
  public void setId(Long id) {
      this.id = id;
  }
  
  public Utilisateur getUtilisateur() {
      return utilisateur;
  }
  
  public void setUtilisateur(Utilisateur utilisateur) {
      this.utilisateur = utilisateur;
  }
  
  public Objet getObjet() {
      return objet;
  }
  
  public void setObjet(Objet objet) {
      this.objet = objet;
  }

}