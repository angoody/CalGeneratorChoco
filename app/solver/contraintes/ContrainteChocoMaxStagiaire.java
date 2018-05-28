package solver.contraintes;

import models.input.ConstraintPriority;
import models.input.Periode;
import models.input.StagiaireEntreprise;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.CoursChoco;
import solver.modelChoco.ModuleChoco;
import solver.modelChoco.PeriodeChoco;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContrainteChocoMaxStagiaire extends ContrainteChoco<StagiaireEntreprise>
{
    private final List<PeriodeChoco> coursRefuse;

    public ContrainteChocoMaxStagiaire(Model model, ConstraintPriority<StagiaireEntreprise> contrainteModel, List<ModuleChoco> modulesInChoco)
    {
        super(model, contrainteModel, modulesInChoco);

        // Liste les cours dont le maximum de stagiaire est déjà atteint
        coursRefuse = contrainteModel.getValue().getStagiaireEntreprise().stream()
                .flatMap(stagiaire -> stagiaire.getCours().stream())
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet().stream()
                .filter(c -> c.getValue() >= contrainteModel.getValue().getMaxStagiaireEnFormation())
                .map(Map.Entry::getKey).map(c -> new PeriodeChoco(c.getPeriode())).collect(Collectors.toList());
    }

    @Override
    public Constraint createConstraint(ModuleChoco module)
    {
        return model.and(coursRefuse.stream()
                                 .map(c -> model.and(
                                         model.notMember(module.getDebut(), c.getDebut(), c.getFin()),
                                         model.notMember(module.getFin(), c.getDebut(), c.getFin())))
                                 .toArray(Constraint[]::new));
    }

    @Override
    public String getConstraintName()
    {
        return String.format(language.getString("contrainte.stagiaires.entreprise"), getContraintePriority().getValue().getMaxStagiaireEnFormation());
    }
}
