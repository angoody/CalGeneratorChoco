package solver.contraintes;

import models.common.ConstraintPriority;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleChoco;
import solver.propagator.PropagatorContraintePrerequis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContrainteChocoPrerequis extends ContrainteChoco<Boolean>
{
    private Map<ModuleChoco, PropagatorContraintePrerequis> propagators = new HashMap<>();
    private Boolean optional;


    public ContrainteChocoPrerequis(Model model, ConstraintPriority<Boolean> prerequis, List<ModuleChoco> modulesInChoco, Boolean optional)
    {
        super(model, prerequis, modulesInChoco);
        this.optional = optional;
    }

    @Override
    public Constraint createConstraint(ModuleChoco module)
    {
        List<Constraint> contraintes = new ArrayList<>();
        if (!optional && module.getModuleRequis().size() > 0)
        {
            contraintes.addAll(module.getModuleRequis().stream().map(m -> model.arithm(module.getDebut(), ">", m.getFin())).collect(Collectors.toList()));
        }
        if (optional && module.getModuleFacultatif().size() > 0 )
        {
            contraintes.addAll(module.getModuleFacultatif().stream().map(m -> model.arithm(module.getDebut(), ">", m.getFin())).collect(Collectors.toList()));
        }
        if (contraintes.size() == 0)
            return model.trueConstraint();
        else
            return model.and(contraintes.stream().toArray(Constraint[]::new));
    }

    @Override
    public String getConstraintName()
    {
        return "Modules prérequis";
    }

    @Override
    public Boolean isAlternateSearch(ModuleChoco module) {
        if (!optional)
            return getModulesInChoco().stream().filter(m -> m.getDebut().getValue() > module.getFin().getValue()).anyMatch(m -> module.getModuleRequis().contains(m));
        else
            return getModulesInChoco().stream().filter(m -> m.getDebut().getValue() > module.getFin().getValue()).anyMatch(m -> module.getModuleFacultatif().contains(m));
    }
}
