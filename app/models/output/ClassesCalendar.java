package models.output;

import solver.modelChoco.CoursChoco;

import java.util.ArrayList;
import java.util.List;

public class ClassesCalendar
{
    private final String start;
    private final String end;
    private final int    idModule;
    private String       idClasses;
    private List<ConstraintRespected> constraints = new ArrayList<>();


    public ClassesCalendar(CoursChoco cours, List<ConstraintRespected> constraints)
    {
        this.idClasses = cours.getClasses().getIdClasses();
        this.idModule = cours.getIdModule();
        this.start = cours.getClasses().getPeriod().getStart();
        this.end = cours.getClasses().getPeriod().getEnd();
        this.constraints = constraints;
    }

    public String getIdClasses()
    {
        return idClasses;
    }

    public void setIdClasses(String idClasses)
    {
        this.idClasses = idClasses;
    }


    public List<ConstraintRespected> getConstraints()
    {
        return constraints;
    }

    public void setConstraints(List<ConstraintRespected> constraints)
    {
        this.constraints = constraints;
    }


    public String getStart()
    {
        return start;
    }

    public String getEnd()
    {
        return end;
    }

    public int getIdModule()
    {
        return idModule;
    }
}
