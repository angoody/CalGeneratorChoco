package solver.propagator;

import models.common.Period;
import org.chocosolver.solver.constraints.Operator;
import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.constraints.nary.sum.PropSum;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;
import solver.modelChoco.ModuleChoco;
import utils.DateTimeHelper;

import java.util.List;

public class PropagatorMaxAnnualHour extends Propagator<IntVar> {


    private ModuleChoco module;
    private final List<ModuleChoco> modules;
    private static List<Integer> countHour;
    private final Integer nbHour;
    private final Integer fin;
    private final Integer debut;
    private boolean alternatif = false;
    private ESat isEntailed;

    public PropagatorMaxAnnualHour (ModuleChoco module, List<ModuleChoco> modules, Period period, Integer nbHour)
    {
        super(module.getDebut());
        this.module = module;
        this.modules = modules;
        this.nbHour = nbHour;
        fin = DateTimeHelper.toDays(period.getEnd());
        debut = DateTimeHelper.toDays(period.getStart());
    }


    public void searchAternatif(Boolean alternatif)
    {
        this.alternatif = alternatif;
    }

    public Boolean isAternatifSearch()
    {
        return alternatif;
    }

    @Override
    public void fails() throws ContradictionException {
        super.fails();
        countHour.clear();
    }

    @Override
    public void propagate(int evtmask) throws ContradictionException {

        // determine le premier cours
        final Integer[] datePremierModule;
        datePremierModule = new Integer[]{fin};
        modules.stream().filter(m -> m.getDebut().getValue() > debut && m.getDebut().getValue() < fin).forEach(m -> datePremierModule[0] = m.getDebut().getValue());

        int annee = module.getFin().getValue() - datePremierModule[0] / 365;

        while (countHour.size() < annee) { countHour.add(0);}

        for (IntVar var : getVars()) {
            if ((countHour.get(annee) + module.getDuration().getValue()) < nbHour) {
                countHour.set(annee, countHour.get(annee) + module.getDuration().getValue());
                isEntailed = ESat.TRUE;
            }
            else {
                var.removeValue(var.getValue(), this);
                isEntailed = ESat.UNDEFINED;
            }
        }


    }

    @Override
    public ESat isEntailed() {
        return isEntailed;
    }
}
