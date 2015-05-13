package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

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
        return ok(indexAdmin.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result modifyText() {
        return ok(indexAdmin.render("This is the header !!!!!", "This is the body !!!!!"));
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
        return ok(indexAdmin.render("This is the header !!!!!", "This is the body !!!!!"));
    }
}
