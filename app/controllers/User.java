package controllers;

import play.*;
import play.data.*;
import play.data.Form;
import play.data.DynamicForm;
import play.mvc.*;


import core.*;
import java.util.Date;
import java.util.*;
import java.text.*;
import models.Utilisateur;

import views.html.*;
import views.html.User.*;

public class User extends Controller {

    public static Result login() {
        DynamicForm requestData = Form.form().bindFromRequest();
        String username = requestData.get("username");
        String password = requestData.get("password");
        String message = "";
        Utilisateur user = Utilisateur.find.where().eq("username", username).findUnique();
        if (user != null) {
            if (Crypt.checkPassword(password, user.password)) {
                session("connected", username);
                // TODO
                if (user.rights != null) {
                    session("right", user.rights);
                }

                message = "Vous avez été connecté";
                return ok(message);
            }
            else {
                message = "Ce nom d'utilisateur ou ce mot de passe n'est pas correct";
                return ok(message);
            }
        }
        else {
            message = "Ce nom d'utilisateur ou ce mot de passe n'est pas correct";
            return ok(message);
        }
        
        
        
        
    }

    public static Result register() {

        DynamicForm requestData = Form.form().bindFromRequest();
        String username = requestData.get("username");
        String email = requestData.get("email");
        String password = requestData.get("password");
        String conditions = requestData.get("conditions");

        if( conditions == null ){
            return ok(register.render("<div class='alert alert-alert' role='alert'> Attention, ne desactivez pas le JavaScript.  Veuillez accepter les conditions générales d'utilisation! </div>"));
        }

        Utilisateur utilisateur = Utilisateur.find.where().eq("username", username).findUnique();
        if( utilisateur != null ){
            return ok(register.render("<div class='alert alert-alert' role='alert'> Attention, ne desactivez pas le JavaScript. Le nom d'utilisateur n'est pas disponible! </div>"));
        }
        utilisateur = Utilisateur.find.where().eq("email", email).findUnique();
        if( utilisateur != null ){
            return ok(register.render("<div class='alert alert-alert' role='alert'> Attention, ne desactivez pas le JavaScript. L'adresse email n'est pas disponible! </div>"));
        }


        utilisateur = new Utilisateur();
            utilisateur.username = username;
            utilisateur.email = email;
            utilisateur.password = Crypt.createPassword(password);
            utilisateur.creationDate = new Date();

        utilisateur.save();
        
        return ok(register.render("<div class='alert alert-success' role='alert'> Votre compte a bien été créé. </div>"));
    }

    public static Result passwordForgotten(){
        return ok(index.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result profil(){
        String user = session("connected");
        return ok(index.render("Ton profil", user));
    }
}
