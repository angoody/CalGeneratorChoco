package solver.contraintes;

import models.ContrainteDecompose;
import models.IntegerContrainte;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.constraints.PropagatorPriority;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.constraints.extension.nary.PropCompactTable;
import org.chocosolver.solver.constraints.nary.channeling.PropEnumDomainChanneling;
import org.chocosolver.solver.constraints.unary.PropEqualXC;
import org.chocosolver.solver.constraints.unary.PropMemberEnum;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.explanations.RuleStore;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.events.IEventType;
import org.chocosolver.solver.variables.events.IntEventType;
import org.chocosolver.util.ESat;
import org.chocosolver.util.iterators.DisposableValueIterator;
import org.chocosolver.util.iterators.IntVarValueIterator;
import solver.propagator.PropagatorContrainteLieu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.oracle.jrockit.jfr.FlightRecorder.isActive;

public class ContrainteChocoLieu extends ContrainteChoco {

    private final IntegerContrainte lieu;
    private List<Integer> lieuxPossible;

    public ContrainteChocoLieu(Model model, IntegerContrainte lieu, List<Integer> lieuxPossible) {
        super(model, lieu);
        this.lieu = lieu;

        this.lieuxPossible = lieuxPossible;
    }



    @Override
    public Constraint createConstraint(IntVar... var) {
        Constraint constraint = new Constraint("MyConstraint ", new PropagatorContrainteLieu(var[0], lieu.getValue(), lieuxPossible));
        model.arithm(var[0], "=", lieu.getValue());
        return constraint;
    }

    @Override
    public void enableAlternateSearch(IntVar... var) {
        getContrainteModel().setRespeced(false);
        Arrays.stream(constraints.get(var[0].getName()).getPropagators()).filter(p -> p instanceof PropagatorContrainteLieu).forEach(p -> ((PropagatorContrainteLieu) p).searchAternatif((true)));
    }

    @Override
    public void disableAlternateSearch(IntVar... var) {
        getContrainteModel().setRespeced(true);
        Arrays.stream(constraints.get(var[0].getName()).getPropagators()).filter(p -> p instanceof PropagatorContrainteLieu).forEach(p -> ((PropagatorContrainteLieu) p).searchAternatif((true)));
    }


// https://stackoverflow.com/questions/46468877/choco-solver-propogation-and-search-strategy-interaction


}
