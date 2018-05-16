package solver;

import models.Contrainte;
import models.Periode;
import models.Stagiaire;

import java.util.List;

public class ContrainteChoco {

    private int idContrainte;
    private Contrainte contrainte;

    public ContrainteChoco(Contrainte contrainte)
    {

        this.contrainte = contrainte;
    }

    public int getIdContrainte() {
        return idContrainte;
    }

    public void setIdContrainte(int idContrainte) {
        this.idContrainte = idContrainte;
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
