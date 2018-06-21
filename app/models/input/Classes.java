package models.input;

import utils.DateTimeHelper;

public class Classes
{
    private String  idClasses;
    private Integer IdPlace;
    private Period  period;
    private Integer realDuration;

    public Classes()
    {
    }

    public Classes(Period period, String idClasses, Integer place, Integer realDuration)
    {
        this.period = period;
        this.idClasses = idClasses;
        this.IdPlace = place;
        this.realDuration = realDuration;
    }

    public Period getPeriod()
    {
        return period;
    }

    public void setPeriod(Period period)
    {
        this.period = period;
    }

    public String getIdClasses()
    {
        return idClasses;
    }

    public void setIdClasses(String idClasses)
    {
        this.idClasses = idClasses;
    }

    public Integer getIdPlace()
    {
        return IdPlace;
    }

    public void setIdPlace(Integer idPlace)
    {
        this.IdPlace = idPlace;
    }

    public Integer getRealDuration()
    {
        return realDuration;
    }

    public Integer getWorkingDayDuration()
    {
        Integer workingHour = DateTimeHelper.toHourBetweenDateWithoutHolydays(period);

        return workingHour > realDuration ? realDuration : workingHour;
    }

    public void setRealDuration(Integer realDuration)
    {
        this.realDuration = realDuration;
    }

    //  private Salle codeSalle;
    /*

    case class Classes (
	                 debut: String,
	                 fin: String,
	                 dureeReelleEnHeures: Int,
	                 codePromotion: String,
	                 idClasses: String,
	                 prixPublicAffecte: Float,
	                 idModule: Int,
	                 libelleCours: String,
	                 dureePrevueEnHeures: Int,
	                 dateAdefinir: Boolean,
	                 codeSalle: String,
	                 codeFormateur: Int,
	                 codeLieu: Int
                 )
     */
}
