package solver.contraintes;

import models.input.ConstraintPriority;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleDecomposeChoco;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ListeContrainteChoco<T extends ItemContrainteChoco> {

    public final static int OR = 1;
    public final static int AND = 2;



    // List des contraintes créé qui permettent de faire le lien entre les contrainte de Choco et les contraintes du model
    protected List<T> contraintesChoco = new ArrayList<>();

    // liste des contraintes créé dans Choco
    protected Map<T, Constraint> constraints  = new HashMap<>();


    // Model de Choco
    protected Model model;
    private List<ModuleDecomposeChoco> moduleInChoco = new ArrayList<>();
    private Integer operation;


    public ListeContrainteChoco(Model model, List<? extends ConstraintPriority> contrainteDecomposeModele, Class<T> classContrainte, List<ModuleDecomposeChoco> moduleInChoco, Integer operation ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.model = model;
        this.moduleInChoco = moduleInChoco;
        this.operation = operation;

        Constructor<T> constructor = classContrainte.getConstructor(Model.class, ConstraintPriority.class, List.class, ListeContrainteChoco.class);
        for (ConstraintPriority constrainteDecompose : contrainteDecomposeModele) {
            contraintesChoco.add(constructor.newInstance(model, constrainteDecompose, moduleInChoco, ListeContrainteChoco.this));
        }
    }

    public List<T> getContraintesChoco() {
        return contraintesChoco;
    }

    // Toute class héritant de cette class doit implémenter la méthode createContraints
    // L'intégration de la liste des contrainte dépend du type de contrainte (or ou and ...)


    public Constraint createConstraint(T itemContrainteChocoDecompose) {

        Constraint contrainte = null;
        Constraint[] lesContraintes = moduleInChoco.stream().map(m -> itemContrainteChocoDecompose.createConstraint(m)).toArray(Constraint[]::new);
        switch (operation) {
            case OR:
                contrainte = model.or(lesContraintes);
                break;

            case AND:
                contrainte = model.and(lesContraintes);
                break;

            default:
                contrainte = model.or(lesContraintes);
                break;
        }


        return contrainte;
    }

    // si la contrainte d'ensemble des contraintes de la list n'est pas créé on la crée puis on la poste
    // sinon on la retrouve et on la post de nouveau (uniquement si elle est en status free ou reify)

    public Constraint post(T contrainte)
    {

        Constraint constraint = constraints.get(contrainte);
        if (constraint == null) {
            constraint = createConstraint(contrainte);
            constraints.put(contrainte, constraint);
        }

        if (constraint.getStatus() == Constraint.Status.REIFIED)
            model.unpost(constraint);

        // On ne doit pas reposter deux fois la même contrainte dans Choco
        if (constraint.getStatus() != Constraint.Status.POSTED)
            constraint.post();

        // au post les contraintes sont respectées
        contrainte.getConstrainteRespected().setRespected(true);

        return constraint;
    }

    public List<Constraint> post()
    {
        return contraintesChoco.stream().map(c -> post(c)).collect(Collectors.toList());
    }

    /*public void addConstraintInEngine(T item, ModuleDecomposeChoco module)
    {
        List<T> contraintesRemoved = constraintsRemovedInEngine.get(module);
        if (contraintesRemoved == null)
        {
            contraintesRemoved = new ArrayList<>();
            constraintsRemovedInEngine.put(module, contraintesRemoved);
        }
        contraintesRemoved.remove(item);

        post(item, module);
    }*/


    public Constraint unPost(T contrainte)
    {
        Constraint constraint = constraints.get(contrainte);
        if (constraint.getStatus() == Constraint.Status.POSTED)
            model.unpost(constraint);
        /*if (constraint.getStatus() != Constraint.Status.REIFIED)
            constraint.reify();*/
        contrainte.getConstrainteRespected().setRespected(false);

        return constraint;
    }

}
