package pda.datas;

import java.text.SimpleDateFormat;
import java.io.*;

/**
 * Modélise la date permettant de l'associer aux images
 */

public class Date implements Serializable {

    /**
     * Version de la sérialisation
     */

    private static final long serialVersionUID = 0;

    /**
     * Le jour
     */

    private String day;

    /**
     * Le mois
     */

    private String month;

    /**
     * L'année
     */

    private String year;

    /**
     * La date sous forme "DD-MM-YYYY"
     */

    private String TheDate;

    /**
     * Construit la date avec la date courante
     */

    public Date(){
        this.TheDate = new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date());
        this.convertTheTime();
    }

    /**
     * Construit la date en fonction des paramètres
     * @param day Le jour
     * @param month Le mois
     * @param year L'année
     */

    public Date(int day, int month, int year){
        this.day = (day < 10 ? "0" : "") + day;
        this.month = (month < 10 ? "0" : "") + month;
        this.year = Integer.toString(year);
        this.setTheTime();
    }

    /**
     * Convertit la date sous la forme "DD-MM-YYYY"
     */

    public void setTheTime(){
        this.TheDate = this.day+"-"+this.month+"-"+this.year;
    }

    /**
     * Découpe la chaine de caractère pour affecter les attributs (jours, mois, années)
     */

    private void convertTheTime(){
        String[] tab  = this.TheDate.split("-");
        this.day = tab[0];
        this.month = tab[1];
        this.year = tab[2];
    }

    /**
     * Donne la date de l'objet sous forme "DD-MM-YYYY"
     * @return La date
     */

    public String getTheTime(){
        return this.TheDate;
    }

}