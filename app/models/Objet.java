package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class Objet extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;
  
  @Constraints.Required
  public String nom;

  @Constraints.Required
  public String reference;

  @Constraints.Required
  @Column(columnDefinition = "TEXT")
  public String description;

  public String type;

  public String matiere;

  public Double largeur;

  public Double longeur;
  
  public Double hauteur;
  
  public Double poids;

  public String localisationActuelle;
  
  public String localisationOrigine;
  
  public String archéologue;

  public Date dateDecouverte;

  public String civilisation;

  public String model3D;

  @ManyToMany
  @JoinTable(name="statistique_objet",
   joinColumns=@JoinColumn(name="objet1"),
   inverseJoinColumns=@JoinColumn(name="objet2")
  )
  private List<Objet> statEnfants;

  @ManyToMany
  @JoinTable(name="statistique_objet",
   joinColumns=@JoinColumn(name="objet2"),
   inverseJoinColumns=@JoinColumn(name="objet1")
  )
  private List<Objet> statParents;


  @ManyToMany
  @JoinTable(name="oeuvre_composite",
   joinColumns=@JoinColumn(name="oeuvre_principale"),
   inverseJoinColumns=@JoinColumn(name="oeuvre_inspiree")
  )
  private List<Objet> enfants;

  @ManyToMany
  @JoinTable(name="oeuvre_composite",
   joinColumns=@JoinColumn(name="oeuvre_inspiree"),
   inverseJoinColumns=@JoinColumn(name="oeuvre_principale")
  )
  private List<Objet> parents;



  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  List<Image> images = new ArrayList<Image>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  List<Video> videos = new ArrayList<Video>();

  @OneToMany(cascade=CascadeType.ALL, mappedBy="objet")
  List<Audio> audios = new ArrayList<Audio>();

  @Formats.DateTime(pattern="dd/MM/yyyy")
  public Date dueDate = new Date();
  
  public static Finder<Long,Objet> find = new Finder<Long,Objet>(
    Long.class, Objet.class
  ); 

}