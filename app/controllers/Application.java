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
        int objetsSize = 5;


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

        String liste_result = "";
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
                .ilike("longeur", longueur)
                .ilike("hauteur", hauteur)
                .ilike("largeur", largeur)
                .ilike("civilisation", civilisation)
                .ilike("localisationActuelle", "%"+locationAct+"%")
                .ilike("localisationOrigine", "%"+locationTr+"%")
                // .ilike("dateDecouverte", dateTr)
            .findList();

	        for (Objet obj : liste_objet) {
	            String image = Image.find.select("lien").where().eq("objet_id", obj.id).findUnique().lien;
                
                liste_result += "<div class=\"panel panel-default\"><div class=\"panel-heading\">"+obj.nom+"</div><div class=\"panel-body\"><div class=\"col-md-2\"><img class=\"searchImage\" src=\"/assets/imgObjet/"+image+"\"></div><div class=\"col-md-3\">Référence :"+obj.reference+"</div><div class=\"col-md-4 col-md-offset-0\">"+obj.description+"</div><div class=\"col-md-3 col-md-offset-0\">Type : "+obj.type_objet+"<br>Matière : "+obj.matiere+"<br>Poids : "+obj.poids+" gramme(s)<br></div></div></div>";
	           
            }
            System.out.println(liste_objet);

            
        }

        nom = Verification.eraser(nom);
        
            
        return ok(search.render(nom, liste_result, addCivList));
        //return ok(search.render("This is the header !", "This is the body !"));
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
