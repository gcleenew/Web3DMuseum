package controllers;

import play.*;
import play.data.*;
import play.data.Form;
import play.data.DynamicForm;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import java.lang.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.*;
import java.io.File;
import java.nio.file.*;
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
        String stringparentid = requestData.get("parentid");
        Integer parentid = 0;
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
        if (stringparentid != null && stringparentid != "") {
            parentid = Integer.parseInt(stringparentid);
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
            if (parentid.equals("")) {
                
            }else{
                objet1.compositeParentId = parentid;
            }
            
            objet1.dateDecouverte = dateDecouverte;
            objet1.localisationActuelle = localisationActuelle;
            if (localisationOrigine.equals("")) {
                
            }
            else{
                objet1.localisationOrigine = localisationOrigine;
            }
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

    public static Result deleteObjet(Integer id) {

        Objet objet = Objet.find.byId(id);

        objet.delete();
        String message = "<div id='retour' class='alert alert-success' role='alert'> Objet supprimé </div>";
        flash("delete", message);
        return ok("delete");
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
        String stringparentid = requestData.get("parentid");
        Integer parentid = 0;
        if (stringparentid != null && stringparentid != "") {
            parentid = Integer.parseInt(stringparentid);
        }
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
            if (parentid.equals("")) {
                
            }else{
                objet.compositeParentId = parentid;
            }
            objet.matiere = matiere;
            objet.largeur = largeur;
            objet.longueur = longueur;
            objet.hauteur = hauteur;
            objet.poids = poids;

            objet.dateDecouverte = dateDecouverte;
            objet.localisationActuelle = localisationActuelle;
            if (localisationOrigine.equals("")) {
                
            }
            else{
                objet.localisationOrigine = localisationOrigine;
            }
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

        return ok(addObjet.render(message));
    }

    public static Result addParcours() {
        // initialisation du message d'erreur
        String message = "";
        // création des différentes variable et remplissage avec les variables du formulaire
        DynamicForm requestData = Form.form().bindFromRequest();
        String nom = requestData.get("nom");

        Parcours parcours = Parcours.find.where().eq("nom", nom).findUnique();

        if( nom == null ){

        }
        else if( parcours == null ){
            parcours = new Parcours();
            parcours.nom = nom;
            parcours.save();

            message = "<div id='retourFeedback' class='alert alert-success' role='alert'> Le parcours : "+nom+" a été créé. </div>";
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
                File file = picture.getFile();
                // added lines


                String myUploadPath = Play.application().configuration().getString("myUploadPathPhoto");
                // Replace the /public/ folder
                File newFile=Play.application().getFile(myUploadPath + fileName);
                // Move the tmp file to the final location
                // Not the best way but it works !
                boolean bool = file.renameTo(newFile);

            } else {
                flash("error", "Missing file");
            }
        }


        Image image = Image.find.where().eq("nom", nom).findUnique();

        if( nom == null && objet == null){

        }
        else if( nom.equals("") || objet.equals("") ){
            message = "<div id='retourFeedback' class='alert alert-danger' role='alert'> La photo "+nom+" n'a pas été créé car un des champs n'était pas rempli.</div>";
        }
        else if( image == null ){
            image = new Image();
            image.nom = nom;
            image.objet = Objet.find.where().eq("nom", objet).findUnique();
            image.lien = nom+".png";
            image.save();

            message = "<div id='retourFeedback' class='alert alert-success' role='alert'> La photo : "+nom+" a été créé. </div>";
        }
        else {
            message = "<div id='retourFeedback' class='alert alert-danger' role='alert'> La photo "+nom+" n'a pas été créé car elle existe déjà </div>";
        }


        return ok(addPhoto.render(message));
    }

    public static Result addVideo() {
        // initialisation du message d'erreur
        String message = "";
        // création des différentes variable et remplissage avec les variables du formulaire
        DynamicForm requestData = Form.form().bindFromRequest();
        String nom = requestData.get("nom");
        String objet = requestData.get("objet");

        play.mvc.Http.MultipartFormData body = request().body().asMultipartFormData();

        if( body != null ){
            play.mvc.Http.MultipartFormData.FilePart videoFile = body.getFile("video");
            if (videoFile != null) {
                String fileName = nom+".mp4";
                File file = videoFile.getFile();
                // added lines


                String myUploadPath = Play.application().configuration().getString("myUploadPathVideo");
                // Replace the /public/ folder
                File newFile=Play.application().getFile(myUploadPath + fileName);
                // Move the tmp file to the final location
                // Not the best way but it works !
                boolean bool = file.renameTo(newFile);

            } else {
                flash("error", "Missing file");
            }
        }


        Video video = Video.find.where().eq("nom", nom).findUnique();

        if( nom == null && objet == null){

        }
        else if( nom.equals("") || objet.equals("") ){
            message = "<div id='retourFeedback' class='alert alert-danger' role='alert'> La vidéo "+nom+" n'a pas été créé car un des champs n'était pas rempli.</div>";
        }
        else if( video == null ){
            video = new Video();
            video.nom = nom;
            video.objet = Objet.find.where().eq("nom", objet).findUnique();
            video.lien = nom+".mp4";
            video.save();

            message = "<div id='retourFeedback' class='alert alert-success' role='alert'> La video : "+nom+" a été créé. </div>";
        }
        else {
            message = "<div id='retourFeedback' class='alert alert-danger' role='alert'> La video "+nom+" n'a pas été créé car elle existe déjà </div>";
        }


        return ok(addVideo.render(message));
    }

    public static Result addAudio() {
        // initialisation du message d'erreur
        String message = "";
        // création des différentes variable et remplissage avec les variables du formulaire
        DynamicForm requestData = Form.form().bindFromRequest();
        String nom = requestData.get("nom");
        String objet = requestData.get("objet");

        play.mvc.Http.MultipartFormData body = request().body().asMultipartFormData();

        if( body != null ){
            play.mvc.Http.MultipartFormData.FilePart audioFile = body.getFile("audio");
            if (audioFile != null) {
                String fileName = nom+".mp3";
                File file = audioFile.getFile();
                // added lines


                String myUploadPath = Play.application().configuration().getString("myUploadPathAudio");
                // Replace the /public/ folder
                File newFile=Play.application().getFile(myUploadPath + fileName);
                // Move the tmp file to the final location
                // Not the best way but it works !
                boolean bool = file.renameTo(newFile);

            } else {
                flash("error", "Missing file");
            }
        }


        Audio audio = Audio.find.where().eq("nom", nom).findUnique();

        if( nom == null && objet == null){

        }
        else if( nom.equals("") || objet.equals("") ){
            message = "<div id='retourFeedback' class='alert alert-danger' role='alert'> L'audio "+nom+" n'a pas été créé car un des champs n'était pas rempli.</div>";
        }
        else if( audio == null ){
            audio = new Audio();
            audio.nom = nom;
            audio.objet = Objet.find.where().eq("nom", objet).findUnique();
            audio.lien = nom+".png";
            audio.save();

            message = "<div id='retourFeedback' class='alert alert-success' role='alert'> L'audio : "+nom+" a été créé. </div>";
        }
        else {
            message = "<div id='retourFeedback' class='alert alert-danger' role='alert'> L'audio "+nom+" n'a pas été créé car elle existe déjà </div>";
        }


        return ok(addAudio.render(message));
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
        List<Parcours> parcours = Parcours.find.all();
        return ok(listParcours.render(parcours));
    }

    public static Result parcour(Integer id) {
        Parcours parcours1 = Parcours.find.byId(id);
        DynamicForm requestData = Form.form().bindFromRequest();

        String reference = requestData.get("reference");

        Objet objet = Objet.find.where().eq("reference", reference).findUnique();
        String alert = "";
        if (objet == null )
        {
            if (reference != "" && reference != null)
                alert = "<div id='retourRecherche' class='alert alert-danger' role='alert'> Aucun objet sur le site est atribué à cette référence, veuillez vérifier si cette référence à bien été entrée et que l'objet existe bien sur le site. </div>";
            flash("fail", alert);
        }
        else {
            ParcoursObjet parcoursObjet = new ParcoursObjet();
            parcoursObjet.objet = objet;
            parcoursObjet.parcours = parcours1;

            parcoursObjet.save();
            alert = "<div id='retour' class='alert alert-success' role='alert'> Objet ajouté </div>";
            flash("valide", alert);
        }

        return ok(parcour.render(parcours1));
    }

    public static Result deleteObjetParcours(Long id) {
        ParcoursObjet parcoursObjet = ParcoursObjet.find.byId(id);
        Parcours parcours1 = parcoursObjet.parcours;
        parcoursObjet.delete();
        String alert = "";
        alert = "<div id='retour' class='alert alert-success' role='alert'> Objet supprimé </div>";
        flash("delete", alert);
        return redirect(controllers.routes.BackOffice.parcour(parcours1.id));
    }

     public static Result contact() {
        List<Feedback> feedbacks = Feedback.find.all();

        return ok(contact.render("", feedbacks));
    }

    public static Result contactMessage(String message) {
        List<Feedback> feedbacks = Feedback.find.all();

        if ( message.equals("delete")){
            message = "<div id='retour' class='alert alert-success' role='alert'> Feedback supprimé </div>";
        }
        return ok(contact.render(message, feedbacks));
    }

    public static Result deleteContact(Integer id) {

        Feedback feedback = Feedback.find.byId((long) (id));

        feedback.delete();


        return ok("delete");
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
