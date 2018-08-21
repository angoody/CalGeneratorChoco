package models.common;

import java.util.ArrayList;
import java.util.List;

public class Module
{

    private Integer idModule;
    private Integer nbWeekOfModule;
    private Integer nbHourOfModule;
    private List<Integer> listIdModulePrerequisite = new ArrayList<>();
    private List<Integer> listIdModuleOptional;
    private List<Classes> listClasses = new ArrayList<>();


    public Module()
    {
    }

    public Module(Integer idModule, List<Integer> listIdModulePrerequisite, List<Integer> listIdModuleOptional, List<Classes> listClasses, Integer nbSemainePrevu, Integer nbHourOfModule)
    {
        this.idModule = idModule;
        this.listIdModulePrerequisite = listIdModulePrerequisite;
        this.listIdModuleOptional = listIdModuleOptional;
        this.listClasses = listClasses;
        this.nbWeekOfModule = nbSemainePrevu;
        this.nbHourOfModule = nbHourOfModule;
    }

    public Integer getIdModule()
    {
        return idModule;
    }

    public void setIdModule(Integer idModule)
    {
        this.idModule = idModule;
    }

    public List<Integer> getListIdModulePrerequisite()
    {
        return listIdModulePrerequisite;
    }

    public void setListIdModulePrerequisite(List<Integer> listIdModulePrerequisite)
    {
        this.listIdModulePrerequisite = listIdModulePrerequisite;
    }

    public List<Classes> getListClasses()
    {
        return listClasses;
    }

    public void setListClasses(List<Classes> listClasses)
    {
        this.listClasses = listClasses;
    }

    public Integer getNbWeekOfModule()
    {
        return nbWeekOfModule;
    }

    public void setNbWeekOfModule(Integer nbWeekOfModule)
    {
        this.nbWeekOfModule = nbWeekOfModule;
    }

    public Integer getNbHourOfModule()
    {
        return nbHourOfModule;
    }

    public void setNbHourOfModule(Integer nbHourOfModule)
    {
        this.nbHourOfModule = nbHourOfModule;
    }

    public List<Integer> getListIdModuleOptional()
    {
        return listIdModuleOptional;
    }

    public void setListIdModuleOptional(List<Integer> listIdModuleOptional)
    {
        this.listIdModuleOptional = listIdModuleOptional;
    }

}
