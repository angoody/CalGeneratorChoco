package solver.contraintes;

import models.ContrainteDecompose;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;
import solver.modelChoco.ModuleChoco;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListeContrainteChoco<T extends ItemContrainteChocoDecompose> {

    public final static int OR = 1;
    public final static int AND = 2;


    // liste des contraintes créé dans Choco
    protected Map<ModuleChoco, Constraint> constraints  = new HashMap<>();

    // List des contraintes créé qui permettent de faire le lien entre les contrainte de Choco et les contraintes du model
    protected List<T> contraintesChoco = new ArrayList<>();
    protected Map<ModuleChoco, List<T>> constraintsRemovedInEngine = new HashMap<>();

    // Model de Choco
    protected Model model;
    private Integer operation;



    public ListeContrainteChoco(Model model, List<? extends ContrainteDecompose> contrainteDecomposeModele, Class<T> classContrainte, List<ModuleChoco> moduleInChoco, Integer operation ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.model = model;
        this.operation = operation;

        Constructor<T> constructor = classContrainte.getConstructor(Model.class, ContrainteDecompose.class, List.class, ListeContrainteChoco.class);
        for (ContrainteDecompose contrainteDecompose : contrainteDecomposeModele) {
            contraintesChoco.add(constructor.newInstance(model, contrainteDecompose, moduleInChoco, ListeContrainteChoco.this));
        }
    }

    public List<T> getContraintesChoco() {
        return contraintesChoco;
    }

    // Toute class héritant de cette class doit implémenter la méthode createContraints
    // L'intégration de la liste des contrainte dépend du type de contrainte (or ou and ...)
    public Constraint createConstraints(ModuleChoco module) {
        Constraint contrainte = null;
        if (constraintsRemovedInEngine.get(module) == null)
            constraintsRemovedInEngine.put(module, new ArrayList<>());
        Constraint[] lesContraintes = contraintesChoco.stream().filter(c -> constraintsRemovedInEngine.get(module).contains(c) == false).map(c -> c.createConstraint(module)).toArray(Constraint[]::new);
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
    public Constraint post(ModuleChoco module)
    {

        Constraint constraint = constraints.get(module);
        if (constraint == null) {
            constraint = createConstraints(module);
            constraints.put(module, constraint);
        }

        // On ne doit pas reposter deux fois la même contrainte dans Choco
        if (constraint.getStatus() != Constraint.Status.POSTED)
            constraint.post();

        // au premier post les contraintes sont respectées
        contraintesChoco.stream().forEach(c -> c.getContrainteModel().setRespeced(true));

        return constraint;
    }

    public void addConstraintInEngine(T item, ModuleChoco module)
    {
        List<T> contraintesRemoved = constraintsRemovedInEngine.get(module);
        if (contraintesRemoved == null)
        {
            contraintesRemoved = new ArrayList<>();
            constraintsRemovedInEngine.put(module, contraintesRemoved);
        }
        contraintesRemoved.remove(item);

        post(item, module);
    }

    private void post(T item, ModuleChoco module) {
        Constraint constraint = constraints.get(module);
        if (constraint != null) {
            model.unpost(constraint);
        }

        constraint = createConstraints(module);

        constraint.post();
        constraints.put(module, constraint);

        // Si toutes les contraintes sont posté, alors la contrainte est respectée
        if (item.getConstraint().stream().filter(c -> c.getStatus() == Constraint.Status.POSTED).count() ==  item.getConstraint().size())
            item.getContrainteModel().setRespeced(true);
    }

    public void removeConstraintInEngine(T item, ModuleChoco module)
    {
        List<T> contraintesRemoved = constraintsRemovedInEngine.get(module);
        if (contraintesRemoved == null)
        {
            contraintesRemoved = new ArrayList<>();
            constraintsRemovedInEngine.put(module, contraintesRemoved);
        }
        contraintesRemoved.add(item);

        post(item, module);
    }

    public Constraint unPost(IntVar... var)
    {
        Constraint constraint = constraints.get(var[0].getName());
        model.unpost(constraint);
        constraint.reify();
        contraintesChoco.stream().forEach(c -> c.getContrainteModel().setRespeced(false));

        return constraint;
    }

}
