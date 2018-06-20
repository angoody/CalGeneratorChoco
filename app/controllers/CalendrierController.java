package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Probleme;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import solver.ChocoSolver;
import solver.test;

public class CalendrierController  extends Controller {

    public Result solve() {

        JsonNode json = request().body().asJson();
        if (json == null)
            return badRequest();
        final Probleme probleme = Json.fromJson(json, Probleme.class);
        return ok(Json.toJson(new ChocoSolver(probleme).resoudre(5)));
    }

    public Result test() { return ok(Json.toJson(test.main(new String[] {"test"})));}
}
