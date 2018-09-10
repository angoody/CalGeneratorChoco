package solver.contraintes;

import models.common.ConstraintPriority;
import models.common.Period;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleChoco;
import utils.DateTimeHelper;

import java.util.List;

public class ContrainteChocoPeriodeInclusion extends ItemContrainteChoco<Period>
{
    private Integer debut;
    private Integer fin;

    public ContrainteChocoPeriodeInclusion(Model model, ConstraintPriority<Period> contrainte, List<ModuleChoco> modulesInChoco, ListeContrainteChoco parent) {
        super(model, contrainte, modulesInChoco, parent);
        debut = DateTimeHelper.toDays(getContraintePriority().getValue().getStart());
        fin = DateTimeHelper.toDays(getContraintePriority().getValue().getEnd());
    }

    @Override
    public Boolean isAlternateSearch(ModuleChoco module) {
        return getIsAlternateSearch() ? debut.compareTo(module.getFin().getValue()) > 0 || fin.compareTo(module.getDebut().getValue()) < 0 : getIsAlternateSearch();
    }

    @Override
    public Constraint createConstraint(ModuleChoco module) {
        return model.and(
                model.member(module.getDebut(), debut, fin),
                model.member(module.getFin(), debut, fin));
    }

    @Override
    public String getConstraintName()
    {
        return String.format("Périodes inclues du %s au %s", getContraintePriority().getValue().getStart(), getContraintePriority().getValue().getEnd());
    }


}
