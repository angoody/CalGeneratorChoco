package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Contrainte implements Serializable {
    private List<Integer> idLieux = new ArrayList<>();
    private Integer nbHeureAnnuel = 0;
    private Integer dureeMaxFormation = 0;
    private Integer maxSemaineFormation = 0;
    private List<Periode> periodeFormationExclusion = new ArrayList<>();
    private List<Periode> periodeFormationInclusion = new ArrayList<>();
    private Integer maxStagiaireEntrepriseEnFormation = 0;
    private List<Stagiaire> stagiairesEntreprise = new ArrayList<>();
    private List<Stagiaire> stagiairesRecquis = new ArrayList<>();

    public Contrainte() {
    }

    public Contrainte(List<Integer> idLieux, Integer nbHeureAnnuel, Integer dureeMaxFormation, Integer maxSemaineFormation, List<Periode> periodeFormationExclusion, List<Periode> periodeFormationInclusion, Integer maxStagiaireEntrepriseEnFormation, List<Stagiaire> stagiairesEntreprise, List<Stagiaire> stagiairesRecquis) {
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

    public List<Integer> getIdLieux() {
        return idLieux;
    }

    public void setIdLieux(List<Integer> idLieux) {
        this.idLieux = idLieux;
    }

    public Integer getNbHeureAnnuel() {
        return nbHeureAnnuel;
    }

    public void setNbHeureAnnuel(Integer nbHeureAnnuel) {
        this.nbHeureAnnuel = nbHeureAnnuel;
    }

    public Integer getDureeMaxFormation() {
        return dureeMaxFormation;
    }

    public void setDureeMaxFormation(Integer dureeMaxFormation) {
        this.dureeMaxFormation = dureeMaxFormation;
    }

    public Integer getMaxSemaineFormation() {
        return maxSemaineFormation;
    }

    public void setMaxSemaineFormation(Integer maxSemaineFormation) {
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

    public Integer getMaxStagiaireEntrepriseEnFormation() {
        return maxStagiaireEntrepriseEnFormation;
    }

    public void setMaxStagiaireEntrepriseEnFormation(Integer maxStagiaireEntrepriseEnFormation) {
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
