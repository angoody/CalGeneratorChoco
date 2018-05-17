package models;

import utils.DateTimeHelper;

import java.io.Serializable;
import java.time.Instant;

public class Periode extends ContrainteDecompose {

    private String debut;
    private String fin;
    private Instant instantDebut;
    private Instant instantFin;
    private String format = "yyyy-MM-dd" ;  // valeur par défaut utilisé par le from json

    // Utilisé uniquement pour le test, n'est jamais appelé par le from json
    public Periode(String debut, String fin) {
        this.format = "yyyy-MM-dd";
        this.debut = debut;
        this.fin = fin;
        this.instantDebut = DateTimeHelper.format(this.debut, format);
        this.instantFin = DateTimeHelper.format(this.fin, format);
    }

    public String getFin() {
        return fin;
    }

    public String getDebut() {
        return debut;
    }

    public Instant getInstantDebut() {
        return instantDebut == null ? instantDebut = DateTimeHelper.format(this.debut, format): instantDebut;
    }

    public Instant getInstantFin() {
        return instantFin == null ? instantFin = DateTimeHelper.format(this.fin, format) : instantFin;
    }

}
