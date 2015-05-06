package controllers;

import play.*;
import play.data.*;
import play.data.Form;
import play.data.DynamicForm;
import play.mvc.*;



import java.util.Date;
import java.util.*;
import java.text.*;
import models.*;

import views.html.*;
import views.html.application.*;

public class Static extends Controller {

    public static Result aboutUs() {
        return ok(index.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result faq() {
        String content   = ContenuSite.find.where().eq("emplacement", "faq").findUnique().contenu;
        return ok(faq.render("Foire aux questions", content));
    }
    
    public static Result informations() {
        return ok(index.render("This is the header !!!!!", "This is the body !!!!!"));
    }

    public static Result conditionsGenerales() {
        return ok(index.render("This is the header !!!!!", "This is the body !!!!!"));
    }
}
