package models;

public class ContrainteDecompose {
    private Boolean isRespeced = false;
    private Integer priority = 0;

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
