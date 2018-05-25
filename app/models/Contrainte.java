package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Contrainte implements Serializable {
    private IntegerContrainte idLieux                           = new IntegerContrainte();
    private IntegerContrainte nbHeureAnnuel                     = new IntegerContrainte();
    private IntegerContrainte dureeMaxFormation                 = new IntegerContrainte();
    private IntegerContrainte maxSemaineFormation               = new IntegerContrainte();
    private IntegerContrainte minSemaineEntreprise              = new IntegerContrainte();
    private List<Periode> periodeFormationExclusion             = new ArrayList<>();
    private List<Periode> periodeFormationInclusion             = new ArrayList<>();
    private IntegerContrainte maxStagiaireEntrepriseEnFormation = new IntegerContrainte();
    private List<Stagiaire> stagiairesEntreprise                = new ArrayList<>();
    private List<Stagiaire> stagiairesRecquis                   = new ArrayList<>();
    private ContrainteDecompose prerequisModule                 = new ContrainteDecompose(true, 1);

    public Contrainte() {
    }

    public Contrainte(IntegerContrainte idLieux, IntegerContrainte nbHeureAnnuel, IntegerContrainte dureeMaxFormation, IntegerContrainte maxSemaineFormation, List<Periode> periodeFormationExclusion, List<Periode> periodeFormationInclusion, IntegerContrainte maxStagiaireEntrepriseEnFormation, List<Stagiaire> stagiairesEntreprise, List<Stagiaire> stagiairesRecquis) {
        this.idLieux                            = idLieux;
        this.nbHeureAnnuel                      = nbHeureAnnuel;
        this.dureeMaxFormation                  = dureeMaxFormation;
        this.maxSemaineFormation                = maxSemaineFormation;
        this.periodeFormationExclusion          = periodeFormationExclusion;
        this.periodeFormationInclusion          = periodeFormationInclusion;
        this.maxStagiaireEntrepriseEnFormation  = maxStagiaireEntrepriseEnFormation;
        this.stagiairesEntreprise               = stagiairesEntreprise;
        this.stagiairesRecquis                  = stagiairesRecquis;
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

    public ContrainteDecompose getPrerequisModule() {
        return prerequisModule;
    }

    public void setPrerequisModule(ContrainteDecompose prerequisModule) {
        this.prerequisModule = prerequisModule;
    }

    public IntegerContrainte getMinSemaineEntreprise() {
        return minSemaineEntreprise;
    }

    public void setMinSemaineEntreprise(IntegerContrainte minSemaineEntreprise) {
        this.minSemaineEntreprise = minSemaineEntreprise;
    }
}
