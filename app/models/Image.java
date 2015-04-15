package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class Image extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;
  
  @Constraints.Required
  public String lien;

  @ManyToOne
  public Objet objet;
  
  public static Finder<Long,Image> find = new Finder<Long,Image>(
    Long.class, Image.class
  ); 
}