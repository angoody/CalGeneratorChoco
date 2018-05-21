package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Calendrier implements Serializable{

    private List<String> idCours;

    private Contrainte contrainte;

    public Calendrier() {
    }

    public Calendrier(List<String> idCours) {
        this.idCours = idCours;
    }

    public List<String> getCours() {
        return idCours;
    }

    public void setCours(List<String> cours) {
        this.idCours = cours;
    }

    public Contrainte getContrainte() {
        return contrainte;
    }

    public void setContrainte(Contrainte contrainte) {
        this.contrainte = contrainte;
    }
}
