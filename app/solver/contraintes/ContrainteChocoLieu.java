package solver.contraintes;

import models.input.ConstraintPriority;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleDecomposeChoco;
import solver.propagator.PropagatorContrainteLieu;

import java.util.*;
import java.util.stream.Collectors;

public class ContrainteChocoLieu extends ContrainteChoco<Integer>
{

    private List<Integer>                                       lieuxPossible = new ArrayList<>();
    private Map<ModuleDecomposeChoco, PropagatorContrainteLieu> propagators   = new HashMap<>();

    public ContrainteChocoLieu(Model model, ConstraintPriority<Integer> lieu, List<ModuleDecomposeChoco> modulesInChoco)
    {
        super(model, lieu, modulesInChoco);
        Map<Integer, Long> lieuxPossibleGroupe = modulesInChoco.stream()
                .flatMap(m -> m.getModule().getListClasses().stream())
                .map(c -> new Integer(c.getIdPlace()))
                .filter(l -> l != lieu.getValue())
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        Comparator<Map.Entry<Integer, Long>> valueComparator = new Comparator<Map.Entry<Integer, Long>>()
        {

            @Override
            public int compare(Map.Entry<Integer, Long> e1, Map.Entry<Integer, Long> e2)
            {
                Long v1 = e1.getValue();
                Long v2 = e2.getValue();
                return v1.compareTo(v2);
            }
        };

        List<Map.Entry<Integer, Long>> listOfEntries = new ArrayList<Map.Entry<Integer, Long>>(lieuxPossibleGroupe.entrySet());
        Collections.sort(listOfEntries, valueComparator);

        // sorting HashMap by values using comparator
        Collections.reverse(listOfEntries);

        this.lieuxPossible = listOfEntries.stream().map(e -> e.getKey()).collect(Collectors.toList());
    }

    @Override
    public Constraint createConstraint(ModuleDecomposeChoco module)
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
    public void enableAlternateSearch(ModuleDecomposeChoco module)
    {
        propagators.get(module).searchAternatif((true));
    }

    @Override
    public void disableAlternateSearch(ModuleDecomposeChoco module)
    {
        propagators.get(module).searchAternatif((false));
    }

    @Override
    public Boolean isAlternateSearch(ModuleDecomposeChoco module)
    {
        return propagators.get(module).isAternatifSearch();
    }

    // https://stackoverflow.com/questions/46468877/choco-solver-propogation-and-search-strategy-interaction
}
