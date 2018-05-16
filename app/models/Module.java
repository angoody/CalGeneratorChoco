package models;

import java.io.Serializable;
import java.util.Set;

public class Module implements Serializable {

    private Integer idModule;
    private Set<Module> prerequis;
    private Set<Cours> cours;
    private Integer nbSemainePrevu;
    private Integer nbHeurePrevu;

    public Module() {
    }

    public Module(Integer idModule, Set<Module> prerequis, Set<Cours> cours, Integer nbSemainePrevu, Integer nbHeurePrevu) {
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

    public Set<Module> getPrerequis() {
        return prerequis;
    }

    public void setPrerequis(Set<Module> prerequis) {
        this.prerequis = prerequis;
    }

    public Set<Cours> getCours() {
        return cours;
    }

    public void setCours(Set<Cours> cours) {
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
