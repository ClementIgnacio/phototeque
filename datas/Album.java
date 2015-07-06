package pda.datas;


import java.io.*;
import java.lang.String;
import java.util.ArrayList;

/**
 * Modélisation de l'album qui permet de gérer les images
 */

public class Album implements Serializable {

    /**
     * Version de la serialisation
     */

    private static final long serialVersionUID = 0;


    /**
     * Contient toutes les images présentent dans l'album
     */

    private ArrayList<Image> listeImages;

    /**
     * Nom de la catégorie de l'image
     */

    private String category;

    /**
     * Constructeur permettant de créer un album en lui assignant un nom de catégorie
     * @param category Le nom de l'album
     */
    public Album(String category) {
        this.category = category;
        this.listeImages = new ArrayList<Image>();
    }


    /**
     * Modifie le nom de la catégorie
     * @param cat Le nouveau nom de la catégorie
     */

    public void setCategory(String cat) {
        this.category = cat;
    }

    /**
     * Renvoie le nom de la catégorie
     * @return Le nom de la catégorie
     */

    public String getCategory() {
        return this.category;
    }

    /**
     * Renvoi les images de l'album
     * @return Les images de l'album
     */

    public ArrayList<Image> getListeImages(){
        return this.listeImages;
    }

    /**
     * Cherche une image en fonction de son chemin
     * @param path Le chemin de l'image
     * @return L'image ayant ce chemin
     */

    public Image searchByPath(String path){
        Image img = null;
        for(int i=0; i<this.getListeImages().size(); i++){
            if(this.getListeImages().get(i).getPath().equals(path)){
                img = this.getListeImages().get(i);
            }
        }
        return img;
    }

    /**
     * Cherche toutes les images appartenant à un auteur
     * @param author L'auteur de ou des images
     * @return Les images
     */

    public ArrayList<Image> searchByAuthor(String author) {
        ArrayList<Image> list = new ArrayList<Image>();
        for (int i = 0; i < this.listeImages.size(); i++) {
            if (this.listeImages.get(i).getAuthor().equals(author))
                list.add(this.listeImages.get(i));
        }
        return list;
    }

    /**
     * Cherche toutes les images ayant pour mot clé celui passé en paramètre
     * @param kw Le mot clé de ou des images
     * @return Les images ayant ce mot clé
     */

    public ArrayList<Image> searchByKeywords(String kw) {
        ArrayList<Image> list = new ArrayList<Image>();
        for (int i = 0; i < this.listeImages.size(); i++) {
            for (int j = 0; j < this.listeImages.get(i).getKeywords().size(); j++) {
                if (this.listeImages.get(i).getKeywords().get(j).equals(kw))
                    list.add(this.listeImages.get(i));
            }
        }
        return list;
    }

    /**
     * Cherche les images ayant la date passée en paramètre
     * @param date La date permettant de trouver les images
     * @return Les images
     */

    public ArrayList<Image> searchByDate(Date date) {
        ArrayList<Image> list = new ArrayList<Image>();
        for (int i = 0; i < this.listeImages.size(); i++) {
            if (this.listeImages.get(i).getDate().getTheTime().equals(date.getTheTime()))
                list.add(this.listeImages.get(i));
        }
        return list;
    }

    /**
     * Ajout d'une image
     * @param image L'image à ajouter
     */

    public void addImage(Image image){
        this.listeImages.add(image);
    }

    /**
     * Supprime une image
     * @param image L'image à supprimer
     */

    public void deleteImage(Image image){
        this.listeImages.remove(image);
    }


}