package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class PropositionModification extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;

  @Constraints.Required
  public String nomChamp;

  @Constraints.Required
  @Column(columnDefinition = "TEXT")
  public String nouveauContenu;

  @ManyToOne
  public Utilisateur utilisateur;

  @ManyToOne
  public Objet objet;
  
  public static Finder<Long,PropositionModification> find = new Finder<Long,PropositionModification>(
    Long.class, PropositionModification.class
  ); 

  
  public Long getId() {
      return id;
  }
  
  public void setId(Long id) {
      this.id = id;
  }

  
  public String getNomChamp() {
      return nomChamp;
  }
  
  public void setNomChamp(String nomChamp) {
      this.nomChamp = nomChamp;
  }

  public String getNouveauContenu() {
      return nouveauContenu;
  }
  
  public void setNouveauContenu(String nouveauContenu) {
      this.nouveauContenu = nouveauContenu;
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