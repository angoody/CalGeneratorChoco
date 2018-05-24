package solver.contraintes;

import models.*;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import solver.modelChoco.ModuleChoco;
import solver.modelChoco.PeriodeChoco;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class ContrainteManager {

    private int idContrainte;
    private Contrainte contrainte;
    private Model model;
    private List<ModuleChoco> moduleInChoco;


    private List<IntVar> lesContraintesDansChoco = new ArrayList<>();
    private List<BoolVar> respectDesContraintes = new ArrayList<>();
    private List<PeriodeChoco> coursDesStagiairesRecquis;
    private List<PeriodeChoco> coursRefuse;
    private ContrainteChocoLieu contrainteLieu = null;
    private ContrainteChocoPrerequis contraintePrerequis = null;
    private ListeContrainteChoco<ContrainteChocoPeriodeExclusion> contraintePeriodeExclusion = null;
    private List<ContrainteChoco> contrainteChocoDecomposeParPriorite = new ArrayList<>();
    private int oldStart = 0;
    private int oldNbModuleToFree = 0;
    private int oldNbConstraintToFree = 0;
    private ListeContrainteChoco<ContrainteChocoPeriodeInclusion> contraintePeriodeInclusion;


    public ContrainteManager(Model model, Probleme probleme, List<ModuleChoco> moduleInChoco) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        this.contrainte = probleme.getContrainte();
        this.model = model;
        this.moduleInChoco = moduleInChoco;


        contraintePrerequis = new ContrainteChocoPrerequis(model, contrainte.getPrerequisModule(), moduleInChoco);

        if (contrainte.getIdLieux().getValue() > -1) {

            Map<Integer, Long> lieuxPossibleGroupe = probleme.getModulesFormation().stream()
                    .flatMap(m -> m.getCours().stream())
                    .map(c -> new Integer(c.getLieu()))
                    .filter(l -> l != contrainte.getIdLieux().getValue())
                    .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

            Comparator<Map.Entry<Integer, Long>> valueComparator = new Comparator<Map.Entry<Integer, Long>>() {

                @Override
                public int compare(Map.Entry<Integer, Long> e1, Map.Entry<Integer, Long> e2) {
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
                    contrainte.getIdLieux(),
                    moduleInChoco,
                    lieuxPossible);
            moduleInChoco.forEach(m -> contrainteLieu.post(m));

        }

        if (contrainte.getPeriodeFormationExclusion().size() > 0){
            contraintePeriodeExclusion = new ListeContrainteChoco<ContrainteChocoPeriodeExclusion>(model,contrainte.getPeriodeFormationExclusion(), ContrainteChocoPeriodeExclusion.class, moduleInChoco, ListeContrainteChoco.AND );
            moduleInChoco.forEach(m -> contraintePeriodeExclusion.post(m));
        }

        if (contrainte.getPeriodeFormationInclusion().size() > 0) {
            contraintePeriodeInclusion = new ListeContrainteChoco<ContrainteChocoPeriodeInclusion>(model, contrainte.getPeriodeFormationInclusion(), ContrainteChocoPeriodeInclusion.class, moduleInChoco, ListeContrainteChoco.OR);
        }

        // les cours autorisés des stagiaires recquis
        if (contrainte.getStagiairesRecquis().size() > 0)
            coursDesStagiairesRecquis = contrainte.getStagiairesRecquis().stream().flatMap(stagiaire -> stagiaire.getCours().stream().map(cr -> new PeriodeChoco(cr.getPeriode()))).collect(Collectors.toList());

        // les cours dont le nombre de stagiaire a atteint le nombre maximum
        if (contrainte.getMaxStagiaireEntrepriseEnFormation().getValue() > 0)
            coursRefuse = contrainte.getStagiairesEntreprise().stream()
                    .flatMap(stagiaire -> stagiaire.getCours().stream())
                    .collect(Collectors.groupingBy(e->e, Collectors.counting()))
                    .entrySet().stream()
                    .filter(c -> c.getValue() >= contrainte.getMaxStagiaireEntrepriseEnFormation().getValue() )
                    .map(Map.Entry::getKey).map(c -> new PeriodeChoco(c.getPeriode())).collect(Collectors.toList());

        contrainteChocoDecomposeParPriorite.add(contrainteLieu);
        contrainteChocoDecomposeParPriorite.addAll(contraintePeriodeExclusion.getContraintesChoco());

        Collections.sort(contrainteChocoDecomposeParPriorite, (Comparator.comparing(o -> o.getContrainteModel().getPriority())));
        Collections.reverse(contrainteChocoDecomposeParPriorite);

    }

    public Contrainte getContraintes()
    {
        Contrainte contrainteRespecte = new Contrainte();
        contrainteRespecte.setIdLieux(new IntegerContrainte(this.contrainte.getIdLieux(), contrainteLieu.isAlternateSearch()));
        contrainteRespecte.setDureeMaxFormation(new IntegerContrainte(this.contrainte.getDureeMaxFormation(), true));
        contrainteRespecte.setMaxSemaineFormation(new IntegerContrainte(this.contrainte.getMaxSemaineFormation(), true));
        contrainteRespecte.setMaxStagiaireEntrepriseEnFormation(new IntegerContrainte(this.contrainte.getMaxStagiaireEntrepriseEnFormation(), true));
        contrainteRespecte.setNbHeureAnnuel(new IntegerContrainte(this.contrainte.getNbHeureAnnuel(), true));
        contrainteRespecte.setPrerequisModule ( new ContrainteDecompose(contraintePrerequis.isAlternateSearch(), this.contrainte.getPrerequisModule().getPriority()));
        if (contraintePeriodeExclusion != null)
            contraintePeriodeExclusion.getContraintesChoco().stream()
                .forEach( c -> contrainteRespecte.getPeriodeFormationExclusion().add(new Periode((Periode) c.getContrainteModel(), c.isAlternateSearch())));
        else
            contrainteRespecte.setPeriodeFormationExclusion(this.contrainte.getPeriodeFormationExclusion());

        if (contraintePeriodeInclusion != null)
            contraintePeriodeInclusion.getContraintesChoco().stream()
                .forEach( c -> contrainteRespecte.getPeriodeFormationInclusion().add(new Periode((Periode) c.getContrainteModel(), c.isAlternateSearch())));
        else
            contrainteRespecte.setPeriodeFormationInclusion(this.contrainte.getPeriodeFormationInclusion());

        // TODO stagiaires
        return contrainteRespecte;
    }

    private void respawnConstraint(int start, int nbModuleToFree, int nbConstraintToFree) {
        for (int i = 0; i < nbConstraintToFree; i++)
        {
            if (start + nbModuleToFree <= moduleInChoco.size()) {
                for (int j = start; j < start + nbModuleToFree; j++) {
                    contrainteChocoDecomposeParPriorite.get(i).post(moduleInChoco.get(j));
                }
            }
            else
            {
                for (int j = start; j < moduleInChoco.size(); j++) {
                    contrainteChocoDecomposeParPriorite.get(i).post(moduleInChoco.get(j));
                }
                for (int j = 0; j < (start + nbModuleToFree - moduleInChoco.size()); j++ )
                {
                    contrainteChocoDecomposeParPriorite.get(i).post(moduleInChoco.get(j));
                }
            }


        }
    }

    private void disableAlternateSearch(int start, int nbModuleToFree, int nbConstraintToFree) {

        for (int i = 0; i < nbConstraintToFree; i++)
        {
            if (start + nbModuleToFree <= moduleInChoco.size()) {
                for (int j = start; j < start + nbModuleToFree; j++) {
                    contrainteChocoDecomposeParPriorite.get(i).disableAlternateSearch(moduleInChoco.get(j));
                }
            }
            else
            {
                for (int j = start; j < moduleInChoco.size(); j++) {
                    contrainteChocoDecomposeParPriorite.get(i).disableAlternateSearch(moduleInChoco.get(j));
                }
                for (int j = 0; j < (start + nbModuleToFree - moduleInChoco.size()); j++ )
                {
                    contrainteChocoDecomposeParPriorite.get(i).disableAlternateSearch(moduleInChoco.get(j));
                }
            }


        }
    }

    public void alternateSearch(int nbEssai) {


    }

    public void alternateSearch(int start, int nbModuleToFree, int nbConstraintToFree) {
        disableAlternateSearch(oldStart,oldNbModuleToFree, oldNbConstraintToFree);

        for (int i = 0; i < nbConstraintToFree; i++)
        {
            if (start + nbModuleToFree <= moduleInChoco.size()) {
                for (int j = start; j < start + nbModuleToFree; j++) {
                    contrainteChocoDecomposeParPriorite.get(i).enableAlternateSearch(moduleInChoco.get(j));
                }
            }
            else
            {
                for (int j = start; j < moduleInChoco.size(); j++) {
                    contrainteChocoDecomposeParPriorite.get(i).enableAlternateSearch(moduleInChoco.get(j));
                }
                for (int j = 0; j < (start + nbModuleToFree - moduleInChoco.size()); j++ )
                {
                    contrainteChocoDecomposeParPriorite.get(i).enableAlternateSearch(moduleInChoco.get(j));
                }
            }


        }
        oldStart = start;
        oldNbModuleToFree = nbModuleToFree;
        oldNbConstraintToFree = nbConstraintToFree;

    }

    public void freeConstraint(int start, int nbModuleToFree, int nbConstraintToFree) {
        respawnConstraint(oldStart,oldNbModuleToFree, oldNbConstraintToFree);

        for (int i = 0; i < nbConstraintToFree; i++)
        {
            if (start + nbModuleToFree <= moduleInChoco.size()) {
                for (int j = start; j < start + nbModuleToFree; j++) {
                    contrainteChocoDecomposeParPriorite.get(i).unPost(moduleInChoco.get(j));
                }
            }
            else
            {
                for (int j = start; j < moduleInChoco.size(); j++) {
                    contrainteChocoDecomposeParPriorite.get(i).unPost(moduleInChoco.get(j));
                }
                for (int j = 0; j < (start + nbModuleToFree - moduleInChoco.size()); j++ )
                {
                    contrainteChocoDecomposeParPriorite.get(i).unPost(moduleInChoco.get(j));
                }
            }


        }

        oldStart = start;
        oldNbModuleToFree = nbModuleToFree;
        oldNbConstraintToFree = nbConstraintToFree;

    }
}
