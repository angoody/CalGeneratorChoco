package solver.contraintes;

import models.ContrainteDecompose;
import models.Periode;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;
import solver.PeriodeChoco;

import java.util.List;
import java.util.stream.Collectors;

public class ContraintePeriodeExclusion extends Contrainte {

    private final List<PeriodeChoco> periodExclusion;

    public ContraintePeriodeExclusion(Model model, List<Periode> contrainte) {
        super(model, contrainte);
        this.periodExclusion = contrainte.stream().map(p -> new PeriodeChoco(p)).collect(Collectors.toList());
    }

    @Override
    public Constraint createConstraint(IntVar... var) {

        Constraint contrainte;
        if (periodExclusion.size() == 1)
        {
            contrainte = model.and(
                    model.notMember(var[0], periodExclusion.get(0).getDebut(), periodExclusion.get(0).getFin()),
                    model.notMember(var[1], periodExclusion.get(0).getDebut(), periodExclusion.get(0).getFin()));
        }
        else
        {
            contrainte = model.and(periodExclusion.stream().map(
                    (PeriodeChoco p) -> model.and(
                            model.notMember(var[0], p.getDebut(), p.getFin()),
                            model.notMember(var[1], p.getDebut(), p.getFin())))
                    .toArray(Constraint[]::new));
        }
        return contrainte;
    }
}
