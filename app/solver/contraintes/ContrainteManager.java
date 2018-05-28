package solver.contraintes;

import models.input.Constrainte;
import models.input.Probleme;
import models.output.ConstraintRespected;
import org.chocosolver.solver.Model;
import solver.modelChoco.ModuleChoco;
import solver.modelChoco.PeriodeChoco;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class ContrainteManager
{


    private int                   oldStart                            = 0;
    private int                   oldNbModuleToFree                   = 0;
    private int                   oldNbConstraintToFree     = 0;
    private Constrainte           constrainte               = null;
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


    public ContrainteManager(Model model, Probleme probleme, List<ModuleChoco> moduleInChoco) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {

        this.constrainte = probleme.getContrainte();
        this.model = model;
        this.moduleInChoco = moduleInChoco;


        contraintePrerequis = new ContrainteChocoPrerequis(model, constrainte.getPrerequisModule(), moduleInChoco);
        moduleInChoco.forEach(m -> contraintePrerequis.post(m));
        contrainteParPriorite.add(contraintePrerequis);

        if (constrainte.getIdLieu().getValue() > -1)
        {


            contrainteLieu = new ContrainteChocoLieu(
                    model,
                    constrainte.getIdLieu(),
                    moduleInChoco);
            moduleInChoco.forEach(m -> contrainteLieu.post(m));
            contrainteParPriorite.add(contrainteLieu);

        }

        if (constrainte.getPeriodeFormationExclusion().size() > 0)
        {
            contraintePeriodeExclusion = new ListeContrainteChoco<ContrainteChocoPeriodeExclusion>(model, constrainte.getPeriodeFormationExclusion(), ContrainteChocoPeriodeExclusion.class, moduleInChoco, ListeContrainteChoco.AND);
            contraintePeriodeExclusion.post();
            contrainteParPriorite.addAll(contraintePeriodeExclusion.getContraintesChoco());

        }

        if (constrainte.getPeriodeFormationInclusion().size() > 0)
        {
            contraintePeriodeInclusion = new ListeContrainteChoco<ContrainteChocoPeriodeInclusion>(model, constrainte.getPeriodeFormationInclusion(), ContrainteChocoPeriodeInclusion.class, moduleInChoco, ListeContrainteChoco.OR);
            contraintePeriodeInclusion.post();
            contrainteParPriorite.addAll(contraintePeriodeInclusion.getContraintesChoco());
        }

        if (constrainte.getFrequenceFormation().getValue().getMaxSemaineFormation() > 0)
        {
            contraintePeriodeFormation = new ContrainteChocoPeriodeFormation(model, constrainte.getFrequenceFormation(), moduleInChoco);
            moduleInChoco.forEach(m -> contraintePeriodeFormation.post(m));
            contrainteParPriorite.add(contraintePeriodeFormation);
        }

        // les cours autorisés des stagiaires recquis
        if (constrainte.getStagiairesRecquis().size() > 0)
        {
            //coursDesStagiairesRecquis = constrainte.getStagiairesRecquis().stream().flatMap(stagiaire -> stagiaire.getValue().getCours().stream().map(cr -> new PeriodeChoco(cr))).collect(Collectors.toList());
            contraintePeriodeInclusion = new ListeContrainteChoco<ContrainteChocoPeriodeInclusion>(model, constrainte.getPeriodeFormationInclusion(), ContrainteChocoPeriodeInclusion.class, moduleInChoco, ListeContrainteChoco.OR);
            contraintePeriodeInclusion.post();
            contrainteParPriorite.addAll(contraintePeriodeInclusion.getContraintesChoco());
        }

        // les cours dont le nombre de stagiaire a atteint le nombre maximum
        if (constrainte.getMaxStagiaireEnFormation().getValue().getMaxStagiaireEnFormation() > 0)

        {
            contrainteChocoMaxStagiaire = new ContrainteChocoMaxStagiaire(model, constrainte.getMaxStagiaireEnFormation(), moduleInChoco);
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
