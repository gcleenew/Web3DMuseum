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
}