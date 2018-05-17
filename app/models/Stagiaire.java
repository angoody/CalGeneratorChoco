package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Stagiaire implements Serializable {
    private List<Cours> cours = new ArrayList<>();


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
}
