package models.input;

import java.util.List;

public class Stagiaire
{
    private List<Periode> cours;
    private Integer     idStagiaire;

    public Stagiaire(Integer idStagiaire, List<Periode> cours)
    {
        this.cours = cours;
        this.idStagiaire = idStagiaire;
    }

    public List<Periode> getCours()
    {
        return cours;
    }

    public Integer getIdStagiaire()
    {
        return idStagiaire;
    }

}
