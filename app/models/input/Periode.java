package models.input;

import utils.DateTimeHelper;

import java.time.Instant;

public class Periode
{

    private String  debut;
    private String  fin;

    // Utilisé uniquement pour le test, n'est jamais appelé par le from json
    public Periode()
    {
        this(DateTimeHelper.format(Instant.now()), DateTimeHelper.format(Instant.now()));
    }

    public Periode(String debut, String fin)
    {
        this.debut = debut;
        this.fin = fin;
    }

    public String getFin()
    {
        return fin;
    }

    public String getDebut()
    {
        return debut;
    }

    public Instant getInstantDebut()
    {
        return DateTimeHelper.format(this.debut, "yyyy-MM-dd");
    }

    public Instant getInstantFin()
    {
        return DateTimeHelper.format(this.fin, "yyyy-MM-dd");
    }

}
