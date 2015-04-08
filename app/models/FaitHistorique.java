package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class FaitHistorique extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;

  @Constraints.Required
  @Column(columnDefinition = "TEXT")
  public String contenu;

  public boolean valide;

  @ManyToOne
  public Utilisateur utilisateur;

  @ManyToOne
  public Objet objet;
  
  public static Finder<Long,FaitHistorique> find = new Finder<Long,FaitHistorique>(
    Long.class, FaitHistorique.class
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

  public boolean getValide() {
      return valide;
  }
  
  public void setValide(boolean valide) {
      this.valide = valide;
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