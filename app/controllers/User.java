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
import views.html.application.*;

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
                session("right", user.rights);

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
        return ok(index.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result passwordForgotten(){
        return ok(index.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result profil(){
        String user = session("connected");
        return ok(index.render("Ton profil", user));
    }
}
