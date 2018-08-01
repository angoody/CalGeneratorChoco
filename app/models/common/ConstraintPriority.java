package models.common;

public class ConstraintPriority<T>
{
    private Integer priority = -1;
    private T value;

    public ConstraintPriority()
    {

    }

    public ConstraintPriority(Integer priority, T contrainte)
    {
        this.priority = priority;
        this.value = contrainte;
    }

    public Integer getPriority()
    {
        return priority;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }

    public T getValue()
    {
        return value;
    }

    public void setValue(T value)
    {
        this.value = value;
    }
}
