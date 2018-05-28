package solver.contraintes;

import models.input.ConstraintPriority;
import models.input.Cours;
import models.input.Stagiaire;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.CoursChoco;
import solver.modelChoco.ModuleChoco;

import java.util.List;
import java.util.stream.Collectors;

public class ContrainteChocoStagiaireRecquis extends ItemContrainteChoco<Stagiaire>
{
    private final List<CoursChoco> cours;

    public ContrainteChocoStagiaireRecquis(Model model, ConstraintPriority<Stagiaire> contrainte, List<ModuleChoco> modulesInChoco, ListeContrainteChoco parent)
    {
        super(model, contrainte, modulesInChoco, parent);
        cours = contrainte.getValue().getCours().stream().map(c -> new CoursChoco(c)).collect(Collectors.toList());
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
        return language.getString("contrainte.stagiaires.recquis");
    }

    @Override
    public Boolean isAlternateSearch(ModuleChoco module)
    {
        return getIsAlternateSearch() ? !(cours.stream().filter(c -> module.getFin().getValue() > c.getDebut() && module.getDebut().getValue() < c.getFin()).count() > 0) : getIsAlternateSearch();
    }
}
