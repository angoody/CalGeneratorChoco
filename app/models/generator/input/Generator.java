package models.generator.input;

import models.common.Constraint;
import models.common.Module;
import models.common.Period;
import models.common.Problem;
import models.verify.input.Verify;

import java.io.Serializable;
import java.util.List;

public class Generator extends Problem implements Serializable
{
    private Integer    numberOfCalendarToFound = 5;

    public Generator()
    {
        super();
    }

    public Generator(Problem problem)
    {
        super(problem.getPeriodOfTraining(), problem.getConstraints(), problem.getModuleOfTraining());
    }

    public Generator(Period periodOfTraining, Integer numberOfCalendarToFound, Constraint constraints, List<Module> moduleOfTraining)
    {
        super(periodOfTraining, constraints,moduleOfTraining);
        this.numberOfCalendarToFound = numberOfCalendarToFound;
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
