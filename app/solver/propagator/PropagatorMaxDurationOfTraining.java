package solver.propagator;

import models.common.Period;
import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;
import solver.modelChoco.ModuleChoco;
import utils.DateTimeHelper;

import java.util.List;

public class PropagatorMaxDurationOfTraining extends Propagator<IntVar> {


    private ModuleChoco module;
    private final List<ModuleChoco> modules;
    private final Integer nbWeek;
    private boolean alternatif = false;
    private ESat isEntailed;

    public PropagatorMaxDurationOfTraining(ModuleChoco module, List<ModuleChoco> modules, Integer nbWeek)
    {
        super(module.getFin());
        this.module = module;
        this.modules = modules;
        this.nbWeek = nbWeek;
    }


    public void searchAternatif(Boolean alternatif)
    {
        this.alternatif = alternatif;
    }

    public Boolean isAternatifSearch()
    {
        return countNumberOfWeek() > nbWeek;
    }

    @Override
    public void propagate(int evtmask) throws ContradictionException {

        // determine le premier cours
        if (alternatif)
        {
            isEntailed = ESat.TRUE;
        }
        else {
            int datePremierModule = modules.stream().mapToInt(m -> m.getDebut().getValue()).min().getAsInt();
            for (IntVar var : getVars()) {
                if ( ((var.getValue() - datePremierModule) / 7) > nbWeek) {
                    var.removeValue(var.getValue(), this);
                    isEntailed = ESat.UNDEFINED;
                } else {
                    isEntailed = ESat.TRUE;
                }
            }


        }

    }

    @Override
    public ESat isEntailed() {
        return isEntailed;
    }

    private Integer countNumberOfWeek() {
        Integer datePremierModule = modules.stream().mapToInt(m -> m.getDebut().getValue()).min().getAsInt();
        Integer dateDernierModule = modules.stream().mapToInt(m -> m.getFin().getValue()).max().getAsInt();

        return (dateDernierModule - datePremierModule) / 7;

    }
}
