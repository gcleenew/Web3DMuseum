package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import models.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("This is the header !", "This is the body !"));
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
        return ok(index.render("This is the header !", "This is the body !"));
    }

    public static Result random() {
        return ok(index.render("This is the header !", "This is the body !"));
    }

    public static Result objet(Long id) {
        Objet objet1 = Objet.find.byId(id);

        return ok(objet.render("Objet", objet1));
    }

    public static Result contact() {
        return ok(index.render("This is the header !", "This is the body !"));
    }
}
