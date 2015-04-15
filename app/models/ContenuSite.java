package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity 
public class ContenuSite extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;
  
  @Constraints.Required
  @Column(columnDefinition = "TEXT")
  public String contenu;
  
  @Constraints.Required
  public String emplacement;
  
  public static Finder<Long,ContenuSite> find = new Finder<Long,ContenuSite>(
    Long.class, ContenuSite.class
  );

}