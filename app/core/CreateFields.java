package core;

import play.*;
import play.mvc.*;

import java.util.Date;
import java.util.*;
import java.text.*;
import models.*;


public class CreateFields {
	
	public static String checkPropose(String champ, String valeur, Utilisateur utilisateur, Objet objet1) {


	    if (valeur != null && valeur != "") {
	    	if (!valeur.equals( objet1.getField(champ))) {
    			PropositionModification propose = new PropositionModification();
	    		propose.nomChamp = champ;
	    		propose.nouveauContenu = valeur;
	    		propose.utilisateur = utilisateur;
	    		propose.objet = objet1;

	    		propose.save();
	    		return "La proposition de "+champ+" a été créée, ";

	    	}
	    	else {
	    		return "";
	    	}
	    }
	    else {
	    	return "";
	    }
	    
	}
}
