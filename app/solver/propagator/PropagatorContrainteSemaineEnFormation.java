package solver.propagator;

import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.constraints.nary.PropIntValuePrecedeChain;
import org.chocosolver.solver.constraints.reification.PropConditionnal;
import org.chocosolver.solver.constraints.unary.PropMemberEnum;
import org.chocosolver.solver.constraints.unary.PropNotMemberBound;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.SetVar;
import org.chocosolver.solver.variables.Variable;
import org.chocosolver.util.ESat;
import solver.modelChoco.ModuleChoco;

import java.util.List;

public class PropagatorContrainteSemaineEnFormation  {


    private ModuleChoco module;
    private List<ModuleChoco> AutreModules;
    private int nbSemaineFormation;
    private int nbSemaineEntreprise;

    public PropagatorContrainteSemaineEnFormation (ModuleChoco module, List<ModuleChoco> modules, int nbSemaineFormation, int nbSemaineEntreprise) {
        modules.stream().map(m -> m.getDebut()).toArray(IntVar[]::new);
        this.module = module;
        this.AutreModules = modules;
        this.AutreModules.remove(module);
        this.nbSemaineFormation = nbSemaineFormation;
        this.nbSemaineEntreprise = nbSemaineEntreprise;

    }

}
