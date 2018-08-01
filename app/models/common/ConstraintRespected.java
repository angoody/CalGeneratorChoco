package models.common;

public class ConstraintRespected extends ConstraintPriority
{
    private Boolean isRespected = true;
    private String  name       = "";

    public ConstraintRespected(String name, ConstraintPriority constraintPriority)
    {
        super(constraintPriority.getPriority(), constraintPriority.getValue());
        this.name = name;
    }

    public ConstraintRespected(ConstraintRespected constrainteRespected, Boolean isRespected)
    {
        super(constrainteRespected.getPriority(), constrainteRespected.getValue());
        this.name = constrainteRespected.getName();
        this.isRespected = isRespected;
    }

    public Boolean isRespected()
    {
        return isRespected;
    }

    public void setRespected(Boolean respected)
    {
        isRespected = respected;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
