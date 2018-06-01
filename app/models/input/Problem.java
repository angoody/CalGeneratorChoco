package models.input;

import java.io.Serializable;
import java.util.List;

public class Problem implements Serializable
{
    private Period  periodOfTraining        = new Period();
    private Integer numberOfCalendarToFound = 5;
    private Constraint   constraints;
    private List<Module> moduleOfTraining;


    public Problem()
    {
    }

    public Problem(Period periodOfTraining, Integer numberOfCalendarToFound, Constraint constraints, List<Module> moduleOfTraining)
    {
        this.periodOfTraining = periodOfTraining;
        this.numberOfCalendarToFound = numberOfCalendarToFound;
        this.moduleOfTraining = moduleOfTraining;
        this.constraints = constraints;
    }

    public Period getPeriodOfTraining()
    {
        return periodOfTraining;
    }

    public void setPeriodOfTraining(Period periodFormation)
    {
        this.periodOfTraining = periodFormation;
    }

    public List<Module> getModuleOfTraining()
    {
        return moduleOfTraining;
    }

    public void setModuleOfTraining(List<Module> moduleOfTraining)
    {
        this.moduleOfTraining = moduleOfTraining;
    }

    public Constraint getConstraints()
    {
        return constraints;
    }

    public void setConstraints(Constraint constraints)
    {
        this.constraints = constraints;
    }

    public Integer getNumberOfCalendarToFound()
    {
        return numberOfCalendarToFound;
    }

    public void setNumberOfCalendarToFound(Integer numberOfCalendarToFound)
    {
        this.numberOfCalendarToFound = numberOfCalendarToFound;
    }
}
