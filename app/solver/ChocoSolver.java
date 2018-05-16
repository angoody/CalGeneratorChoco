package solver;

import models.Calendrier;
import models.Cours;
import models.Probleme;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.search.loop.monitors.IMonitorSolution;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.search.strategy.assignments.DecisionOperatorFactory;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import utils.DateTimeHelper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChocoSolver {

    private Probleme probleme;
    int nbModules;
    private Model model;
    private Set<ChocoSolverListener> listeners = new HashSet<>();
    List<ModuleChoco> moduleInChoco;


    public ChocoSolver(Probleme probleme){
        this.probleme = probleme;
    }

    public ChocoSolver(Probleme probleme, ChocoSolverListener listener){
        this(probleme);
        listeners.add(listener);
    }

    public void addListener(ChocoSolverListener listener)
    {
        listeners.add(listener);
    }

    public void removeListener(ChocoSolverListener listener)
    {
        listeners.remove(listener);
    }



    public List<Calendrier> resoudre (int nbCalendrier) {

        List<Calendrier> calendriersTrouve = new ArrayList<>();
        model = new Model("Generer calendrier");
        nbModules = probleme.getModulesFormation().size();
        int nbMaxStagiaireEntreprise = probleme.getContraintes().stream().mapToInt(c -> c.getMaxStagiaireEntrepriseEnFormation()).min().getAsInt();
        int debutFormation = DateTimeHelper.InstantToDays(probleme.getPeriodeFormation().getInstantDebut());
        int finFormation = DateTimeHelper.InstantToDays(probleme.getPeriodeFormation().getInstantFin());

        // Transforme les modules en objet préparé pour Choco

        moduleInChoco = probleme.getModulesFormation().stream().map(m -> new ModuleChoco(m, probleme.getContraintes(), probleme.getPeriodeFormation())).collect(Collectors.toList());

        new ArrayList<>();

        // Filtre sur les contraintes :
        // les lieux autorisés
        List<Integer> listLieuxAutorises = probleme.getContraintes().stream().flatMap(c -> c.getIdLieux().stream()).collect(Collectors.toList());

        // les cours autorisés des stagiaires recquis
        Set<Cours> coursAutorise = probleme.getContraintes().stream().flatMap(c -> c.getStagiairesRecquis().stream().flatMap(stagiaire -> stagiaire.getCours().stream())).collect(Collectors.toSet());

        // les cours dont le nombre de stagiaire a atteint le nombre maximum
        Set<Cours> coursRefuse = probleme.getContraintes().stream()
                .flatMap(c -> c.getStagiairesEntreprise().stream()
                        .flatMap(stagiaire -> stagiaire.getCours().stream()))
                .collect(Collectors.groupingBy( e->e, Collectors.counting())).entrySet().stream()
                .filter(c -> c.getValue() >= nbMaxStagiaireEntreprise ).map(c -> c.getKey()).collect(Collectors.toSet());

        // Création des jeux de données autorisé pour Choco
        int nbCours = moduleInChoco.stream().flatMap(m -> m.getCoursDuModule().stream()).toArray().length;
        int[][] cours = new int[nbCours][];

        int z = 0;
        for (int i=0; i < nbModules; i++ )
        {
            for (int j=0; j < moduleInChoco.get(i).getCoursDuModule().size(); j++)
            {
                cours[z] = moduleInChoco.get(i).getCoursDuModule().get(j).getInt();
                z++;
            }
        }


        IntVar[] modulesDebut = IntStream
                .range(0, nbModules)
                .mapToObj(i ->
                        model.intVar(
                                "Debut " + moduleInChoco.get(i).getId(),
                                moduleInChoco.get(i).getDebut()))
                .toArray(IntVar[]::new);


        IntVar[] coursIdentifier = IntStream
                .range(0, nbModules)
                .mapToObj(i ->
                        model.intVar(
                                "Module " + moduleInChoco.get(i).getId(),
                                moduleInChoco.get(i).getCoursIdentifier()))
                .toArray(IntVar[]::new);


        IntVar[] modulesFin = IntStream
                .range(0, nbModules)
                .mapToObj(i ->
                        model.intVar(
                                "Fin " + moduleInChoco.get(i).getId(),
                                moduleInChoco.get(i).getFin()))
                .toArray(IntVar[]::new);

        IntVar[] modulesLieu = IntStream
                .range(0, nbModules)
                .mapToObj(i ->
                        model.intVar(
                                "Fin " + moduleInChoco.get(i).getId(),
                                moduleInChoco.get(i).getLieu()))
                .toArray(IntVar[]::new);

        IntVar[] modulesID = IntStream
                .range(0, nbModules)
                .mapToObj(i ->
                        model.intVar(
                                "ID module " + moduleInChoco.get(i).getId(),
                                moduleInChoco.get(i).getId()))
                .toArray(IntVar[]::new);

        IntVar[] modulesDuration = IntStream
                .range(0, nbModules)
                .mapToObj(i ->
                        model.intVar(
                                "Duration " + moduleInChoco.get(i).getId(),
                                moduleInChoco.get(i).getDuration()))
                .toArray(IntVar[]::new);

        IntVar[] modulesNbSemaine = IntStream
                .range(0, nbModules)
                .mapToObj(i ->
                        model.intVar(
                                "Nb Semaine " + moduleInChoco.get(i).getId(),
                                moduleInChoco.get(i).getNbSemaine()))
                .toArray(IntVar[]::new);

        IntVar[] modulesNbHeure = IntStream
                .range(0, nbModules)
                .mapToObj(i ->
                        model.intVar(
                                "Nb Heure " + moduleInChoco.get(i).getId(),
                                moduleInChoco.get(i).getNbHeure()))
                .toArray(IntVar[]::new);

        //IntVar tot_dev = model.intVar("tot_dev", 0, IntVar.MAX_INT_BOUND);
        // Constraint posting
        // one module to be schedule at a time:
        //model.allDifferent(modulesDebut).post();
        //model.allDifferent(modulesFin).post();

        // Permet de restreindre la recherche à des cours avec des périodes différentes pour chaque module
        //model.allDifferent(tousLesIdModule).post();
        //model.allDifferent(coursIdentifier).post();


        /*IntVar[] table = { tousLesIdModule, tousLesDebut, toutesLesFin, tousLesIdentifiants, tousLesLieux};
        tousLesLieux.eq(lieuxAutorise).post();
        Tuples tuple = new Tuples(cours, true);
        model.table(table, tuple ).post();*/
        // pour chaque module 'i'
        IntVar[][] table = new IntVar[nbModules][];
        Tuples tuple = new Tuples(cours, true);

        for (int i = 0; i < nbModules; i++) {

            // La liste des cours sont les seuls enregistrements autorisées
            table[i] = new IntVar[] { modulesID[i], modulesDebut[i], modulesFin[i], coursIdentifier[i], modulesLieu[i], modulesDuration[i], modulesNbHeure[i], modulesNbSemaine[i]};
            model.table(table[i], tuple ).post();


            // Contrainte de lieux
            int finalI = i;
            Constraint[] contraintesDeLieux = IntStream.range(0, listLieuxAutorises.size()).mapToObj(a -> model.arithm(modulesLieu[finalI], "=", listLieuxAutorises.get(a))).toArray(Constraint[]::new);
            model.or(contraintesDeLieux).post();


            // Début et fin de la formation
            modulesDebut[i].ge(debutFormation).post();
            modulesFin[i].le(finFormation).post();

            //modulesLieu[i].eq(lieuxAutorise).post();
            // Pour chaque module qui n'a pas été traité
            // On ajoute une contrainte entre les modules,
            // soit la fin du suivant est inférieur au début du module en cours
            // soit la fin du en cours est inférieur au début du suivant
            // Cette contraintes évite les chevauchements
            for (int j = i + 1; j < nbModules; j++) {
                model.or(
                        model.arithm(modulesFin[i], "<=", modulesDebut[j]),
                        model.arithm(modulesFin[j], "<=", modulesDebut[i])
                ).post();
            }

        }

        // Permet de ressortir la solution, non nécessaire pour le moment
        // Solution solution = new Solution(model);

        // Ajout du moniteur pour avoir les informations au fil de l'eau
        Solver solver = model.getSolver();


        // Essaie non significatif pour optimiser
        //ParetoOptimizer po = new ParetoOptimizer(Model.MAXIMIZE, coursIdentifier);
        //solver.plugMonitor(po);

        // Permet de récupérer le calendrier trouvé, calendrier par calendrier
        solver.plugMonitor((IMonitorSolution) () -> {
            List<Cours> lesCoursChoisi = new ArrayList<>();
            for (int i = 0; i < nbModules; i++) {
                // La valeur dans le modulesID... correspond à la valeur sélectionné par Choco
                rechercheCours(modulesID[i], modulesDebut[i], modulesFin[i], coursIdentifier[i], modulesLieu[i]).forEach(
                        c ->
                        {
                            lesCoursChoisi.add(c);
                            listeners.forEach(l -> l.foundCours(c));
                        }

                );
            }
            Calendrier calendrierTrouve = new Calendrier(lesCoursChoisi.stream().sorted(Comparator.comparing(o -> o.getPeriode().getInstantDebut())).collect(Collectors.toList()));
            calendriersTrouve .add(calendrierTrouve);
            listeners.forEach(l -> l.foundCalendar(calendrierTrouve));
        });

        // Si aucune solution n'est trouvée, permet de savoir pourquoi
        //solver.showContradiction();

        // Lorsqu'une solution est trouvé, permet de comprendre le cheminement
        //solver.showDecisions();

        // Statistiques complètes
        //solver.showStatistics();

        solver.showShortStatistics();

        // Piste d'optimisation non significatif
        //solver.findOptimalSolution(tot_dev, false);


        // Permet de s'assurer que les solutions sont différentes les unes des autres
        Map<IntVar, Integer> map = IntStream
                .range(0, nbModules)
                .boxed()
                .collect(Collectors.toMap(i -> coursIdentifier[i], i -> coursIdentifier[i].getValue()));
        solver.setSearch(Search.intVarSearch(
                variables -> Arrays.stream(variables)
                        .filter(v -> !v.isInstantiated())
                        .min((v1, v2) -> closest(v2, map) - closest(v1, map))
                        .orElse(null),
                var -> closest(var, map),
                DecisionOperatorFactory.makeIntEq(),
                coursIdentifier
        ));

        for (int i = 0; i < nbCalendrier; i++) solver.solve();

        return calendriersTrouve;

    }

    private List<Cours> rechercheCours(IntVar idModule, IntVar debut, IntVar fin, IntVar periodeIdentifier, IntVar lieux) {

        return moduleInChoco.stream().filter(m -> m.getId() == idModule.getValue()).flatMap(m -> m.getCoursDuModule().stream()).filter(
                cours ->
                        cours.getDebut() == debut.getValue() && cours.getFin() == fin.getValue() && cours.getCoursIdentifier() == periodeIdentifier.getValue() && cours.getLieu() == lieux.getValue()
        ).map(cours -> cours.getCours()).collect(Collectors.toList());

    }

    private static int closest(IntVar var, Map<IntVar, Integer> map) {
        int target = map.get(var);
        if (var.contains(target)) {
            return target;
        } else {
            int p = var.previousValue(target);
            int n = var.nextValue(target);
            return Math.abs(target - p) < Math.abs(n - target) ? p : n;
        }
    }

}
