package solver.contraintes;

import models.common.ConstraintPriority;
import models.common.Student;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.CoursChocoStagiaire;
import solver.modelChoco.ModuleChoco;

import java.util.List;
import java.util.stream.Collectors;

public class ContrainteChocoStagiaireRecquis extends ItemContrainteChoco<Student>
{
    private final List<CoursChocoStagiaire> cours;

    public ContrainteChocoStagiaireRecquis(Model model, ConstraintPriority<Student> contrainte, List<ModuleChoco> modulesInChoco, ListeContrainteChoco parent)
    {
        super(model, contrainte, modulesInChoco, parent);
        cours = contrainte.getValue().getListClassees().stream().map(c -> new CoursChocoStagiaire(c)).collect(Collectors.toList());
    }

    @Override
    public Constraint createConstraint(ModuleChoco module)
    {
        return model.or(cours.stream()
                                .map(c -> model.and(
                                        model.member(module.getDebut(), c.getDebut(), c.getFin()),
                                        model.member(module.getFin(), c.getDebut(), c.getFin()),
                                        model.arithm(module.getLieu(), "=", c.getLieu())))
                                .toArray(Constraint[]::new));
    }

    @Override
    public String getConstraintName()
    {
        return "Stagiaires recquis";
    }

    @Override
    public Boolean isAlternateSearch(ModuleChoco module)
    {
        return getIsAlternateSearch() ? !(cours.stream().filter(c -> module.getFin().getValue() > c.getDebut() && module.getDebut().getValue() < c.getFin()).count() > 0) : getIsAlternateSearch();
    }
}
