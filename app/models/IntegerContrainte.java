package models;

public class IntegerContrainte extends ContrainteDecompose{
    private Integer value = -1;

    public IntegerContrainte(Integer value)
    {
        this.value = value;
    }

    public IntegerContrainte(Integer value, Integer priority)
    {
        this.value = value;
        setPriority(priority);
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
