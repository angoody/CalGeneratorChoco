package solver.contraintes;

import models.input.Constrainte;
import models.input.Module;
import models.input.Probleme;
import models.output.ConstraintRespected;
import org.chocosolver.solver.Model;
import solver.modelChoco.ModuleChoco;
import solver.modelChoco.PeriodeChoco;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.pow;

public class ContrainteManagerV2
{


    private int                   oldStart              = 0;
    private int                   oldNbModuleToFree     = 0;
    private int                   oldNbConstraintToFree = 0;
    private int                   maxCombinaison        = 0;
    private Constrainte           constrainte           = null;
    private List<ModuleChoco>     moduleInChoco         = new ArrayList<>();
    private List<ContrainteChoco> contrainteParPriorite = new ArrayList<>();


    private ContrainteChocoLieu                                   contrainteLieu              = null;
    private ContrainteChocoPrerequis                              contraintePrerequis         = null;
    private ContrainteChocoPeriodeFormation                       contraintePeriodeFormation  = null;
    private ListeContrainteChoco<ContrainteChocoPeriodeExclusion> contraintePeriodeExclusion  = null;
    private ListeContrainteChoco<ContrainteChocoPeriodeInclusion> contraintePeriodeInclusion  = null;
    private ContrainteChocoMaxStagiaire                           contrainteChocoMaxStagiaire = null;


    public ContrainteManagerV2(Model model, Probleme probleme, List<ModuleChoco> moduleInChoco) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {

        this.constrainte = probleme.getContrainte();
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


    public Integer maxAlternateSearch()
    {
        return getMaxCombinaison() * contrainteParPriorite.size();

    }

    private int getMaxCombinaison()
    {
        if (maxCombinaison == 0)
        {
            int combi = moduleInChoco.size();
            while (combi > 0)
            {
                maxCombinaison += pow(2,combi);
                combi--;
            }
        }
        return maxCombinaison + moduleInChoco.size();
    }

    public void alternateSearch(int nbEssai)
    {

        for (int i = 0; i <= (nbEssai / getMaxCombinaison()); i++)
        {
            int finalI = i;
            // On réactive toutes les contraintes
            moduleInChoco.stream().forEach(m -> contrainteParPriorite.get(finalI).disableAlternateSearch(m));

            // on ne désactive que les contraintes correspondant au nombre d'essai
            for (ModuleChoco module : calculCombination(nbEssai % getMaxCombinaison()))
            {
                contrainteParPriorite.get(i).enableAlternateSearch(module);
            }
        }
    }

    private List<ModuleChoco> calculCombination(int nbEssai)
    {

        List<ModuleChoco> modules = new ArrayList<>();

        if (nbEssai < moduleInChoco.size())
        {
            modules.add(moduleInChoco.get(nbEssai));
        }
        else
        {

            // Converti le modulo du nombre d'essai sur le maximum de possibilités en nombre de base 2, ce qui permet d'avoir toutes les combinaisons des modules à désactiver

            String listModules = Integer.toString(nbEssai - moduleInChoco.size(), 2);


            while (listModules.length() < moduleInChoco.size())
            {
                listModules = "0" + listModules;
            }
            System.out.println(listModules);

            for (int i = 0; i < listModules.length(); i++)
            {
                if (listModules.charAt(i) == '1')
                {
                    modules.add(moduleInChoco.get(i));
                }
            }

        }

        return modules;
    }

}
