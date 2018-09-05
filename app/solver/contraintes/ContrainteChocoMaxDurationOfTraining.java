package solver.contraintes;

import models.common.ConstraintPriority;
import models.common.Period;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleChoco;
import solver.propagator.PropagatorMaxAnnualHour;
import solver.propagator.PropagatorMaxDurationOfTraining;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContrainteChocoMaxDurationOfTraining extends ContrainteChoco<Integer> {

    private Map<ModuleChoco, PropagatorMaxDurationOfTraining> propagators   = new HashMap<>();

    public ContrainteChocoMaxDurationOfTraining(Model model, ConstraintPriority<Integer> contrainteModel, List<ModuleChoco> modulesInChoco, Period periodOfTraining) {
        super(model, contrainteModel, modulesInChoco);
    }

    @Override
    public Constraint createConstraint(ModuleChoco module) {
        PropagatorMaxDurationOfTraining prop       = new PropagatorMaxDurationOfTraining(module, getModulesInChoco(), getContraintePriority().getValue());
        Constraint constraint = new Constraint("Max week of training " + module.getIdModule(), prop);
        propagators.put(module, prop);
        return constraint;
    }

    @Override
    public String getConstraintName() {

        return String.format(language.getString("contrainte.training.duration.max"), getContraintePriority().getValue());
    }

    @Override
    public void enableAlternateSearch(ModuleChoco module)
    {
        propagators.get(module).searchAternatif((true));
    }

    @Override
    public void disableAlternateSearch(ModuleChoco module)
    {
        propagators.get(module).searchAternatif((false));
    }

    @Override
    public Boolean isAlternateSearch(ModuleChoco module)
    {
        return propagators.get(module).isAternatifSearch();
    }


}
