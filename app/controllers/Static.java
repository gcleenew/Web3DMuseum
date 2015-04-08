package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Static extends Controller {

    public static Result aboutUs() {
        return ok(index.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result faq() {
        return ok(index.render("This is the header !!!!!", "This is the body !!!!!"));
    }
    
    public static Result informations() {
        return ok(index.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result conditionsGenerales() {
        return ok(index.render("This is the header !!!!!", "This is the body !!!!!"));
    }
}
