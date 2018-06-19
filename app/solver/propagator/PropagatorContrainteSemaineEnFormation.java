package solver.propagator;

import org.chocosolver.solver.variables.IntVar;
import solver.modelChoco.ModuleDecomposeChoco;

import java.util.List;

public class PropagatorContrainteSemaineEnFormation  {


    private ModuleDecomposeChoco       module;
    private List<ModuleDecomposeChoco> AutreModules;
    private int                        nbSemaineFormation;
    private int                        nbSemaineEntreprise;

    public PropagatorContrainteSemaineEnFormation (ModuleDecomposeChoco module, List<ModuleDecomposeChoco> modules, int nbSemaineFormation, int nbSemaineEntreprise) {
        modules.stream().map(m -> m.getDebut()).toArray(IntVar[]::new);
        this.module = module;
        this.AutreModules = modules;
        this.AutreModules.remove(module);
        this.nbSemaineFormation = nbSemaineFormation;
        this.nbSemaineEntreprise = nbSemaineEntreprise;

    }

}
