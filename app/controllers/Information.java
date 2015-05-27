package controllers;

import play.*;
import play.data.*;
import play.data.Form;
import play.data.DynamicForm;
import play.mvc.*;

import java.util.Date;
import java.util.*;
import java.text.*;
import models.*;

import views.html.*;


public class Information extends Controller {

    

    public static Result isThereUserByName(String username ) {
        Utilisateur user = Utilisateur.find.where().eq("username", username).findUnique();
        if( user != null ){
            return ok("false");
        }
        return ok("true");
    }
    public static Result isThereUserByEmail(String email ) {
        Utilisateur user = Utilisateur.find.where().eq("email", email).findUnique();
        if( user != null ){
            return ok("false");
        }
        return ok("true");
    }
    public static Result getTextFromEmplacement(String emplacement){

        ContenuSite contenu = ContenuSite.find.where().eq("emplacement", emplacement).findUnique();
        return ok(contenu.contenu);
    }
}