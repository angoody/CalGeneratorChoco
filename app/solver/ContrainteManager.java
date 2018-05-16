package solver;

import models.Contrainte;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;
import utils.DateTimeHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ContrainteManager {
    private List<Contrainte> contraintes;
    private List<ContrainteChoco> contraintesInChoco;

    public ContrainteManager(Model model, List<Contrainte> contraintes) {
        this.contraintes = contraintes;
        this.contraintesInChoco = new ArrayList<>(contraintes.stream().map(cont -> new ContrainteChoco(cont, model)).collect(Collectors.toList()));
    }

    public void createContrainteLieu(IntVar lieu)
    {
        // Contrainte de lieu
        contraintesInChoco.stream().forEach(cc -> cc.createContrainteLieu(lieu));

    }

    public void createContraintePeriodeExclusion(IntVar debut, IntVar fin)
    {
        contraintesInChoco.stream().forEach(cc -> cc.createContraintePeriodeExclusion(debut, fin));

    }


    public List<Contrainte> getContraintesRespecte() {
        return contraintesInChoco.stream().filter(cc -> cc.allConstraintRespected() == true).map(cc -> cc.getContrainte()).collect(Collectors.toList());
    }

    public List<Contrainte> getContraintesNonRespecte() {
        return contraintesInChoco.stream().filter(cc -> cc.allConstraintRespected() == false).map(cc -> cc.getContrainte()).collect(Collectors.toList());
    }


}
