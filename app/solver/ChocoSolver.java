package solver;

import models.output.Calendrier;
import models.input.Cours;
import models.output.CoursCalendrier;
import models.input.Probleme;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.search.loop.monitors.IMonitorSolution;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.search.strategy.assignments.DecisionOperatorFactory;
import org.chocosolver.solver.search.strategy.decision.Decision;
import org.chocosolver.solver.search.strategy.strategy.AbstractStrategy;
import org.chocosolver.solver.search.strategy.strategy.ConflictOrderingSearch;
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


    // Création des modèles de données des modules pour Choco
        // Transforme les modules en objet préparé pour Choco
        moduleInChoco = probleme.getModulesFormation().stream().filter(m -> m.getCours().size() >0).map(m -> new ModuleChoco(m, model)).collect(Collectors.toList());
        moduleInChoco.forEach(m -> m.setModule(moduleInChoco));
        nbModules = moduleInChoco.size();

    //Traitement des contraintes

        //Période de formation
        int debutFormation = DateTimeHelper.toDays(probleme.getPeriodeFormation().getInstantDebut());
        int finFormation = DateTimeHelper.toDays(probleme.getPeriodeFormation().getInstantFin());


    // Création des jeux de données basé sur tous les cours pour Choco
        List<CoursChoco> coursChocoAutorise = moduleInChoco.stream().flatMap(m -> m.getCoursDuModule().stream()).collect(Collectors.toList());
        int[][] coursListeBlanche = new int[coursChocoAutorise.size()][];


        for (int i=0; i < coursChocoAutorise.size(); i++ )
        {
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
            contrainteManager = new ContrainteManager(model, probleme, moduleInChoco);
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
            table[i] = new IntVar[] {
                    moduleInChoco.get(i).getId(),
                    moduleInChoco.get(i).getCoursId(),
                    moduleInChoco.get(i).getDebut(),
                    moduleInChoco.get(i).getFin(),
                    moduleInChoco.get(i).getCoursIdentifier(),
                    moduleInChoco.get(i).getLieu(),
                    moduleInChoco.get(i).getDuration(),
                    moduleInChoco.get(i).getNbHeure(),
                    moduleInChoco.get(i).getNbSemaine()};
            model.table(table[i], tuple ).post();

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
            Calendrier calendrierTrouve = new Calendrier();
            List<Cours> lesCoursTrouve = new ArrayList<>();
            for (int i = 0; i < nbModules; i++) {
                // La valeur dans le modulesID... correspond à la valeur sélectionné par Choco
                Cours coursTrouve = moduleInChoco.get(i).getCoursDuModule().get(moduleInChoco.get(i).getCoursId().getValue()).getCours();

                lesCoursTrouve.add(coursTrouve);
                calendrierTrouve.addCours(new CoursCalendrier(coursTrouve, finalContrainteManager.getContraintes(moduleInChoco.get(i))));

                listeners.forEach(l -> l.foundCours(coursTrouve));
            }

            // tri des cours par date de début
            Collections.sort(lesCoursTrouve, Comparator.comparing(o -> o.getPeriode().getInstantDebut()) );
            for (Cours cours:lesCoursTrouve)
            {
                afficheCours(cours);
            }
            calendrierTrouve.getCours().sort(Comparator.comparing(o -> lesCoursTrouve.indexOf(lesCoursTrouve.stream().filter( c -> c.getIdCours().contentEquals(o.getIdCours())))));

            calendrierTrouve.setConstraint(finalContrainteManager.getContraintes());
            calendriersTrouve.add(calendrierTrouve);

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
                .collect(Collectors.toMap(i -> moduleInChoco.get(i).getLieu(), i -> moduleInChoco.get(i).getLieu().getValue()));

        IntVar[] coursIdentifier = IntStream.range(0, nbModules).mapToObj( i -> moduleInChoco.get(i).getCoursIdentifier()).toArray(IntVar[]::new);
        IntVar[] lieux = IntStream.range(0, nbModules).mapToObj( i -> moduleInChoco.get(i).getLieu()).toArray(IntVar[]::new);

        solver.setSearch(Search.conflictOrderingSearch(Search.intVarSearch(
                variables -> Arrays.stream(lieux)
                        .filter(v -> !v.isInstantiated())
                        .filter(v -> v.getValue() == probleme.getContrainte().getIdLieu().getValue())
                        .findFirst()
                        .orElse(null),
                var -> var.getValue(),
                DecisionOperatorFactory.makeIntEq(),
                coursIdentifier
        )));



        System.out.println("Choco max : " + contrainteManager.maxAlternateSearch());

        int j = 0;
        int nbEssai = 0;
        int nbConstraintToFree = 1;
        while ((calendriersTrouve.size() < nbCalendrier) & (nbEssai < contrainteManager.maxAlternateSearch()))
        {

            if (solver.solve() == false) {

                contrainteManager.alternateSearch(nbEssai);
                solver.reset();
            }
            nbEssai++;
        }
        System.out.println("Essai " + nbEssai);
        return calendriersTrouve;

    }

    private void afficheCours(Cours c) {
        System.out.printf("Cours d'id %s du Module d'id %s à %d le %s au %s\n",
                c.getIdCours(),
                c.getIdModule(),
                c.getLieu(),
                c.getPeriode().getDebut(),
                c.getPeriode().getFin());

    }

    private List<Cours> rechercheCours(IntVar idModule, IntVar debut, IntVar fin, IntVar periodeIdentifier, IntVar lieux) {

        return moduleInChoco.stream().filter(m -> m.getIdModule() == idModule.getValue()).flatMap(m -> m.getCoursDuModule().stream()).filter(
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
