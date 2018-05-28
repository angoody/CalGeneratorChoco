package models.output;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Calendrier implements Serializable
{

    private List<CoursCalendrier> listCours = new ArrayList<>();

    private List<ConstraintRespected> constraint;

    public List<ConstraintRespected> getConstraint()
    {
        return constraint;
    }

    public List<CoursCalendrier> getCours()
    {
        return listCours;
    }

    public void setConstraint(List<ConstraintRespected> constraints)
    {
        this.constraint = constraints;
    }

    public void setCours(List<CoursCalendrier> cours)
    {
        this.listCours = cours;
    }

    public void addCours(CoursCalendrier cours)
    {
        this.listCours.add(cours);
    }
}
