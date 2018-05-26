package solver;

import models.output.Calendrier;
import models.input.Cours;

public interface ChocoSolverListener {
    public void foundCours(Cours cours);
    public void foundCalendar(Calendrier calendrier);
    public void finish();
}
