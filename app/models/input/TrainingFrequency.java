package models.input;

public class TrainingFrequency
{
    private Integer maxWeekInTraining = -1;
    private Integer minWeekInCompany  = -1;

    public Integer getMaxWeekInTraining()
    {
        return maxWeekInTraining;
    }

    public void setMaxWeekInTraining(Integer maxWeekInTraining)
    {
        this.maxWeekInTraining = maxWeekInTraining;
    }

    public Integer getMinWeekInCompany()
    {
        return minWeekInCompany;
    }

    public void setMinWeekInCompany(Integer minWeekInCompany)
    {
        this.minWeekInCompany = minWeekInCompany;
    }
}
