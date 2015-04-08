package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

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
        return ok(index.render("This is the header !", "This is the body !"));
    }

    public static Result contact() {
        return ok(index.render("This is the header !", "This is the body !"));
    }

    public static Result login() {
        return ok(index.render("This is the header !", "This is the body !"));
    }

    public static Result register() {
        return ok(index.render("This is the header !", "This is the body !"));
    }

}
