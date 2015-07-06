
package pda.datas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import java.io.*;
import java.util.Vector;

/**
 * Modélisation d'une image appartenant à un album
 */

public class Image implements Serializable {

    /**
     * Version de la sérialisation
     */

    private static final long serialVersionUID = 0;

    /**
     * Le chemin de l'image
     */

    private String path;

    /**
     * L'auteur de l'image
     */

    private String author;

    /**
     * Les mots clés de l'image
     */

    private ArrayList<String> keywords;

    /**
     * La date de l'image
     */

    private Date date;

    /**
     * Le tatouage numerique
     */

    private boolean watermark;

    /**
     * La valeur totale de la couleur rouge de l'image
     */

    private Integer red = null;

    /**
     * La valeur totale de la couleur verte de l'image
     */

    private Integer green = null;

    /**
     * La valeur total de la couleur bleu de l'image
     */

    private Integer blue = null;

    /**
     * Construit l'image en fonction du chemin de l'image
     * @param path Le chemin de l'image
     */

    public Image(String path){
        this.path = path;
        this.author = null;
        this.keywords = new ArrayList<String>();
        this.date = new Date();
        this.watermark = false;
        this.invisibleWatermark();
    }

    /**
     * Construit l'image en fonction du chemin et de l'auteur
     * @param path Le chemin de l'image
     * @param author L'auteur de l'image
     */

    public Image(String path, String author){
        this.path = path;
        this.author = author;
        this.keywords = new ArrayList<String>();
        this.date = new Date();
        this.invisibleWatermark();
    }

    /**
     * Ajout d'un mot clé à l'image
     * @param keyword Le mot clé à ajouter
     */

    public void addKeyword(String keyword){
        this.keywords.add(keyword);
    }

    /**
     * Suppression d'un mot clé de l'image
     * @param keyword Le mot clé à supprimer
     */

    public void deleteKeyword(String keyword){
        for(int i=0; i< this.keywords.size(); i++){
            if(this.keywords.get(i).equals(keyword))
                this.keywords.remove(i);
        }
    }

    /**
     * Suppression complète des mots clés
     */

    public void deleteAllKeyWords(){
        this.keywords = new ArrayList<String>();
    }

    /**
     * Rend le tatouage invisible
     */

    public void deleteWatermark(){ this.watermark = false; }


    public void exportToPdf(){
        String input = this.path; // .gif and .jpg are ok too!
        String output = this.path.split(".", 0)+".pdf";

    }

    /**
     * Analyse l'image  et associe la valeur des pixels rouges, verts, bleus à leurs attributs.
     * @return Vrai ou faux si l'image est identique à celle initialement importée.
     */

    public boolean invisibleWatermark() {
        boolean different = false;
        int vertPhot1 = 0;
        int rougePhot1 = 0;
        int bleuPhot1 = 0;
        int w1 = 0;
        int h1 = 0;
        int sommeTotale = 0;

        try {
            BufferedImage im = ImageIO.read(new FileInputStream(this.path));
            w1 = im.getWidth(null);
            h1 = im.getHeight(null);
            int[] rgbs = new int[w1 * h1];
            int x1 = 0;
            im.getRGB(0, 0, w1, h1, rgbs, 0, w1);

            for (int i = 0; i < w1; i++) {
                Vector line = new Vector();
                for (int j = 0; j < h1; j++) {
                    line.add(new Integer(rgbs[x1]));
                    rougePhot1 = rougePhot1 + ((rgbs[x1] >> 16) & 0xFF);
                    vertPhot1 = vertPhot1 + ((rgbs[x1] >> 8) & 0xFF);
                    bleuPhot1 = bleuPhot1 + (rgbs[x1] & 0xFF);
                    sommeTotale = sommeTotale + rgbs[x1];
                    x1++;

                }
            }
            Color c = new Color(rougePhot1%255, vertPhot1%255, bleuPhot1%255);

            if(this.red == null && this.green == null && this.blue == null) {
                im.setRGB(w1 - 1, h1 - 1, c.getRGB());

                this.red = rougePhot1 % 255;
                this.green = vertPhot1 % 255;
                this.blue = bleuPhot1 % 255;
            }
            else{
                different = this.analyse(c);
            }

        } catch (Exception ex) {
            different = true;
        }

        return different;
    }

    /**
     * Analyse si l'image actuelle est identique à celle importée initialement
     * @param c La couleur de l'image actuelle
     * @return Vrai si c'est identique ou faux si c'est différent
     */

    private boolean analyse(Color c){
        boolean different = false;
        Color c2 = new Color(this.red, this.green, this.blue);
        if(c.getRGB() != c2.getRGB()){
            different = true;
        }
        return different;
    }

    /**
     * Modifie l'auteur de l'image
     * @param author Le nouvel auteur de l'image
     */

    public void setAuthor(String author){
        this.author = author;
    }

    /**
     * Modifie la date de l'image
     * @param date La nouvelle date
     */

    public void setDate(Date date){
        this.date = date;
    }

    /**
     * Modifie le statut du tatouage
     * @param watermark La nouvelle valeur du tatouage
     */

    public void setWatermark(boolean watermark){
        this.watermark = watermark;
    }

    /**
     * Renvoi l'auteur de l'image
     * @return L'auteur de l'image
     */

    public String getAuthor() {
        return author;
    }

    /**
     * Les mots clés de l'image
     * @return Les mots clés de l'image
     */

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    /**
     * Renvoi la date de l'image
     * @return la date
     */

    public Date getDate() {
        return date;
    }

    /**
     * Renvoi si le watermark doit être visible
     * @return La valeur du watermark
     */

    public boolean getWatermark() {
        return watermark;
    }

    /**
     * Renvoi le chemin de l'image
     * @return le chemin
     */

    public String getPath() { return path; }

    /**
     * Renvoi la valeur rouge de l'image
     * @return La valeur rouge
     */

    public int getRed() { return red; }

    /**
     * Renvoi la valeur verte de l'image
     * @return La valeur verte
     */

    public int getGreen() { return green; }

    /**
     * Renvoi la valeur bleue de l'image
     * @return La valeur bleue
     */

    public int getBlue() { return blue; }
}