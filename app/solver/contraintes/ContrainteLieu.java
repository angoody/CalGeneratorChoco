package solver.contraintes;

import models.ContrainteDecompose;
import models.IntegerContrainte;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

import java.util.List;
import java.util.Map;

public class ContrainteLieu extends Contrainte{


    private final List<IntegerContrainte> lieu;

    public ContrainteLieu(Model model, List<IntegerContrainte> lieu) {
        super(model, lieu);
        this.lieu = lieu;
    }



    @Override
    public Constraint createConstraint(IntVar... var) {
        Constraint constraint;
        if (lieu.size() == 1)
        {
            constraint = model.arithm(var[0], "=", lieu.get(0).getValue());
        }
        else
        {
            constraint = model.or(lieu.stream().map(l -> model.arithm( var[0], "=", l.getValue())).toArray(Constraint[]::new));
        }
        return constraint;
    }
}
