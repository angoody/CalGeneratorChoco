package models;

import java.io.Serializable;
import java.util.List;

public class Probleme implements Serializable {
    private Periode periodeFormation;
    private List<Module> modulesFormation;
    private List<Contrainte> contraintes;

    public Probleme() {
    }

    public Probleme(Periode periodeFormation, List<Module> modulesFormation, List<Contrainte> contraintes) {
        this.periodeFormation = periodeFormation;
        this.modulesFormation = modulesFormation;
        this.contraintes = contraintes;
    }

    public Periode getPeriodeFormation() {
        return periodeFormation;
    }

    public void setPeriodeFormation(Periode periodeFormation) {
        this.periodeFormation = periodeFormation;
    }

    public List<Module> getModulesFormation() {
        return modulesFormation;
    }

    public void setModulesFormation(List<Module> modulesFormation) {
        this.modulesFormation = modulesFormation;
    }

    public List<Contrainte> getContraintes() {
        return contraintes;
    }

    public void setContraintes(List<Contrainte> contraintes) {
        this.contraintes = contraintes;
    }
}
