package solver.contraintes;

import models.common.ConstraintPriority;
import models.common.ConstraintRespected;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.util.ESat;
import solver.modelChoco.ModuleChoco;

import java.util.*;

public abstract class ContrainteChoco<T> {

    protected static ResourceBundle language = ResourceBundle.getBundle("language", Locale.getDefault());

    protected Map<ModuleChoco, BoolVar> constraints = new HashMap<>();
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
        return constraints.get(module) == null ? false : (constraints.get(module).getBooleanValue() != ESat.TRUE);
    }

    public Boolean isAlternateSearch() {
        return modulesInChoco.stream().filter(m -> isAlternateSearch(m)).count() > 0;
    }

    public Constraint post(int nbModule) {
        if (heuristic != null)
            model.unpost(heuristic);

        heuristic = model.sum(reify(), "=", nbModule);
        heuristic.post();
        return heuristic;
    }

    public Constraint post(ModuleChoco module) {
        BoolVar constraint = constraints.get(module);
        if (constraint == null) {
            constraint = createConstraint(module).reify();
            constraints.put(module, constraint);
        }

        return model.trueConstraint();
    }

    public Constraint unPost(ModuleChoco module) {

        return model.falseConstraint();
    }

    public List<BoolVar> getConstraintReified()
    {
        return new ArrayList<>(constraints.values());
    }

    public Constraint getContraint(ModuleChoco module) {

        return model.trueConstraint();
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
        if (constraints.size() == 0) {
            //modulesInChoco.forEach(m -> unPost(m));
            modulesInChoco.stream().forEach(m -> constraints.put(m, createConstraint(m).reify()));

        }
        return constraints.values().stream().toArray(BoolVar[]::new);

    }


}
