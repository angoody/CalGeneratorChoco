package models;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Calendrier implements Serializable{

    private List<Cours> cours;

    private Set<Contrainte> contraintesResolus;
    private Set<Contrainte> contrainteNonResolu;

    public Calendrier() {
    }

    public Calendrier(List<Cours> cours) {
        this.cours = cours;
    }

    public List<Cours> getCours() {
        return cours;
    }

    public void setCours(List<Cours> cours) {
        this.cours = cours;
    }

    public Set<Contrainte> getContraintesResolus() {
        return contraintesResolus;
    }

    public void setContraintesResolus(Set<Contrainte> contraintesResolus) {
        this.contraintesResolus = contraintesResolus;
    }

    public Set<Contrainte> getContrainteNonResolu() {
        return contrainteNonResolu;
    }

    public void setContrainteNonResolu(Set<Contrainte> contrainteNonResolu) {
        this.contrainteNonResolu = contrainteNonResolu;
    }
}
