package solver.contraintes;

import models.input.Constraint;
import models.input.Problem;
import models.output.ConstraintRespected;
import org.chocosolver.solver.Model;
import solver.modelChoco.ModuleChoco;
import solver.modelChoco.PeriodeChoco;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class ContrainteManager
{


    private int                   oldStart                  = 0;
    private int                   oldNbModuleToFree         = 0;
    private int                   oldNbConstraintToFree     = 0;
    private Constraint            constraint                = null;
    private Model                 model                     = null;
    private List<ModuleChoco>     moduleInChoco             = new ArrayList<>();
    private List<PeriodeChoco>    coursDesStagiairesRecquis = new ArrayList<>();
    private List<PeriodeChoco>    coursRefuse               = new ArrayList<>();
    private List<ContrainteChoco> contrainteParPriorite     = new ArrayList<>();

    private ContrainteChocoLieu                                   contrainteLieu              = null;
    private ContrainteChocoPrerequis                              contraintePrerequis         = null;
    private ContrainteChocoPeriodeFormation                       contraintePeriodeFormation  = null;
    private ListeContrainteChoco<ContrainteChocoPeriodeExclusion> contraintePeriodeExclusion  = null;
    private ListeContrainteChoco<ContrainteChocoPeriodeInclusion> contraintePeriodeInclusion  = null;
    private ContrainteChocoMaxStagiaire                           contrainteChocoMaxStagiaire = null;


    public ContrainteManager(Model model, Problem problem, List<ModuleChoco> moduleInChoco) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {

        this.constraint = problem.getConstraints();
        this.model = model;
        this.moduleInChoco = moduleInChoco;


        contraintePrerequis = new ContrainteChocoPrerequis(model, constraint.getPrerequisModule(), moduleInChoco);
        moduleInChoco.stream().filter(m -> m.getModuleRequis().size() > 0).forEach(m -> contraintePrerequis.post(m));
        contrainteParPriorite.add(contraintePrerequis);

        if (constraint.getPlace().getValue() > -1)
        {


            contrainteLieu = new ContrainteChocoLieu(
                    model,
                    constraint.getPlace(),
                    moduleInChoco);
            moduleInChoco.forEach(m -> contrainteLieu.post(m));
            contrainteParPriorite.add(contrainteLieu);

        }

        if (constraint.getListPeriodeOfTrainingExclusion().size() > 0)
        {
            contraintePeriodeExclusion = new ListeContrainteChoco<ContrainteChocoPeriodeExclusion>(model, constraint.getListPeriodeOfTrainingExclusion(), ContrainteChocoPeriodeExclusion.class, moduleInChoco, ListeContrainteChoco.AND);
            contraintePeriodeExclusion.post();
            contrainteParPriorite.addAll(contraintePeriodeExclusion.getContraintesChoco());

        }

        if (constraint.getListPeriodeOfTrainingInclusion().size() > 0)
        {
            contraintePeriodeInclusion = new ListeContrainteChoco<ContrainteChocoPeriodeInclusion>(model, constraint.getListPeriodeOfTrainingInclusion(), ContrainteChocoPeriodeInclusion.class, moduleInChoco, ListeContrainteChoco.OR);
            contraintePeriodeInclusion.post();
            contrainteParPriorite.addAll(contraintePeriodeInclusion.getContraintesChoco());
        }

        if (constraint.getTrainingFrequency().getValue().getMaxWeekInTraining() > 0)
        {
            contraintePeriodeFormation = new ContrainteChocoPeriodeFormation(model, constraint.getTrainingFrequency(), moduleInChoco);
            moduleInChoco.forEach(m -> contraintePeriodeFormation.post(m));
            contrainteParPriorite.add(contraintePeriodeFormation);
        }

        // les cours autorisés des stagiaires recquis
        if (constraint.getListStudentRequired().size() > 0)
        {
            //coursDesStagiairesRecquis = constraint.getListStudentRequired().stream().flatMap(stagiaire -> stagiaire.getValue().getListClasses().stream().map(cr -> new PeriodeChoco(cr))).collect(Collectors.toList());
            contraintePeriodeInclusion = new ListeContrainteChoco<ContrainteChocoPeriodeInclusion>(model, constraint.getListPeriodeOfTrainingInclusion(), ContrainteChocoPeriodeInclusion.class, moduleInChoco, ListeContrainteChoco.OR);
            contraintePeriodeInclusion.post();
            contrainteParPriorite.addAll(contraintePeriodeInclusion.getContraintesChoco());
        }

        // les cours dont le nombre de stagiaire a atteint le nombre maximum
        if (constraint.getMaxStudentInTraining().getValue().getMaxStudentInTraining() > 0)

        {
            contrainteChocoMaxStagiaire = new ContrainteChocoMaxStagiaire(model, constraint.getMaxStudentInTraining(), moduleInChoco);
            moduleInChoco.forEach(m -> contrainteChocoMaxStagiaire.post(m));
            contrainteParPriorite.add(contrainteChocoMaxStagiaire);
        }

        // Les contraintes décomposé retirable sont ajoutée dans la liste des contrainteParPriorite décroissante

        Collections.sort(contrainteParPriorite, (Comparator.comparing(o -> o.getConstrainteRespected().getPriority())));
        Collections.reverse(contrainteParPriorite);

    }

    public List<ConstraintRespected> getContraintes(ModuleChoco module)
    {
        return contrainteParPriorite.stream().map(c -> c.calculateRespectOfConstraint(module)).collect(Collectors.toList());
    }


    public List<ConstraintRespected> getContraintes()
    {
        return contrainteParPriorite.stream().map(c -> c.calculateRespectOfConstraint()).collect(Collectors.toList());
    }

    private void disableAlternateSearch(int start, int nbModuleToFree, int nbConstraintToFree)
    {

        for (int i = 0; i < nbConstraintToFree; i++)
        {
            if (start + nbModuleToFree <= moduleInChoco.size())
            {
                for (int j = start; j < start + nbModuleToFree; j++)
                {
                    contrainteParPriorite.get(i).disableAlternateSearch(moduleInChoco.get(j));
                }
            }
            else
            {
                for (int j = start; j < moduleInChoco.size(); j++)
                {
                    contrainteParPriorite.get(i).disableAlternateSearch(moduleInChoco.get(j));
                }
                for (int j = 0; j < (start + nbModuleToFree - moduleInChoco.size()); j++)
                {
                    contrainteParPriorite.get(i).disableAlternateSearch(moduleInChoco.get(j));
                }
            }


        }
    }

    public Integer maxAlternateSearch()
    {
        return (moduleInChoco.size()) * (moduleInChoco.size()) * contrainteParPriorite.size();

    }

    public void alternateSearch(int nbEssai)
    {
        alternateSearch(nbEssai % moduleInChoco.size(), (nbEssai / moduleInChoco.size()) % moduleInChoco.size(), (nbEssai / (moduleInChoco.size() * moduleInChoco.size() + 1)));

        
    }

    public void alternateSearch(int start, int nbModuleToFree, int nbConstraintToFree)
    {
        disableAlternateSearch(oldStart, oldNbModuleToFree, oldNbConstraintToFree);

        for (int i = 0; i < nbConstraintToFree; i++)
        {
            if (start + nbModuleToFree <= moduleInChoco.size())
            {
                for (int j = start; j < start + nbModuleToFree; j++)
                {
                    contrainteParPriorite.get(i).enableAlternateSearch(moduleInChoco.get(j));
                }
            }
            else
            {
                for (int j = start; j < moduleInChoco.size(); j++)
                {
                    contrainteParPriorite.get(i).enableAlternateSearch(moduleInChoco.get(j));
                }
                for (int j = 0; j < (start + nbModuleToFree - moduleInChoco.size()); j++)
                {
                    contrainteParPriorite.get(i).enableAlternateSearch(moduleInChoco.get(j));
                }
            }


        }
        oldStart = start;
        oldNbModuleToFree = nbModuleToFree;
        oldNbConstraintToFree = nbConstraintToFree;

    }

}
