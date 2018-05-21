package solver.contraintes;

import models.ContrainteDecompose;
import models.IntegerContrainte;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.constraints.PropagatorPriority;
import org.chocosolver.solver.constraints.nary.channeling.PropEnumDomainChanneling;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.explanations.RuleStore;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.events.IEventType;
import org.chocosolver.solver.variables.events.IntEventType;
import org.chocosolver.util.ESat;
import org.chocosolver.util.iterators.DisposableValueIterator;
import org.chocosolver.util.iterators.IntVarValueIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Constraint constraint = new Constraint("MyConstraint ", new MyPropagator(var[0], lieu.getValue()));
        model.arithm(var[0], "=", lieu.getValue());
        return constraint;
    }

    @Override
    public void enableAlternateSearch(IntVar... var) {
        getContrainteModel().setRespeced(false);
        Arrays.stream(constraints.get(var[0].getName()).getPropagators()).filter(p -> p instanceof MyPropagator).forEach(p -> ((MyPropagator) p).addAternatif((lieuxPossible.get(0))));
    }

    @Override
    public void disableAlternateSearch(IntVar... var) {
        getContrainteModel().setRespeced(true);
        Arrays.stream(constraints.get(var[0].getName()).getPropagators()).filter(p -> p instanceof MyPropagator).forEach(p -> ((MyPropagator) p).addAternatif((lieuxPossible.get(0))));
    }




    public class MyPropagator extends PropEnumDomainChanneling {
        public MyPropagator(BoolVar[] bvars, IntVar aVar, int offSet) {
            super(bvars, aVar, offSet);
        }
    }
    /*public class MyPropagator extends Propagator<IntVar>{
        final int b;
        int alternatif = -1;
        private boolean isDefined = false;
        List<Integer> values = new ArrayList<>();

        public MyPropagator(IntVar x, int b) {
            super(x);
            DisposableValueIterator valueIterator = x.getValueIterator(true);
            while (valueIterator.hasNext())
            {
                values.add(valueIterator.next());
            }
            if (x.contains(b)) {
                setActive();
            }
            else if (isActive())
            {
                setPassive();
            }
            this.b = b;
        }

        public void addAternatif(int alternatif)
        {
            if (isActive() == false && (values.contains(alternatif) || values.contains(b))) {
                setActive();
            }
            else if (isActive() == true && !values.contains(alternatif) && !values.contains(b))
            {
                setPassive();
            }
            this.alternatif = alternatif;
        }

        public void removeAternatif()
        {
            // Il ne faut pas activer une contrainte déjà active
            if (isActive() == false &&values.contains(b)) {
                setActive();
            }
            else if (isActive() == true)
            {
                setPassive();
            }
            this.alternatif = -1;
        }

        @Override
        public int getPropagationConditions(int vIdx) {
            return IntEventType.combine(IntEventType.INSTANTIATE, IntEventType.INCLOW);
        }

        @Override
        public void propagate(int evtmask) throws ContradictionException {
            if (isActive() == true) {
                int LB = alternatif == -1 ? b : alternatif;
                int GB = alternatif == -1 ? b : alternatif;
                if (LB == -1)
                    LB = b;
                getVar(0).updateBounds(LB, GB, this);
            }

        }

        @Override
        public void propagate(int idxVarInProp, int mask) throws ContradictionException {
            propagate(mask);
            super.propagate(idxVarInProp, mask);
        }

        @Override
        public ESat isEntailed() {
            if (getVar(0).getValue() == b || getVar(0).getValue() == alternatif) {

                return ESat.TRUE;
            }
            else
            {
                return ESat.UNDEFINED;
            }

            //return ESat.UNDEFINED;
        }

    }*/
}
