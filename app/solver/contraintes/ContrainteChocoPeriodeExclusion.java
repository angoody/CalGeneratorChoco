package solver.contraintes;

import models.ContrainteDecompose;
import models.Periode;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleChoco;
import solver.modelChoco.PeriodeChoco;

import java.util.List;

public class ContrainteChocoPeriodeExclusion extends ItemContrainteChocoDecompose {

    private final PeriodeChoco periodExclusion;

    public ContrainteChocoPeriodeExclusion(Model model, ContrainteDecompose contrainte, List<ModuleChoco> modulesIncChoco, ListeContrainteChoco parent) {
        super(model, contrainte, modulesIncChoco,  parent);
        this.periodExclusion = new PeriodeChoco((Periode) contrainte);
    }

    @Override
    public Constraint createConstraint(ModuleChoco module) {

        return model.and(
                model.notMember(module.getDebut(), periodExclusion.getDebut(), periodExclusion.getFin()),
                model.notMember(module.getFin(), periodExclusion.getDebut(), periodExclusion.getFin()));
    }

    @Override
    public void enableAlternateSearch(ModuleChoco module) {
        unPost(module);
    }

    @Override
    public void disableAlternateSearch(ModuleChoco module) {
        post(module);
    }
}
