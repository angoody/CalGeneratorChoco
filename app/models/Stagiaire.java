package models;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Stagiaire extends ContrainteDecompose {
    private List<Cours> cours;
    private Integer idStagiaire;
    private Boolean isRespected = false;


    public Stagiaire() {
        super(true, 0);
    }

    public Stagiaire(Stagiaire stagiaire, Boolean isRespected) {
        super(isRespected, stagiaire.getPriority());
        this.cours          = stagiaire.getCours();
        this.idStagiaire    = stagiaire.getIdStagiaire();
    }

    public Stagiaire(Integer idStagiaire, List<Cours> cours) {
        super(true, 0);
        this.cours = cours;
        this.idStagiaire = idStagiaire;
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

    public Integer getIdStagiaire() {
        return idStagiaire;
    }

    public void setIdStagiaire(Integer idStagiaire) {
        this.idStagiaire = idStagiaire;
    }
}
