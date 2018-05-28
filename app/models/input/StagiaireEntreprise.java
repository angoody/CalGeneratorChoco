package models.input;

import java.util.ArrayList;
import java.util.List;

public class StagiaireEntreprise
{
    private Integer         maxStagiaireEnFormation = -1;
    private List<Stagiaire> stagiaireEntreprise     = new ArrayList<>();

    public Integer getMaxStagiaireEnFormation()
    {
        return maxStagiaireEnFormation;
    }

    public void setMaxStagiaireEnFormation(Integer maxStagiaireEnFormation)
    {
        this.maxStagiaireEnFormation = maxStagiaireEnFormation;
    }

    public List<Stagiaire> getStagiaireEntreprise()
    {
        return stagiaireEntreprise;
    }

    public void setStagiaireEntreprise(List<Stagiaire> stagiaireEntreprise)
    {
        this.stagiaireEntreprise = stagiaireEntreprise;
    }
}
