package solver;


import models.common.*;
import models.generator.input.Generator;
import models.generator.output.Calendar;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.objective.ParetoOptimizer;
import org.chocosolver.solver.search.loop.monitors.IMonitorSolution;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.criteria.Criterion;
import solver.contraintes.ContrainteChoco;
import solver.modelChoco.CoursChoco;
import solver.modelChoco.CoursChocoStagiaire;
import solver.modelChoco.ModuleChoco;
import utils.DateTimeHelper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChocoGenerator
{

    private final ChocoModelInitialiser model;
    private final int nbModules;
    private Generator problem;
    private Set<ChocoGeneratorListener> listeners = new HashSet<>();
    private List<Calendar> calendriersTrouve = new ArrayList<>();


    public ChocoGenerator(Generator problem) {

        this.problem = problem;
        this.model = new ChocoModelInitialiser(problem)
        {
            @Override
            public List<ModuleChoco> transformModuleOfTraining(List<Module> moduleOfTraining)
            {
                return moduleOfTraining.stream().filter(m -> m.getListClasses().size() > 0)
                    .flatMap(
                            m -> IntStream.range(0, (m.getListClasses().stream().mapToInt(Classes::getRealDuration).max().getAsInt() / m.getListClasses().stream().mapToInt(Classes::getWorkingDayDuration).min().getAsInt()))
                                    .mapToObj(i -> new ModuleChoco(m, getModel(), i))).collect(Collectors.toList());
            }
        };
        nbModules = model.getModuleInChoco().size();
    }

    public ChocoGenerator(Generator problem, ChocoGeneratorListener listener) {
        this(problem);
        listeners.add(listener);
    }

    public void addListener(ChocoGeneratorListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ChocoGeneratorListener listener) {
        listeners.remove(listener);
    }

    public List<Calendar> solve() {



        // Permet de ressortir la solution, non nécessaire pour le moment
        // Solution solution = new Solution(model);

        // Ajout du moniteur pour avoir les informations au fil de l'eau
        Solver solver = model.getModel().getSolver();


        // Essaie non significatif pour optimiser
        //ParetoOptimizer po = new ParetoOptimizer(Model.MAXIMIZE, coursIdentifier);
        //solver.plugMonitor(po);

        // Permet de récupérer le calendrier trouvé, calendrier par calendrier
        solver.plugMonitor((IMonitorSolution) () ->
        {

        });

        // Si aucune solution n'est trouvée, permet de savoir pourquoi
        //solver.showContradiction();

        // Lorsqu'une solution est trouvé, permet de comprendre le cheminement
        //solver.showDecisions();

        // Statistiques complètes
        //solver.showStatistics();

        //solver.showShortStatistics();

        // Piste d'optimisation non significatif
        //solver.findOptimalSolution(tot_dev, false);


        // Permet de s'assurer que les solutions sont différentes les unes des autres

        IntVar[] coursIdentifier = IntStream.range(0, nbModules).mapToObj(i -> model.getModuleInChoco().get(i).getCoursIdentifier()).toArray(IntVar[]::new);
        IntVar[] moduleIdentifier = IntStream.range(0, nbModules).mapToObj(i -> model.getModuleInChoco().get(i).getId()).toArray(IntVar[]::new);
        //IntVar[][] coursParModules = IntStream.range(0, nbModules).mapToObj(i -> moduleInChoco.get(i).getId()).toArray(IntVar[]::new);
        IntVar[] occurenceIdentifier = IntStream.range(0, nbModules).mapToObj(i -> model.getModuleInChoco().get(i).getOccurenceVar()).toArray(IntVar[]::new);
        IntVar[] lieux = IntStream.range(0, nbModules).mapToObj(i -> model.getModuleInChoco().get(i).getLieu()).toArray(IntVar[]::new);

        if (model.getModuleInChoco().size() ==0 || model.getModuleInChoco().stream().anyMatch(m -> m.getCoursDuModule().size() == 0)) {
            System.out.println("Au moins un module n'a aucun cours");
        }
        else
        {
            // Si tous les modules ont au moins un cours
            ParetoOptimizer po = new ParetoOptimizer(Model.MAXIMIZE, coursIdentifier);
            solver.plugMonitor(po);
            solver.setSearch(Search.conflictOrderingSearch(Search.defaultSearch(model.getModel())));

            System.out.println("Choco max : " + model.getContrainteManager().maxAlternateSearch());


            Criterion stop = new Criterion() {
                @Override
                public boolean isMet() {
                    return solver.getSolutionCount() == problem.getNumberOfCalendarToFound();
                }
            };
            int j = 0;
            int nbEssai = 0;
            int nbConstraintToFree = 1;

            System.out.println("Recherche de " + problem.getNumberOfCalendarToFound() + " calendriers avec ");
            System.out.println("- période : du " + problem.getPeriodOfTraining().getStart() + " au " + problem.getPeriodOfTraining().getEnd());
            System.out.println("- modules : " + problem.getModuleOfTraining().size());
            System.out.println("- place : " + problem.getConstraints().getPlace().getValue() );
            System.out.println("- AnnualNumberOfHour : " + problem.getConstraints().getAnnualNumberOfHour().getValue() );
            for (ConstraintPriority<Period> periodConstraintPriority : problem.getConstraints().getListPeriodeOfTrainingExclusion()) {
                System.out.println("- Periode d'exclusion : " + periodConstraintPriority.getValue() );
            }
            for (ConstraintPriority<Period> periodConstraintPriority : problem.getConstraints().getListPeriodeOfTrainingInclusion()) {
                System.out.println("- Periode obligatoire : " + periodConstraintPriority.getValue() );
            }
            for (ConstraintPriority<Student> periodConstraintPriority : problem.getConstraints().getListStudentRequired()) {
                System.out.println("- Etudiant requis : " + periodConstraintPriority.getValue() );
            }
            System.out.println("- Durée max de formation : " + problem.getConstraints().getMaxDurationOfTraining().getValue() );
            System.out.println("- Max stagiaire même entreprise : " + problem.getConstraints().getMaxStudentInTraining().getValue() );
            System.out.println("- contraintes : " + model.getContrainteManager().getContrainteParPriorite().size() );

            for (ContrainteChoco contrainteChoco : model.getContrainteManager().getContrainteParPriorite()) {
                System.out.println("- contraintes : " + contrainteChoco.getConstraintName());
            }


            while ((calendriersTrouve.size() < problem.getNumberOfCalendarToFound()) & (nbEssai < model.getContrainteManager().maxAlternateSearch())) {


                // Si une solution est trouvée
                if ( solver.solve() ) {
                    Calendar calendarTrouve = new Calendar();
                    List<CoursChoco> lesCoursTrouve = new ArrayList<>();
                    // Pour chaque module on retrouve le cours associé dans cette solution
                    for (int i = 0; i < nbModules; i++) {

                        // La valeur dans le modulesID... correspond à la valeur sélectionné par Choco
                        if (model.getModuleInChoco().get(i).getModulesWorkingDayDuration().getValue() > 0) {
                            CoursChoco coursTrouve = model.getModuleInChoco().get(i).getCoursDuModule().get(model.getModuleInChoco().get(i).getCoursId().getValue());
                            lesCoursTrouve.add(coursTrouve);
                            ClassesCalendar classesCalendar = new ClassesCalendar(coursTrouve, model.getContrainteManager().getContraintesFausses(model.getModuleInChoco().get(i)));
                            calendarTrouve.addCours(classesCalendar);

                            listeners.forEach(l -> l.foundCours(classesCalendar));
                        }
                    }

                    // tri des cours par date de début
                    lesCoursTrouve.sort(Comparator.comparing(CoursChocoStagiaire::getDebut));
                    calendarTrouve.getCours().sort(Comparator.comparing(o -> DateTimeHelper.toInstant(o.getStart())));

                    calendarTrouve.setConstraints(model.getContrainteManager().getContraintesFausses());

                    if (!compare(calendriersTrouve, calendarTrouve)) {
                        calendriersTrouve.add(calendarTrouve);
                        // Lors de l'utilisation de modules scindés, pour ne pas avoir les mêmes résultats sur les différents modules portant le même id
                        // Ajout de contrainte pour que les prochains résultats ne retourne pas les mêmes modules
                        // Toujours sur le modules le moins fort

                        List<org.chocosolver.solver.constraints.Constraint> contraintes = new ArrayList<>();

                        // Crée des contraintes sur chaque module pour que la suite de cours d'un même module ne soit pas sélectionné de nouveau
                        model.getModuleInChoco().stream()
                                .filter(m -> m.getModulesWorkingDayDuration().getValue() > 0)
                                .forEach( module ->
                                        contraintes.add(
                                                model.getModel().and(
                                                        model.getModuleInChoco().stream()
                                                                .filter(m -> module.getIdModule() == m.getIdModule() )
                                                                .map(m -> model.getModel().arithm(m.getCoursId(), "!=", module.getCoursId().getValue()))
                                                                .toArray(Constraint[]::new)
                                                )
                                        )
                                );
                        //  solver.reset();

                        model.getModel().or(contraintes.stream().toArray(Constraint[]::new)).post();
                        System.out.println("Calendrier trouvé à l'essai " + nbEssai);
                        listeners.forEach(l -> l.foundCalendar(calendarTrouve));
                    }
                    else
                    {
                        System.out.println("Doublon trouvé essai " + nbEssai);
                    }


                }
                else
                {
                    model.getContrainteManager().alternateSearch(nbEssai);
                    solver.reset();
                }
                nbEssai++;

            }
            System.out.println("Nombre d'essai parcouru " + nbEssai);
        }



        return calendriersTrouve;

    }


    private Boolean compare(List<Calendar> calendars, Calendar calendar) {
        final Boolean[] doublon = {false};

        List<Calendar> autresCalendars = new ArrayList<>(calendars);
        autresCalendars.remove(calendar);
        List<String> stringStreamCalendar = calendar.getCours().stream().map(c2 -> c2.getIdClasses()).collect(Collectors.toList());
        autresCalendars.stream().filter(cal -> cal.getCours().stream().map(c -> c.getIdClasses()).allMatch(classe -> stringStreamCalendar.contains(classe))).forEach(c -> doublon[0] = true);


        return doublon[0];
    }



    private List<Classes> rechercheCours(IntVar idModule, IntVar debut, IntVar fin, IntVar periodeIdentifier, IntVar lieux) {

        return model.getModuleInChoco().stream().filter(m -> m.getIdModule() == idModule.getValue()).flatMap(m -> m.getCoursDuModule().stream()).filter(
                cours ->
                        cours.getDebut() == debut.getValue() && cours.getFin() == fin.getValue() && cours.getCoursIdentifier() == periodeIdentifier.getValue() && cours.getLieu() == lieux.getValue()
        ).map(cours -> cours.getClasses()).collect(Collectors.toList());

    }


}
