package solver.contraintes;

import models.input.ConstraintPriority;
import models.input.FrequenceFormation;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleChoco;

import java.util.List;

public class ContrainteChocoPeriodeFormation extends ContrainteChoco<FrequenceFormation>  {


    public ContrainteChocoPeriodeFormation(Model model, ConstraintPriority<FrequenceFormation> contrainteMaxSemaineFormation, List<ModuleChoco> modulesInChoco) {
        super(model, contrainteMaxSemaineFormation, modulesInChoco);
    }

    @Override
    public Constraint createConstraint(ModuleChoco module) {
        return model.and( getModulesInChoco().stream().filter(m -> m != module).map( m ->
                    model.or(
                            model.arithm(
                                    module.getFin(),"-", m.getDebut(),
                                    "<", getContraintePriority().getValue().getMaxSemaineFormation()*7 ),
                            model.arithm(
                                    module.getDebut(),"-", m.getFin(),
                                    ">", getContraintePriority().getValue().getMinSemaineEntreprise() * 7 + 1)
                    )).toArray(Constraint[]::new));

    }

    @Override
    public String getConstraintName()
    {
        return String.format(language.getString("contrainte.frequence.formation"), getContraintePriority().getValue().getMaxSemaineFormation(), getContraintePriority().getValue().getMinSemaineEntreprise());
    }

}
