package models.common;

import java.util.UUID;

public class ConstraintPriority<T>
{
    private Integer priority = -1;
    private String ID = "";
    private T value;

    public ConstraintPriority()
    {

    }

    public ConstraintPriority(T contrainte)
    {
        this(-1, UUID.randomUUID().toString(), contrainte);
    }

    public ConstraintPriority(Integer priority, T contrainte)
    {
        this(priority, UUID.randomUUID().toString(), contrainte);
    }

    public ConstraintPriority(Integer priority, String ID, T contrainte)
    {
        this.priority = priority;
        this.ID = ID;
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

    public String getID() {
        return ID;
    }

    public void setID(String id) {
        this.ID = id;
    }
}
