package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Module implements Serializable {

    private Integer idModule;
    private List<Module> prerequis = new ArrayList<>();
    private List<Cours> cours = new ArrayList<>();
    private Integer nbSemainePrevu;
    private Integer nbHeurePrevu;

    public Module() {
    }

    public Module(Integer idModule, List<Module> prerequis, List<Cours> cours, Integer nbSemainePrevu, Integer nbHeurePrevu) {
        this.idModule = idModule;
        this.prerequis = prerequis;
        this.cours = cours;
        this.nbSemainePrevu = nbSemainePrevu;
        this.nbHeurePrevu = nbHeurePrevu;
    }

    public Integer getIdModule() {
        return idModule;
    }

    public void setIdModule(Integer idModule) {
        this.idModule = idModule;
    }

    public List<Module> getPrerequis() {
        return prerequis;
    }

    public void setPrerequis(List<Module> prerequis) {
        this.prerequis = prerequis;
    }

    public List<Cours> getCours() {
        return cours;
    }

    public void setCours(List<Cours> cours) {
        this.cours = cours;
    }

    public Integer getNbSemainePrevu() {
        return nbSemainePrevu;
    }

    public void setNbSemainePrevu(Integer nbSemainePrevu) {
        this.nbSemainePrevu = nbSemainePrevu;
    }

    public Integer getNbHeurePrevu() {
        return nbHeurePrevu;
    }

    public void setNbHeurePrevu(Integer nbHeurePrevu) {
        this.nbHeurePrevu = nbHeurePrevu;
    }

    /*libelle: String,
    dureeEnHeures: Int,
    dureeEnSemaines: Int,
    prixPublicEnCours: Float,
    libelleCourt: String,
    idModule: Int,
    archiver: Boolean,
    typeModule: Int*/
}
