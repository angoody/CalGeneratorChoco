package models;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Calendrier implements Serializable{

    private List<String> idCours;

    private Set<Contrainte> contraintesResolus;
    private Set<Contrainte> contrainteNonResolu;

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
