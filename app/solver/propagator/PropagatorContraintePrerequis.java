package solver.propagator;

import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;
import solver.modelChoco.ModuleChoco;

import java.util.OptionalInt;

public class PropagatorContraintePrerequis extends Propagator<IntVar> {

    private ModuleChoco module;
    private Boolean alternatif;
    private Boolean noPossibility = false;

    public PropagatorContraintePrerequis(ModuleChoco module)
    {
        super(module.getDebut());

        this.module = module;
    }

    public void searchAternatif(Boolean alternatif)
    {
        this.alternatif = alternatif;
    }

    public Boolean isAternatifSearch()
    {
        return alternatif || noPossibility;
    }

    @Override
    public void propagate(int evtmask) throws ContradictionException {

        int dateMax;
        int finalDateMax = getVar(0).getUB();

        if (module.getModuleRequis().stream().mapToInt(m -> m.getFin().getUB()).filter(ub -> ub > finalDateMax).count() > 0)
            noPossibility = true;

        if (!noPossibility && module.getModuleFacultatif().stream().mapToInt(m -> m.getFin().getUB()).filter(ub -> ub > finalDateMax).count() > 0)
            noPossibility = true;

        if (!alternatif) {
            OptionalInt LBFort = module.getModuleRequis().stream().mapToInt(m -> m.getFin().getUB()).filter(ub -> ub < finalDateMax).max();
            OptionalInt LBFaible = module.getModuleRequis().stream().mapToInt(m -> m.getFin().getUB()).filter(ub -> ub < finalDateMax).max();

            if (LBFort.isPresent() && LBFaible.isPresent())
                dateMax = LBFort.getAsInt() > LBFaible.getAsInt() ? LBFort.getAsInt() : LBFaible.getAsInt();
            else if (LBFort.isPresent())
                dateMax = LBFort.getAsInt();
            else if (LBFaible.isPresent())
                dateMax = LBFaible.getAsInt();
            else
                dateMax = getVar(0).getLB();

            getVar(0).updateLowerBound(dateMax,this);
        }

    }

    @Override
    public ESat isEntailed() {

        return ESat.UNDEFINED;
    }
}
