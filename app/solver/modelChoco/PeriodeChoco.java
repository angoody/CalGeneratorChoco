package solver.modelChoco;

import models.input.Periode;
import utils.DateTimeHelper;

public class PeriodeChoco {

    private int debut;
    private int fin;

    public PeriodeChoco(Periode periode) {
        this.debut = DateTimeHelper.toDays(periode.getInstantDebut());
        this.fin = DateTimeHelper.toDays(periode.getInstantFin());
    }

    public int getDebut() {
        return debut;
    }

    public void setDebut(int debut) {
        this.debut = debut;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }
}
