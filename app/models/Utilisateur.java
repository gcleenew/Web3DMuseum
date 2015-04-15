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

  @OneToMany(cascade=CascadeType.ALL, mappedBy="utilisateur")
  public List<Favori> favoris = new ArrayList<Favori>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="utilisateur")
  public List<Historique> historiques = new ArrayList<Historique>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  public List<PropositionModification> propositionModifications = new ArrayList<PropositionModification>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  public List<FaitHistorique> faitHistoriques = new ArrayList<FaitHistorique>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  public List<Commentaire> commentaires = new ArrayList<Commentaire>();

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

  public List<Favori> getFavoris() {
      return favoris;
  }
  
  public void setFavoris(List<Favori> favoris) {
      this.favoris = favoris;
  }

  public List<Historique> getHistoriques() {
      return historiques;
  }
  
  public void setHistoriques(List<Historique> historiques) {
      this.historiques = historiques;
  }

  public List<PropositionModification> getPropositionModifications() {
      return propositionModifications;
  }
  
  public void setPropositionModifications(List<PropositionModification> propositionModifications) {
      this.propositionModifications = propositionModifications;
  }

  public List<FaitHistorique> getFaitHistoriques() {
      return faitHistoriques;
  }
  
  public void setFaitHistoriques(List<FaitHistorique> faitHistoriques) {
      this.faitHistoriques = faitHistoriques;
  }

  public List<Commentaire> getCommentaires() {
      return commentaires;
  }
  
  public void setCommentaires(List<Commentaire> commentaires) {
      this.commentaires = commentaires;
  }

  public Date getCreationDate() {
      return creationDate;
  }
  
  public void setCreationDate(Date creationDate) {
      this.creationDate = creationDate;
  }

}