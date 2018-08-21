package solver;

import models.common.Module;
import models.common.Problem;
import models.generator.output.Calendar;
import models.verify.input.Verify;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;
import solver.contraintes.ContrainteManager;
import solver.modelChoco.CoursChoco;
import solver.modelChoco.ModuleChoco;
import utils.DateTimeHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Permet d'initialiser le model de Choco, les modules et les contraintes via le contrainteManager
 */
public abstract class ChocoModelInitialiser
{


    /**
     * Contient le model Choco initialisé en fonction du problème
     */
    private final Model                   model;
    /**
     * Contient la liste des modules avec les variables initialisé pour Choco
     */
    private final List<ModuleChoco>       moduleInChoco;

    /**
     * Permet de gérer les contraintes sur les modules
     */
    private ContrainteManager contrainteManager;


    /**
     * @param problem : Contient le problème transmis en entrée
     */
    public ChocoModelInitialiser(Problem problem)
    {


        List<Calendar> calendriersTrouve = new ArrayList<>();
        model = new Model("Generer calendrier");


        // Création des modèles de données des modules pour Choco
        // Transforme les modules en objet préparé pour Choco
        moduleInChoco = transformModuleOfTraining(problem.getModuleOfTraining());
        moduleInChoco.forEach(m -> m.setModule(moduleInChoco));
        int nbModules = moduleInChoco.size();


        //Traitement des contraintes

        //Période de formation
        int debutFormation = DateTimeHelper.toDays(problem.getPeriodOfTraining().getStart());
        int finFormation = DateTimeHelper.toDays(problem.getPeriodOfTraining().getEnd());


        // Création des jeux de données basé sur tous les cours pour Choco
        List<CoursChoco> coursChocoAutorise = moduleInChoco.stream().flatMap(m -> m.getCoursDuModule().stream()).collect(Collectors.toList());
        int[][]          coursListeBlanche  = new int[coursChocoAutorise.size()][];


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
        Tuples            tuple             = new Tuples(coursListeBlanche, true);
        contrainteManager = null;
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


        for (int i = 0; i < moduleInChoco.size(); i++) {

            // La liste des cours à rechercher
            table[i] = new IntVar[]{
                    moduleInChoco.get(i).getId(),
                    moduleInChoco.get(i).getOccurenceVar(),
                    moduleInChoco.get(i).getCoursId(),
                    moduleInChoco.get(i).getDebut(),
                    moduleInChoco.get(i).getFin(),
                    moduleInChoco.get(i).getCoursIdentifier(),
                    moduleInChoco.get(i).getLieu(),
                    moduleInChoco.get(i).getModulesWorkingDayDuration(),
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
                model.ifThen(
                        model.and(
                                model.arithm(moduleInChoco.get(i).getModulesWorkingDayDuration(), ">", 0),
                                model.arithm(moduleInChoco.get(j).getModulesWorkingDayDuration(), ">", 0)
                        ),
                        model.or(
                                model.arithm(moduleInChoco.get(i).getFin(), "<=", moduleInChoco.get(j).getDebut()),
                                model.arithm(moduleInChoco.get(j).getFin(), "<=", moduleInChoco.get(i).getDebut())
                        )
                );
            }
        }
    }

    abstract public List<ModuleChoco> transformModuleOfTraining(List<Module> moduleOfTraining);

    public ContrainteManager getContrainteManager()
    {
        return contrainteManager;
    }


    public Model getModel()
    {
        return model;
    }


    public List<ModuleChoco> getModuleInChoco()
    {
        return moduleInChoco;
    }
}
