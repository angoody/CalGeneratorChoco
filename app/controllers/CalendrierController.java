package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.generator.input.Generator;
import models.verify.input.Verify;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import solver.ChocoGenerator;
import solver.ChocoTest;
import solver.ChocoVerify;

public class CalendrierController  extends Controller {

    public Result verify()
    {

        JsonNode json = request().body().asJson();
        if (json == null)
            return badRequest();
        final Verify problem = Json.fromJson(json, Verify.class);
        return ok(Json.toJson(new ChocoVerify(problem).solve()));
    }

    public Result solve() {

        JsonNode json = request().body().asJson();
        if (json == null)
            return badRequest();
        final Generator problem = Json.fromJson(json, Generator.class);
        return ok(Json.toJson(new ChocoGenerator(problem).solve()));
    }

    public Result outputGeneratorExample() { return ok(Json.toJson(ChocoTest.solve()));}

    public Result inputGeneratorExample() { return ok(Json.toJson(ChocoTest.getGeneratorInput()));}

    public Result outputVerifyExample() { return ok(Json.toJson(ChocoTest.verify()));}

    public Result inputVerifyExample() { return ok(Json.toJson(ChocoTest.getVerifyInput()));}
}
