package solver.propagator;

import org.chocosolver.solver.constraints.unary.PropMemberEnum;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.events.IntEventType;
import org.chocosolver.util.ESat;
import org.chocosolver.util.iterators.DisposableValueIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class PropagatorContrainteLieu extends PropMemberEnum {
    List<Integer> lieuxDuModule = new ArrayList<>();
    int lieuPrefere;
    public List<Integer> autreLieuPossible = null;
    private Boolean alternatif = false;

    public PropagatorContrainteLieu(IntVar var, Integer lieuPrefere, List<Integer> lieuxPossible) {
        super(var, new int[] {lieuPrefere});

        this.lieuPrefere = lieuPrefere;
        if ( autreLieuPossible == null){
            autreLieuPossible = lieuxPossible;
        }

        DisposableValueIterator valueIterator = var.getValueIterator(true);
        while (valueIterator.hasNext())
        {
            this.lieuxDuModule.add(valueIterator.next());
        }


        autreLieuPossible = autreLieuPossible.stream().filter(al -> lieuxDuModule.contains(al)).collect(Collectors.toList());
    }

    public void searchAternatif(Boolean alternatif)
    {
        this.alternatif = alternatif;
    }

    @Override
    public int getPropagationConditions(int vIdx) {
        return IntEventType.combine(IntEventType.INSTANTIATE, IntEventType.BOUND);
    }

    @Override
    public void propagate(int evtmask) throws ContradictionException {
        if (!alternatif)
            for (IntVar var : getVars()) {
                for (Integer integer : lieuxDuModule) {
                    if (integer != lieuPrefere) {
                        var.removeValue(integer, this);
                    }

                }
            }

        /*for (IntVar var : getVars()) {
            for (Integer integer : lieuxDuModule) {
                if (alternatif) {
                    if (integer != lieuPrefere && integer.compareTo(autreLieuPossible.get(0)) != 0)
                        var.removeValue(integer, this);
                }
                else if (integer != lieuPrefere) {
                    var.removeValue(integer, this);
                }

            }
        }*/



        /*get
        lieuxDuModule.stream().filter(l -> Arrays.stream(getVIndices()).filter(value -> l == value).count() > 0).forEach(l -> getVar(0).removeValue(l, this));
                IntStream.range(0, getVIndices().length - 1).forEach(i -> (getVIndice(i)) ).forEach( l -> );
        getVar(0).removeValue()

        if (isActive() == true) {
            int LB = alternatif == -1 ? b : alternatif;
            int GB = alternatif == -1 ? b : alternatif;
            if (LB == -1)
                LB = lieux;
            getVar(0).
        }
*/
    }

    @Override
    public void propagate(int idxVarInProp, int mask) throws ContradictionException {
        propagate(mask);
        super.propagate(idxVarInProp, mask);
    }

    @Override
    public ESat isEntailed() {
        if (getVar(0).getValue() == lieuPrefere) {

            return ESat.TRUE;
        }
        else if ((alternatif == true) && autreLieuPossible.get(0).compareTo(getVar(0).getValue()) == 0)
        {
            return ESat.TRUE;
        }
        else {
            return ESat.UNDEFINED;
        }


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

