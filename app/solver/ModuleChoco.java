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

public class ModuleChoco {

    private Module module;
    private IntVar moduleInChoco;
    private Map<String, CoursChoco> coursDuModule;

    public ModuleChoco(Module module, List<Contrainte> contraintes, Periode periodeFormation) {

        // Initialisation des variables
        this.module = module;

        List<Cours> lesCours = module.getCours().stream()
                .sorted(Comparator.comparing(o -> o.getPeriode().getInstantDebut())).collect(Collectors.toList());

        coursDuModule = lesCours.stream().collect(
                Collectors.toMap(
                        c -> c.getIdCours(),
                        c -> new CoursChoco(c, module.getIdModule(), module.getNbSemainePrevu(), module.getNbHeurePrevu()) ));

    }


    public List<CoursChoco> getCoursDuModule() {
        return coursDuModule.values().stream().collect(Collectors.toList());
    }

    public int[] getCoursIdentifier() {
        return coursDuModule.values().stream().mapToInt(c -> c.getCoursIdentifier()).toArray();
    }

    public int[] getIdModule() {

        return coursDuModule.values().stream().mapToInt(c -> c.getIdModule()).toArray();
    }

    public Module getModule() {
        return module;
    }

    public int getId() {
        return module.getIdModule();
    }

    public int[] getLieu() {
        return coursDuModule.values().stream().mapToInt(c -> c.getLieu()).toArray();
    }

    public int[] getDuration() {
        return coursDuModule.values().stream().mapToInt(c -> c.getDuration()).toArray();
    }

    public int[] getDebut(){
        return coursDuModule.values().stream().mapToInt(c -> c.getDebut()).toArray();
    }

    public int[] getFin() {
        return coursDuModule.values().stream().mapToInt(c -> c.getFin()).toArray();
    }

    public int[] getNbSemaine() {
        return coursDuModule.values().stream().mapToInt(c -> c.getNbSemaine()).toArray();
    }

    public int[] getNbHeure() {
        return coursDuModule.values().stream().mapToInt(c -> c.getNbHeure()).toArray();
    }

}
