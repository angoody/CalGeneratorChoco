package solver.contraintes;

import models.common.ConstraintPriority;
import org.chocosolver.solver.Model;
import solver.modelChoco.ModuleChoco;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class ItemContrainteChoco<T> extends ContrainteChoco<T> {
    protected static ResourceBundle language = ResourceBundle.getBundle("language", Locale.getDefault());
    private ListeContrainteChoco parent;
    private Boolean isAlternateSearch = false;

    public ItemContrainteChoco(Model model, ConstraintPriority<T> contrainte, List<ModuleChoco> modulesInChoco, ListeContrainteChoco parent) {
        super(model, contrainte, modulesInChoco);
        this.parent = parent;
    }


    public ListeContrainteChoco getParent() {
        return parent;
    }

    @Override
    public void enableAlternateSearch(ModuleChoco module) {
        getParent().unPost(this);
        isAlternateSearch = true;

    }

    @Override
    public void disableAlternateSearch(ModuleChoco module) {
        getParent().post(this);
        isAlternateSearch = false;

    }

    public Boolean getIsAlternateSearch ()
    {
        return isAlternateSearch;
    }

    @Override
    public abstract Boolean isAlternateSearch(ModuleChoco module);
}
