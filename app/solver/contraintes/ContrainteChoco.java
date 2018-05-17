package solver.contraintes;

import models.Contrainte;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;
import solver.PeriodeChoco;

import java.util.*;
import java.util.stream.Collectors;

public class ContrainteChoco {

    private int idContrainte;
    private Contrainte contrainte;
    private Model model;


    private List<IntVar> lesContraintesDansChoco = new ArrayList<>();
    private List<BoolVar> respectDesContraintes = new ArrayList<>();
    private List<PeriodeChoco> coursDesStagiairesRecquis;
    private List<PeriodeChoco> coursRefuse;
    private ContrainteLieu contrainteLieu = null;
    private ContraintePeriodeExclusion contrainteperiodeExclusion = null;

    public ContrainteChoco( Model model, Contrainte contrainte)
    {

        this.contrainte = contrainte;
        this.model = model;

        if (contrainte.getIdLieux().getValue() > -1)
            contrainteLieu = new ContrainteLieu(model, Arrays.asList(contrainte.getIdLieux()));

        if (contrainte.getPeriodeFormationExclusion().size() > 0){
            contrainteperiodeExclusion = new ContraintePeriodeExclusion(model,contrainte.getPeriodeFormationExclusion() );
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

    }


    public Constraint postContrainteLieu(IntVar lieu) {
        return contrainteLieu.post(lieu);
    }

    public Constraint postContraintePeriodeExclusion(IntVar debut, IntVar fin) {
        return contrainteperiodeExclusion.post(debut, fin);
    }


    public void freeConstraint(IntVar integers) {
        contrainteLieu.unPost(integers);
    }
}
