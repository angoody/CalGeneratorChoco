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

            // retrouve la date de début de l'année par rapport au premier cours
            // si le premier module commence le 03/04/2017, alors la seconde année commence le 03/04/2018
            int anneeDebut = (((module.getFin().getValue() - datePremierModule) / 365) * 365) + datePremierModule;
            int anneeFin = anneeDebut + 365;

            Integer countHour = 0;

            for (ModuleChoco moduleChoco : modules) {
                if (moduleChoco.getDebut().getValue() >= anneeDebut && moduleChoco.getFin().getValue() <= anneeFin) {
                    countHour = countHour + moduleChoco.getModulesWorkingDayDuration().getValue();
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
