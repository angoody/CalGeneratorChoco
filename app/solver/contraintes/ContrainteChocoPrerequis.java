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


    public ContrainteChocoPrerequis(Model model, ConstraintPriority<Boolean> prerequis, List<ModuleChoco> modulesInChoco)
    {
        super(model, prerequis, modulesInChoco);
    }

    @Override
    public Constraint createConstraint(ModuleChoco module)
    {
        List<Constraint> contraintes = new ArrayList<>();
        if (module.getModuleRequis().size() > 0)
        {
            contraintes.addAll(module.getModuleRequis().stream().map(m -> model.arithm(module.getDebut(), ">", m.getFin())).collect(Collectors.toList()));
        }
        if (module.getModuleFacultatif().size() > 0 )
        {
            contraintes.addAll(module.getModuleFacultatif().stream().map(m -> model.arithm(module.getDebut(), ">", m.getFin())).collect(Collectors.toList()));
        }

        return model.and(contraintes.stream().toArray(Constraint[]::new));
    }

    @Override
    public String getConstraintName()
    {
        return language.getString("contrainte.modules.prerequis");
    }

}
