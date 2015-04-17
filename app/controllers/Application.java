package controllers;

import play.*;
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
        int aleatoireCarrousel = Integer.parseInt(date)/8000; 

        List<Objet> objets = Objet.find.all();
        int objetsSize = Collections.max(objets, new ObjetComparator()).id;

        String descriptionSite = ContenuSite.find.where().eq("emplacement", "descriptionSite").findUnique().contenu;
        String description3D   = ContenuSite.find.where().eq("emplacement", "description3D").findUnique().contenu;

        int i = 1;
        String firstImage  = "";
        String secondImage = "";
        String thirdImage  = "";
        String fourthImage = "";


        while( firstImage == "" ){
            i = i + aleatoireCarrousel/1300;
            aleatoireCarrousel = aleatoireCarrousel+i;
            Objet firstObjet = Objet.find.byId( (aleatoireCarrousel/8000) % objetsSize + 1);
            if( firstObjet == null ){
                continue;
            }     
            if( firstObjet.images.isEmpty() ){
                continue;
            }            
            firstImage = firstObjet.images.get(0).lien;
        }
        while( secondImage == "" || secondImage.equals(firstImage) ){
            i = i + aleatoireCarrousel/1300;
            aleatoireCarrousel = aleatoireCarrousel+i;
            Objet secondObjet = Objet.find.byId((aleatoireCarrousel/8000) % objetsSize + 1);
            if( secondObjet == null ){
                continue;
            }  
            if( secondObjet.images.isEmpty() ){
                continue;
            }            
            secondImage = secondObjet.images.get(0).lien;
        }
        while( thirdImage == "" || thirdImage.equals(firstImage) || thirdImage.equals(secondImage) ){
            i = i + aleatoireCarrousel/1300;
            aleatoireCarrousel = aleatoireCarrousel+i;
            Objet thirdObjet = Objet.find.byId((aleatoireCarrousel/8000) % objetsSize + 1);
            if( thirdObjet == null ){
                continue;
            } 
            if( thirdObjet.images.isEmpty() ){
                continue;
            }            
            thirdImage = thirdObjet.images.get(0).lien;
        }
        while( fourthImage == "" || fourthImage.equals(firstImage) || fourthImage.equals(secondImage) || fourthImage.equals(thirdImage) ){
            i = i + aleatoireCarrousel/1300;
            aleatoireCarrousel = aleatoireCarrousel+i;
            Objet fourthObjet = Objet.find.byId((aleatoireCarrousel/8000) % objetsSize + 1);
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
        return ok(search.render("", ""));
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
        return ok(index.render("This is the header !", "This is the body !"));
    }

    public static Result objet(Integer id) {
        Objet objet1 = Objet.find.byId(id);

        return ok(objet.render("Objet", objet1));
    }

    public static Result contact() {
        return ok(contact.render());
    }
}
