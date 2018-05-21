package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Contrainte implements Serializable {
    private IntegerContrainte idLieux;
    private IntegerContrainte nbHeureAnnuel;
    private IntegerContrainte dureeMaxFormation;
    private IntegerContrainte maxSemaineFormation;
    private List<Periode> periodeFormationExclusion = new ArrayList<>();
    private List<Periode> periodeFormationInclusion = new ArrayList<>();
    private IntegerContrainte maxStagiaireEntrepriseEnFormation;
    private List<Stagiaire> stagiairesEntreprise = new ArrayList<>();
    private List<Stagiaire> stagiairesRecquis = new ArrayList<>();

    public Contrainte() {
    }

    public Contrainte(IntegerContrainte idLieux, IntegerContrainte nbHeureAnnuel, IntegerContrainte dureeMaxFormation, IntegerContrainte maxSemaineFormation, List<Periode> periodeFormationExclusion, List<Periode> periodeFormationInclusion, IntegerContrainte maxStagiaireEntrepriseEnFormation, List<Stagiaire> stagiairesEntreprise, List<Stagiaire> stagiairesRecquis) {
        this.idLieux = idLieux;
        this.nbHeureAnnuel = nbHeureAnnuel;
        this.dureeMaxFormation = dureeMaxFormation;
        this.maxSemaineFormation = maxSemaineFormation;
        this.periodeFormationExclusion = periodeFormationExclusion;
        this.periodeFormationInclusion = periodeFormationInclusion;
        this.maxStagiaireEntrepriseEnFormation = maxStagiaireEntrepriseEnFormation;
        this.stagiairesEntreprise = stagiairesEntreprise;
        this.stagiairesRecquis = stagiairesRecquis;
    }

    public IntegerContrainte getIdLieux() {
        return idLieux;
    }

    public void setIdLieux(IntegerContrainte idLieux) {
        this.idLieux = idLieux;
    }

    public IntegerContrainte getNbHeureAnnuel() {
        return nbHeureAnnuel;
    }

    public void setNbHeureAnnuel(IntegerContrainte nbHeureAnnuel) {
        this.nbHeureAnnuel = nbHeureAnnuel;
    }

    public IntegerContrainte getDureeMaxFormation() {
        return dureeMaxFormation;
    }

    public void setDureeMaxFormation(IntegerContrainte dureeMaxFormation) {
        this.dureeMaxFormation = dureeMaxFormation;
    }

    public IntegerContrainte getMaxSemaineFormation() {
        return maxSemaineFormation;
    }

    public void setMaxSemaineFormation(IntegerContrainte maxSemaineFormation) {
        this.maxSemaineFormation = maxSemaineFormation;
    }

    public List<Periode> getPeriodeFormationExclusion() {
        return periodeFormationExclusion;
    }

    public void setPeriodeFormationExclusion(List<Periode> periodeFormationExclusion) {
        this.periodeFormationExclusion = periodeFormationExclusion;
    }

    public List<Periode> getPeriodeFormationInclusion() {
        return periodeFormationInclusion;
    }

    public void setPeriodeFormationInclusion(List<Periode> periodeFormationInclusion) {
        this.periodeFormationInclusion = periodeFormationInclusion;
    }

    public IntegerContrainte getMaxStagiaireEntrepriseEnFormation() {
        return maxStagiaireEntrepriseEnFormation;
    }

    public void setMaxStagiaireEntrepriseEnFormation(IntegerContrainte maxStagiaireEntrepriseEnFormation) {
        this.maxStagiaireEntrepriseEnFormation = maxStagiaireEntrepriseEnFormation;
    }

    public List<Stagiaire> getStagiairesEntreprise() {
        return stagiairesEntreprise;
    }

    public void setStagiairesEntreprise(List<Stagiaire> stagiairesEntreprise) {
        this.stagiairesEntreprise = stagiairesEntreprise;
    }

    public List<Stagiaire> getStagiairesRecquis() {
        return stagiairesRecquis;
    }

    public void setStagiairesRecquis(List<Stagiaire> stagiairesRecquis) {
        this.stagiairesRecquis = stagiairesRecquis;
    }

}
