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
  public Objet objet;
  
  public static Finder<Long,Audio> find = new Finder<Long,Audio>(
    Long.class, Audio.class
  );  
}