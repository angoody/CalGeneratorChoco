package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Calendrier implements Serializable{

    private List<String> idCours;

    private List<Contrainte> contraintesResolus = new ArrayList<>();
    private List<Contrainte> contrainteNonResolu = new ArrayList<>();

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

    public List<Contrainte> getContraintesResolus() {
        return contraintesResolus;
    }

    public void setContraintesResolus(List<Contrainte> contraintesResolus) {
        this.contraintesResolus = contraintesResolus;
    }

    public List<Contrainte> getContrainteNonResolu() {
        return contrainteNonResolu;
    }

    public void setContrainteNonResolu(List<Contrainte> contrainteNonResolu) {
        this.contrainteNonResolu = contrainteNonResolu;
    }
}
