package models.common;

import utils.DateTimeHelper;

import java.time.Instant;

public class Period
{

    private String start;
    private String end;

    // Utilisé uniquement pour le ChocoTest, n'est jamais appelé par le from json
    public Period()
    {
        this(DateTimeHelper.toString(Instant.now()), DateTimeHelper.toString(Instant.now()));
    }

    public Period(String start, String end)
    {
        this.start = start;
        this.end = end;
    }

    public String getEnd()
    {
        return end;
    }

    public String getStart()
    {
        return start;
    }

}
