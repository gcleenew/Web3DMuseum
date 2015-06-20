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
import models.*;

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
                // session.maxAge = 5d;
                // TODO
                if (user.rights != null) {
                    session("right", user.rights);
                }

                message = "Vous avez été connecté";
                
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
        
        return redirect(routes.Application.index());
        
        
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
        if( utilisateur != null && username.length() < 5){
            return ok(register.render("<div class='alert alert-alert' role='alert'> Attention, ne desactivez pas le JavaScript. Le nom d'utilisateur n'est pas disponible! </div>"));
        }
        utilisateur = Utilisateur.find.where().eq("email", email).findUnique();
        if( utilisateur != null && email.length() < 5){
            return ok(register.render("<div class='alert alert-alert' role='alert'> Attention, ne desactivez pas le JavaScript. L'adresse email n'est pas disponible! </div>"));
        }
        if( password.length() < 5 ){
            return ok(register.render("<div class='alert alert-alert' role='alert'> Attention, ne desactivez pas le JavaScript. Votre mot de passe est trop court! </div>"));
        }

        utilisateur = new Utilisateur();
            utilisateur.username = username;
            utilisateur.email = email;
            utilisateur.password = Crypt.createPassword(password);
            utilisateur.creationDate = new Date();
            utilisateur.rights = "0";

        utilisateur.save();
        session("connected", username);
        session("right", utilisateur.rights);
        return ok(register.render("<div class='alert alert-success' role='alert'> Votre compte a bien été créé. Vous êtes connecté.</div>"));
    }

    public static Result passwordForgotten(){
        return ok(index.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result profil(){
        // Recupération du pseudo de l'user.

        String username = session("connected");

        //
        Utilisateur user = Utilisateur.find.where().eq("username", username).findUnique();

        // Recherche des commentaires associés à cet utilisateur.

        List<Commentaire> liste_com = Commentaire.find
            .where()
                .eq("utilisateur_id", user.id)
                
            .findList();
        
        String liste_result = "";

        for (Commentaire com : liste_com) {
                String val = "Non validé";
                String nom_objet = Objet.find.select("nom").where().eq("id", com.objet.id).findUnique().nom;
                String image = Image.find.select("lien").where().eq("objet_id", com.objet.id).findUnique().lien;

                if(com.valide){
                    val = "Validé";
                }

                liste_result += "<a href=\"/objet/"+com.objet+"\"><div class=\"panel panel-default searchPanel\"><div class=\"panel-heading\">Commentaire datant du "+com.creationDate+"  sur l'objet "+nom_objet+"</div><div class=\"panel-body\"><div class=\"col-md-2\"><img class=\"searchImage\" src=\"/assets/imgObjet/"+image+"\"></div><div class=\"col-md-9\">"+com.contenu+"</div><div class=\"col-md-1\">"+val+"</div></div></div></a>";
               
            }
        // Information compte
        
        
        return ok(profil.render(user, liste_result));
    }

    public static Result proposeModification(Integer id){


        DynamicForm requestData = Form.form().bindFromRequest();
        Objet objet1 = Objet.find.byId(id);
        String user = session("connected");

        Utilisateur utilisateur = Utilisateur.find.where().eq("username", user).findUnique();
        // initialisation du message d'erreur
        String message = "Rien n'as été changé";
        // création des différentes variable et remplissage avec les variables du formulaire

        String description = requestData.get("description");
        String type_objet = requestData.get("type_objet");
        String matiere = requestData.get("matiere");
        String localisationActuelle = requestData.get("localisationActuelle");
        String localisationOrigine = requestData.get("localisationOrigine");
        String archeologue = requestData.get("archeologue");
        String civilisation = requestData.get("civilisation");

        if (utilisateur != null) {
            message = CreateFields.checkPropose("description", description, utilisateur, objet1);
            message += CreateFields.checkPropose("type_objet", type_objet, utilisateur, objet1);
            message += CreateFields.checkPropose("matiere", matiere, utilisateur, objet1);
            message += CreateFields.checkPropose("localisationActuelle", localisationActuelle, utilisateur, objet1);
            message += CreateFields.checkPropose("localisationOrigine", localisationOrigine, utilisateur, objet1);
            message += CreateFields.checkPropose("archeologue", archeologue, utilisateur, objet1);
            message += CreateFields.checkPropose("civilisation", civilisation, utilisateur, objet1);
        }
        else{
            message = "Vous n'avez pas les droits pour proposer une modification";
        }


        if (message.equals("")) {
            message = "Rien n'as été changé";
        }
        

        return ok(proposeModification.render("Proposition de modification", objet1, message));
    }

    public static Result createCommentaire(Integer id) {
        DynamicForm requestData = Form.form().bindFromRequest();
        
        Objet objet1 = Objet.find.byId(id);
        String contenu = requestData.get("contenu");

        String user = session("connected");
        Utilisateur utilisateur = Utilisateur.find.where().eq("username", user).findUnique();

        String alert = "";
        // Fait une alerte si le contenu est sauvegardé 
        if( contenu != null ){

            Commentaire commentaire = new Commentaire();
            commentaire.contenu = contenu;
            commentaire.utilisateur = utilisateur;
            commentaire.objet = objet1;
            commentaire.creationDate = new Date();
            commentaire.valide = false;

            commentaire.save();

            alert = "<div id='retourCommentaire' class='alert alert-success' role='alert'> Votre commentaire a été envoyé, il devra être validé par un administrateur </div>";

        }
        flash("success", alert);
        // TO DO : create feedback 
        return redirect(controllers.routes.Application.objet(id));
    }

    public static Result createFait(Integer id) {
        DynamicForm requestData = Form.form().bindFromRequest();
        
        Objet objet1 = Objet.find.byId(id);
        String contenu = requestData.get("contenu");

        String user = session("connected");
        Utilisateur utilisateur = Utilisateur.find.where().eq("username", user).findUnique();

        String alert = "";
        // Fait une alerte si le contenu est sauvegardé 
        if( contenu != null ){

            FaitHistorique fait = new FaitHistorique();
            fait.contenu = contenu;
            fait.utilisateur = utilisateur;
            fait.objet = objet1;
            fait.creationDate = new Date();
            fait.valide = false;

            fait.save();

            alert = "<div id='retourCommentaire' class='alert alert-success' role='alert'> Votre fait historique a été envoyé, il devra être validé par un administrateur </div>";

        }
        flash("success", alert);
        // TO DO : create feedback 
        return redirect(controllers.routes.Application.objet(id));
    }


    public static Result logout(){
        session().clear();
        flash("info", "Deconnecté");
        return redirect(routes.Application.index());
    }

    public static Result changeMail(){
        String username = session("connected");
        Utilisateur user = Utilisateur.find.where().eq("username", username).findUnique();

        DynamicForm requestData = Form.form().bindFromRequest();
        
        System.out.println(requestData);

        String newMail = requestData.get("mail");

        if(newMail != null){

            if(!newMail.equals("")){
                user.email = newMail;
                user.save();
                String success = "<div id='successMail' class='alert alert-success' role='alert'>Votre email a été modifié.</div>";
                flash("successMail", success);
                return redirect(controllers.routes.User.profil());
            }
            else{
                String echec = "<div id='echecMail' class='alert alert-danger' role='alert'>Une erreur s'est produite veuillez réessayer.</div>";
                flash("echecMail", echec);
            }
        } 
        

        return ok(changeMail.render(user));
    }

    public static Result changePassword(){
        String username = session("connected");
        Utilisateur user = Utilisateur.find.where().eq("username", username).findUnique();

        DynamicForm requestData = Form.form().bindFromRequest();

        String password1 = requestData.get("password1");

        String password2 = requestData.get("password2");

        if(password1 != null && password2 != null){

            if(!password1.equals("") && !password2.equals("")){
                if(password1.equals(password2)){
                    user.password = Crypt.createPassword(password1);
                    user.save();
                    String success = "<div id='successPass' class='alert alert-success' role='alert'>Votre mot de passe a été modifié.</div>";
                    flash("successPass", success);
                    return redirect(controllers.routes.User.profil());
                }
                else{
                    String echec = "<div id='echecPass' class='alert alert-danger' role='alert'>Les mots de passe ne correspondent pas.</div>";
                    flash("echecPass", echec);
                }
            }
            else{
                String echec = "<div id='echecPass' class='alert alert-danger' role='alert'>Une erreur s'est produite veuillez réessayer.</div>";
                flash("echecPass", echec);
            }
        } 



        return ok(changePassword.render(user));
    }

}
