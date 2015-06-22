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

@With(core.CheckMod.class)
public class BackOffice extends Controller {

    public static Result index() {
        return ok(indexAdmin.render("", ""));
    }

    public static Result searchObject() {
        DynamicForm requestData = Form.form().bindFromRequest();
        String reference = requestData.get("reference");
        List<Objet> objets = Objet.find.all();
        Objet objet = Objet.find.where().eq("reference", reference).findUnique();
        String alert = "";
        if (objet == null ) 
        {
            if (reference != "" && reference != null)
                alert = "<div id='retourRecherche' class='alert alert-danger' role='alert'> Aucun objet sur le site est atribué à cette référence, veuillez vérifier si cette référence à bien été entrée et que l'objet existe bien sur le site. </div>";
            flash("fail", alert);
            return ok(searchObject.render(objets));
        }
        else {
            return redirect(controllers.routes.BackOffice.searchObjectResult(objet.id));
        }   
    }

    public static Result searchObjectResult(Integer id) {
        Objet objet = Objet.find.byId(id);
        return ok(searchObjectResult.render(objet));
    }

    public static Result deleteObject(Integer id) {
        Objet objet = Objet.find.byId(id);
        objet.delete();
        String alert = "";
        alert = "<div id='retour' class='alert alert-success' role='alert'> Objet supprimé </div>";
        flash("delete", alert);
        return redirect(controllers.routes.BackOffice.searchObject());
    }

    public static Result objet(Integer id) {

        DynamicForm requestData = Form.form().bindFromRequest();
        Objet objet1 = Objet.find.byId(id);
        String alert;

        String nom = requestData.get("nom");
        String reference = requestData.get("reference");
        String description = requestData.get("description");
        String type_objet = requestData.get("type_objet");
        String matiere = requestData.get("matiere");
        String stringlarg = requestData.get("largeur");
        String stringlong = requestData.get("longueur");
        String stringhaut = requestData.get("hauteur");
        String stringpoids = requestData.get("poids");
        Double largeur = 0d;
        Double longueur = 0d;
        Double hauteur = 0d;
        Double poids = 0d;
        Date dateDecouverte = objet1.dateDecouverte;
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
        if (description == null) {
            
        }
        else if (description != "" && description != null) {
            objet1.description = description;
            objet1.type_objet = type_objet;
            objet1.matiere = matiere;
            objet1.largeur = largeur;
            objet1.longueur = longueur;
            objet1.hauteur = hauteur;
            objet1.poids = poids;
            objet1.dateDecouverte = dateDecouverte;
            objet1.localisationActuelle = localisationActuelle;
            objet1.localisationOrigine = localisationOrigine;
            objet1.archeologue = archeologue;
            objet1.civilisation = civilisation;
            objet1.model3D = model3D;

            objet1.save();
            alert = "<div id='retourRecherche' class='alert alert-success' role='alert'> L'objet à bien été modifié</div>";
            flash("sucess", alert);
        }
        else {
            alert = "<div id='retourRecherche' class='alert alert-danger' role='alert'> Veuillez vérifier que le champ description n'as pas été vidé.</div>";
            flash("fail", alert);
        }



        return ok(objet.render(objet1));
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
        Double largeur = 0d;
        Double longueur = 0d;
        Double hauteur = 0d;
        Double poids = 0d;
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
        if (description == null) {
            
        }
        else if (nom != "" && nom != null && reference != "" && reference != null && description != "" && description != null) {
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

            message = "<div id='retourRecherche' class='alert alert-success' role='alert'> L'objet à bien été crée</div>";
            flash("success", message);
        }
        else {
            message = "<div id='retourRecherche' class='alert alert-danger' role='alert'> Les trois premiers champs n'ont pas été remplis.</div>";
            flash("fail", message);
        }
        

        return ok(addObjet.render("Ajout d'objet"));
    }

    public static Result listPropositions() {
        List<PropositionModification> propositions = PropositionModification.find.all();
        return ok(listPropositions.render(propositions));
    }

    public static Result proposition(Long id) {
        DynamicForm requestData = Form.form().bindFromRequest();
        PropositionModification proposition1 = PropositionModification.find.byId(id);
        String alert;
        String action = requestData.get("propositionAction");
        Objet objet = proposition1.objet;

        String champvalue = objet.getField(proposition1.nomChamp);

        if (action != null && action.equals("valid")){

            if (proposition1.nomChamp.equals("description")) {
              objet.description = proposition1.nouveauContenu;
            }
            else if (proposition1.nomChamp.equals("type_objet")) {
              objet.type_objet = proposition1.nouveauContenu;
            }
            else if (proposition1.nomChamp.equals("matiere")) {
              objet.matiere = proposition1.nouveauContenu;
            }
            else if (proposition1.nomChamp.equals("localisationActuelle")) {
              objet.localisationActuelle = proposition1.nouveauContenu;
            }
            else if (proposition1.nomChamp.equals("localisationOrigine")) {
              objet.localisationOrigine = proposition1.nouveauContenu;
            }
            else if (proposition1.nomChamp.equals("archeologue")) {
              objet.archeologue = proposition1.nouveauContenu;
            }
            else if (proposition1.nomChamp.equals("civilisation")) {
              objet.civilisation = proposition1.nouveauContenu;
            }

            objet.save();
            alert = "<div id='retour' class='alert alert-success' role='alert'> Propositon de modification validé </div>";
            flash("valide", alert);
            proposition1.delete();
            return redirect(controllers.routes.BackOffice.listPropositions());
        }
        else if (action != null && action.equals("delete")){
            proposition1.delete();
            alert = "<div id='retour' class='alert alert-success' role='alert'> Proposition de modification supprimé </div>";
            flash("delete", alert);
            return redirect(controllers.routes.BackOffice.listPropositions());
        }
        else {

        }
        return ok(proposition.render(proposition1, champvalue));
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

    public static Result listFaits() {
        List<FaitHistorique> faits = FaitHistorique.find.all();
        return ok(listFaits.render(faits));
    }

    public static Result faitHistorique(Long id) {
        DynamicForm requestData = Form.form().bindFromRequest();
        FaitHistorique fait = FaitHistorique.find.byId(id);
        String alert;
        String action = requestData.get("faitAction");

        if (action != null && action.equals("valid")){
            fait.valide = true;
            fait.save();
            alert = "<div id='retour' class='alert alert-success' role='alert'> Fait Historique validé </div>";
            flash("valide", alert);
            return redirect(controllers.routes.BackOffice.listFaits());
        }
        else if (action != null && action.equals("delete")){
            fait.delete();
            alert = "<div id='retour' class='alert alert-success' role='alert'> Fait Historique supprimé </div>";
            flash("delete", alert);
            return redirect(controllers.routes.BackOffice.listFaits());
        }
        else {

        }
        return ok(faitHistorique.render(fait));
    }

    public static Result listCommentaires() {
        List<Commentaire> commentaires = Commentaire.find.all();
        return ok(listCommentaires.render(commentaires));
    }

    public static Result commentaire(Long id) {
        DynamicForm requestData = Form.form().bindFromRequest();
        Commentaire commentaire1 = Commentaire.find.byId(id);
        String alert;
        String action = requestData.get("commentaireAction");

        if (action != null && action.equals("valid")){
            commentaire1.valide = true;
            commentaire1.save();
            alert = "<div id='retour' class='alert alert-success' role='alert'> Commentaire validé </div>";
            flash("valide", alert);
            return redirect(controllers.routes.BackOffice.listCommentaires());
        }
        else if (action != null && action.equals("delete")){
            commentaire1.delete();
            alert = "<div id='retour' class='alert alert-success' role='alert'> Commentaire supprimé </div>";
            flash("delete", alert);
            return redirect(controllers.routes.BackOffice.listCommentaires());
        }
        else {

        }
        return ok(commentaire.render(commentaire1));
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
