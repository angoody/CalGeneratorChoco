package solver.contraintes;

import models.input.ConstraintPriority;
import models.input.Period;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleDecomposeChoco;
import utils.DateTimeHelper;

import java.util.List;

public class ContrainteChocoPeriodeInclusion extends ItemContrainteChoco<Period>
{
    private Integer debut;
    private Integer fin;

    public ContrainteChocoPeriodeInclusion(Model model, ConstraintPriority<Period> contrainte, List<ModuleDecomposeChoco> modulesInChoco, ListeContrainteChoco parent) {
        super(model, contrainte, modulesInChoco, parent);
        debut = DateTimeHelper.toDays(getContraintePriority().getValue().getStart());
        fin = DateTimeHelper.toDays(getContraintePriority().getValue().getEnd());
    }

    @Override
    public Boolean isAlternateSearch(ModuleDecomposeChoco module) {
        return getIsAlternateSearch() ? debut.compareTo(module.getFin().getValue()) > 0 || fin.compareTo(module.getDebut().getValue()) < 0 : getIsAlternateSearch();
    }

    @Override
    public Constraint createConstraint(ModuleDecomposeChoco module) {
        return model.and(
                model.member(module.getDebut(), debut, fin),
                model.member(module.getFin(), debut, fin));
    }

    @Override
    public String getConstraintName()
    {
        return String.format(language.getString("contrainte.periodes.inclues"), getContraintePriority().getValue().getStart(), getContraintePriority().getValue().getEnd());
    }


}
