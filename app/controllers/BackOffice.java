package controllers;

import play.*;
import play.data.*;
import play.data.Form;
import play.data.DynamicForm;
import play.mvc.*;

import java.lang.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.*;
import models.*;

import views.html.*;
import views.html.BackOffice.*;

@With(core.CheckRights.class)
public class BackOffice extends Controller {

    public static Result index() {
        return ok(indexAdmin.render("This is the ADMIN header !", "This is the ADMIN body !"));
    }

    public static Result searchObject() {
        return ok(indexAdmin.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result searchObjectResult() {
        return ok(indexAdmin.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result objet(Integer id) {
        return ok(indexAdmin.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result addObjet() {
        // initialisation du message d'erreur
        String message = "";
        // création des différentes variable et remplissage avec les variables du formulaire
        DynamicForm requestData = Form.form().bindFromRequest();
        String nom = requestData.get("nom");
        String reference = requestData.get("reference");
        String description = requestData.get("description");
        String type_objet = requestData.get("type_objet");
        String matiere = requestData.get("matiere");
        String stringlarg = requestData.get("largeur");
        String stringlong = requestData.get("longueur");
        String stringhaut = requestData.get("hauteur");
        String stringpoids = requestData.get("poids");
        Double largeur = 3.1415926535;
        Double longueur = 3.1415926535;
        Double hauteur = 3.1415926535;
        Double poids = 3.1415926535;
        Date dateDecouverte = new Date();
        //parsage des string en double
        if (stringlarg != null && stringlarg != "") {
            largeur = Double.parseDouble(stringlarg);
        }
        if (stringlong != null && stringlong != "") {
            longueur = Double.parseDouble(stringlong);
        }
        if (stringhaut != null && stringhaut != "") {
            hauteur = Double.parseDouble(stringhaut);
        }
        if (stringpoids != null && stringpoids != "") {
            poids = Double.parseDouble(stringpoids);
        }

        // (1) create a SimpleDateFormat object with the desired format.
        // this is the format/pattern we're expecting to receive.


        String expectedPattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
        if (requestData.get("dateDecouverte") != null) {
            try
            {
              // (2) give the formatter a String that matches the SimpleDateFormat pattern
              String stringdate = requestData.get("dateDecouverte");
              dateDecouverte = formatter.parse(stringdate);
            }
            catch (ParseException e)
            {
              // execution will come here if the String that is given
              // does not match the expected format.
              e.printStackTrace();
            }
        }
        // remplissage avec les données du formulaire.
        String localisationActuelle = requestData.get("localisationActuelle");
        String localisationOrigine = requestData.get("localisationOrigine");
        String archeologue = requestData.get("archeologue");

        String civilisation = requestData.get("civilisation");
        String model3D = requestData.get("model3D");
        // si les trois première informations de l'objet sont remplies alors création de l'objet
        if (nom != "" && nom != null && reference != "" && reference != null && description != "" && description != null) {
            Objet objet = new Objet();
            objet.nom = nom;
            objet.reference = reference;
            objet.description = description;
            objet.type_objet = type_objet;
            objet.matiere = matiere;
            objet.largeur = largeur;
            objet.longueur = longueur;
            objet.hauteur = hauteur;
            objet.poids = poids;

            objet.dateDecouverte = dateDecouverte;
            objet.localisationActuelle = localisationActuelle;
            objet.localisationOrigine = localisationOrigine;
            objet.archeologue = archeologue;
            objet.civilisation = civilisation;
            objet.model3D = model3D;

            objet.save();
            message = "L'objet : "+nom+" a été crée";
        }
        else {
            message = "Vous n'avez pas remplis les trois premiers champs";
        }
        

        return ok(addObjet.render(message));
    }

    public static Result addParcours() {
        // initialisation du message d'erreur
        String message = "";
        // création des différentes variable et remplissage avec les variables du formulaire
        DynamicForm requestData = Form.form().bindFromRequest();
        String nom = requestData.get("nom");

        Parcours parcours = Parcours.find.where().eq("nom", nom).findUnique();

        if( parcours == null ){
            parcours = new Parcours();
            parcours.nom = nom;
            parcours.save();
       
            message = "<div id='retourFeedback' class='alert alert-success' role='alert'> Le parcours : "+nom+" a été créé. </div>";
        }
        else if( nom == null ){

        }
        else {
            message = "<div id='retourFeedback' class='alert alert-danger' role='alert'> Le parcours "+nom+" existe déjà. </div>";
        }
        

        return ok(addParcours.render(message));
    }

    public static Result addPhoto() {
        // initialisation du message d'erreur
        String message = "";
        // création des différentes variable et remplissage avec les variables du formulaire
        DynamicForm requestData = Form.form().bindFromRequest();
        String nom = requestData.get("nom");
        String objet = requestData.get("objet");


        play.mvc.Http.MultipartFormData body = request().body().asMultipartFormData();
        if( body != null ){
            play.mvc.Http.MultipartFormData.FilePart picture = body.getFile("image");
            if (picture != null) {
                String fileName = nom+".png";
                String contentType = picture.getContentType();
                java.io.File file = picture.getFile();
                // added lines
                String myUploadPath = Play.application().configuration().getString("myUploadPath");
                file.renameTo(new java.io.File(myUploadPath, fileName));

                return ok("file saved as " + myUploadPath + fileName );
            } else {
                flash("error", "Missing file");
                return redirect("/mod/addPhoto");
            }
        }
        
        Image image = Image.find.where().eq("nom", nom).findUnique();

        if( nom == null || objet == null || image == null ){
            message = "<div id='retourFeedback' class='alert alert-danger' role='alert'> La photo "+nom+" n'a pas été créé car un des champs n'était pas rempli.</div>";
        }
        else if( image == null ){
            image = new Image();
            image.nom = nom;
            image.objet = Objet.find.where().eq("nom", objet).findUnique();
            image.save();
       
            message = "<div id='retourFeedback' class='alert alert-success' role='alert'> La photo : "+nom+" a été créé. </div>";
        }        
        else {
            message = "<div id='retourFeedback' class='alert alert-danger' role='alert'> La photo "+nom+" n'a pas été créé car elle existe déjà </div>";
        }
        

        return ok(addPhoto.render(message));
    }


    public static Result modifyText() {
        String select = "";
        String alert = "";


        DynamicForm requestData = Form.form().bindFromRequest();
        String emplacement = requestData.get("emplacement");
        String contenu = requestData.get("contenu");

        if( emplacement != null && contenu != null ){

            ContenuSite contenuSite = ContenuSite.find.where().eq("emplacement", emplacement).findUnique();
            contenuSite.contenu = contenu;

            contenuSite.save();
            alert = "<div id='retourFeedback' class='alert alert-success' role='alert'> Le contenu a été modifié. </div>";


        }

        for(ContenuSite cont: ContenuSite.find.select("emplacement").findList()) {
            select += "<option value="+cont.emplacement+">"+cont.emplacement+"</option>";
            
        }

        return ok(modifyText.render(alert, select));
    }

    public static Result searchAdd() {
        return ok(indexAdmin.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result searchAddResult() {
        return ok(indexAdmin.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result feedback() {
        return ok(indexAdmin.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result listParcours() {
        return ok(indexAdmin.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result parcours(Integer id) {
        return ok(indexAdmin.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result stats() {
        return ok(indexAdmin.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result users() {

        List<Utilisateur> utilisateurs = Utilisateur.find.all();

        return ok(userAdmin.render(utilisateurs, ""));
    }

    public static Result usersWithMessage(String message) {

        List<Utilisateur> utilisateurs = Utilisateur.find.all();

        if( message.equals("promu")){
            message = "<div id='retour' class='alert alert-success' role='alert'> Utilisateur promu </div>";
        }
        else if( message.equals("admin")) {
            message = "<div id='retour' class='alert alert-danger' role='alert'> Utilisateur non promu car déjà administrateur </div>";
        }
        else{
            message = "<div id='retour' class='alert alert-success' role='alert'> Utilisateur supprimé </div>";
        }

        return ok(userAdmin.render(utilisateurs, message));
    }

    public static Result promoteUser(Integer id) {

        Utilisateur utilisateur = Utilisateur.find.byId((long) (id));

        if( utilisateur.rights.equals("admin") ){
            return ok("admin");
        }
        else if( !utilisateur.rights.equals("mod") ){
            utilisateur.rights = "mod";
        }
        else{
            utilisateur.rights = "admin";
        }

        utilisateur.save();


        return ok("promu");
    }

    public static Result deleteUser(Integer id) {

        Utilisateur utilisateur = Utilisateur.find.byId((long) (id));

        utilisateur.delete();


        return ok("delete");
    }
}
