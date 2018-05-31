package models.output;

import models.input.Cours;

import java.util.ArrayList;
import java.util.List;

public class CoursCalendrier
{
    private final String debut;
    private final String fin;
    private String idCours;
    private List<ConstraintRespected> constraint = new ArrayList<>();


    public CoursCalendrier(Cours cours, List<ConstraintRespected> constraint)
    {
        this.idCours = cours.getIdCours();
        this.debut = cours.getPeriode().getDebut();
        this.fin = cours.getPeriode().getFin();
        this.constraint = constraint;
    }

    public String getIdCours()
    {
        return idCours;
    }

    public void setIdCours(String idCours)
    {
        this.idCours = idCours;
    }


    public List<ConstraintRespected> getConstraint()
    {
        return constraint;
    }

    public void setConstraint(List<ConstraintRespected> constraint)
    {
        this.constraint = constraint;
    }


    public String getDebut()
    {
        return debut;
    }

    public String getFin()
    {
        return fin;
    }
}
