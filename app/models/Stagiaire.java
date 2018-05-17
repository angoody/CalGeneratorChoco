package models;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Stagiaire extends ContrainteDecompose {
    private List<Cours> cours;
    private Boolean isRespected = false;


    public Stagiaire() {
    }

    public Stagiaire(List<Cours> cours) {
        this.cours = cours;
    }

    public List<Cours> getCours() {
        return cours;
    }

    public void setCours(List<Cours> cours) {
        this.cours = cours;
    }

    public Boolean getRespected() {
        return isRespected;
    }

    public void setRespected(Boolean respected) {
        isRespected = respected;
    }
}
