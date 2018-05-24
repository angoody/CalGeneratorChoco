package solver.contraintes;

import models.ContrainteDecompose;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleChoco;

import java.util.List;

public class ContrainteChocoPeriodeInclusion extends ItemContrainteChocoDecompose {
    public ContrainteChocoPeriodeInclusion(Model model, ContrainteDecompose contrainte, List<ModuleChoco> modulesInChoco, ListeContrainteChoco parent) {
        super(model, contrainte, modulesInChoco, parent);
    }

    @Override
    public Constraint createConstraint(ModuleChoco module) {
        // TODO
        return null;
    }
}
