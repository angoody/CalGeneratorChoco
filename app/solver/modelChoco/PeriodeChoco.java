package solver.modelChoco;

import models.common.Period;
import utils.DateTimeHelper;

public class PeriodeChoco {

    private int debut;
    private int fin;

    public PeriodeChoco(Period period) {
        this.debut = DateTimeHelper.toDays(period.getStart());
        this.fin = DateTimeHelper.toDays(period.getEnd());
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
