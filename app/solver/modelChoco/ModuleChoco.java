package solver.modelChoco;

import models.Cours;
import models.Module;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ModuleChoco {

    private Module module;
    private List<CoursChoco> coursDuModule;
    private IntVar debut;
    private IntVar fin;
    private IntVar lieux;
    private IntVar coursIdentifier;
    private IntVar id;
    private IntVar coursId;
    private IntVar modulesDuration;
    private IntVar nbSemaine;
    private IntVar nbHeure;
    private List<ModuleChoco> moduleRequis = new ArrayList<>();
    private List<ModuleChoco> moduleFacultatif = new ArrayList<>();

    public ModuleChoco(Module module, Model model) {

        // Initialisation des variables
        this.module = module;

        List<Cours> lesCours = module.getCours().stream()
                .sorted(Comparator.comparing(o -> o.getPeriode().getInstantDebut())).collect(Collectors.toList());

        coursDuModule = lesCours.stream().map(c -> new CoursChoco(c, module.getIdModule(), module.getNbSemainePrevu(), module.getNbHeurePrevu()) ).collect(Collectors.toList());
        IntStream.range(0, coursDuModule.size()).forEach(i -> coursDuModule.get(i).setIdCours(i));

        debut = model.intVar("Debut " + getIdModule(), coursDuModule.stream().mapToInt(c -> c.getDebut()).toArray());
        fin = model.intVar("Fin " + getIdModule(), coursDuModule.stream().mapToInt(c -> c.getFin()).toArray());
        lieux = model.intVar("Lieu " + getIdModule(), coursDuModule.stream().mapToInt(c -> c.getLieu()).toArray());
        coursIdentifier = model.intVar("Module " + getIdModule(), coursDuModule.stream().mapToInt(c -> c.getCoursIdentifier()).toArray());
        id = model.intVar("ID module " + getIdModule(), getIdModule());
        coursId = model.intVar("ID module " + getIdModule(), coursDuModule.stream().mapToInt(c -> c.getIdCours()).toArray());

        modulesDuration = model.intVar("Duration " + getIdModule(), coursDuModule.stream().mapToInt(c -> c.getDuration()).toArray());

        nbSemaine = model.intVar("Nb Semaine " + getIdModule(), coursDuModule.stream().mapToInt(c -> c.getNbSemaine()).toArray());

        nbHeure = model.intVar("Nb Heure " + getIdModule(), coursDuModule.stream().mapToInt(c -> c.getNbHeure()).toArray());
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
        moduleRequis = moduleInChoco.stream().filter(mc -> module.getIdModulePrerequis().contains(mc.getIdModule())).collect(Collectors.toList());
        moduleFacultatif = moduleInChoco.stream().filter(mc -> module.getIdModuleFacultatif().contains(mc.getIdModule())).collect(Collectors.toList());
    }

    public List<ModuleChoco> getModuleRequis() {
        return moduleRequis;
    }

    public List<ModuleChoco> getModuleFacultatif() {
        return moduleFacultatif;
    }
}
