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
import views.html.Static.*;

public class Static extends Controller {

    public static Result aboutUs() {
        String content   = ContenuSite.find.where().eq("emplacement", "aboutUs").findUnique().contenu;
        return ok(aboutUs.render(content));
    }

    public static Result faq() {
        String content   = ContenuSite.find.where().eq("emplacement", "faq").findUnique().contenu;
        return ok(faq.render(content));
    }

    public static Result informations() {
        String content   = ContenuSite.find.where().eq("emplacement", "informations").findUnique().contenu;
        return ok(conditionsGenerales.render(content));
    }

    public static Result conditionsGenerales() {
        String content   = ContenuSite.find.where().eq("emplacement", "conditionsGenerales").findUnique().contenu;
        return ok(conditionsGenerales.render(content));
    }
}
