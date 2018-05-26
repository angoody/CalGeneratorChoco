package solver.modelChoco;

import models.input.Cours;

public class CoursChoco {

    private final Cours cours;
    private PeriodeChoco periode ;
    private int idModule;
    private int nbSemaine;
    private int nbHeure;
    private int coursIdentifier;
    private int duration;

    private int lieu;
    private int idCours;

    public CoursChoco(Cours cours, Integer idModule, Integer nbSemaine, Integer nbHeure) {
        this.cours = cours;
        this.idCours = idCours;
        this.periode = new PeriodeChoco(cours.getPeriode());
        this.idModule = idModule;
        this.nbSemaine = nbSemaine;
        this.nbHeure = nbHeure;
        this.coursIdentifier = (periode.getDebut() + periode.getFin()) / 2;
        this.duration = cours.getNbHeureReel();
        this.lieu = cours.getLieu();
    }

    public int getDebut() {
        return periode.getDebut();
    }

    public int getFin() {
        return periode.getFin();
    }

    public int getCoursIdentifier() {
        return coursIdentifier;
    }

    public int getDuration() {
        return duration;
    }

    public int getLieu() {
        return lieu;
    }

    public int getIdModule() {
        return idModule;
    }

    public int getNbHeure() {
        return nbHeure;
    }

    public void setNbHeure(int nbHeure) {
        this.nbHeure = nbHeure;
    }

    public int getNbSemaine() {
        return nbSemaine;
    }

    public void setNbSemaine(int nbSemaine) {
        this.nbSemaine = nbSemaine;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }

    public Cours getCours() {
        return cours;
    }

    public int getIdCours() {
        return idCours;
    }

    public int[] getInt() {
        return new int[] { idModule, idCours, periode.getDebut(), periode.getFin(), coursIdentifier, lieu, duration, nbHeure, nbSemaine};
    }

}
