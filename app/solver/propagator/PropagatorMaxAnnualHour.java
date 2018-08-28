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

import java.util.ArrayList;
import java.util.List;

public class PropagatorMaxAnnualHour extends Propagator<IntVar> {


    private ModuleChoco module;
    private final List<ModuleChoco> modules;
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
    public void propagate(int evtmask) throws ContradictionException {

        // determine le premier cours
        if (isAternatifSearch())
        {
            isEntailed = ESat.TRUE;
        }
        else {
            Integer datePremierModule = fin;
            for (ModuleChoco moduleChoco : modules) {
                if (moduleChoco.getDebut().getValue() >= debut && moduleChoco.getFin().getValue() <= fin && moduleChoco.getDebut().getValue() < datePremierModule) {
                    datePremierModule = moduleChoco.getDebut().getValue();
                }
            }


            int anneeDebut = (((module.getFin().getValue() - datePremierModule) / 365) * 365) + datePremierModule;
            int anneeFin = ((((module.getFin().getValue() - datePremierModule) / 365) + 1) * 365) + datePremierModule;

            Integer countHour = 0;

            for (ModuleChoco moduleChoco : modules) {
                if (moduleChoco.getDebut().getValue() >= anneeDebut && moduleChoco.getFin().getValue() <= anneeFin) {
                    countHour = countHour + moduleChoco.getDuration().getValue();
                }
            }

            if (countHour > nbHour) {
                for (IntVar var : getVars()) {
                    var.removeValue(var.getValue(), this);
                    isEntailed = ESat.UNDEFINED;
                }
            } else {
                isEntailed = ESat.TRUE;
            }
        }

    }

    @Override
    public ESat isEntailed() {
        return isEntailed;
    }
}
