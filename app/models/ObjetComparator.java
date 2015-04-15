package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import models.Objet;


public class ObjetComparator implements Comparator<Objet> {
  @Override
  public int compare(Objet first, Objet second) {
    return new Integer(first.getId()).compareTo(new Integer(second.getId()));
  }
  
}

