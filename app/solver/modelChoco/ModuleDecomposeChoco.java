package solver.modelChoco;

import models.input.Classes;
import models.input.Module;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import utils.DateTimeHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ModuleDecomposeChoco
{

    private final Model model;
    private Module module;
    private List<CoursChoco> coursDuModule;
    private ModuleChoco moduleChoco;
    private IntVar debut;
    private IntVar fin;
    private IntVar lieux;
    private IntVar coursIdentifier;
    private IntVar id;
    private IntVar coursId;
    private IntVar modulesDuration;
    private IntVar nbSemaine;
    private IntVar nbHeure;
    private IntVar idModuleDecomposeInChoco;
    private int idModuleDecompose;


    public ModuleDecomposeChoco(Module module, List<Classes> cours, ModuleChoco moduleChoco, Model model) {

        // Initialisation des variables
        this.module = module;
        this.model = model;


        coursDuModule = cours.stream().map(c -> new CoursChoco(c, moduleChoco, this )).collect(Collectors.toList());
        this.moduleChoco = moduleChoco;
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


    public IntVar getIdModuleDecomposeInChoco()
    {
        return idModuleDecomposeInChoco;
    }

    public int getIdModuleDecompose()
    {
        return idModuleDecompose;
    }

    public void setIdModuleDecompose(int idModuleDecompose)
    {
        this.idModuleDecomposeInChoco = model.intVar(idModuleDecompose);
        this.idModuleDecompose = idModuleDecompose;
    }
}
