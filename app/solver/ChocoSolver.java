package solver;

import models.input.Problem;
import models.output.Calendar;
import models.input.Classes;
import models.output.ClassesCalendar;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.search.loop.monitors.IMonitorSolution;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;
import solver.contraintes.ContrainteManager;
import solver.modelChoco.CoursChoco;
import solver.modelChoco.ModuleChoco;
import utils.DateTimeHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChocoSolver {

    private Problem problem;
    int nbModules;
    private Model model;
    private Set<ChocoSolverListener> listeners = new HashSet<>();
    List<ModuleChoco> moduleInChoco;


    public ChocoSolver(Problem problem) {
        this.problem = problem;
    }

    public ChocoSolver(Problem problem, ChocoSolverListener listener) {
        this(problem);
        listeners.add(listener);
    }

    public void addListener(ChocoSolverListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ChocoSolverListener listener) {
        listeners.remove(listener);
    }

    public List<Calendar> solve() {
        return solve(problem.getNumberOfCalendarToFound());
    }

    public List<Calendar> solve(int nbCalendrier) {


        List<Calendar> calendriersTrouve = new ArrayList<>();
        model = new Model("Generer calendrier");


        // Création des modèles de données des modules pour Choco
        // Transforme les modules en objet préparé pour Choco
        moduleInChoco = problem.getModuleOfTraining().stream().filter(m -> m.getListClasses().size() > 0).map(m -> new ModuleChoco(m, model)).collect(Collectors.toList());
        moduleInChoco.forEach(m -> m.setModule(moduleInChoco));
        nbModules = moduleInChoco.size();

        //Traitement des contraintes

        //Période de formation
        int debutFormation = DateTimeHelper.toDays(problem.getPeriodOfTraining().getStart());
        int finFormation = DateTimeHelper.toDays(problem.getPeriodOfTraining().getEnd());


        // Création des jeux de données basé sur tous les cours pour Choco
        List<CoursChoco> coursChocoAutorise = moduleInChoco.stream().flatMap(m -> m.getCoursDuModule().stream()).collect(Collectors.toList());
        int[][] coursListeBlanche = new int[coursChocoAutorise.size()][];


        for (int i = 0; i < coursChocoAutorise.size(); i++) {
            coursListeBlanche[i] = coursChocoAutorise.get(i).getInt();
        }
        // Création des jeux de données basé sur les périodes d'inclusion et les périodes d'exclusion


        // Création des jeux de données basé sur les stagiaires autorisé et non autorisé


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

        // Création des contraintes
        IntVar[][] table = new IntVar[nbModules][];

        // Liste blanche des cours
        Tuples tuple = new Tuples(coursListeBlanche, true);
        ContrainteManager contrainteManager = null;
        try {
            contrainteManager = new ContrainteManager(model, problem, moduleInChoco);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < nbModules; i++) {

            // La liste des cours à rechercher
            table[i] = new IntVar[]{
                    moduleInChoco.get(i).getId(),
                    moduleInChoco.get(i).getCoursId(),
                    moduleInChoco.get(i).getDebut(),
                    moduleInChoco.get(i).getFin(),
                    moduleInChoco.get(i).getCoursIdentifier(),
                    moduleInChoco.get(i).getLieu(),
                    moduleInChoco.get(i).getDuration(),
                    moduleInChoco.get(i).getNbHeure(),
                    moduleInChoco.get(i).getNbSemaine()};
            model.table(table[i], tuple).post();

            // Début et fin de la formation
            moduleInChoco.get(i).getDebut().ge(debutFormation).post();
            moduleInChoco.get(i).getFin().le(finFormation).post();


            //modulesLieu[i].eq(lieuxAutorise).post();
            // Pour chaque module qui n'a pas été traité
            // On ajoute une contrainte entre les modules,
            // soit la fin du suivant est inférieur au début du module en cours
            // soit la fin du en cours est inférieur au début du suivant
            // Cette contraintes évite les chevauchements
            for (int j = i + 1; j < nbModules; j++) {
                model.or(
                        model.arithm(moduleInChoco.get(i).getFin(), "<=", moduleInChoco.get(j).getDebut()),
                        model.arithm(moduleInChoco.get(j).getFin(), "<=", moduleInChoco.get(i).getDebut())
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
        ContrainteManager finalContrainteManager = contrainteManager;
        solver.plugMonitor((IMonitorSolution) () -> {
            Calendar calendarTrouve = new Calendar();
            List<CoursChoco> lesCoursTrouve = new ArrayList<>();
            for (int i = 0; i < nbModules; i++) {
                // La valeur dans le modulesID... correspond à la valeur sélectionné par Choco
                CoursChoco coursTrouve = moduleInChoco.get(i).getCoursDuModule().get(moduleInChoco.get(i).getCoursId().getValue());

                lesCoursTrouve.add(coursTrouve);
                ClassesCalendar classesCalendar = new ClassesCalendar(coursTrouve, finalContrainteManager.getContraintes(moduleInChoco.get(i)));
                calendarTrouve.addCours(classesCalendar);

                listeners.forEach(l -> l.foundCours(classesCalendar));
            }

            // tri des cours par date de début
            Collections.sort(lesCoursTrouve, Comparator.comparing(o -> o.getDebut()));
            for (CoursChoco cours : lesCoursTrouve) {
                afficheCours(cours);
            }
            Collections.sort(calendarTrouve.getCours(), Comparator.comparing(o -> DateTimeHelper.toInstant(o.getStart())));
            //.sort(Comparator.comparing(o -> lesCoursTrouve.indexOf(lesCoursTrouve.stream().filter( c -> c.getIdClasses().contentEquals(o.getIdClasses())))));

            calendarTrouve.setConstraint(finalContrainteManager.getContraintes());
            calendriersTrouve.add(calendarTrouve);

            listeners.forEach(l -> l.foundCalendar(calendarTrouve));
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
                .collect(Collectors.toMap(i -> moduleInChoco.get(i).getLieu(), i -> moduleInChoco.get(i).getLieu().getValue()));

        IntVar[] coursIdentifier = IntStream.range(0, nbModules).mapToObj(i -> moduleInChoco.get(i).getCoursIdentifier()).toArray(IntVar[]::new);
        IntVar[] lieux = IntStream.range(0, nbModules).mapToObj(i -> moduleInChoco.get(i).getLieu()).toArray(IntVar[]::new);

        solver.setSearch(Search.conflictOrderingSearch(Search.defaultSearch(model)));


                /*Search.intVarSearch(
                variables -> Arrays.stream(lieux)
                        .filter(v -> !v.isInstantiated())
                        .filter(v -> v.getValue() == problem.getContraintes().getPlace().getValue())
                        .findFirst()
                        .orElse(null),
                var -> var.getValue(),
                DecisionOperatorFactory.makeIntEq(),
                coursIdentifier
        ));*/
        //Search.conflictOrderingSearch(Search.defaultSearch(model)));
        //
        //                                                               /*


        System.out.println("Choco max : " + contrainteManager.maxAlternateSearch());

        int j = 0;
        int nbEssai = 0;
        int nbConstraintToFree = 1;
        while ((calendriersTrouve.size() < nbCalendrier) & (nbEssai < contrainteManager.maxAlternateSearch())) {

            if (nbEssai == 430) {
                j = -1;
            }
            if (solver.solve() == false) {

                contrainteManager.alternateSearch(nbEssai);
                solver.reset();
            }
            nbEssai++;
        }
        System.out.println("Essai " + nbEssai);
        return calendriersTrouve;

    }

    private void afficheCours(CoursChoco c) {
        System.out.printf("Classes d'id %s du Module d'id %s > %s à %d le %s au %s\n",
                c.getIdCours(),
                c.getIdModule(),
                String.join(",", moduleInChoco.stream()
                        .filter(m -> m.getIdModule().compareTo(c.getIdModule()) == 0)
                        .flatMap(m -> m.getModuleRequis().stream())
                        .map(m -> String.valueOf(m.getIdModule()))
                        .collect(Collectors.toList())),
                c.getLieu(),
                DateTimeHelper.toString(c.getDebut()),
                DateTimeHelper.toString(c.getFin()));

    }

    private List<Classes> rechercheCours(IntVar idModule, IntVar debut, IntVar fin, IntVar periodeIdentifier, IntVar lieux) {

        return moduleInChoco.stream().filter(m -> m.getIdModule() == idModule.getValue()).flatMap(m -> m.getCoursDuModule().stream()).filter(
                cours ->
                        cours.getDebut() == debut.getValue() && cours.getFin() == fin.getValue() && cours.getCoursIdentifier() == periodeIdentifier.getValue() && cours.getLieu() == lieux.getValue()
        ).map(cours -> cours.getClasses()).collect(Collectors.toList());

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
