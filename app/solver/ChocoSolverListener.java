package solver;

import models.Calendrier;
import models.Cours;

public interface ChocoSolverListener {
    public void foundCours(Cours cours);
    public void foundCalendar(Calendrier calendrier);
    public void finish();
}
