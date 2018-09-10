package solver.contraintes;

import models.common.ConstraintPriority;
import models.common.StudentCompany;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleChoco;
import solver.modelChoco.PeriodeChoco;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContrainteChocoMaxStagiaire extends ContrainteChoco<StudentCompany>
{
    private final List<PeriodeChoco> coursRefuse;

    public ContrainteChocoMaxStagiaire(Model model, ConstraintPriority<StudentCompany> contrainteModel, List<ModuleChoco> modulesInChoco)
    {
        super(model, contrainteModel, modulesInChoco);

        // Liste les cours dont le maximum de stagiaire est déjà atteint
        coursRefuse = contrainteModel.getValue().getListStudentCompany().stream()
                .flatMap(stagiaire -> stagiaire.getListClassees().stream())
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet().stream()
                .filter(c -> c.getValue() >= contrainteModel.getValue().getMaxStudentInTraining())
                .map(Map.Entry::getKey).map(c -> new PeriodeChoco(c.getPeriod())).collect(Collectors.toList());
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
        return String.format("%d stagiaires de la même entreprise", getContraintePriority().getValue().getMaxStudentInTraining());
    }
}
