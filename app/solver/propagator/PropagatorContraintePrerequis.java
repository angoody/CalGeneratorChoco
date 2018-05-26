package solver.propagator;

import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;
import solver.modelChoco.ModuleChoco;

import java.util.OptionalInt;

public class PropagatorContraintePrerequis extends Propagator<IntVar> {

    private ModuleChoco module;
    private Boolean alternatif = false;
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
        return alternatif || noPossibility ;
    }

    @Override
    public void propagate(int evtmask) throws ContradictionException {

        int dateMax;
        int finalDateMax = getVar(0).getUB();

        noPossibility = false;

        if (module.getModuleRequis().stream().mapToInt(m -> m.getFin().getLB()).filter(lb -> lb > finalDateMax).count() > 0)
            noPossibility = true;

        if (!noPossibility && module.getModuleFacultatif().stream().mapToInt(m -> m.getFin().getLB()).filter(lb -> lb > finalDateMax).count() > 0)
            noPossibility = true;

        if (!alternatif) {
            /*for (ModuleChoco moduleRequis: module.getModuleRequis() ) {
                moduleRequis.getFin().updateUpperBound(getVar(0).getValue(), this);
            }

            for (ModuleChoco moduleFacultatif: module.getModuleRequis() ) {
                moduleFacultatif.getFin().updateUpperBound(getVar(0).getValue(), this);
            }*/

            OptionalInt LBFort = module.getModuleRequis().stream().mapToInt(m -> m.getFin().getValue()).max();
            OptionalInt LBFaible = module.getModuleRequis().stream().mapToInt(m -> m.getFin().getValue()).max();

            if (LBFort.isPresent() && LBFaible.isPresent())
                dateMax = LBFort.getAsInt() > LBFaible.getAsInt() ? LBFort.getAsInt() : LBFaible.getAsInt();
            else if (LBFort.isPresent())
                dateMax = LBFort.getAsInt();
            else if (LBFaible.isPresent())
                dateMax = LBFaible.getAsInt();
            else
                dateMax = getVar(0).getLB();

            if (dateMax > getVar(0).getUB()) {
                getVar(0).updateUpperBound(dateMax, this);

            }
            getVar(0).updateLowerBound(dateMax, this);

        }

    }

    @Override
    public ESat isEntailed() {

        return ESat.UNDEFINED;
    }
}
