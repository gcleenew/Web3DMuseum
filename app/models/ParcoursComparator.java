package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import models.Parcours;



public class ParcoursComparator implements Comparator<Parcours> {
  @Override
  public int compare(Parcours first, Parcours second) {
    return new Integer(first.id).compareTo(new Integer(second.id));
  }
  
}
  