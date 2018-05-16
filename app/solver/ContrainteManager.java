package solver;

import models.Contrainte;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;
import utils.DateTimeHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ContrainteManager {
    private final int nbModule;
    private List<Contrainte> contraintes;
    private List<ContrainteChoco> contraintesInChoco;
    private Map<IntVar, Constraint> contrainteLieuParModule;
    private List<List<Constraint>> contraintePeriodeExclusion;
    private IntVar[] modulesID;
    private IntVar[] coursID;
    private IntVar[] modulesDebut;
    private IntVar[] modulesFin;
    private IntVar[] coursIdentifier;
    private IntVar[] modulesLieu;
    private IntVar[] modulesDuration;
    private IntVar[] modulesNbHeure;
    private IntVar[] modulesNbSemaine;

    public ContrainteManager(Model model, List<Contrainte> contraintes, IntVar[] modulesID, IntVar[] coursID, IntVar[] modulesDebut, IntVar[] modulesFin, IntVar[] coursIdentifier, IntVar[] modulesLieu, IntVar[] modulesDuration, IntVar[] modulesNbHeure, IntVar[] modulesNbSemaine) {
        this.nbModule = this.modulesID.length;
        this.contraintes = contraintes;
        this.contraintesInChoco = new ArrayList<>(contraintes.stream().map(cont -> new ContrainteChoco(cont)).collect(Collectors.toList()));
        this.modulesID = modulesID;
        this.coursID = coursID;
        this.modulesDebut = modulesDebut;
        this.modulesFin = modulesFin;
        this.coursIdentifier = coursIdentifier;
        this.modulesLieu = modulesLieu;
        this.modulesDuration = modulesDuration;
        this.modulesNbHeure = modulesNbHeure;
        this.modulesNbSemaine = modulesNbSemaine;
    }

    public void postContrainteLieu(IntVar lieu)
    {
        // Contrainte de lieux
        int finalI = i;
        Constraint[] contraintesDeLieux = IntStream.range(0, listLieuxAutorises.size()).mapToObj(a -> model.arithm(modulesLieu[finalI], "=", listLieuxAutorises.get(a))).toArray(Constraint[]::new);
        model.or(contraintesDeLieux).post();

        // Contrainte de période exclusion
            /*Constraint[] contraintesDePeriodeExclues = IntStream.range(0, periodeExclusion.size())
                    .mapToObj(a -> model.and(
                            model.or(model.arithm(modulesFin[finalI], "<", DateTimeHelper.InstantToDays(periodeExclusion.get(a).getInstantDebut())),
                                    model.arithm(modulesFin[finalI], ">", DateTimeHelper.InstantToDays(periodeExclusion.get(a).getInstantFin()))),
                            model.or(model.arithm(modulesDebut[finalI], "<", DateTimeHelper.InstantToDays(periodeExclusion.get(a).getInstantDebut())),
                                    model.arithm(modulesDebut[finalI], ">", DateTimeHelper.InstantToDays(periodeExclusion.get(a).getInstantFin())))))
                    .toArray(Constraint[]::new);*/

        Constraint[] contraintesDePeriodeExclues = IntStream.range(0, periodeExclusion.size())
                .mapToObj(a -> model.and(
                        model.notMember(
                                modulesFin[finalI],
                                DateTimeHelper.InstantToDays(periodeExclusion.get(a).getInstantDebut()),
                                DateTimeHelper.InstantToDays(periodeExclusion.get(a).getInstantFin())),
                        model.notMember(
                                modulesDebut[finalI],
                                DateTimeHelper.InstantToDays(periodeExclusion.get(a).getInstantDebut()),
                                DateTimeHelper.InstantToDays(periodeExclusion.get(a).getInstantFin()))))
                .toArray(Constraint[]::new);
        model.and(contraintesDePeriodeExclues).post();
    }

    public void unpost(Model model)
    {

    }


    public List<Contrainte> getContraintes() {
        return contraintes;
    }


    public void setContraintes(List<Contrainte> contraintes) {
        this.contraintes = contraintes;
    }

    public List<ContrainteChoco> getContraintesInChoco() {
        return contraintesInChoco;
    }

    public void setContraintesInChoco(List<ContrainteChoco> contraintesInChoco) {
        this.contraintesInChoco = contraintesInChoco;
    }

    public IntVar[] getModulesID() {
        return modulesID;
    }

    public void setModulesID(IntVar[] modulesID) {
        this.modulesID = modulesID;
    }

    public IntVar[] getCoursID() {
        return coursID;
    }

    public void setCoursID(IntVar[] coursID) {
        this.coursID = coursID;
    }

    public IntVar[] getModulesDebut() {
        return modulesDebut;
    }

    public void setModulesDebut(IntVar[] modulesDebut) {
        this.modulesDebut = modulesDebut;
    }

    public IntVar[] getModulesFin() {
        return modulesFin;
    }

    public void setModulesFin(IntVar[] modulesFin) {
        this.modulesFin = modulesFin;
    }

    public IntVar[] getCoursIdentifier() {
        return coursIdentifier;
    }

    public void setCoursIdentifier(IntVar[] coursIdentifier) {
        this.coursIdentifier = coursIdentifier;
    }

    public IntVar[] getModulesLieu() {
        return modulesLieu;
    }

    public void setModulesLieu(IntVar[] modulesLieu) {
        this.modulesLieu = modulesLieu;
    }

    public IntVar[] getModulesDuration() {
        return modulesDuration;
    }

    public void setModulesDuration(IntVar[] modulesDuration) {
        this.modulesDuration = modulesDuration;
    }

    public IntVar[] getModulesNbHeure() {
        return modulesNbHeure;
    }

    public void setModulesNbHeure(IntVar[] modulesNbHeure) {
        this.modulesNbHeure = modulesNbHeure;
    }

    public IntVar[] getModulesNbSemaine() {
        return modulesNbSemaine;
    }

    public void setModulesNbSemaine(IntVar[] modulesNbSemaine) {
        this.modulesNbSemaine = modulesNbSemaine;
    }
}
