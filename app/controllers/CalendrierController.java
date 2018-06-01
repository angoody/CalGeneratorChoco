package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.input.Problem;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import solver.ChocoSolver;
import solver.Test;

public class CalendrierController  extends Controller {

    public Result solve() {

        JsonNode json = request().body().asJson();
        if (json == null)
            return badRequest();
        final Problem problem = Json.fromJson(json, Problem.class);
        return ok(Json.toJson(new ChocoSolver(problem).solve()));
    }

    public Result test() { return ok(Json.toJson(Test.solve()));}


    public Result inputExample() { return ok(Json.toJson(Test.getInput()));}
}
