package solver.contraintes;

import models.Contrainte;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import solver.PeriodeChoco;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ContrainteManager {

    private int idContrainte;
    private Contrainte contrainte;
    private Model model;


    private List<IntVar> lesContraintesDansChoco = new ArrayList<>();
    private List<BoolVar> respectDesContraintes = new ArrayList<>();
    private List<PeriodeChoco> coursDesStagiairesRecquis;
    private List<PeriodeChoco> coursRefuse;
    private ContrainteChocoLieu contrainteLieu = null;
    private ListeContrainteChoco contraintePeriodeExclusion = null;
    private List<ContrainteChoco> contrainteChocoParPriorite = new ArrayList<>();
    private Map<Object, IntVar[]> variableParContrainte = new HashMap<>();
    private int oldStart = 0;
    private int oldNbModuleToFree = 0;
    private int oldNbConstraintToFree = 0;


    public ContrainteManager(Model model, Contrainte contrainte) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        this.contrainte = contrainte;
        this.model = model;

        if (contrainte.getIdLieux().getValue() > -1)
            contrainteLieu = new ContrainteChocoLieu(model, contrainte.getIdLieux());

        if (contrainte.getPeriodeFormationExclusion().size() > 0){
            contraintePeriodeExclusion = new ListeContrainteChoco<ContrainteChocoPeriodeExclusion>(model,contrainte.getPeriodeFormationExclusion(), ContrainteChocoPeriodeExclusion.class, ListeContrainteChoco.AND );
        }

        // les cours autorisés des stagiaires recquis
        if (contrainte.getStagiairesRecquis().size() > 0)
            coursDesStagiairesRecquis = contrainte.getStagiairesRecquis().stream().flatMap(stagiaire -> stagiaire.getCours().stream().map(cr -> new PeriodeChoco(cr.getPeriode()))).collect(Collectors.toList());

        // les cours dont le nombre de stagiaire a atteint le nombre maximum
        if (contrainte.getMaxStagiaireEntrepriseEnFormation().getValue() > 0)
            coursRefuse = contrainte.getStagiairesEntreprise().stream()
                        .flatMap(stagiaire -> stagiaire.getCours().stream())
                .collect(Collectors.groupingBy(e->e, Collectors.counting())).entrySet().stream()
                    .filter(c -> c.getValue() >= contrainte.getMaxStagiaireEntrepriseEnFormation().getValue() ).map(c -> c.getKey()).map(c -> new PeriodeChoco(c.getPeriode())).collect(Collectors.toList());

        contrainteChocoParPriorite.add(contrainteLieu);
        contrainteChocoParPriorite.addAll(contraintePeriodeExclusion.getContraintesChoco());

        Collections.sort(contrainteChocoParPriorite, (Comparator.comparing(o -> o.getContrainteModel().getPriority())));
        Collections.reverse(contrainteChocoParPriorite);

    }


    public Constraint[] postContrainteLieu(IntVar[] lieu) {
        variableParContrainte.put(contrainteLieu, lieu);
        return Arrays.stream(lieu).map(l -> contrainteLieu.post(l)).toArray(Constraint[]::new);
    }

    public Constraint[] postContraintePeriodeExclusion(IntVar[] debut, IntVar[] fin) {
        variableParContrainte.put(contraintePeriodeExclusion, debut);
        return IntStream.range(0, (debut.length - 1)).mapToObj(i -> contraintePeriodeExclusion.post(debut[i], fin[i])).toArray(Constraint[]::new);
    }


    public void freeConstraint(IntVar integers) {
        contrainteLieu.unPost(integers);
    }

    private void respawnConstraint(int start, int nbModuleToFree, int nbConstraintToFree) {
        for (int i = 0; i < nbConstraintToFree; i++)
        {
            IntVar[] var = variableParContrainte.get(contrainteChocoParPriorite.get(i));
            if (start + nbModuleToFree <= var.length) {
                for (int j = start; j < start + nbModuleToFree; j++) {
                    contrainteChocoParPriorite.get(i).post(var[j]);
                }
            }
            else
            {
                for (int j = start; j < var.length; j++) {
                    contrainteChocoParPriorite.get(i).post(var[j]);
                }
                for (int j = 0; j < (start + nbModuleToFree - var.length); j++ )
                {
                    contrainteChocoParPriorite.get(i).post(var[j]);
                }
            }


        }
    }

    public void freeConstraint(int start, int nbModuleToFree, int nbConstraintToFree) {
        respawnConstraint(oldStart,oldNbModuleToFree, oldNbConstraintToFree);

        for (int i = 0; i < nbConstraintToFree; i++)
        {

            IntVar[] var = variableParContrainte.get(contrainteChocoParPriorite.get(i));
            if (start + nbModuleToFree <= var.length) {
                for (int j = start; j < start + nbModuleToFree; j++) {
                    contrainteChocoParPriorite.get(i).unPost(var[j]);
                }
            }
            else
            {
                for (int j = start; j < var.length; j++) {
                    contrainteChocoParPriorite.get(i).unPost(var[j]);
                }
                for (int j = 0; j < (start + nbModuleToFree - var.length); j++ )
                {
                    contrainteChocoParPriorite.get(i).unPost(var[j]);
                }
            }


        }

        oldStart = start;
        oldNbModuleToFree = nbModuleToFree;
        oldNbConstraintToFree = nbConstraintToFree;

    }
}
