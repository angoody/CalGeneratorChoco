package solver;

import models.common.ClassesCalendar;
import models.common.ConstraintRespected;
import models.common.Module;
import models.generator.input.Generator;
import models.generator.output.Calendar;
import models.verify.input.Verify;
import models.verify.output.CalendarVerify;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.exception.SolverException;
import org.chocosolver.solver.objective.ParetoOptimizer;
import org.chocosolver.solver.search.loop.monitors.IMonitorSolution;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.criteria.Criterion;
import solver.contraintes.ContrainteChoco;
import solver.contraintes.ContrainteManager;
import solver.modelChoco.CoursChoco;
import solver.modelChoco.ModuleChoco;
import utils.DateTimeHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChocoVerify
{

    private final Verify problem;
    private final ChocoModelInitialiser model;
    private final int nbModules;
    private CalendarVerify calendriersTrouve;

    public ChocoVerify(Verify problem) {

        this.problem = problem;
        this.model = new ChocoModelInitialiser(problem)
        {
            @Override
            public List<ModuleChoco> transformModuleOfTraining(List<Module> moduleOfTraining)
            {
                // Pour vérifier on crée un module par cours du module
                return moduleOfTraining.stream().filter(m -> m.getListClasses().size() > 0)
                        .flatMap(
                                m -> IntStream.range(0, m.getListClasses().size())
                                        .mapToObj(i -> new ModuleChoco(m, getModel(), i))).collect(Collectors.toList());
            }
        };
        nbModules = model.getModuleInChoco().size();
    }

    public CalendarVerify solve()
    {


        // Permet de ressortir la solution, non nécessaire pour le moment
        // Solution solution = new Solution(model);

        // Ajout du moniteur pour avoir les informations au fil de l'eau
        Solver solver = model.getModel().getSolver();


        // Essaie non significatif pour optimiser
        //ParetoOptimizer po = new ParetoOptimizer(Model.MAXIMIZE, coursIdentifier);
        //solver.plugMonitor(po);

        // Permet de récupérer le calendrier trouvé, calendrier par calendrier
        /*solver.plugMonitor((IMonitorSolution) () ->
        {

        });*/

        // Si aucune solution n'est trouvée, permet de savoir pourquoi
        //solver.showContradiction();

        // Lorsqu'une solution est trouvé, permet de comprendre le cheminement
        //solver.showDecisions();

        // Statistiques complètes
        //solver.showStatistics();

        solver.showShortStatistics();

        // Piste d'optimisation non significatif
        //solver.findOptimalSolution(tot_dev, false);

        calendriersTrouve = new CalendarVerify();

        if (model.getModuleInChoco().size() ==0 || model.getModuleInChoco().stream().anyMatch(m -> m.getCoursDuModule().size() == 0)) {
            System.out.println("Au moins un module n'a aucun cours");
        }
        else {
            // Permet de s'assurer que les solutions sont différentes les unes des autres

            /*IntVar[] coursIdentifier = IntStream.range(0, nbModules).mapToObj(i -> model.getModuleInChoco().get(i).getCoursIdentifier()).toArray(IntVar[]::new);
            IntVar[] moduleIdentifier = IntStream.range(0, nbModules).mapToObj(i -> model.getModuleInChoco().get(i).getId()).toArray(IntVar[]::new);
            //IntVar[][] coursParModules = IntStream.range(0, nbModules).mapToObj(i -> moduleInChoco.get(i).getId()).toArray(IntVar[]::new);
            IntVar[] occurenceIdentifier = IntStream.range(0, nbModules).mapToObj(i -> model.getModuleInChoco().get(i).getOccurenceVar()).toArray(IntVar[]::new);
            IntVar[] lieux = IntStream.range(0, nbModules).mapToObj(i -> model.getModuleInChoco().get(i).getLieu()).toArray(IntVar[]::new);

            ParetoOptimizer po = new ParetoOptimizer(Model.MAXIMIZE, coursIdentifier);

            solver.plugMonitor(po);

            solver.setSearch(Search.conflictOrderingSearch(Search.defaultSearch(model.getModel())));
*/

        /*if (problem.getConstraints().getPlace().getValue() != -1) {
            solver.setSearch(Search.intVarSearch(
                    variables -> Arrays.stream(lieux)
                            .filter(v -> !v.isInstantiated())
                            .filter(v -> v.getValue() == problem.getConstraints().getPlace().getValue())
                            .findFirst()
                            .orElse(null),
                    var -> var.getValue(),
                    DecisionOperatorFactory.makeIntEq(),
                    coursIdentifier
            ));
        }*/


            //model.setObjective(Model.MAXIMIZE, moduleIdentifier);

            System.out.println("Choco max : " + model.getContrainteManager().maxAlternateSearch());


            List<CoursChoco> lesCoursTrouve = new ArrayList<>();

            // Si une solution est trouvée
            if (solver.solve()) {


                // Pour chaque module on retrouve le cours associé dans cette solution
                for (ModuleChoco module : model.getModuleInChoco()) {
                    calendriersTrouve.addCours(getCours(module));
                }
            } else {
                model.getContrainteManager().disableConstraint();

                for (ModuleChoco module : model.getModuleInChoco()) {

                    Boolean verif = false;
                    try{
                        solver.reset();
                        model.getContrainteManager().enableConstraint(module);
                        verif = solver.solve();
                    }
                    catch (SolverException e)
                    {
                        verif = false;
                    }
                    finally {
                        if (verif == true) {
                            calendriersTrouve.addCours(getCours(module));
                        } else {
                            model.getContrainteManager().disableConstraint(module);
                            List<ConstraintRespected> constraintRespected = new ArrayList<>();
                            for (ContrainteChoco contrainte : model.getContrainteManager().getContrainteParPriorite()) {

                                verif = false;
                                try{
                                    solver.reset();
                                    model.getContrainteManager().enableConstraint(contrainte, module);
                                    verif = solver.solve();
                                }
                                catch (SolverException e2)
                                {
                                    verif = false;
                                }
                                finally {
                                    if (verif == false) {
                                        model.getContrainteManager().disableConstraint(contrainte, module);
                                    }
                                    ConstraintRespected contrainteDuCours = model.getContrainteManager().getContrainte(module, contrainte);
                                    if (contrainteDuCours.getRespected() == false)
                                        constraintRespected.add(contrainteDuCours);
                                }

                            }
                            calendriersTrouve.addCours(getCours(module, constraintRespected));
                        }
                    }
                }


            }
            // tri des cours par date de début
            Collections.sort(calendriersTrouve.getCours(), Comparator.comparing(o -> DateTimeHelper.toInstant(o.getStart())));
            //.sort(Comparator.comparing(o -> lesCoursTrouve.indexOf(lesCoursTrouve.stream().filter( c -> c.getIdClasses().contentEquals(o.getIdClasses())))));

            calendriersTrouve.setConstraints(model.getContrainteManager().getContraintesFausses());
        }
        return calendriersTrouve;
    }

    private ClassesCalendar getCours(ModuleChoco module, List<ConstraintRespected> constraintRespected)
    {
        return new ClassesCalendar(module.getCoursDuModule().get(module.getCoursId().getValue()), constraintRespected);
    }

    private ClassesCalendar getCours(ModuleChoco module)
    {
        return getCours(module, model.getContrainteManager().getContraintesFausses(module));
    }
}
