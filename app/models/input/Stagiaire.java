package models.input;

import java.util.List;

public class Stagiaire
{
    private List<Cours> cours;
    private Integer     idStagiaire;

    public Stagiaire(Integer idStagiaire, List<Cours> cours)
    {
        this.cours = cours;
        this.idStagiaire = idStagiaire;
    }

    public List<Cours> getCours()
    {
        return cours;
    }

    public Integer getIdStagiaire()
    {
        return idStagiaire;
    }

}
