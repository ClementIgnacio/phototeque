package pda.datas;

import java.io.*;
import java.util.ArrayList;

/**
 * Modélisation de la bibliothèque. Une bibliothèque peut contenir plusieurs albums
 */

public class Bibli implements Serializable{

    /**
     * Version de la sérialisation
     */

    private static final long serialVersionUID = 0;

    /**
     * La liste des albums
     */

    private ArrayList<Album> listeAlbums;

    /**
     * Constructeur de la bibliothèque permettant d'initialiser les albums
     */

    public Bibli(){
        this.listeAlbums = new ArrayList<Album>();
    }

    /**
     * Recherche de l'album par catégorie
     * @param cat Le nom de la catégorie
     * @return L'album
     */

    /**
     * Donne les albums
     * @return Les albums
     */

    public ArrayList<Album> getListeAlbums(){ return this.listeAlbums; }

    /**
     * Donne le nombre d'albums ayant pour catégorie le nom passé en paramètre
     * @param str Le nom de la catégorie
     * @return Le nombre d'album(s)
     */

    public int getCategory(String str){
        Integer res = null;
        for(int i = 0; i < this.getListeAlbums().size(); i++){
            if(this.getListeAlbums().get(i).getCategory().equals(str)){
                res = i;
            }
        }
        return res;
    }

    public Album searchByCategory(String cat) {
        Album alb = null;
        for (int i = 0; i < this.listeAlbums.size(); i++) {
            if (this.listeAlbums.get(i).getCategory().equalsIgnoreCase(cat)) {
                alb = this.listeAlbums.get(i);
            }
        }
        return alb;
    }

    /**
     * Permet de sauvegarder la bibliothèque
     */

    public void save() {
        String nomFich = "src/pda/datas/bibli.out";
        ObjectOutputStream flux = null;
        try {
            flux = new ObjectOutputStream(new FileOutputStream(nomFich));
            flux.writeObject(this);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Charge la bibliothèque
     * @return La bibliothèque
     */

    public static Bibli load() {
        String nomFich = "src/pda/datas/bibli.out";
        ObjectInputStream flux = null;
        Bibli b = null;
        try {
            flux = new ObjectInputStream(new FileInputStream(nomFich));
            b = (Bibli) flux.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ClassCastException e) {
            System.out.println(e.getMessage());
        }
        return b;
    }

    /**
     * Ajout d'un album à la bibliothèque
     * @param alb L'album à ajouter
     */

    public void addAlbum(Album alb){
        this.listeAlbums.add(alb);
    }

    /**
     * Suppression d'un album
     * @param alb L'album à supprimer
     */

    public void deleteAlbum(Album alb){
        this.listeAlbums.remove(alb);
    }

}