package controllers;

import play.*;
import play.libs.Json;
import play.mvc.*;

import solver.test;
import views.html.*;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

}
