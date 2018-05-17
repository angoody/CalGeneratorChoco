package models;

import utils.DateTimeHelper;

import java.io.Serializable;
import java.time.Instant;

public class Periode implements Serializable {

    private String debut;
    private String fin;
    private Instant instantDebut;
    private Instant instantFin;
    private String format = "yyyy-MM-dd" ;

    public Periode(String debut, String fin,String format) {
        this.format = format;
        this.debut = debut;
        this.fin = fin;
    }

    public Periode(String debut, String fin) {
        this.format = "yyyy-MM-dd";
        this.debut = debut;
        this.fin = fin;
        this.instantDebut = DateTimeHelper.format(this.debut, format);
        this.instantFin = DateTimeHelper.format(this.fin, format);
    }

    public Periode(Instant instantDebut, Instant instantFin, String format) {
        this.format = format;
        this.instantDebut = instantDebut;
        this.instantFin = instantFin;
        this.debut = this.instantDebut.toString();
        this.fin = this.instantFin.toString();

    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getDebut() {
        return debut;
    }


    public Instant getInstantDebut() {
        if (instantDebut == null)
        {
            instantDebut = DateTimeHelper.format(this.debut, format);
        }
        return instantDebut;
    }

    public void setInstantDebut(Instant instantDebut) {
        this.instantDebut = instantDebut;
    }

    public Instant getInstantFin() {
        if (instantFin == null)
        {
            instantFin = DateTimeHelper.format(this.fin, format);
        }
        return instantFin;
    }

    public void setInstantFin(Instant instantFin) {
        this.instantFin = instantFin;
    }

    public Periode() {
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

}
