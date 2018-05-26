package models.input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Module
{

    private Integer idModule;
    private List<Integer> idModulePrerequis = new ArrayList<>();
    private List<Integer> idModuleFacultatif;
    private List<Cours> cours = new ArrayList<>();
    private Integer nbSemainePrevu;
    private Integer nbHeurePrevu;

    public Module()
    {
    }

    public Module(Integer idModule, List<Integer> idModulePrerequis, List<Integer> idModuleFacultatif, List<Cours> cours, Integer nbSemainePrevu, Integer nbHeurePrevu)
    {
        this.idModule = idModule;
        this.idModulePrerequis = idModulePrerequis;
        this.idModuleFacultatif = idModuleFacultatif;
        this.cours = cours;
        this.nbSemainePrevu = nbSemainePrevu;
        this.nbHeurePrevu = nbHeurePrevu;
    }

    public Integer getIdModule()
    {
        return idModule;
    }

    public void setIdModule(Integer idModule)
    {
        this.idModule = idModule;
    }

    public List<Integer> getIdModulePrerequis()
    {
        return idModulePrerequis;
    }

    public void setIdModulePrerequis(List<Integer> idModulePrerequis)
    {
        this.idModulePrerequis = idModulePrerequis;
    }

    public List<Cours> getCours()
    {
        return cours;
    }

    public void setCours(List<Cours> cours)
    {
        this.cours = cours;
    }

    public Integer getNbSemainePrevu()
    {
        return nbSemainePrevu;
    }

    public void setNbSemainePrevu(Integer nbSemainePrevu)
    {
        this.nbSemainePrevu = nbSemainePrevu;
    }

    public Integer getNbHeurePrevu()
    {
        return nbHeurePrevu;
    }

    public void setNbHeurePrevu(Integer nbHeurePrevu)
    {
        this.nbHeurePrevu = nbHeurePrevu;
    }

    public List<Integer> getIdModuleFacultatif()
    {
        return idModuleFacultatif;
    }

    public void setIdModuleFacultatif(List<Integer> idModuleFacultatif)
    {
        this.idModuleFacultatif = idModuleFacultatif;
    }

}
