package models.generator.output;

import models.common.ClassesCalendar;
import models.common.ConstraintRespected;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Calendar implements Serializable
{

    private List<ClassesCalendar> listClasses = new ArrayList<>();

    private List<ConstraintRespected> constraints;

    public List<ConstraintRespected> getConstraints()
    {
        return constraints;
    }

    public List<ClassesCalendar> getCours()
    {
        return listClasses;
    }

    public void setConstraints(List<ConstraintRespected> constraints)
    {
        this.constraints = constraints;
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
