package solver.contraintes;

import models.ContrainteDecompose;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

import java.util.*;

public abstract class Contrainte  {
    protected Map<String, Constraint> constraints  = new HashMap<>();;
    protected List<? extends ContrainteDecompose> contrainte;
    protected Model model;

    public Contrainte(Model model, List<? extends ContrainteDecompose> contrainte)
    {
        this.model = model;
        this.contrainte = contrainte;
    }

    public abstract Constraint createConstraint(IntVar... var);

    public Constraint post(IntVar... var)
    {
        Constraint constraint = constraints.get(var);
        if (constraint == null) {
            constraint = createConstraint(var);
            constraints.put(var[0].getName(), constraint);
        }
        constraint.post();

        // Si toutes les contraintes sont posté, alors la contrainte est respectée
        if (constraints.values().stream().filter(c -> c.getStatus() == Constraint.Status.POSTED).count() == constraints.values().size())
            contrainte.forEach(c -> c.setRespeced(true));

        return constraint;
    }

    public Constraint unPost(IntVar... var)
    {
        Constraint constraint = constraints.get(var[0].getName());
        model.unpost(constraint);
        constraint.reify();
        contrainte.forEach(c -> c.setRespeced(false));

        return constraint;
    }



}
