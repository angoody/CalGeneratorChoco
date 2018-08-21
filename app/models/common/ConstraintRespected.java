package models.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ConstraintRespected {
    private String ID = "";
    private String name = "";

    @JsonIgnore
    private Boolean respected = false;
    @JsonIgnore
    private Integer priority = -1;


    public ConstraintRespected(String name, ConstraintPriority constraintPriority) {
        this.ID = constraintPriority.getID();
        this.priority = constraintPriority.getPriority();
        this.name = name;
    }

    public ConstraintRespected(ConstraintRespected constrainteRespected, Boolean isRespected) {
        this.name = constrainteRespected.getName();
        this.respected = isRespected;
        this.priority = constrainteRespected.priority;
        this.ID = constrainteRespected.getID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Boolean getRespected() {
        return respected;
    }

    public void setRespected(Boolean respected) {
        this.respected = respected;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
