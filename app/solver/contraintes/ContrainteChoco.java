package solver.contraintes;

import models.ContrainteDecompose;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;

import java.util.*;
import java.util.stream.Collectors;

public abstract class ContrainteChoco {
    protected Map<String, Constraint> constraints  = new HashMap<>();
    private ContrainteDecompose contrainteModel;
    protected Model model;
    private ContrainteDecompose alternative;

    public ContrainteChoco(Model model, ContrainteDecompose contrainte)
    {
        this.model = model;
        this.contrainteModel = contrainte;
    }

    // Toute class héritant de cette class doit implémenter la méthode createContraints
    public abstract Constraint createConstraint(IntVar... var);

    public ContrainteDecompose getContrainteModel() {
        return contrainteModel;
    }

    public void setAlternative(ContrainteDecompose contrainte)
    {
        this.alternative = contrainte;
    }

    public ContrainteDecompose getAlternative() {
        return alternative;
    }

    public abstract void enableAlternateSearch(IntVar... var);

    public abstract void disableAlternateSearch(IntVar... var);

    public Constraint post(IntVar... var) {
        Constraint constraint = constraints.get(var[0].getName());
        if (constraint == null) {
            constraint = createConstraint(var);
            constraints.put(var[0].getName(), constraint);
        }
        if (constraint.getStatus() == Constraint.Status.REIFIED)
            model.unpost(constraint);

        constraint.post();

        // Si toutes les contraintes sont posté, alors la contrainte est respectée
        if (constraints.values().stream().filter(c -> c.getStatus() == Constraint.Status.POSTED).count() == constraints.values().size())
            contrainteModel.setRespeced(true);

        return constraint;
    }

    public Constraint unPost(IntVar... var)
    {
        Constraint constraint = constraints.get(var[0].getName());
        model.unpost(constraint);
        //constraint.reify();
        contrainteModel.setRespeced(false);

        return constraint;
    }

    public Constraint getContraint(IntVar... var) {
        Constraint constraint = constraints.get(var[0].getName());
        if (constraint == null) {
            constraint = createConstraint(var);
            constraints.put(var[0].getName(), constraint);
        }
        return constraint;
    }

    public List<Constraint> getConstraint()
    {
        return constraints.values().stream().collect(Collectors.toList());
    }


}
