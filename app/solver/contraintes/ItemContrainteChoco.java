package solver.contraintes;

import models.ContrainteDecompose;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

public abstract class ItemContrainteChoco extends ContrainteChoco {
    private ListeContrainteChoco parent;

    public ItemContrainteChoco(Model model, ContrainteDecompose contrainte, ListeContrainteChoco parent) {
        super(model, contrainte);
        this.parent = parent;
    }

    @Override
    public Constraint post(IntVar... var) {
        parent.addConstraintInEngine(ItemContrainteChoco.this, var);
        return super.post(var);
    }


    @Override
    public Constraint unPost(IntVar... var) {
        parent.removeConstraintInEngine(ItemContrainteChoco.this, var);
        return super.post(var);
    }

}
