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

  
  @Formats.DateTime(pattern="dd/MM/yyyy")
  public Date creationDate = new Date();

  @ManyToOne
  public Utilisateur utilisateur;

  @ManyToOne
  public Objet objet;
  
  public static Finder<Long,PropositionModification> find = new Finder<Long,PropositionModification>(
    Long.class, PropositionModification.class
  ); 
}