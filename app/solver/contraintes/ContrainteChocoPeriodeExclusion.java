package solver.contraintes;

import models.input.ConstraintPriority;
import models.input.Period;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleChoco;
import utils.DateTimeHelper;

import java.util.List;

public class ContrainteChocoPeriodeExclusion extends ItemContrainteChoco<Period>
{

    private Integer debut;
    private Integer fin;

    public ContrainteChocoPeriodeExclusion(Model model, ConstraintPriority<Period> contrainte, List<ModuleChoco> modulesIncChoco, ListeContrainteChoco parent)
    {
        super(model, contrainte, modulesIncChoco, parent);

        debut = DateTimeHelper.toDays(getContraintePriority().getValue().getStart());
        fin = DateTimeHelper.toDays(getContraintePriority().getValue().getEnd());
    }

    @Override
    public Boolean isAlternateSearch(ModuleChoco module)
    {
        return getIsAlternateSearch() ? debut.compareTo(module.getFin().getValue()) >= 0 && fin.compareTo(module.getDebut().getValue()) <= 0 : getIsAlternateSearch();
    }

    @Override
    public Constraint createConstraint(ModuleChoco module)
    {

        return model.and(
                model.notMember(module.getDebut(), debut, fin),
                model.notMember(module.getFin(), debut, fin));
    }

    @Override
    public String getConstraintName()
    {
        return String.format(language.getString("contrainte.periodes.exclues"), getContraintePriority().getValue().getStart(), getContraintePriority().getValue().getEnd());
    }


}
