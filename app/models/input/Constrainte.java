package models.input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Constrainte implements Serializable
{
    private ConstraintPriority<Integer>            idLieu                            = new ConstraintPriority<>(-1, -1);
    private ConstraintPriority<Integer>            heureAnnuel                       = new ConstraintPriority<>(-1, -1);
    private ConstraintPriority<Integer>            heureAnnuelpriorite               = new ConstraintPriority<>(-1, -1);
    private ConstraintPriority<Integer>            dureeMaxFormation                 = new ConstraintPriority<>(-1, -1);
    private ConstraintPriority<FrequenceFormation> frequenceFormation                = new ConstraintPriority<>(-1, new FrequenceFormation());
    private ConstraintPriority<Integer>            maxStagiaireEntrepriseEnFormation = new ConstraintPriority<>(-1, -1);
    private List<ConstraintPriority<Stagiaire>>    stagiairesEntreprise              = new ArrayList<>();
    private List<ConstraintPriority<Stagiaire>>    stagiairesRecquis                 = new ArrayList<>();
    private List<ConstraintPriority<Periode>>      periodeFormationExclusion         = new ArrayList<>();
    private List<ConstraintPriority<Periode>>      periodeFormationInclusion         = new ArrayList<>();


    public Constrainte()
    {
    }


    public ConstraintPriority<Integer> getIdLieu()
    {
        return idLieu;
    }

    public void setIdLieu(ConstraintPriority<Integer> idLieu)
    {
        this.idLieu = idLieu;
    }

    public ConstraintPriority<Integer> getHeureAnnuel()
    {
        return heureAnnuel;
    }

    public void setHeureAnnuel(ConstraintPriority<Integer> heureAnnuel)
    {
        this.heureAnnuel = heureAnnuel;
    }

    public ConstraintPriority<Integer> getHeureAnnuelpriorite()
    {
        return heureAnnuelpriorite;
    }

    public void setHeureAnnuelpriorite(ConstraintPriority<Integer> heureAnnuelpriorite)
    {
        this.heureAnnuelpriorite = heureAnnuelpriorite;
    }

    public ConstraintPriority<Integer> getDureeMaxFormation()
    {
        return dureeMaxFormation;
    }

    public void setDureeMaxFormation(ConstraintPriority<Integer> dureeMaxFormation)
    {
        this.dureeMaxFormation = dureeMaxFormation;
    }

    public ConstraintPriority<Integer> getMaxStagiaireEntrepriseEnFormation()
    {
        return maxStagiaireEntrepriseEnFormation;
    }

    public void setMaxStagiaireEntrepriseEnFormation(ConstraintPriority<Integer> maxStagiaireEntrepriseEnFormation)
    {
        this.maxStagiaireEntrepriseEnFormation = maxStagiaireEntrepriseEnFormation;
    }


    public ConstraintPriority<FrequenceFormation> getFrequenceFormation()
    {
        return frequenceFormation;
    }

    public void setFrequenceFormation(ConstraintPriority<FrequenceFormation> frequenceFormation)
    {
        this.frequenceFormation = frequenceFormation;
    }

    public List<ConstraintPriority<Stagiaire>> getStagiairesEntreprise()
    {
        return stagiairesEntreprise;
    }

    public void setStagiairesEntreprise(List<ConstraintPriority<Stagiaire>> stagiairesEntreprise)
    {
        this.stagiairesEntreprise = stagiairesEntreprise;
    }

    public List<ConstraintPriority<Stagiaire>> getStagiairesRecquis()
    {
        return stagiairesRecquis;
    }

    public void setStagiairesRecquis(List<ConstraintPriority<Stagiaire>> stagiairesRecquis)
    {
        this.stagiairesRecquis = stagiairesRecquis;
    }

    public List<ConstraintPriority<Periode>> getPeriodeFormationExclusion()
    {
        return periodeFormationExclusion;
    }

    public void setPeriodeFormationExclusion(List<ConstraintPriority<Periode>> periodeFormationExclusion)
    {
        this.periodeFormationExclusion = periodeFormationExclusion;
    }

    public List<ConstraintPriority<Periode>> getPeriodeFormationInclusion()
    {
        return periodeFormationInclusion;
    }

    public void setPeriodeFormationInclusion(List<ConstraintPriority<Periode>> periodeFormationInclusion)
    {
        this.periodeFormationInclusion = periodeFormationInclusion;
    }

    public ConstraintPriority<Boolean> getPrerequisModule()
    {
        return new ConstraintPriority<Boolean>(-1, true);
    }
}
