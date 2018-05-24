package models;

public class ContrainteDecompose {
    private Boolean isRespeced  = true;
    private Integer priority    = 0;

    public ContrainteDecompose(Boolean isRespected, Integer priority )
    {
        this.isRespeced = isRespected;
        this.priority = priority;
    }
    public Boolean getRespeced() {
        return isRespeced;
    }

    public void setRespeced(Boolean respeced) {
        isRespeced = respeced;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
