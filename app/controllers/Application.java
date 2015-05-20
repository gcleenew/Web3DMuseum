package controllers;

import play.*;
import play.data.*;
import play.data.Form;
import play.data.DynamicForm;
import play.mvc.*;


import play.db.ebean.*;


import java.util.Date;
import java.util.*;
import java.text.*;
import models.*;

import views.html.*;
import views.html.application.*;

import core.*;


public class Application extends Controller {

    public static Result index() {

        SimpleDateFormat d = new SimpleDateFormat ("yyyyMMdd"); 
        String date = d.format(new Date());
        int aleatoireCarrousel = Integer.parseInt(date)/2400000; 

        List<Objet> objets = Objet.find.all();
        int objetsSize = 5;


        String descriptionSite = ContenuSite.find.where().eq("emplacement", "descriptionSite").findUnique().contenu;
        String description3D   = ContenuSite.find.where().eq("emplacement", "description3D").findUnique().contenu;

        int i = 1;
        int y = 1;
        String firstImage  = "";
        String secondImage = "";
        String thirdImage  = "";
        String fourthImage = "";
        String firstLink  = "";
        String secondLink = "";
        String thirdLink  = "";
        String fourthLink = "";


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
            firstLink  = "/objet/"+firstObjet.id;   
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
            secondLink  = "/objet/"+secondObjet.id; 
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
            thirdLink  = "/objet/"+thirdObjet.id; 
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
            fourthLink  = "/objet/"+fourthObjet.id;         
            fourthImage = fourthObjet.images.get(0).lien;
        }

        return ok(home.render( firstLink , secondLink , thirdLink , fourthLink , firstImage, secondImage, thirdImage, fourthImage, descriptionSite, description3D));
    }

    public static Result search() {
        // Récupération des différentes civilisation

        String addCivList = "";
        List<String> li = new ArrayList<String>();
        // SqlQuery query = Objet.createSqlQuery("select distinct civilisation from Objet");
        // List<SqlRow> rows = query.findList();

        for(Objet obj: Objet.find.select("civilisation").findList()) {
            if(!li.contains(obj.civilisation)){
                li.add(obj.civilisation);

                addCivList += "<option value="+obj.civilisation+">"+obj.civilisation+"</option>";
            }
        }
        //System.out.print(addCivList);


    	// Récupération des données du formulaire de recherche

        DynamicForm requestData = Form.form().bindFromRequest();
        String nom = requestData.get("nom");
        String reference = requestData.get("reference");
        String type = requestData.get("type");
        String matiere = requestData.get("matiere");
        String archeo = requestData.get("archeo");
        String poids = requestData.get("poids");
        String longueur = requestData.get("longueur");
        String largeur = requestData.get("largeur");
        String hauteur = requestData.get("hauteur");
        String civilisation = requestData.get("civilisation");
        String locationAct = requestData.get("location-act");
        String locationTr = requestData.get("location-tr");
        String dateTr = requestData.get("date-tr");

        String liste_objet1 = "";
        Boolean launch = false;
        
        // Passage de la variable launch à "true" si une des variable est différent d'une chaine vide.

        nom = Verification.verifParam(nom);
        launch = Verification.verifBool(nom, launch);
        
        reference = Verification.verifParam(reference);
        launch = Verification.verifBool(reference, launch);

        type = Verification.verifParam(type);
        launch = Verification.verifBool(type, launch);

        matiere = Verification.verifParam(matiere);
        launch = Verification.verifBool(matiere, launch);

        archeo = Verification.verifParam(archeo);
        launch = Verification.verifBool(archeo, launch);

        poids = Verification.verifParam(poids);
        launch = Verification.verifBool(poids, launch);

        longueur = Verification.verifParam(longueur);
        launch = Verification.verifBool(longueur, launch);

        largeur = Verification.verifParam(largeur);
        launch = Verification.verifBool(largeur, launch);

        hauteur = Verification.verifParam(hauteur);
        launch = Verification.verifBool(hauteur, launch);

        civilisation = Verification.verifParam(civilisation);
        launch = Verification.verifBool(civilisation, launch);

        locationAct = Verification.verifParam(locationAct);
        launch = Verification.verifBool(locationAct, launch);

        locationTr = Verification.verifParam(locationTr);
        launch = Verification.verifBool(locationTr, launch);

        dateTr = Verification.verifParam(dateTr);
        launch = Verification.verifBool(dateTr, launch);

        System.out.print(launch);
    	// On effectue la requête, si un des paramètre est fourni par l'utilisateur.

        if(launch){
        	List<Objet> liste_objet = Objet.find
            .where()
                .ilike("nom", "%"+nom+"%")
                .ilike("reference", reference)
                .ilike("type_objet", "%"+type+"%")
                .ilike("matiere", "%"+matiere+"%")
                .ilike("archeologue", "%"+archeo+"%")
                .ilike("poids", poids)
                .ilike("longueur", longueur)
                .ilike("hauteur", hauteur)
                .ilike("largeur", largeur)
                .ilike("civilisation", civilisation)
                .ilike("localisationActuelle", "%"+locationAct+"%")
                .ilike("localisationOrigine", "%"+locationTr+"%")
                // .ilike("dateDecouverte", dateTr)
            .findList();

      
        
	        
            
	        for (Objet obj : liste_objet) {
	            liste_objet1 += obj.nom;
	        }
            System.out.println(liste_objet);
        }

        nom = Verification.eraser(nom);
        
            
        return ok(search.render(nom, liste_objet1, addCivList));
        //return ok(search.render("This is the header !", "This is the body !"));
    }

    public static Result searchResult() {
        return ok(index.render("This is the header !", "This is the body !"));
    }

    public static Result map() {

        String pays ="";
        HashMap<String, Integer> paysList = new HashMap<String, Integer>();

        for(Objet obj: Objet.find.select("localisationOrigine").findList()) {
            if( obj.localisationOrigine == null ) continue;
            if(paysList.get(obj.localisationOrigine) == null ){
                paysList.put(obj.localisationOrigine, 1);
            }
            else{
                paysList.put(obj.localisationOrigine , paysList.get(obj.localisationOrigine)+1 ) ;
            }
        }

        for(Map.Entry<String, Integer> entry : paysList.entrySet()) {

            pays += entry.getKey()+":{ fillKey:";
            if( entry.getValue() == 0 ){
                pays += "'NONE'";
            }
            else if ( entry.getValue() < 3 ){
                pays += "'VERYLOW'";
            }
            else if ( entry.getValue() < 6 ){
                pays += "'LOW'";
            }
            else if ( entry.getValue() < 10 ){
                pays += "'MEDIUM'";
            }
            else if ( entry.getValue() < 20 ){
                pays += "'HIGH'";
            }
            else{
                pays += "'VERYHIGH'";
            }
            pays += ", numberOfObject:"+entry.getValue()+",},";
            // do what you have to do here
            // In your case, an other loop.
        }

        return ok(map.render(pays));
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
