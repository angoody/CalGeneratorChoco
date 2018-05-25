package solver.contraintes;

import models.ContrainteDecompose;
import models.Periode;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleChoco;
import solver.modelChoco.PeriodeChoco;

import java.util.List;

public class ContrainteChocoPeriodeInclusion extends ItemContrainteChocoDecompose {
    private final PeriodeChoco periodInclusion;

    public ContrainteChocoPeriodeInclusion(Model model, ContrainteDecompose contrainte, List<ModuleChoco> modulesInChoco, ListeContrainteChoco parent) {
        super(model, contrainte, modulesInChoco, parent);
        this.periodInclusion = new PeriodeChoco((Periode) contrainte);
    }

    @Override
    public Constraint createConstraint(ModuleChoco module) {
        return model.and(
                model.member(module.getDebut(), periodInclusion.getDebut(), periodInclusion.getFin()),
                model.member(module.getFin(), periodInclusion.getDebut(), periodInclusion.getFin()));
    }
}
