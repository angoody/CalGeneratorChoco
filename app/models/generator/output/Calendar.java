package models.generator.output;

import models.common.ClassesCalendar;
import models.common.ConstraintRespected;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Calendar implements Serializable
{

    private List<ClassesCalendar> listClasses = new ArrayList<>();

    private List<ConstraintRespected> constraint;

    public List<ConstraintRespected> getConstraint()
    {
        return constraint;
    }

    public List<ClassesCalendar> getCours()
    {
        return listClasses;
    }

    public void setConstraint(List<ConstraintRespected> constraints)
    {
        this.constraint = constraints;
    }

    public void setCours(List<ClassesCalendar> cours)
    {
        this.listClasses = cours;
    }

    public void addCours(ClassesCalendar cours)
    {
        this.listClasses.add(cours);
    }
}
