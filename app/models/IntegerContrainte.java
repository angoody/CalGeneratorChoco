package models;

public class IntegerContrainte extends ContrainteDecompose{
    private Integer value = -1;

    public IntegerContrainte()
    {
        super(true,0);
        this.value = -1;
    }

    public IntegerContrainte(Integer value)
    {
        super(true,0);
        this.value = value;
    }

    public IntegerContrainte(Integer value, Integer priority)
    {
        super(true,priority);
        this.value = value;
    }

    public IntegerContrainte(Integer value, Integer priority, Boolean isRespected)
    {
        super(isRespected,priority);
        this.value = value;
    }

    public IntegerContrainte(IntegerContrainte integerContrainte, Boolean isRespected)
    {
        super(isRespected,integerContrainte.getPriority());
        this.value = integerContrainte.getValue();
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
