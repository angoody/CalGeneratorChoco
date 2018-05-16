package models;

import utils.DateTimeHelper;

import java.io.Serializable;
import java.time.Instant;

public class Periode implements Serializable {

    private String debut = Instant.now().toString();
    private String fin = Instant.now().toString();
    private Instant instantDebut = Instant.now();
    private Instant instantFin = Instant.now();
    private String format = "YYYY-MM-dd";

    public Periode(String debut, String fin,String format) {
        this.format = format;
        this.debut = debut;
        this.fin = fin;
        this.instantDebut = DateTimeHelper.format(this.debut, this.format);
        this.instantFin = DateTimeHelper.format(this.fin, this.format);
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
        return instantDebut;
    }

    public void setInstantDebut(Instant instantDebut) {
        this.instantDebut = instantDebut;
    }

    public Instant getInstantFin() {
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
