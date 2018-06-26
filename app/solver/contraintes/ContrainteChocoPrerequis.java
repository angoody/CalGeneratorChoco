package solver.contraintes;

import models.input.ConstraintPriority;
import models.output.ConstraintRespected;
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

        /*PropagatorContraintePrerequis prop       = new PropagatorContraintePrerequis(module);
        Constraint                    constraint = new Constraint("Prerequis " + module.getIdModule(), prop);
        propagators.put(module, prop);*/
        return model.and(contraintes.stream().toArray(Constraint[]::new));
    }

    @Override
    public String getConstraintName()
    {
        return language.getString("contrainte.modules.prerequis");
    }


    /*@Override
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
    }*/

}
