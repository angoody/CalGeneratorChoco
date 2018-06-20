package models;

import java.io.Serializable;

public class Cours implements Serializable, Comparable<Cours> {
    private Periode periode;
    private String idCours;
    private Integer idModule;
    private Integer lieu;
    private Integer nbHeureReel;

    public Cours() {
    }

    public Cours(Periode periode, String idCours, Integer idModule, Integer lieu, Integer nbHeureReel) {
        this.periode = periode;
        this.idCours = idCours;
        this.idModule = idModule;
        this.lieu = lieu;
        this.nbHeureReel = nbHeureReel;
    }

    public Periode getPeriode() {
        return periode;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    public String getIdCours() {
        return idCours;
    }

    public void setIdCours(String idCours) {
        this.idCours = idCours;
    }

    public Integer getIdModule() {
        return idModule;
    }

    public void setIdModule(Integer idModule) {
        this.idModule = idModule;
    }

    public Integer getLieu() {
        return lieu;
    }

    public void setLieu(Integer lieu) {
        this.lieu = lieu;
    }

    @Override
    public int compareTo(Cours o) {
        return o.getPeriode().getInstantDebut().compareTo(this.getPeriode().getInstantDebut());
    }

    public Integer getNbHeureReel() {
        return nbHeureReel;
    }

    public void setNbHeureReel(Integer nbHeureReel) {
        this.nbHeureReel = nbHeureReel;
    }

    //  private Salle codeSalle;
    /*

    case class Cours (
	                 debut: String,
	                 fin: String,
	                 dureeReelleEnHeures: Int,
	                 codePromotion: String,
	                 idCours: String,
	                 prixPublicAffecte: Float,
	                 idModule: Int,
	                 libelleCours: String,
	                 dureePrevueEnHeures: Int,
	                 dateAdefinir: Boolean,
	                 codeSalle: String,
	                 codeFormateur: Int,
	                 codeLieu: Int
                 )
     */
}
