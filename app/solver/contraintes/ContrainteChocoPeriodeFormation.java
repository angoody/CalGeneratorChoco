package solver.contraintes;

import models.ContrainteDecompose;
import models.IntegerContrainte;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.BoolVar;
import solver.modelChoco.ModuleChoco;

import java.util.List;

public class ContrainteChocoPeriodeFormation extends ContrainteChoco  {

    private IntegerContrainte contrainteMaxSemaineFormation;
    private IntegerContrainte contrainteMinSemaineEntreprise;

    public ContrainteChocoPeriodeFormation(Model model, IntegerContrainte contrainteMaxSemaineFormation, IntegerContrainte contrainteMinSemaineEntreprise, List<ModuleChoco> modulesInChoco) {
        super(model, contrainteMaxSemaineFormation, modulesInChoco);
        this.contrainteMaxSemaineFormation = contrainteMaxSemaineFormation;
        this.contrainteMinSemaineEntreprise = contrainteMinSemaineEntreprise;
    }

    @Override
    public Constraint createConstraint(ModuleChoco module) {
        return model.and( getModulesInChoco().stream().filter(m -> m != module).map( m ->
                    model.or(
                            model.arithm(
                                    module.getFin(),"-", m.getDebut(),
                                    "<", contrainteMaxSemaineFormation.getValue()*7 ),
                            model.arithm(
                                    module.getDebut(),"-", m.getFin(),
                                    ">", contrainteMinSemaineEntreprise.getValue()*7 + 1)
                    )).toArray(Constraint[]::new));

    }

}
