package solver.modelChoco;

import models.common.Classes;
import models.common.Module;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;
import utils.DateTimeHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ModuleChoco {

    private final Model model;
    private final IntVar occurenceVar;
    private final int occurence;
    private Module module;
    private List<CoursChoco> coursDuModule;
    private IntVar debut;
    private IntVar fin;
    private IntVar lieux;
    private IntVar coursIdentifier;
    private IntVar id;
    private IntVar coursId;
    private IntVar modulesDuration;
    private IntVar modulesWorkingDayDuration;
    private IntVar nbSemaine;
    private IntVar nbHeure;
    private List<ModuleChoco> moduleRequis = new ArrayList<>();
    private List<ModuleChoco> moduleFacultatif = new ArrayList<>();

    public ModuleChoco(Module module, Model model, int occurence) {

        // Initialisation des variables
        this.module = module;
        this.model = model;
        this.occurence = occurence;
        List<Classes> lesCours = module.getListClasses().stream()
                .sorted(Comparator.comparing(o -> DateTimeHelper.toDays(o.getPeriod().getStart()))).collect(Collectors.toList());

        module.setNbHourOfModule(lesCours.stream().mapToInt(c -> c.getRealDuration()).max().getAsInt());

        coursDuModule = lesCours.stream().map(c -> new CoursChoco(c, this) ).collect(Collectors.toList());
        coursDuModule.addAll( lesCours.stream().map(c -> new CoursChoco(c, this, 0) ).collect(Collectors.toList()));
        IntStream.range(0, coursDuModule.size()).forEach(i -> coursDuModule.get(i).setIdCours(i));

        debut = model.intVar("Debut " + getIdModule() + " occurence " + occurence, coursDuModule.stream().mapToInt(c -> c.getDebut()).toArray());
        fin = model.intVar("Fin " + getIdModule() + " occurence " + occurence, coursDuModule.stream().mapToInt(c -> c.getFin()).toArray());
        lieux = model.intVar("Lieu " + getIdModule() + " occurence " + occurence, coursDuModule.stream().mapToInt(c -> c.getLieu()).toArray());
        coursIdentifier = model.intVar("Module " + getIdModule() + " occurence " + occurence, coursDuModule.stream().mapToInt(c -> c.getCoursIdentifier()).toArray());
        id = model.intVar("ID module " + getIdModule() + " occurence " + occurence, getIdModule());
        coursId = model.intVar("ID cours " + getIdModule() + " occurence " + occurence, coursDuModule.stream().mapToInt(c -> c.getIdCours()).toArray());
        occurenceVar = model.intVar("Occurence cours " + getIdModule() + " occurence " + occurence, occurence);

        modulesDuration = model.intVar("Duration " + getIdModule() + " occurence " + occurence, coursDuModule.stream().mapToInt(c -> c.getDuration()).toArray());

        modulesWorkingDayDuration =  model.intVar("Working day Duration " + getIdModule() + " occurence " + occurence, coursDuModule.stream().mapToInt(c -> c.getWorkingDuration()).toArray());

        nbSemaine = model.intVar("Nb Semaine " + getIdModule() + " occurence " + occurence, coursDuModule.stream().mapToInt(c -> c.getNbSemaine()).toArray());

        nbHeure = model.intVar("Nb Heure " + getIdModule() + " occurence " + occurence, coursDuModule.stream().mapToInt(c -> c.getNbHeure()).toArray());
    }

    public void addConstraint(Constraint contrainte)
    {
        model.ifThen(
                model.arithm(getModulesWorkingDayDuration(), "!=", 0),
                contrainte);
    }

    public Integer getIdModule() { return module.getIdModule();}

    public Module getModule() {
        return module;
    }

    public List<CoursChoco> getCoursDuModule() {
        return coursDuModule.stream().collect(Collectors.toList());
    }

    public IntVar getCoursIdentifier() {
        return coursIdentifier;
    }

    public IntVar getId() { return id;}

    public IntVar getLieu() {
        return lieux;
    }

    public IntVar getDuration() {
        return modulesDuration;
    }

    public IntVar getDebut(){
        return debut;
    }

    public IntVar getFin() {
        return fin;
    }

    public IntVar getNbSemaine() { return nbSemaine;}

    public IntVar getNbHeure() {
        return nbHeure;
    }

    public IntVar getCoursId() {
        return coursId;
    }

    public void setModule(List<ModuleChoco> moduleInChoco) {
        moduleRequis = moduleInChoco.stream().filter(mc -> module.getListIdModulePrerequisite().contains(mc.getIdModule())).collect(Collectors.toList());
        moduleFacultatif = moduleInChoco.stream().filter(mc -> module.getListIdModuleOptional().contains(mc.getIdModule())).collect(Collectors.toList());
    }

    public List<ModuleChoco> getModuleRequis() {
        return moduleRequis;
    }

    public List<ModuleChoco> getModuleFacultatif() {
        return moduleFacultatif;
    }

    public IntVar getModulesWorkingDayDuration() {
        return modulesWorkingDayDuration;
    }

    public IntVar getOccurenceVar()
    {
        return occurenceVar;
    }

    public int getOccurence()
    {
        return occurence;
    }
}
