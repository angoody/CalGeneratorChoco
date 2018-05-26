package solver.contraintes;

import models.input.ConstraintPriority;
import models.input.Periode;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleChoco;
import utils.DateTimeHelper;

import java.util.List;

public class ContrainteChocoPeriodeExclusion extends ItemContrainteChocoDecompose<Periode>
{

    private int debut;
    private int fin;

    public ContrainteChocoPeriodeExclusion(Model model, ConstraintPriority<Periode> contrainte, List<ModuleChoco> modulesIncChoco, ListeContrainteChoco parent)
    {
        super(model, contrainte, modulesIncChoco, parent);

        debut = DateTimeHelper.toDays(getContraintePriority().getValue().getDebut());
        fin = DateTimeHelper.toDays(getContraintePriority().getValue().getFin());
    }

    @Override
    public Boolean isAlternateSearch(ModuleChoco module)
    {
        return getIsAlternateSearch() ? module.getFin().getValue() < debut || module.getDebut().getValue() > fin : getIsAlternateSearch();
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
        return String.format(language.getString("contrainte.periodes.exclues"), getContraintePriority().getValue().getDebut(), getContraintePriority().getValue().getFin());
    }


}
