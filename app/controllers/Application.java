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
import views.html.application.*;


public class Application extends Controller {

    public static Result index() {

        SimpleDateFormat d = new SimpleDateFormat ("yyyyMMdd"); 
        String date = d.format(new Date());
        int aleatoireCarrousel = Integer.parseInt(date)/2400000; 

        List<Objet> objets = Objet.find.all();
        int objetsSize = Collections.max(objets, new ObjetComparator()).id;

        String descriptionSite = ContenuSite.find.where().eq("emplacement", "descriptionSite").findUnique().contenu;
        String description3D   = ContenuSite.find.where().eq("emplacement", "description3D").findUnique().contenu;

        int i = 1;
        int y = 1;
        String firstImage  = "";
        String secondImage = "";
        String thirdImage  = "";
        String fourthImage = "";


        while( firstImage == "" ){
            i = i + aleatoireCarrousel + y;
            aleatoireCarrousel = aleatoireCarrousel+i;
            Objet firstObjet = Objet.find.byId( (aleatoireCarrousel) % objetsSize + 1);
            y++;    
            if( firstObjet == null ){
                continue;
            }     
            if( firstObjet.images.isEmpty() ){
                continue;
            }        
            firstImage = firstObjet.images.get(0).lien;
        }
        while( secondImage == "" || secondImage.equals(firstImage) ){
            i = i + aleatoireCarrousel + y;
            aleatoireCarrousel = aleatoireCarrousel+i;
            Objet secondObjet = Objet.find.byId((aleatoireCarrousel) % objetsSize + 1);
            y++;    
            if( secondObjet == null ){
                continue;
            }  
            if( secondObjet.images.isEmpty() ){
                continue;
            }      
            secondImage = secondObjet.images.get(0).lien;
        }
        while( thirdImage == "" || thirdImage.equals(firstImage) || thirdImage.equals(secondImage) ){
            i = i + aleatoireCarrousel + y;
            aleatoireCarrousel = aleatoireCarrousel+i;
            Objet thirdObjet = Objet.find.byId((aleatoireCarrousel) % objetsSize + 1);
            y++;    
            if( thirdObjet == null ){
                continue;
            } 
            if( thirdObjet.images.isEmpty() ){
                continue;
            }            
            thirdImage = thirdObjet.images.get(0).lien;
        }
        while( fourthImage == "" || fourthImage.equals(firstImage) || fourthImage.equals(secondImage) || fourthImage.equals(thirdImage) ){
            i = i + aleatoireCarrousel + y;
            aleatoireCarrousel = aleatoireCarrousel+i;
            Objet fourthObjet = Objet.find.byId((aleatoireCarrousel) % objetsSize + 1);
            y++;    
            if( fourthObjet == null ){
                continue;
            } 
            if( fourthObjet.images.isEmpty() ){
                continue;
            }          
            fourthImage = fourthObjet.images.get(0).lien;
        }

        return ok(home.render(firstImage, secondImage, thirdImage, fourthImage, descriptionSite, description3D));
    }

    public static Result search() {
        return ok(index.render("This is the header !", "This is the body !"));
    }

    public static Result searchResult() {
        return ok(index.render("This is the header !", "This is the body !"));
    }

    public static Result map() {
        return ok(index.render("This is the header !", "This is the body !"));
    }

    public static Result parcoursList() {
        return ok(index.render("This is the header !", "This is the body !"));
    }

    public static Result parcours(Integer id) {
        return ok(index.render("This is the header !", "This is the body !"));
    }

    public static Result random() {


        List<Objet> objets = Objet.find.all();
        int objetsSize = Collections.max(objets, new ObjetComparator()).id;

        Objet objet1 = Objet.find.byId( (int)(Math.random()*8000) % objetsSize + 1);
        Objet objet2 = Objet.find.byId( (int)(Math.random()*8000) % objetsSize + 1);
        Objet objet3 = Objet.find.byId( (int)(Math.random()*8000) % objetsSize + 1);
        Objet objet4 = Objet.find.byId( (int)(Math.random()*8000) % objetsSize + 1);
        Objet objet5 = Objet.find.byId( (int)(Math.random()*8000) % objetsSize + 1);
        Objet objet6 = Objet.find.byId( (int)(Math.random()*8000) % objetsSize + 1);
        Objet objet7 = Objet.find.byId( (int)(Math.random()*8000) % objetsSize + 1);
        Objet objet8 = Objet.find.byId( (int)(Math.random()*8000) % objetsSize + 1);
        Objet objet9 = Objet.find.byId( (int)(Math.random()*8000) % objetsSize + 1);
        Objet objet10 = Objet.find.byId( (int)(Math.random()*8000) % objetsSize + 1);
        Objet objet11 = Objet.find.byId( (int)(Math.random()*8000) % objetsSize + 1);
        Objet objet12 = Objet.find.byId( (int)(Math.random()*8000) % objetsSize + 1);


        List<Parcours> parcours = Parcours.find.all();
        int parcoursSize = Collections.max(parcours, new ParcoursComparator()).id;

        Parcours parcours1 = Parcours.find.byId( (int)(Math.random()*8000) % parcoursSize + 1);
        Parcours parcours2 = Parcours.find.byId( (int)(Math.random()*8000) % parcoursSize + 1);
        Parcours parcours3 = Parcours.find.byId( (int)(Math.random()*8000) % parcoursSize + 1);
        Parcours parcours4 = Parcours.find.byId( (int)(Math.random()*8000) % parcoursSize + 1);


        return ok(random.render(objet1, objet2, objet3, objet4, objet5, objet6, objet7, objet8, objet9, objet10, objet11, objet12, parcours1, parcours2, parcours3, parcours4));
    }

    public static Result objet(Integer id) {
        Objet objet1 = Objet.find.byId(id);
        String imagePrincipale = "";
        if(objet1.model3D != null){
            imagePrincipale = objet1.model3D;
        }
        else if(objet1.images.get(0).lien != null) {
            imagePrincipale = objet1.images.get(0).lien;
        }
        else {
            imagePrincipale = "missing.jpg";
        }
        return ok(objet.render("Objet", objet1, imagePrincipale));
    }

    public static Result contact() {

        DynamicForm requestData = Form.form().bindFromRequest();
        String email = requestData.get("email");
        String message = requestData.get("message");
        String alert = "";

        if( email != null && message != null ){

            Feedback feedback = new Feedback();
            feedback.email = email;
            feedback.contenu = message;
            feedback.creationDate = new Date();

            feedback.save();

            alert = "<div id='retourFeedback' class='alert alert-success' role='alert'> Votre message a été envoyé, il sera pris en compte et nous vous recontacterons </div>";

        }

        // TO DO : create feedback 

        return ok(contact.render(alert));
    }
}