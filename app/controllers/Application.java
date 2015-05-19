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

class ObjetComparator implements Comparator<Objet> {
  @Override
  public int compare(Objet first, Objet second) {
    return new Integer(first.id).compareTo(new Integer(second.id));
  }
}

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

    public static Result parcours(Long id) {
        List<Image> images = new ArrayList<Image>();

        Parcours parcours1 = Parcours.find.byId(id);
        List<ParcoursObjet> parcoursObjets = parcours1.parcoursObjets;
        for (int i = 0; i < parcoursObjets.size(); i++) {
            images.add(parcoursObjets.get(i).objet.images.get(0));
        }
        return ok(parcours.render("Parcours", parcours1, images));
    }

    public static Result random() {
        return ok(index.render("This is the header !", "This is the body !"));
    }

    public static Result objet(Integer id) {
        DynamicForm requestData = Form.form().bindFromRequest();

        String parcourString = requestData.get("parcour_recup");
        Long parcour = 0L;
        if (parcourString != null && parcourString != "") {
            parcour = Long.parseLong(parcourString);
        }
        Objet objet1 = Objet.find.byId(id);
        String imagePrincipale = "";
        Integer previous = 0;
        Integer next = 0;
        if(objet1.model3D != null){
            imagePrincipale = objet1.model3D;
        }
        else if(objet1.images.get(0).lien != null) {
            imagePrincipale = objet1.images.get(0).lien;
        }
        else {
            imagePrincipale = "missing.jpg";
        }


        if ( parcour != 0L ) {
            List<Image> images = new ArrayList<Image>();

            Parcours parcours1 = Parcours.find.byId(parcour);
            List<ParcoursObjet> parcoursObjets = parcours1.parcoursObjets;
            for (int i = 0; i < parcoursObjets.size(); i++) {
                if (parcoursObjets.get(i).objet.id == id) {
                    if (i == 0 && i == parcoursObjets.size()-1) {
                        previous = 0;
                        next = 0;
                    }
                    else if (i == 0) {
                        previous = 0;
                        next = parcoursObjets.get(i+1).objet.id;
                    }
                    else if(i == parcoursObjets.size()-1){
                        previous = parcoursObjets.get(i-1).objet.id;
                        next = 0;
                    }
                    else {
                        previous = parcoursObjets.get(i-1).objet.id;
                        next = parcoursObjets.get(i+1).objet.id;
                    }
                    
                }
            }
            
        }


        return ok(objet.render("Objet", objet1, imagePrincipale, previous, next, parcour));
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