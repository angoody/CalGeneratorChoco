package solver.contraintes;

import models.IntegerContrainte;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleChoco;
import solver.propagator.PropagatorContrainteLieu;

import java.util.*;

public class ContrainteChocoLieu extends ContrainteChoco {

    private IntegerContrainte lieu = new IntegerContrainte();
    private List<Integer> lieuxPossible = new ArrayList<>();
    private Map<ModuleChoco, PropagatorContrainteLieu> propagators = new HashMap<>();

    public ContrainteChocoLieu(Model model, IntegerContrainte lieu, List<ModuleChoco> modulesInChoco, List<Integer> lieuxPossible) {
        super(model, lieu, modulesInChoco);
        this.lieu = lieu;

        this.lieuxPossible = lieuxPossible;
    }



    @Override
    public Constraint createConstraint(ModuleChoco module) {
        PropagatorContrainteLieu prop = new PropagatorContrainteLieu(module.getLieu(), lieu.getValue(), lieuxPossible);
        Constraint constraint = new Constraint("Lieu " + module.getIdModule(), prop );
        propagators.put(module, prop);
        return constraint;
    }

    @Override
    public void enableAlternateSearch(ModuleChoco module) {
        propagators.get(module).searchAternatif((true));
    }

    @Override
    public void disableAlternateSearch(ModuleChoco module) {
        propagators.get(module).searchAternatif((false));
    }

    @Override
    public Boolean isAlternateSearch() {
        return (propagators.values().stream().filter(c -> c.isAternatifSearch()).count() == constraints.values().size());
    }

    // https://stackoverflow.com/questions/46468877/choco-solver-propogation-and-search-strategy-interaction


}
