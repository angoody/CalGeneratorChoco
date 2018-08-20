package solver.modelChoco;

import models.common.Classes;

public class CoursChoco extends CoursChocoStagiaire
{

    private final ModuleChoco moduleChoco;

    public CoursChoco(Classes classes, ModuleChoco moduleChoco)
    {
        super(classes);
        this.moduleChoco = moduleChoco;
    }

    public CoursChoco(Classes classes, ModuleChoco moduleChoco, Integer duration)
    {
        super(classes);
        this.moduleChoco = moduleChoco;
        setWorkingDayDuration(duration);
    }


    public int getIdModule()
    {
        return moduleChoco.getIdModule();
    }

    public int getNbHeure()
    {
        return moduleChoco.getModule().getNbHourOfModule();
    }

    public int getNbSemaine()
    {
        return moduleChoco.getModule().getNbWeekOfModule();
    }


    public ModuleChoco getModuleChoco()
    {
        return moduleChoco;
    }

    @Override
    public int[] getInt()
    {
        return new int[]{getIdModule(), getModuleChoco().getOccurence(), getIdCours(), getDebut(), getFin(), getCoursIdentifier(), getLieu(), getWorkingDuration(), getDuration(), getNbHeure(), getNbSemaine()};
    }
}
