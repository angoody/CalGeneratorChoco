package solver.modelChoco;

import models.input.Classes;

public class CoursChoco extends CoursChocoStagiaire
{

    private final ModuleChoco moduleChoco;
    private ModuleDecomposeChoco moduleDecomposeChoco;

    public CoursChoco(Classes classes, ModuleChoco moduleChoco, ModuleDecomposeChoco moduleDecomposeChoco)
    {
        super(classes);
        this.moduleChoco = moduleChoco;
        this.moduleDecomposeChoco = moduleDecomposeChoco;
    }

    public int getIdModule()
    {
        return moduleChoco.getModule().getIdModule();
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

    public ModuleDecomposeChoco getModuleDecomposeChoco()
    {
        return moduleDecomposeChoco;
    }

    @Override
    public int[] getInt()
    {
        return new int[]{getIdModule(), moduleDecomposeChoco.getIdModuleDecompose(), getIdCours(), getDebut(), getFin(), getCoursIdentifier(), getLieu(), getDuration(), getNbHeure(), getNbSemaine()};
    }


}
