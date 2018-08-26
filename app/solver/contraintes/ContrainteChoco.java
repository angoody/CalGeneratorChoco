package solver.contraintes;

import models.common.ConstraintPriority;
import models.common.ConstraintRespected;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.BoolVar;
import solver.modelChoco.ModuleChoco;

import java.util.*;
import java.util.stream.Collectors;

public abstract class ContrainteChoco<T> {

    protected static ResourceBundle language = ResourceBundle.getBundle("language", Locale.getDefault());

    protected Map<ModuleChoco, Constraint> constraints = new HashMap<>();
    private List<BoolVar> constraintReified = new ArrayList<>();
    private ConstraintRespected constrainteRespected;
    private ConstraintPriority<T> contrainteModel;
    private List<ModuleChoco> modulesInChoco;
    protected Model model;
    private Constraint heuristic = null;

    public ContrainteChoco(Model model, ConstraintPriority<T> contrainteModel, List<ModuleChoco> modulesInChoco) {
        this.model = model;
        this.contrainteModel = contrainteModel;
        this.constrainteRespected = new ConstraintRespected(getConstraintName(), contrainteModel);
        this.modulesInChoco = modulesInChoco;

    }


    // Toute class héritant de cette class doit implémenter la méthode createContraints
    public abstract Constraint createConstraint(ModuleChoco module);

    public abstract String getConstraintName();

    public ConstraintRespected getConstrainteRespected() {
        return constrainteRespected;
    }

    public ConstraintRespected calculateRespectOfConstraint() {
        return new ConstraintRespected(constrainteRespected, !isAlternateSearch());
    }

    public ConstraintRespected calculateRespectOfConstraint(ModuleChoco module) {
        return new ConstraintRespected(constrainteRespected, !isAlternateSearch(module));
    }

    public void enableAlternateSearch(ModuleChoco module) {
        unPost(module);
    }

    public void disableAlternateSearch(ModuleChoco module) {
        post(module);
    }

    public Boolean isAlternateSearch(ModuleChoco module) {
        return constraints.get(module) == null ? false : (constraints.get(module).getStatus() != Constraint.Status.POSTED);
    }

    public Boolean isAlternateSearch() {
        return modulesInChoco.stream().filter(m -> isAlternateSearch(m)).count() > 0;
    }

    public Constraint post(int nbModule) {
        if (heuristic != null)
            model.unpost(heuristic);

        heuristic = model.and(reify());
        heuristic.post();
        return heuristic;
    }

    public Constraint post(ModuleChoco module) {
        Constraint constraint = constraints.get(module);
        if (constraint == null) {
            constraint = createConstraint(module);
            constraints.put(module, constraint);
        }
        if (constraint.getStatus() != Constraint.Status.FREE)
            model.unpost(constraint);

        if (constraint.getStatus() != Constraint.Status.POSTED)
            constraint.post();

        // Si toutes les contraintes sont posté, alors la contrainte est respectée
        if (constraints.values().stream().filter(c -> c.getStatus() == Constraint.Status.POSTED).count() == constraints.values().size())
            constrainteRespected.setRespected(true);

        return constraint;
    }

    public Constraint unPost(ModuleChoco module) {
        Constraint constraint = constraints.get(module);
        if (constraint.getStatus() == Constraint.Status.POSTED)
            model.unpost(constraint);
        //constraint.reify();
        constrainteRespected.setRespected(false);

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

    public List<Constraint> getConstraints() {
        return constraints.values().stream().collect(Collectors.toList());
    }


    public List<ModuleChoco> getModulesInChoco() {
        return modulesInChoco;
    }


    public ConstraintPriority<T> getContraintePriority() {
        return contrainteModel;
    }

    public void setContraintePriority(ConstraintPriority<T> contrainteModel) {
        this.contrainteModel = contrainteModel;
    }

    public BoolVar[] reify() {
        if (constraintReified.isEmpty()) {
            //modulesInChoco.forEach(m -> unPost(m));
            constraintReified.addAll(modulesInChoco.stream().map(m -> getConstraint(m).reify()).collect(Collectors.toList()));
        }
        return constraintReified.stream().toArray(BoolVar[]::new);

    }

    public Constraint getConstraint(ModuleChoco module) {
        Constraint constraint = constraints.get(module);
        if (constraint == null) {
            constraint = createConstraint(module);
            constraints.put(module, constraint);
        }
        return constraint;
    }
}
