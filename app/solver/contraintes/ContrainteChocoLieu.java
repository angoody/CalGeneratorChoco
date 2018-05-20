package solver.contraintes;

import models.IntegerContrainte;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.constraints.PropagatorPriority;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.explanations.RuleStore;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.events.IEventType;
import org.chocosolver.solver.variables.events.IntEventType;
import org.chocosolver.util.ESat;

public class ContrainteChocoLieu extends ContrainteChoco {

    private final IntegerContrainte lieu;

    public ContrainteChocoLieu(Model model, IntegerContrainte lieu) {
        super(model, lieu);
        this.lieu = lieu;
    }



    @Override
    public Constraint createConstraint(IntVar... var) {
        Constraint constraint = new Constraint("MyConstraint ", new MyPropagator(var[0], lieu.getValue()));
        return constraint;
    }

    public class MyPropagator extends Propagator<IntVar>{

        final int b;

        public MyPropagator(IntVar x, int b) {
            super(x);
            this.b = b;
        }

        @Override
        public int getPropagationConditions(int vIdx) {
            return IntEventType.combine(IntEventType.INSTANTIATE, IntEventType.INCLOW);
        }

        @Override
        public void propagate(int evtmask) throws ContradictionException {
            if (!getVar(0).contains(b))
                setPassive();

        }

        @Override
        public void propagate(int varIdx, int mask) throws ContradictionException {
            for (IntVar var : vars) {

            }
            if (!getVar(0).contains(b))
                setPassive();
        }

        @Override
        public ESat isEntailed() {

            if (getVar(0).getValue() == b) {
                return ESat.TRUE;
            }
            else
            {
                return ESat.FALSE;
            }

            //return ESat.UNDEFINED;
        }

        @Override
        public boolean why(RuleStore ruleStore, IntVar var, IEventType evt, int value) {
            boolean newrules = ruleStore.addPropagatorActivationRule(this);

            newrules |=super.why(ruleStore, var, evt, value);

            return newrules;
        }
    }
}
