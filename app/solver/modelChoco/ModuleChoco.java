package solver.modelChoco;

import models.input.Classes;
import models.input.Module;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import utils.DateTimeHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ModuleChoco
{
    private final IntVar moduleDecomposeId;
    private Module module;
    private ModuleDecomposeChoco       moduleEnUnCours;
    private List<ModuleDecomposeChoco> moduleEnPlusieursCours;
    private List<ModuleDecomposeChoco> moduleDecompose;
    private List<ModuleChoco> moduleRequis     = new ArrayList<>();
    private List<ModuleChoco> moduleFacultatif = new ArrayList<>();


    public ModuleChoco(Module module, Model model)
    {

        // Initialisation des variables
        this.module = module;
        List<Integer> dureeCours = module.getListClasses().stream().mapToInt(c -> c.getRealDuration()).distinct().sorted().mapToObj(c -> new Integer(c)).collect(Collectors.toList());
        moduleEnPlusieursCours = new ArrayList<ModuleDecomposeChoco>();

        if (dureeCours.size() == 1)
        {
            moduleEnUnCours = new ModuleDecomposeChoco(
                    module,
                    module.getListClasses().stream()
                            .sorted(Comparator.comparing(o -> DateTimeHelper.toDays(o.getPeriod().getStart())))
                            .collect(Collectors.toList()),
                    this,
                    model);
            moduleDecompose.add(moduleEnUnCours);
        }
        else
        {
            if (dureeCours.contains(module.getNbHourOfModule()))
            {
                moduleEnUnCours = new ModuleDecomposeChoco(
                        module,
                        module.getListClasses().stream()
                                .filter(c -> c.getWorkingDayDuration() == module.getNbHourOfModule())
                                .sorted(Comparator.comparing(o -> DateTimeHelper.toDays(o.getPeriod().getStart())))
                                .collect(Collectors.toList()),
                        this,
                        model);
                dureeCours.remove(module.getNbHourOfModule());
                moduleDecompose.add(moduleEnUnCours);
            }
            for (Integer duree : dureeCours)
            {
                moduleEnPlusieursCours.add(new ModuleDecomposeChoco(
                        module,
                        module.getListClasses().stream()
                                .filter(c -> c.getWorkingDayDuration() == duree)
                                .sorted(Comparator.comparing(o -> DateTimeHelper.toDays(o.getPeriod().getStart())))
                                .collect(Collectors.toList()),
                        this,
                        model));

            }
            moduleDecompose.addAll(moduleEnPlusieursCours);
        }

        IntStream.range(0, moduleDecompose.size()).forEach(i -> moduleDecompose.get(i).setIdModuleDecompose(i));
        moduleDecomposeId = model.intVar("ID moduleDecompose " + module.getIdModule(), moduleDecompose.stream().mapToInt(c -> c.getIdModuleDecompose()).toArray());
    }

    public Module getModule()
    {
        return module;
    }

    public ModuleDecomposeChoco getModuleEnUnCours()
    {
        return moduleEnUnCours;
    }

    public List<ModuleDecomposeChoco> getModuleEnPlusieursCours()
    {
        return moduleEnPlusieursCours;
    }

    public void setModule(List<ModuleChoco> moduleInChoco)
    {
        moduleRequis = moduleInChoco.stream().filter(mc -> module.getListIdModulePrerequisite().contains(mc.getModule().getIdModule())).collect(Collectors.toList());
        moduleFacultatif = moduleInChoco.stream().filter(mc -> module.getListIdModuleOptional().contains(mc.getModule().getIdModule())).collect(Collectors.toList());
    }

    public List<ModuleChoco> getModuleRequis()
    {
        return moduleRequis;
    }

    public List<ModuleChoco> getModuleFacultatif()
    {
        return moduleFacultatif;
    }

    public List<CoursChoco> getCoursDuModule()
    {
        return getModuleDecompose().stream().flatMap(m -> m.getCoursDuModule().stream()).collect(Collectors.toList());
    }

    public List<ModuleDecomposeChoco> getModuleDecompose()
    {
        return moduleDecompose;
    }

    public CoursChoco getCours()
    {
        return getModuleDecompose().get(moduleDecomposeId.getValue()).getCoursDuModule().get(getModuleDecompose().get(moduleDecomposeId.getValue()).getCoursId().getValue());
    }
}
