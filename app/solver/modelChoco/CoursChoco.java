package solver.modelChoco;

import models.input.Classes;

public class CoursChoco extends CoursChocoStagiaire
{

    private final ModuleChoco moduleChoco;

    public CoursChoco(Classes classes, ModuleChoco moduleChoco)
    {
        super(classes);
        this.moduleChoco = moduleChoco;
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
        return new int[]{getIdModule(), getIdCours(), getDebut(), getFin(), getCoursIdentifier(), getLieu(), getDuration(), getNbHeure(), getNbSemaine()};
    }
}
