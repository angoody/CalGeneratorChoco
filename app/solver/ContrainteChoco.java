package solver;

import models.Contrainte;
import models.Periode;
import models.Stagiaire;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;
import utils.DateTimeHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ContrainteChoco {

    private int idContrainte;
    private Contrainte contrainte;
    private Model model;

    private List<Integer> listLieuxAutorises;
    private List<PeriodeChoco> periodeExclusion;
    private List<IntVar> lesContraintesDansChoco = new ArrayList<>();
    private List<BoolVar> respectDesContraintes = new ArrayList<>();

    public ContrainteChoco(Contrainte contrainte, Model model)
    {

        this.contrainte = contrainte;
        this.model = model;


        // Nombre de stagiaire pour l'entreprise
        int nbMaxStagiaireEntreprise = contrainte.getMaxStagiaireEntrepriseEnFormation() > 0 ? contrainte.getMaxStagiaireEntrepriseEnFormation() : 0 ;
        int heureAnnuelMax = contrainte.getNbHeureAnnuel() > 0 ? contrainte.getNbHeureAnnuel() : 0 ;
        int semaineMaxEnFormation = contrainte.getMaxSemaineFormation() > 0 ? contrainte.getMaxSemaineFormation() : 0;
        int dureeMaxEnFormation = contrainte.getDureeMaxFormation() > 0 ? contrainte.getDureeMaxFormation() : 0;

        periodeExclusion = contrainte.getPeriodeFormationExclusion().stream().map(p -> new PeriodeChoco(p)).collect(Collectors.toList());
        List<PeriodeChoco> periodeInclusion = contrainte.getPeriodeFormationInclusion().stream().map(p -> new PeriodeChoco(p)).collect(Collectors.toList());

        // les lieux autorisés

        listLieuxAutorises = contrainte.getIdLieux().stream().collect(Collectors.toList());

        // les cours autorisés des stagiaires recquis
        List<PeriodeChoco> coursDesStagiairesRecquis = contrainte.getStagiairesRecquis().stream().flatMap(stagiaire -> stagiaire.getCours().stream().map(cr -> new PeriodeChoco(cr.getPeriode()))).collect(Collectors.toList());

        // les cours dont le nombre de stagiaire a atteint le nombre maximum
        List<PeriodeChoco> coursRefuse = contrainte.getStagiairesEntreprise().stream()
                        .flatMap(stagiaire -> stagiaire.getCours().stream())
                .collect(Collectors.groupingBy( e->e, Collectors.counting())).entrySet().stream()
                .filter(c -> c.getValue() >= nbMaxStagiaireEntreprise ).map(c -> c.getKey()).map(c -> new PeriodeChoco(c.getPeriode())).collect(Collectors.toList());

    }


    public void createContrainteLieu(IntVar lieu) {
        respectDesContraintes.add(model.or(listLieuxAutorises.stream().map(l -> model.arithm( lieu, "=", l)).toArray(Constraint[]::new)).reify());
    }

    public void createContraintePeriodeExclusion(IntVar debut, IntVar fin) {
        respectDesContraintes.add( model.and(periodeExclusion.stream().map(
                        (PeriodeChoco p) -> model.and(
                                model.notMember(debut, p.getDebut(), p.getFin()),
                                model.notMember(fin, p.getDebut(), p.getFin())))
                        .toArray(Constraint[]::new))
                        .reify());

    }

    public Boolean allConstraintRespected()
    {
        return respectDesContraintes.stream().filter(b -> b.getBooleanValue() == ESat.TRUE).count() == respectDesContraintes.size();
    }

    public Contrainte getContrainte() {
        return contrainte;
    }

    public void setContrainte(Contrainte contrainte) {
        this.contrainte = contrainte;
    }

/*
    public List<Integer> getIdLieux() {
        return idLieux;
    }

    public Integer getNbHeureAnnuel() {
        return nbHeureAnnuel;
    }

    public Integer getDureeMaxFormation() {
        return dureeMaxFormation;
    }

    public Integer getMaxSemaineFormation() {
        return maxSemaineFormation;
    }

    public List<Periode> getPeriodeFormationExclusion() {
        return periodeFormationExclusion;
    }

    public List<Periode> getPeriodeFormationInclusion() {
        return periodeFormationInclusion;
    }

    public Integer getMaxStagiaireEntrepriseEnFormation() {
        return maxStagiaireEntrepriseEnFormation;
    }

    public List<Stagiaire> getStagiairesEntreprise() {
        return stagiairesEntreprise;
    }

    public List<Stagiaire> getStagiairesRecquis() {
        return stagiairesRecquis;
    }
*/
}
