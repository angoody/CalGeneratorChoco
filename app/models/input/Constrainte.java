package models.input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Constrainte implements Serializable
{
    private ConstraintPriority<Integer>             idLieu                    = new ConstraintPriority<>(-1, -1);
    private ConstraintPriority<Integer>             heureAnnuel               = new ConstraintPriority<>(-1, -1);
    private ConstraintPriority<Integer>             dureeMaxFormation         = new ConstraintPriority<>(-1, -1);
    private ConstraintPriority<FrequenceFormation>  frequenceFormation        = new ConstraintPriority<>(-1, new FrequenceFormation());
    private ConstraintPriority<StagiaireEntreprise> maxStagiaireEnFormation   = new ConstraintPriority<>(-1, new StagiaireEntreprise());
    private List<ConstraintPriority<Stagiaire>>     stagiairesRecquis         = new ArrayList<>();
    private List<ConstraintPriority<Periode>>       periodeFormationExclusion = new ArrayList<>();
    private List<ConstraintPriority<Periode>>       periodeFormationInclusion = new ArrayList<>();


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


    public ConstraintPriority<Integer> getDureeMaxFormation()
    {
        return dureeMaxFormation;
    }

    public void setDureeMaxFormation(ConstraintPriority<Integer> dureeMaxFormation)
    {
        this.dureeMaxFormation = dureeMaxFormation;
    }


    public ConstraintPriority<FrequenceFormation> getFrequenceFormation()
    {
        return frequenceFormation;
    }

    public void setFrequenceFormation(ConstraintPriority<FrequenceFormation> frequenceFormation)
    {
        this.frequenceFormation = frequenceFormation;
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

    public ConstraintPriority<StagiaireEntreprise> getMaxStagiaireEnFormation()
    {
        return maxStagiaireEnFormation;
    }

    public void setMaxStagiaireEnFormation(ConstraintPriority<StagiaireEntreprise> maxStagiaireEnFormation)
    {
        this.maxStagiaireEnFormation = maxStagiaireEnFormation;
    }
}
