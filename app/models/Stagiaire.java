package models;

import java.io.Serializable;
import java.util.Set;

public class Stagiaire implements Serializable {
    private Set<Cours> cours;


    public Stagiaire() {
    }

    public Stagiaire(Set<Cours> cours) {
        this.cours = cours;
    }

    public Set<Cours> getCours() {
        return cours;
    }

    public void setCours(Set<Cours> cours) {
        this.cours = cours;
    }
}
