package solver;

import models.common.ClassesCalendar;
import models.generator.output.Calendar;

public interface ChocoGeneratorListener
{
    public void foundCours(ClassesCalendar cours);
    public void foundCalendar(Calendar calendar);
    public void finish();
}
