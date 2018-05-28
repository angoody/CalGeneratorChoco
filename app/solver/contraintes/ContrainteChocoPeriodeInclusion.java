package solver.contraintes;

import models.input.ConstraintPriority;
import models.input.Periode;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleChoco;
import utils.DateTimeHelper;

import java.util.List;

public class ContrainteChocoPeriodeInclusion extends ItemContrainteChoco<Periode>
{
    private int debut;
    private int fin;

    public ContrainteChocoPeriodeInclusion(Model model, ConstraintPriority<Periode> contrainte, List<ModuleChoco> modulesInChoco, ListeContrainteChoco parent) {
        super(model, contrainte, modulesInChoco, parent);
        debut = DateTimeHelper.toDays(getContraintePriority().getValue().getDebut());
        fin = DateTimeHelper.toDays(getContraintePriority().getValue().getFin());
    }

    @Override
    public Boolean isAlternateSearch(ModuleChoco module) {
        return getIsAlternateSearch() ? module.getFin().getValue() < debut || module.getDebut().getValue() > fin : getIsAlternateSearch();
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
        return String.format(language.getString("contrainte.periodes.inclues"), getContraintePriority().getValue().getDebut(), getContraintePriority().getValue().getFin());
    }


}
