package solver.contraintes;

import models.ContrainteDecompose;
import models.Periode;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;
import solver.PeriodeChoco;

import java.util.List;
import java.util.stream.Collectors;

public class ContrainteChocoPeriodeExclusion extends ItemContrainteChoco {

    private final PeriodeChoco periodExclusion;

    public ContrainteChocoPeriodeExclusion(Model model, ContrainteDecompose contrainte, ListeContrainteChoco parent) {
        super(model, contrainte, parent);
        this.periodExclusion = new PeriodeChoco((Periode) contrainte);
    }

    @Override
    public Constraint createConstraint(IntVar... var) {

        Constraint contrainte = model.and(
                model.notMember(var[0], periodExclusion.getDebut(), periodExclusion.getFin()),
                model.notMember(var[1], periodExclusion.getDebut(), periodExclusion.getFin()));

        return contrainte;
    }

    @Override
    public void enableAlternateSearch(IntVar... var) {
        unPost(var);
    }

    @Override
    public void disableAlternateSearch(IntVar... var) {
        post(var);
    }
}
