package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class User extends Controller {

    public static Result login() {
        return ok(index.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result register() {
        return ok(index.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result passwordForgotten(){
        return ok(index.render("This is the header !!!!!", "This is the body !!!!!"));
    }
}
