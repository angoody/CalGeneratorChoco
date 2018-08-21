package solver.contraintes;

import models.common.ConstraintPriority;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;
import scala.language;
import solver.modelChoco.ModuleChoco;
import solver.propagator.PropagatorContraintePrerequis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContrainteChocoModuleDuration extends ContrainteChoco<Boolean>
{
    private Map<Integer, Constraint> constraintPerModule = new HashMap<>();

    public ContrainteChocoModuleDuration(Model model, ConstraintPriority<Boolean> prerequis, List<ModuleChoco> modulesInChoco)
    {
        super(model, prerequis, modulesInChoco);
    }

    @Override
    public Constraint createConstraint(ModuleChoco module)
    {
        Constraint contrainte = constraintPerModule.get(module.getIdModule());

        if (contrainte == null)
        {

            contrainte = model.sum(getModulesInChoco().stream().filter(m2 -> m2.getIdModule() == module.getIdModule())
                                                      .map(m2 -> m2.getModulesWorkingDayDuration()).toArray(IntVar[]::new), "=", module.getModule().getNbHourOfModule());

            constraintPerModule.put(module.getIdModule(), contrainte);
        }

        return contrainte;
    }

    @Override
    public String getConstraintName()
    {
        return language.getString("contrainte.modules.duration");
    }

}
