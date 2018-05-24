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
    public Periode()
    {
        this(DateTimeHelper.format(Instant.now()), DateTimeHelper.format(Instant.now()));
    }

    public Periode(String debut, String fin) {
        super(true, 0);
        this.format         = "yyyy-MM-dd";
        this.debut          = debut;
        this.fin            = fin;
        this.instantDebut   = DateTimeHelper.format(this.debut, format);
        this.instantFin     = DateTimeHelper.format(this.fin, format);
    }

    public Periode(Periode periode, Boolean isRespected) {
        super(isRespected, periode.getPriority());
        this.format         = periode.format;
        this.debut          = periode.getDebut();
        this.fin            = periode.getFin();
        this.instantDebut   = periode.getInstantDebut();
        this.instantFin     = periode.getInstantFin();
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
