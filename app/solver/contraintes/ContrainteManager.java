package solver.contraintes;

import models.input.Constrainte;
import models.input.Periode;
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
    private int                   oldNbConstraintToFree               = 0;
    private Constrainte           constrainte                         = null;
    private Model                 model                               = null;
    private List<ModuleChoco>     moduleInChoco                       = new ArrayList<>();
    private List<PeriodeChoco>    coursDesStagiairesRecquis           = new ArrayList<>();
    private List<PeriodeChoco>    coursRefuse                         = new ArrayList<>();
    private List<ContrainteChoco> contrainteChocoDecomposeParPriorite = new ArrayList<>();

    private ContrainteChocoLieu                                   contrainteLieu             = null;
    private ContrainteChocoPrerequis                              contraintePrerequis        = null;
    private ContrainteChocoPeriodeFormation                       contraintePeriodeFormation = null;
    private ListeContrainteChoco<ContrainteChocoPeriodeExclusion> contraintePeriodeExclusion = null;
    private ListeContrainteChoco<ContrainteChocoPeriodeInclusion> contraintePeriodeInclusion = null;


    public ContrainteManager(Model model, Probleme probleme, List<ModuleChoco> moduleInChoco) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {

        this.constrainte = probleme.getContrainte();
        this.model = model;
        this.moduleInChoco = moduleInChoco;


        contraintePrerequis = new ContrainteChocoPrerequis(model, constrainte.getPrerequisModule(), moduleInChoco);
        moduleInChoco.forEach(m -> contraintePrerequis.post(m));
        contrainteChocoDecomposeParPriorite.add(contraintePrerequis);

        if (constrainte.getIdLieu().getValue() > -1)
        {

            Map<Integer, Long> lieuxPossibleGroupe = probleme.getModulesFormation().stream()
                    .flatMap(m -> m.getCours().stream())
                    .map(c -> new Integer(c.getLieu()))
                    .filter(l -> l != constrainte.getIdLieu().getValue())
                    .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

            Comparator<Map.Entry<Integer, Long>> valueComparator = new Comparator<Map.Entry<Integer, Long>>()
            {

                @Override
                public int compare(Map.Entry<Integer, Long> e1, Map.Entry<Integer, Long> e2)
                {
                    Long v1 = e1.getValue();
                    Long v2 = e2.getValue();
                    return v1.compareTo(v2);
                }
            };

            List<Map.Entry<Integer, Long>> listOfEntries = new ArrayList<Map.Entry<Integer, Long>>(lieuxPossibleGroupe.entrySet());
            Collections.sort(listOfEntries, valueComparator);

            // sorting HashMap by values using comparator
            Collections.reverse(listOfEntries);


            List<Integer> lieuxPossible = listOfEntries.stream().map(e -> e.getKey()).collect(Collectors.toList());

            contrainteLieu = new ContrainteChocoLieu(
                    model,
                    constrainte.getIdLieu(),
                    moduleInChoco,
                    lieuxPossible);
            moduleInChoco.forEach(m -> contrainteLieu.post(m));
            contrainteChocoDecomposeParPriorite.add(contrainteLieu);

        }

        if (constrainte.getPeriodeFormationExclusion().size() > 0)
        {
            contraintePeriodeExclusion = new ListeContrainteChoco<ContrainteChocoPeriodeExclusion>(model, constrainte.getPeriodeFormationExclusion(), ContrainteChocoPeriodeExclusion.class, moduleInChoco, ListeContrainteChoco.AND);
            contraintePeriodeExclusion.post();
            contrainteChocoDecomposeParPriorite.addAll(contraintePeriodeExclusion.getContraintesChoco());

        }

        if (constrainte.getPeriodeFormationInclusion().size() > 0)
        {
            contraintePeriodeInclusion = new ListeContrainteChoco<ContrainteChocoPeriodeInclusion>(model, constrainte.getPeriodeFormationInclusion(), ContrainteChocoPeriodeInclusion.class, moduleInChoco, ListeContrainteChoco.OR);
            contraintePeriodeInclusion.post();
            contrainteChocoDecomposeParPriorite.addAll(contraintePeriodeInclusion.getContraintesChoco());
        }

        if (constrainte.getFrequenceFormation().getValue().getMaxSemaineFormation() > 0)
        {
            contraintePeriodeFormation = new ContrainteChocoPeriodeFormation(model, constrainte.getFrequenceFormation(), moduleInChoco);
            moduleInChoco.forEach(m -> contraintePeriodeFormation.post(m));
            contrainteChocoDecomposeParPriorite.add(contraintePeriodeFormation);
        }

        // les cours autorisés des stagiaires recquis
        if (constrainte.getStagiairesRecquis().size() > 0)
        {
            coursDesStagiairesRecquis = constrainte.getStagiairesRecquis().stream().flatMap(stagiaire -> stagiaire.getValue().getCours().stream().map(cr -> new PeriodeChoco(cr))).collect(Collectors.toList());
        }

        // les cours dont le nombre de stagiaire a atteint le nombre maximum
        if (constrainte.getMaxStagiaireEntrepriseEnFormation().getValue() > 0)
        {
            coursRefuse = constrainte.getStagiairesEntreprise().stream()
                    .flatMap(stagiaire -> stagiaire.getValue().getCours().stream())
                    .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                    .entrySet().stream()
                    .filter(c -> c.getValue() >= constrainte.getMaxStagiaireEntrepriseEnFormation().getValue())
                    .map(Map.Entry::getKey).map(c -> new PeriodeChoco(c)).collect(Collectors.toList());
        }

        // Les contraintes décomposé retirable sont ajoutée dans la liste des contrainteChocoDecomposeParPriorite décroissante

        Collections.sort(contrainteChocoDecomposeParPriorite, (Comparator.comparing(o -> o.getConstrainteRespected().getPriority())));
        Collections.reverse(contrainteChocoDecomposeParPriorite);

    }

    public List<ConstraintRespected> getContraintes(ModuleChoco module)
    {
        return contrainteChocoDecomposeParPriorite.stream().map(c -> c.calculateRespectOfConstraint(module)).collect(Collectors.toList());
    }


    public List<ConstraintRespected> getContraintes()
    {
        return contrainteChocoDecomposeParPriorite.stream().map(c -> c.calculateRespectOfConstraint()).collect(Collectors.toList());
    }

    private void disableAlternateSearch(int start, int nbModuleToFree, int nbConstraintToFree)
    {

        for (int i = 0; i < nbConstraintToFree; i++)
        {
            if (start + nbModuleToFree <= moduleInChoco.size())
            {
                for (int j = start; j < start + nbModuleToFree; j++)
                {
                    contrainteChocoDecomposeParPriorite.get(i).disableAlternateSearch(moduleInChoco.get(j));
                }
            }
            else
            {
                for (int j = start; j < moduleInChoco.size(); j++)
                {
                    contrainteChocoDecomposeParPriorite.get(i).disableAlternateSearch(moduleInChoco.get(j));
                }
                for (int j = 0; j < (start + nbModuleToFree - moduleInChoco.size()); j++)
                {
                    contrainteChocoDecomposeParPriorite.get(i).disableAlternateSearch(moduleInChoco.get(j));
                }
            }


        }
    }

    public Integer maxAlternateSearch()
    {
        return (moduleInChoco.size()) * (moduleInChoco.size()) * contrainteChocoDecomposeParPriorite.size();

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
                    contrainteChocoDecomposeParPriorite.get(i).enableAlternateSearch(moduleInChoco.get(j));
                }
            }
            else
            {
                for (int j = start; j < moduleInChoco.size(); j++)
                {
                    contrainteChocoDecomposeParPriorite.get(i).enableAlternateSearch(moduleInChoco.get(j));
                }
                for (int j = 0; j < (start + nbModuleToFree - moduleInChoco.size()); j++)
                {
                    contrainteChocoDecomposeParPriorite.get(i).enableAlternateSearch(moduleInChoco.get(j));
                }
            }


        }
        oldStart = start;
        oldNbModuleToFree = nbModuleToFree;
        oldNbConstraintToFree = nbConstraintToFree;

    }

}
