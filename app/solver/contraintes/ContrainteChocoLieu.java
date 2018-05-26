package solver.contraintes;

import models.input.ConstraintPriority;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleChoco;
import solver.propagator.PropagatorContrainteLieu;

import java.util.*;

public class ContrainteChocoLieu extends ContrainteChoco<Integer>
{

    private List<Integer>                              lieuxPossible = new ArrayList<>();
    private Map<ModuleChoco, PropagatorContrainteLieu> propagators   = new HashMap<>();

    public ContrainteChocoLieu(Model model, ConstraintPriority<Integer> lieu, List<ModuleChoco> modulesInChoco, List<Integer> lieuxPossible)
    {
        super(model, lieu, modulesInChoco);
        this.lieuxPossible = lieuxPossible;
    }

    @Override
    public Constraint createConstraint(ModuleChoco module)
    {
        PropagatorContrainteLieu prop       = new PropagatorContrainteLieu(module.getLieu(), getContraintePriority().getValue(), lieuxPossible);
        Constraint               constraint = new Constraint("Lieu " + module.getIdModule(), prop);
        propagators.put(module, prop);
        return constraint;
    }

    @Override
    public String getConstraintName()
    {
        return language.getString("contrainte.lieu");
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

    // https://stackoverflow.com/questions/46468877/choco-solver-propogation-and-search-strategy-interaction
}
