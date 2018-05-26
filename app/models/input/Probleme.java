package models.input;

import java.io.Serializable;
import java.util.List;

public class Probleme implements Serializable
{
    private Periode periodeFormation = new Periode();
    private List<Module> modulesFormation;
    private Constrainte  contraintes;

    public Probleme()
    {
    }

    public Probleme(Periode periodeFormation, List<Module> modulesFormation, Constrainte contraintes)
    {
        this.periodeFormation = periodeFormation;
        this.modulesFormation = modulesFormation;
        this.contraintes = contraintes;
    }

    public Periode getPeriodeFormation()
    {
        return periodeFormation;
    }

    public void setPeriodeFormation(Periode periodeFormation)
    {
        this.periodeFormation = periodeFormation;
    }

    public List<Module> getModulesFormation()
    {
        return modulesFormation;
    }

    public void setModulesFormation(List<Module> modulesFormation)
    {
        this.modulesFormation = modulesFormation;
    }

    public Constrainte getContrainte()
    {
        return contraintes;
    }

    public void setContraintes(Constrainte contraintes)
    {
        this.contraintes = contraintes;
    }
}
