package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

class ObjetComparator implements Comparator<Objet> {
  @Override
  public int compare(Objet first, Objet second) {
    return new Integer(first.getId()).compareTo(new Integer(second.getId()));
  }
  
}


@Entity 
public class Objet extends Model {

  @Id
  @Constraints.Min(10)
  public int id;
  
  @Constraints.Required
  public String nom;

  @Constraints.Required
  public String reference;

  @Constraints.Required
  @Column(columnDefinition = "TEXT")
  public String description;

  public String type_objet;

  public String matiere;

  public Double largeur;

  public Double longeur;
  
  public Double hauteur;
  
  public Double poids;

  public String localisationActuelle;
  
  public String localisationOrigine;
  
  public String archeologue;

  public Date dateDecouverte;

  public String civilisation;

  public String model3D;

  @ManyToMany
  @JoinTable(name="statistique_objet",
   joinColumns=@JoinColumn(name="objet1"),
   inverseJoinColumns=@JoinColumn(name="objet2")
  )
  public List<Objet> statEnfants;

  @ManyToMany
  @JoinTable(name="statistique_objet",
   joinColumns=@JoinColumn(name="objet2"),
   inverseJoinColumns=@JoinColumn(name="objet1")
  )
  public List<Objet> statParents;


  @ManyToMany
  @JoinTable(name="oeuvre_composite",
   joinColumns=@JoinColumn(name="oeuvre_principale"),
   inverseJoinColumns=@JoinColumn(name="oeuvre_inspiree")
  )
  public List<Objet> enfants;

  @ManyToMany
  @JoinTable(name="oeuvre_composite",
   joinColumns=@JoinColumn(name="oeuvre_inspiree"),
   inverseJoinColumns=@JoinColumn(name="oeuvre_principale")
  )
  public List<Objet> parents;

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  public List<Favori> favoris = new ArrayList<Favori>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  public List<Historique> historiques = new ArrayList<Historique>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  public List<Image> images = new ArrayList<Image>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  public List<Video> videos = new ArrayList<Video>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  public List<Audio> audios = new ArrayList<Audio>();

  public List<PropositionModification> propositionModifications = new ArrayList<PropositionModification>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  public List<FaitHistorique> faitHistoriques = new ArrayList<FaitHistorique>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  public List<Commentaire> commentaires = new ArrayList<Commentaire>();

  @Formats.DateTime(pattern="dd/MM/yyyy")
  public Date creationDate = new Date();
  
  public static Finder<Integer,Objet> find = new Finder<Integer,Objet>(
    Integer.class, Objet.class
  ); 

  
  public int getId() {
      return id;
  }
  
  public void setId(int id) {
      this.id = id;
  }

  
  public String getNom() {
      return nom;
  }
  
  public void setNom(String nom) {
      this.nom = nom;
  }

  
  public String getReference() {
      return reference;
  }
  
  public void setReference(String reference) {
      this.reference = reference;
  }

  
  public String getDescription() {
      return description;
  }
  
  public void setDescription(String description) {
      this.description = description;
  }

  
  public String getType() {
      return type;
  }
  
  public void setType(String type) {
      this.type = type;
  }

  
  public String getMatiere() {
      return matiere;
  }
  
  public void setMatiere(String matiere) {
      this.matiere = matiere;
  }

  
  public Double getLargeur() {
      return largeur;
  }
  
  public void setLargeur(Double largeur) {
      this.largeur = largeur;
  }

  
  public Double getLongeur() {
      return longeur;
  }
  
  public void setLongeur(Double longeur) {
      this.longeur = longeur;
  }

  
  public Double getHauteur() {
      return hauteur;
  }
  
  public void setHauteur(Double hauteur) {
      this.hauteur = hauteur;
  }

  
  public Double getPoids() {
      return poids;
  }
  
  public void setPoids(Double poids) {
      this.poids = poids;
  }

  
  public String getLocalisationActuelle() {
      return localisationActuelle;
  }
  
  public void setLocalisationActuelle(String localisationActuelle) {
      this.localisationActuelle = localisationActuelle;
  }

  
  public String getLocalisationOrigine() {
      return localisationOrigine;
  }
  
  public void setLocalisationOrigine(String localisationOrigine) {
      this.localisationOrigine = localisationOrigine;
  }

  
  public String getArcheologue() {
      return archeologue;
  }
  
  public void setArcheologue(String archeologue) {
      this.archeologue = archeologue;
  }

  
  public Date getDateDecouverte() {
      return dateDecouverte;
  }
  
  public void setDateDecouverte(Date dateDecouverte) {
      this.dateDecouverte = dateDecouverte;
  }

  
  public String getCivilisation() {
      return civilisation;
  }
  
  public void setCivilisation(String civilisation) {
      this.civilisation = civilisation;
  }

  
  public String getModel3D() {
      return model3D;
  }
  
  public void setModel3D(String model3D) {
      this.model3D = model3D;
  }

  
  public List<Objet> getStatEnfants() {
      return statEnfants;
  }
  
  public void setStatEnfants(List<Objet> statEnfants) {
      this.statEnfants = statEnfants;
  }

  
  public List<Objet> getStatParents() {
      return statParents;
  }
  
  public void setStatParents(List<Objet> statParents) {
      this.statParents = statParents;
  }

  
  public List<Objet> getEnfants() {
      return enfants;
  }
  
  public void setEnfants(List<Objet> enfants) {
      this.enfants = enfants;
  }

  
  public List<Objet> getParents() {
      return parents;
  }
  
  public void setParents(List<Objet> parents) {
      this.parents = parents;
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
  
  public List<Image> getImages() {
      return images;
  }
  
  public void setImages(List<Image> images) {
      this.images = images;
  }

  public List<Video> getVideos() {
      return videos;
  }
  
  public void setVideos(List<Video> videos) {
      this.videos = videos;
  }

  public List<Audio> getAudios() {
      return audios;
  }
  
  public void setAudios(List<Audio> audios) {
      this.audios = audios;
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