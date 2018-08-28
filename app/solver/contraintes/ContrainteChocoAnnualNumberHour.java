package solver.contraintes;

import models.common.ConstraintPriority;
import models.common.Period;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;
import solver.modelChoco.ModuleChoco;
import solver.propagator.PropagatorContrainteLieu;
import solver.propagator.PropagatorMaxAnnualHour;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class ContrainteChocoAnnualNumberHour extends ContrainteChoco<Integer> {

    private final Period periodOfTraining;
    private Map<ModuleChoco, PropagatorMaxAnnualHour> propagators   = new HashMap<>();

    public ContrainteChocoAnnualNumberHour(Model model, ConstraintPriority<Integer> contrainteModel, List<ModuleChoco> modulesInChoco, Period periodOfTraining) {
        super(model, contrainteModel, modulesInChoco);
        this.periodOfTraining = periodOfTraining; 
    }

    @Override
    public Constraint createConstraint(ModuleChoco module) {
        PropagatorMaxAnnualHour prop       = new PropagatorMaxAnnualHour(module, getModulesInChoco(), periodOfTraining, getContraintePriority().getValue());
        Constraint constraint = new Constraint("Max Annual Hour" + module.getIdModule(), prop);
        propagators.put(module, prop);
        return constraint;
    }

    @Override
    public String getConstraintName() {
        return language.getString("contrainte.annualHour");
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
