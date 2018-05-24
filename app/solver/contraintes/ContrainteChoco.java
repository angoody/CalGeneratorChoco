package solver.contraintes;

import models.ContrainteDecompose;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import solver.modelChoco.ModuleChoco;

import java.util.*;
import java.util.stream.Collectors;

public abstract class ContrainteChoco {
    protected Map<ModuleChoco, Constraint> constraints  = new HashMap<>();
    private ContrainteDecompose contrainteModel;
    private List<ModuleChoco> modulesInChoco;
    protected Model model;

    public ContrainteChoco(Model model, ContrainteDecompose contrainte, List<ModuleChoco> modulesInChoco)
    {
        this.model = model;
        this.contrainteModel = contrainte;
        this.modulesInChoco = modulesInChoco;

    }


    // Toute class héritant de cette class doit implémenter la méthode createContraints
    public abstract Constraint createConstraint(ModuleChoco module);

    public ContrainteDecompose getContrainteModel() {
        return contrainteModel;
    }


    public void enableAlternateSearch(ModuleChoco module){
        unPost(module);
    }

    public void disableAlternateSearch(ModuleChoco module){
        post(module);
    }

    public Boolean isAlternateSearch()
    {
        return (constraints.values().stream().filter(c -> c.getStatus() == Constraint.Status.POSTED).count() == constraints.values().size());
    }

    public Constraint post(ModuleChoco module) {
        Constraint constraint = constraints.get(module);
        if (constraint == null) {
            constraint = createConstraint(module);
            constraints.put(module, constraint);
        }
        if (constraint.getStatus() == Constraint.Status.REIFIED)
            model.unpost(constraint);

        constraint.post();

        // Si toutes les contraintes sont posté, alors la contrainte est respectée
        if (constraints.values().stream().filter(c -> c.getStatus() == Constraint.Status.POSTED).count() == constraints.values().size())
            contrainteModel.setRespeced(true);

        return constraint;
    }

    public Constraint unPost(ModuleChoco module)
    {
        Constraint constraint = constraints.get(module);
        model.unpost(constraint);
        //constraint.reify();
        contrainteModel.setRespeced(false);

        return constraint;
    }

    public Constraint getContraint(ModuleChoco module) {
        Constraint constraint = constraints.get(module);
        if (constraint == null) {
            constraint = createConstraint(module);
            constraints.put(module, constraint);
        }
        return constraint;
    }

    public List<Constraint> getConstraint()
    {
        return constraints.values().stream().collect(Collectors.toList());
    }


    public List<ModuleChoco> getModulesInChoco() {
        return modulesInChoco;
    }
}
