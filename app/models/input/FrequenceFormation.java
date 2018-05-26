package models.input;

public class FrequenceFormation
{
    private Integer maxSemaineFormation  = -1;
    private Integer minSemaineEntreprise = -1;

    public Integer getMaxSemaineFormation()
    {
        return maxSemaineFormation;
    }

    public void setMaxSemaineFormation(Integer maxSemaineFormation)
    {
        this.maxSemaineFormation = maxSemaineFormation;
    }

    public Integer getMinSemaineEntreprise()
    {
        return minSemaineEntreprise;
    }

    public void setMinSemaineEntreprise(Integer minSemaineEntreprise)
    {
        this.minSemaineEntreprise = minSemaineEntreprise;
    }
}
