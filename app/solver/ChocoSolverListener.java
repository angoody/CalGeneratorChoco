package solver;

import models.output.Calendar;
import models.output.ClassesCalendar;

public interface ChocoSolverListener {
    public void foundCours(ClassesCalendar cours);
    public void foundCalendar(Calendar calendar);
    public void finish();
}
