package solver.contraintes;

import models.common.Classes;
import models.common.ConstraintPriority;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleChoco;
import solver.propagator.PropagatorContrainteLieu;

import java.util.*;
import java.util.stream.Collectors;

public class ContrainteChocoLieu extends ContrainteChoco<Integer>
{

    private List<Integer>                              lieuxPossible ;
    private Map<ModuleChoco, PropagatorContrainteLieu> propagators   = new HashMap<>();

    public ContrainteChocoLieu(Model model, ConstraintPriority<Integer> lieu, List<ModuleChoco> modulesInChoco)
    {
        super(model, lieu, modulesInChoco);
        Map<Integer, Long> lieuxPossibleGroupe = modulesInChoco.stream()
                .flatMap(m -> m.getModule().getListClasses().stream())
                .map(Classes::getIdPlace)
                .filter(l -> !l.equals(lieu.getValue()))
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        Comparator<Map.Entry<Integer, Long>> valueComparator = (e1, e2) -> {
            Long v1 = e1.getValue();
            Long v2 = e2.getValue();
            return v1.compareTo(v2);
        };

        List<Map.Entry<Integer, Long>> listOfEntries = new ArrayList<>(lieuxPossibleGroupe.entrySet());
        listOfEntries.sort(valueComparator);

        // sorting HashMap by values using comparator
        Collections.reverse(listOfEntries);

        this.lieuxPossible = listOfEntries.stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }

    @Override
    public Constraint createConstraint(ModuleChoco module)
    {
        PropagatorContrainteLieu prop       = new PropagatorContrainteLieu(module.getLieu(), getContraintePriority().getValue(), lieuxPossible);
        Constraint constraint = new Constraint("Lieu " + module.getIdModule(), prop);
        propagators.put(module, prop);
        return constraint;
    }

    @Override
    public String getConstraintName()
    {
        return language.getString("contrainte.idPlace");
    }

    @Override
    public void enableAlternateSearch(ModuleChoco module)
    {
        propagators.get(module).searchAternatif((true));
    }

    @Override
    public void disableAlternateSearch(ModuleChoco module)
    {
        propagators.get(module).searchAternatif((false));
    }

    @Override
    public Boolean isAlternateSearch(ModuleChoco module)
    {
        return propagators.get(module).isAternatifSearch();
    }

    // https://stackoverflow.com/questions/46468877/choco-solver-propogation-and-search-strategy-interaction
}
