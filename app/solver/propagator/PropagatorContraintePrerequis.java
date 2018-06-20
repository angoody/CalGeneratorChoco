package solver.propagator;

import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;
import solver.modelChoco.ModuleDecomposeChoco;

import java.util.OptionalInt;

public class PropagatorContraintePrerequis extends Propagator<IntVar>
{

    private ModuleDecomposeChoco module;
    private Boolean alternatif    = false;
    private Boolean noPossibility = false;
    OptionalInt finDateMinPrerequis;
    OptionalInt finDateMinFacultatif;

    public PropagatorContraintePrerequis(ModuleDecomposeChoco module)
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
    public void propagate(int evtmask) throws ContradictionException
    {

        int dateEnCours   = getVar(0).getValue();
        int dateMaxFort   = 0;
        int dateMaxFaible = 0;
        int finalDateMax  = getVar(0).getUB();


        finDateMinPrerequis = module.getModuleRequis().stream().mapToInt(m -> m.getFin().getLB()).max();
        finDateMinFacultatif = module.getModuleFacultatif().stream().mapToInt(m -> m.getFin().getLB()).max();

        if (finDateMinPrerequis.isPresent() && finDateMinPrerequis.getAsInt() > finalDateMax)
        {
            noPossibility = true;
        }

        if (!noPossibility && finDateMinFacultatif.isPresent() && finDateMinFacultatif.getAsInt() > finalDateMax)
        {
            noPossibility = true;
        }

        if (!alternatif)
        {
            /*for (ModuleDecomposeChoco moduleRequis: module.getModuleRequis() ) {
                moduleRequis.getEnd().updateUpperBound(getVar(0).getValue(), this);
            }

            for (ModuleDecomposeChoco moduleFacultatif: module.getModuleRequis() ) {
                moduleFacultatif.getEnd().updateUpperBound(getVar(0).getValue(), this);
            }*/

            OptionalInt LBFort   = module.getModuleRequis().stream().mapToInt(m -> m.getFin().getValue()).max();
            OptionalInt LBFaible = module.getModuleFacultatif().stream().mapToInt(m -> m.getFin().getValue()).max();

            if (LBFort.isPresent())
            {
                dateMaxFort = LBFort.getAsInt();
            }
            if (LBFaible.isPresent())
            {
                dateMaxFaible = LBFaible.getAsInt();
            }

            if (dateMaxFort > 0 || dateMaxFaible > 0)
            {
                getVar(0).removeInterval(0, (dateMaxFort < dateMaxFaible && dateMaxFaible > dateEnCours) ? dateMaxFort : dateMaxFaible == 0 ? dateMaxFort : dateMaxFaible, this);
                //getVar(0).updateLowerBound((dateMaxFort < dateMaxFaible && dateMaxFaible > dateEnCours) ? dateMaxFort : dateMaxFaible == 0 ? dateMaxFort : dateMaxFaible, this);
            }

        }

    }

    @Override
    public ESat isEntailed()
    {

        OptionalInt LBFort        = module.getModuleRequis().stream().mapToInt(m -> m.getFin().getValue()).max();
        OptionalInt LBFaible      = module.getModuleFacultatif().stream().mapToInt(m -> m.getFin().getValue()).max();
        int         dateMaxFort   = 0;
        int         dateMaxFaible = 0;

        if (LBFort.isPresent())
        {
            dateMaxFort = LBFort.getAsInt();
        }
        if (LBFaible.isPresent())
        {
            dateMaxFaible = LBFaible.getAsInt();
        }

        if (getVar(0).getValue() > dateMaxFort && getVar(0).getValue() > dateMaxFaible)
        {
            return ESat.TRUE;
        }
        return ESat.UNDEFINED;
    }
}
