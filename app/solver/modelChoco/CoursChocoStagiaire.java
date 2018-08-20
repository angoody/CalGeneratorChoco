package solver.modelChoco;

import models.common.Classes;

public class CoursChocoStagiaire {

    private final Classes      classes;
    private final PeriodeChoco periode ;
    private final int          coursIdentifier;
    private       int          duration;
    private       int          workingDuration;
    private final int          lieu;
    private       int          idCours;

    public CoursChocoStagiaire(Classes classes) {
        this.classes = classes;
        this.idCours = -1;
        this.periode = new PeriodeChoco(classes.getPeriod());

        this.coursIdentifier = (periode.getDebut() + periode.getFin()) / 2;
        this.duration = classes.getRealDuration();
        this.workingDuration = classes.getWorkingDayDuration();
        this.lieu = classes.getIdPlace();
    }

    public int getDebut() {
        return periode.getDebut();
    }

    public int getFin() {
        return periode.getFin();
    }

    public int getCoursIdentifier() {
        return coursIdentifier;
    }

    public int getDuration() {
        return duration;
    }


    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getLieu() {
        return lieu;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }

    public Classes getClasses() {
        return classes;
    }

    public int getIdCours() {
        return idCours;
    }

    public int[] getInt() {
        return new int[] { idCours, periode.getDebut(), periode.getFin(), coursIdentifier, lieu, duration};
    }

    public int getWorkingDuration()
    {
        return workingDuration;
    }

    protected void setWorkingDayDuration(Integer duration) {
        this.workingDuration = duration;
    }

}
