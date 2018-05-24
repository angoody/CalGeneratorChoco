package solver.contraintes;

import models.ContrainteDecompose;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleChoco;

import java.util.List;

public abstract class ItemContrainteChocoDecompose extends ContrainteChoco {
    private ListeContrainteChoco parent;

    public ItemContrainteChocoDecompose(Model model, ContrainteDecompose contrainte, List<ModuleChoco> modulesInChoco, ListeContrainteChoco parent) {
        super(model, contrainte, modulesInChoco);
        this.parent = parent;
    }

    @Override
    public Constraint post(ModuleChoco module) {
        parent.addConstraintInEngine(ItemContrainteChocoDecompose.this, module);
        return super.post(module);
    }


    @Override
    public Constraint unPost(ModuleChoco module) {
        parent.removeConstraintInEngine(ItemContrainteChocoDecompose.this, module);
        return super.post(module);
    }

}
