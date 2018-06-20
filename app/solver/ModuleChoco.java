package solver;

import models.Contrainte;
import models.Cours;
import models.Module;
import models.Periode;
import org.chocosolver.solver.variables.IntVar;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ModuleChoco {

    private Module module;
    private IntVar moduleInChoco;
    private List<CoursChoco> coursDuModule;

    public ModuleChoco(Module module, List<Contrainte> contraintes, Periode periodeFormation) {

        // Initialisation des variables
        this.module = module;

        List<Cours> lesCours = module.getCours().stream()
                .sorted(Comparator.comparing(o -> o.getPeriode().getInstantDebut())).collect(Collectors.toList());

        coursDuModule = lesCours.stream().map(c -> new CoursChoco(c, module.getIdModule(), module.getNbSemainePrevu(), module.getNbHeurePrevu()) ).collect(Collectors.toList());
        IntStream.range(0, coursDuModule.size()).forEach(i -> coursDuModule.get(i).setIdCours(i));
    }


    public List<CoursChoco> getCoursDuModule() {
        return coursDuModule.stream().collect(Collectors.toList());
    }

    public int[] getCoursIdentifier() {
        return coursDuModule.stream().mapToInt(c -> c.getCoursIdentifier()).toArray();
    }

    public int[] getIdModule() {

        return coursDuModule.stream().mapToInt(c -> c.getIdModule()).toArray();
    }

    public Module getModule() {
        return module;
    }

    public int getId() {
        return module.getIdModule();
    }

    public int[] getLieu() {
        return coursDuModule.stream().mapToInt(c -> c.getLieu()).toArray();
    }

    public int[] getDuration() {
        return coursDuModule.stream().mapToInt(c -> c.getDuration()).toArray();
    }

    public int[] getDebut(){
        return coursDuModule.stream().mapToInt(c -> c.getDebut()).toArray();
    }

    public int[] getFin() {
        return coursDuModule.stream().mapToInt(c -> c.getFin()).toArray();
    }

    public int[] getNbSemaine() {
        return coursDuModule.stream().mapToInt(c -> c.getNbSemaine()).toArray();
    }

    public int[] getNbHeure() {
        return coursDuModule.stream().mapToInt(c -> c.getNbHeure()).toArray();
    }

    public int[] getCoursId() {
        return coursDuModule.stream().mapToInt(c -> c.getIdCours()).toArray();
    }
}
