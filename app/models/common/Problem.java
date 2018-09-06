package models.common;

import java.util.ArrayList;
import java.util.List;

public class Problem
{

    private Period periodOfTraining;
    private Constraint constraints = new Constraint();
    private List<Module> moduleOfTraining = new ArrayList<>();

    public Problem()
    {
    }

    public Problem(Period periodOfTraining, Constraint constraints, List<Module> moduleOfTraining)
    {
        this.periodOfTraining = periodOfTraining;
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


}
