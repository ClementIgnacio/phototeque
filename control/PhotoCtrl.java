/*
 *  PDA project -- UBS/IUT de Vannes -- Dept Informatique
 *
 *  $Id$
 *
 *  Copyright 2007-08 © IUT de Vannes
 *  Ce logiciel pédagogique est diffusé sous licence GPL
 */
package pda.control;

import pda.datas.*;
import pda.view.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 *  The simplest application in the PDA.
 *
 *  It can be used to construct other applications.
 *
 *  @author F. Merciol, D. Deveaux MAJ J-F. Kamp
 *                      <{francois.merciol|daniel.deveaux}@univ-ubs.fr>
 *  @version $Revision: 27 $
 */
public class PhotoCtrl implements IApplication, ActionListener {
	/*
     	 * Private implementation -------------------------------------------------
     	 */

    /** the name of the application */
    private String name;

    /** the view of the application */
    private PhotoView view;

    /** the engine of the application */
    private PhotoDatas engine;

    /**
     * Permet de charger les dossiers
     */
    private JFileChooser file;

    	/*
     	 *  Public ressources -----------------------------------------------------
     	 *
     	 *  Constructors
     	 */

    /**
     * Initialize the datas and ihm of Photo application.
     */

    public PhotoCtrl () {
        engine = new PhotoDatas();
        view = new PhotoView(engine);
    }

    /**
     * Constructeur permettant d'associer la vue à celle passée en paramètre
     * @param view La nouvelle vue
     */

    public PhotoCtrl (PhotoView view) {
        this.view = view;
    } // --------------------------------------------------------- PhotoCtrl()

    	/*
     	 *  Public methods
     	 */

    /*
      * See documentation of interface
      */
    public void start ( PdaCtrl pda ) {
        System.out.println ( "Start of Photo application" );
    } // -------------------------------------------------------------- start()

    /*
      * See documentation of interface
      */
    public String getAppliName() {
        return name;
    } // ---------------------------------------------------------- getAppliName()

    /*
      * See documentation of interface
      */
    public JPanel getAppliPanel() {
        return view.getPanel();
    } // ---------------------------------------------------------- getAppliPanel()

    /*
      * See documentation of interface
      */
    public boolean close() {
        return true;
    } // ---------------------------------------------------------- close()

    /*
     * See documentation of interface
     */
    public void setAppliName ( String theName ) {
        this.name = theName;
    } // ---------------------------------------------------------- setAppliName()

    /**
     * Callback method, reaction to button pushed
     * @param e the captured event
     */
    public void actionPerformed( ActionEvent e ) {

        // NAVBAR

        // Ajouter un nouvel album
        if(this.view.getAddAlbum() == e.getSource()){
            this.view.afficherNouvelAlbum();
        }
        // Infos de la bibli
        if(this.view.getInfosAlbums() == e.getSource()){
            this.view.infosAlbums();
        }
        // Infos d'un album
        if(this.view.getInfosImages() == e.getSource()){
            this.view.infosImages();
        }
        // Infos d'une image
        if(this.view.getInfosImage() == e.getSource()){
            this.view.infosImage();
        }
        // Ajout d'info sur une image
        if(this.view.getAddInfos() == e.getSource()){
            this.view.addInfos();
        }
        // Enregistrer info sur une image
        if(this.view.getValiderImageInfos() == e.getSource())
            this.ajouterNouvellesInfos();
        // Annuler la modification sur une image
        if(this.view.getAnnulerImageInfos() == e.getSource())
            this.view.afficherImage();
        // Ajouter une image à l'album
        if(this.view.getAddImage() == e.getSource()){
            this.view.afficherNouvelleImage();
        }
        // Rechercher une image (chemin)
        if(this.view.getSearchImage() == e.getSource()){
            this.ChoisirUneImage();
        }
        // Faire une recherche sur les images
        if(this.view.getBoutSearchImages() == e.getSource()){
            this.view.RechercherImages();
        }
        // Annuler la recherche sur les images
        if(this.view.getBoutAnnulerSearchImages() == e.getSource()){
            this.view.afficherAlbum(this.view.getAlbumCourant());
        }
        // Faire une rechercher sur une image
        if(this.view.getBoutSearchImage() == e.getSource()){
            this.view.RechercherImage();
        }
        // Annuler la recherche sur l'image
        if(this.view.getBoutAnnulerSearchImage() == e.getSource()){
            this.view.afficherImage();
        }
        // Faire une recherche par similarité visuelle
        if(this.view.getBoutSimilVisu() == e.getSource())
            this.similVisuelle();
        // Renvoyer vers l'album
        if(this.view.getBoutValiderSearchImages() == e.getSource()){
            this.searchImages();
        }
        // Enregistrer la nouvelle image
        if(this.view.getBoutValiderImage() == e.getSource()){
            this.enregistrerImage();
        }
        // Annuler la nouvelle image
        if (this.view.getBoutAnnulerImage() == e.getSource()){
            this.view.afficherAlbum(this.view.getAlbumCourant());
        }
        // Ajouter un nouvel album
        if (this.view.getBoutValiderAlbum() == e.getSource()){
            this.CreerNewAlbum();
        }
        // Annuler la création de l'album
        if (this.view.getBoutAnnulerAlbum() == e.getSource()){
            this.view.afficherAlbums();
        }
        // Rechercher un nouvel album
        if(this.view.getBoutSearchAlbum() == e.getSource()){
            this.view.RechercherAlbum();
        }
        // Valider la recherche d'un album
        if(this.view.getBoutValiderSearchAlbum() == e.getSource()){
            this.searchAnAlbum();
        }
        // Annuler la recherche d'un album
        if(this.view.getBoutAnnulerSearchAlbum() == e.getSource()){
            this.view.afficherAlbums();
        }
        //Retour vers la liste des albums
        if (this.view.getBoutRetourAlbum() == e.getSource()){
            this.view.afficherAlbums();
        }
        //Supprimer un album
        if (this.view.getSupprimerAlbum() == e.getSource()){
            for(int i = 0; i < this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).getListeImages().size(); i++){
                this.view.setImageCourante(this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).getListeImages().get(i).getPath());
                this.suppressionImage();
            }
            this.view.getBibli().deleteAlbum(this.view.getBibli().searchByCategory(this.view.getAlbumCourant()));
            this.view.getBibli().save();
            this.view.afficherAlbums();
        }
        //Retour vers l'album
        if (this.view.getBoutRetourImage() == e.getSource()){
            this.view.afficherAlbum(this.view.getAlbumCourant());
        }
        // Suppression d'une image
        if(this.view.getSupprimerImage() == e.getSource()){
            this.suppressionImage();
        }
        // Associations des boutons avec les images d'albums
        for (int i = 0; i< this.view.getjButArray().size(); i++){
            if (this.view.getjButArray().get(i) == e.getSource()) {
                this.view.setAlbumCourant(this.view.getBibli().getListeAlbums().get(i).getCategory());
                this.view.afficherAlbum(this.view.getBibli().getListeAlbums().get(i).getCategory());
            }
        }
        // Associations des boutons avec les images
        for (int i = 0; i< this.view.getjButImageArray().size(); i++){
            if (this.view.getjButImageArray().get(i) == e.getSource()) {
                this.view.setImageCourante(this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).getListeImages().get(i).getPath());
                if(this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).getListeImages().get(i).invisibleWatermark())
                    this.view.afficherMessage("Erreur", "L'image a été modifiée !", "Invisible watermark");
                else {
                    this.view.getPanel().revalidate();
                    this.view.afficherImage();
                }
            }
        }
    } // ---------------------------------------------------------- actionPerformed()

    /**
     * Choisir une image dans ses dossiers
     */

    private void ChoisirUneImage() {
        try {
            this.file = new JFileChooser();
            int retour = this.file.showOpenDialog(this.view.getSearchImage());
        }catch (Exception e){
            this.view.afficherMessage("Erreur", "L'Utilisateur n'a pas choisi de fichier", "Nouvelle image");
        }
    }

    /**
     * Permet la création d'un nouvel album
     */

    private void CreerNewAlbum() {
        try {
            String s = this.view.getTextNouvelAlbum().getText();
            if (!(s.isEmpty())) {
                boolean arret = false;
                int i=0;

                while(i < this.view.getBibli().getListeAlbums().size() && !arret){
                    if(s.equalsIgnoreCase(this.view.getBibli().getListeAlbums().get(i).getCategory())){
                        this.view.afficherMessage("Erreur", "Cette catégorie existe déjà !", "Nouvel album");
                        arret = true;
                    }
                    i++;
                }

                if(!arret){
                    this.view.getBibli().addAlbum(new Album(s));
                    this.view.getBibli().save();
                    this.view.setAlbumCourant(s);
                    this.view.afficherAlbums();
                }
            }
        } catch (Exception e) {
            this.view.afficherMessage("Erreur", "L'application n'a pas réussi à créer cet album !", "Albums");
        }

    }

    /**
     * Enregistrer une nouvelle image
     */

    private void enregistrerImage() {
        try{
            int i = 0;
            boolean fin = false;

            if (this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).getListeImages().size() == 0) {
                this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).addImage(new Image(this.file.getSelectedFile().getPath()));
                this.view.getBibli().save();
            }
            else {
                while (i < this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).getListeImages().size() && !fin) {
                    String[] path = this.file.getSelectedFile().getPath().split("[/]");
                    if (this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).getListeImages().get(i).getPath().equals("src/pda/img/"+path[path.length-1])) {
                        this.view.afficherMessage("Erreur", "Cette image existe déjà !", "Nouvelle image");
                        fin = true;
                    }
                    i++;
                }

                if(!fin){

                    FileChannel in = null;
                    FileChannel out = null;

                    File file = new File(this.file.getSelectedFile().getPath());

                    String[] path = this.file.getSelectedFile().getPath().split("[/]");
                    String[] path2 = path[path.length-1].split("[.]");
                    if(!(path2[1].equals("png") || path2[1].equals("jpeg") || path2[1].equals("jpg") || path2[1].equals("gif") ||
                            path2[1].equals("tiff") || path2[1].equals("bmp")))
                        throw new Exception("Ce n'est pas une image !");

                    try {
                        in = new FileInputStream(this.file.getSelectedFile().getPath()).getChannel();
                        out = new FileOutputStream("src/pda/img/" + path[path.length-1]).getChannel();
                        String pathFinal = "src/pda/img/" + path[path.length-1];
                        in.transferTo(0, in.size(), out);
                        this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).addImage(new Image(pathFinal));

                    } catch (FileNotFoundException e) {
                       this.view.afficherMessage("Erreur", "Le fichier n'a pas été trouvé !", "Nouvelle image");

                    } catch (IOException e) {
                        this.view.afficherMessage("Erreur", "L'importation a échouée !", "Nouvelle image");
                    }

                    this.view.getBibli().save();
                    fin = true;
                    this.view.getPanel().repaint();
                    this.view.afficherAlbum(this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).getCategory());
                }
            }
        }
        catch (Exception e){
            this.ChoisirUneImage();
        }
    }

    /**
     * Cherche un album
     */

    private void searchAnAlbum(){
        // On regarde si le texte est pas vide
        if(this.view.getTextSearchAlbum().getText().isEmpty()){
            this.view.afficherMessage("Erreur", "Il faut remplir !", "Rechercher album");
        }
        // Si il n'est pas vide :
        else{
            // On regarde quel type de recherche l'utilisateur veut faire :
            if(this.view.getComboSearchAlbum().getSelectedIndex() == 0){

                Album alb = null;
                try {
                    alb = this.view.getBibli().searchByCategory(this.view.getTextSearchAlbum().getText());
                    if (alb == null) {
                        this.view.afficherMessage("Erreur", "Cette catégorie n'existe pas !", "Rechercher album");
                    } else {
                        this.view.setAlbumCourant(alb.getCategory());
                        this.view.afficherAlbum(alb.getCategory());
                    }
                }
                catch (Exception e){
                    this.view.afficherMessage("Erreur", "La recherche n'a pas pu être effectuée", "Recherche Album");
                }

            }
        }
    }

    /**
     * Ajouts de nouvelles informations à l'image
     */

    public void ajouterNouvellesInfos(){
        if(this.view.getNewAuthor().getText() != null)
            this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).searchByPath(this.view.getImageCourante()).setAuthor(this.view.getNewAuthor().getText());
        if(!this.view.getNewDate().getText().equals("")){
            String[] date = this.view.getNewDate().getText().split("-");
            this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).searchByPath(this.view.getImageCourante()).setDate(new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])));
        }

        if(this.view.getCheckWatermark().isSelected()){
            if(this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).searchByPath(this.view.getImageCourante()).getWatermark())
                this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).searchByPath(this.view.getImageCourante()).setWatermark(false);
            else
                this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).searchByPath(this.view.getImageCourante()).setWatermark(true);

        }

        if(this.view.getNewKeywords().getText() != null){
            String mot1 = this.view.getNewKeywords().getText()+",";
            mot1.replaceAll(" ", ",");
            String[] keywords = mot1.split(",");
            this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).searchByPath(this.view.getImageCourante()).deleteAllKeyWords();
            for(int i = 0 ; i < keywords.length; i++ )
                if(!this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).searchByPath(this.view.getImageCourante()).getKeywords().equals(keywords[i]))
                    this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).searchByPath(this.view.getImageCourante()).addKeyword(keywords[i]);
        }
        this.view.getBibli().save();
        this.view.infosImage();
    }


    /**
     * Recherche des images en fonction de la saisie de l'utilisateur
     */

    public void searchImages(){

        // On regarde si le texte est pas vide
        if(this.view.getTextSearchImages().getText().isEmpty()){
            this.view.afficherMessage("Erreur", "Il faut remplir !", "Rechercher images");
        }
        // Si il n'est pas vide :
        else{
            // On regarde quel type de recherche l'utilisateur veut faire :
            if(this.view.getComboSearchImages().getSelectedIndex() == 0){
                Album alb = new Album("Résultats de la recherche");
                ArrayList<Image> img;
                try {
                    img = this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).searchByAuthor(this.view.getTextSearchImages().getText());
                    for (int i = 0; i < img.size(); i++) {
                        alb.addImage(img.get(i));
                    }
                    this.view.getBibli().addAlbum(alb);
                    this.view.afficherAlbum(alb.getCategory());
                    this.view.getBibli().deleteAlbum(alb);

                    if (alb.getListeImages().size() == 0)
                        this.view.afficherMessage("Erreur", "Aucune image ne correspond !!", "Rechercher images");
                }catch (Exception e){
                    this.view.afficherMessage("Erreur", "La recherche n'a pas pu être effectuée", "Rechercher images");
                }

            }else if(this.view.getComboSearchImages().getSelectedIndex() == 1){
                Album alb = new Album("Résultats de la recherche");
                ArrayList<Image> img;
                try {
                    img = this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).searchByKeywords(this.view.getTextSearchImages().getText());
                    for (int i = 0; i < img.size(); i++) {
                        alb.addImage(img.get(i));
                    }
                    this.view.getBibli().addAlbum(alb);
                    this.view.afficherAlbum(alb.getCategory());
                    this.view.getBibli().deleteAlbum(alb);

                    if (alb.getListeImages().size() == 0)
                        this.view.afficherMessage("Erreur", "Aucune image ne correspond !!", "Rechercher images");
                }catch (Exception e){
                    this.view.afficherMessage("Erreur", "La recherche n'a pas pu être effectuée", "Rechercher images");
                }
            }else if(this.view.getComboSearchImages().getSelectedIndex() == 2){
                try {
                    Image img;
                    img = this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).searchByPath(this.view.getTextSearchImages().getText());
                    if(img != null) {
                        this.view.setImageCourante(this.view.getTextSearchImages().getText());
                        this.view.afficherImage();
                    }
                    else{
                        this.view.afficherMessage("Erreur", "Aucune image ne correspond !", "Rechercher images");
                    }
                }
                catch (NullPointerException e1){
                    this.view.afficherMessage("Erreur", "Aucune image ne correspond !", "Rechercher images");
                } catch (Exception e){
                    this.view.afficherMessage("Erreur", "La recherche n'a pas pu être faite !", "Rechercher images");
                }

            }else if(this.view.getComboSearchImages().getSelectedIndex() == 3){
                Album alb = new Album("Résultats de la recherche");
                ArrayList<Image> img;
                StringTokenizer st = new StringTokenizer(this.view.getTextSearchImages().getText(),"-");
                int jour = 0;
                int mois = 0;
                int annee = 0;

                try {
                    while (st.hasMoreElements()) {
                        jour = Integer.parseInt(st.nextToken());
                        mois = Integer.parseInt(st.nextToken());
                        annee = Integer.parseInt(st.nextToken());
                    }

                    img = this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).searchByDate(new Date(jour, mois, annee));
                    for (int i = 0; i < img.size(); i++) {
                        alb.addImage(img.get(i));
                    }
                    this.view.getBibli().addAlbum(alb);
                    this.view.afficherAlbum(alb.getCategory());
                    this.view.getBibli().deleteAlbum(alb);

                    if (alb.getListeImages().size() == 0) {
                        this.view.afficherMessage("Erreur", "Aucune image ne correspond !!", "Rechercher images");
                    }
                }
                catch (NumberFormatException e1){
                    this.view.afficherMessage("Erreur", "Ce n'est pas une date !", "Rechercher images");
                }
                catch (NoSuchElementException e2){
                    this.view.afficherMessage("Erreur", "Veuillez saisir une date valide !", "Rechercher images");
                }
                catch (Exception e1){
                    this.view.afficherMessage("Erreur", "La recherche n'a pas pu être faite !", "Rechercher images");
                }
            }
        }
    }

    /**
     * Cherche les similaritées visuelles des images
     */

    private void similVisuelle(){
        Album alb = new Album("SimilVisu");
        Image monImg = this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).searchByPath(this.view.getImageCourante());

        for (int i = 0 ; i < this.view.getBibli().getListeAlbums().size() ; i++ ){
            int j = 0;
            while(j < this.view.getBibli().getListeAlbums().get(i).getListeImages().size()) {
                if(!monImg.getPath().equals(this.view.getBibli().getListeAlbums().get(i).getListeImages().get(j).getPath())){
                    boolean different1 = false;
                    boolean different2 = false;
                    boolean different3 = false;
                    double monRouge = 0;
                    double monVert = 0;
                    double monBleu = 0;
                    try {
                        System.out.println("Rouge : " + monImg.getRed() + " / " + this.view.getBibli().getListeAlbums().get(i).getListeImages().get(j).getRed());
                        monRouge = (100*(monImg.getRed()) / (this.view.getBibli().getListeAlbums().get(i).getListeImages().get(j).getRed()));
                    }catch(Exception e){
                        monRouge = 1;
                    }
                    try{
                        System.out.println("Vert : " + monImg.getGreen() + " / " + this.view.getBibli().getListeAlbums().get(i).getListeImages().get(j).getGreen());
                        monVert = (100*(monImg.getGreen())/(this.view.getBibli().getListeAlbums().get(i).getListeImages().get(j).getGreen()));
                    } catch (Exception e){
                        monVert = 1;
                    }
                    try{
                        System.out.println("Bleu: " + monImg.getBlue() + " / " + this.view.getBibli().getListeAlbums().get(i).getListeImages().get(j).getBlue());
                        monBleu = (100*(monImg.getBlue())/(this.view.getBibli().getListeAlbums().get(i).getListeImages().get(j).getBlue()));
                    }catch (Exception e){
                        monBleu = 1;
                    }

                    System.out.println("Rouge : " + monRouge+"%");
                    System.out.println("Vert : " + monVert+"%");
                    System.out.println("Bleu : " + monBleu+"%");

                    if(!(monRouge > 60 && monRouge < 140))
                        different1 = true;
                    if(!(monVert > 60 && monVert < 140))
                        different2 = true;
                    if(!(monBleu > 60 && monBleu < 140))
                        different3 = true;

                    if( (!different1 && !different2) || (!different1 && !different3) || (!different2 && !different3) ){
                        alb.addImage(this.view.getBibli().getListeAlbums().get(i).getListeImages().get(j));
                    }
                }
                j++;
            }
        }

        if (alb.getListeImages().size() == 0){
            this.view.afficherMessage("Erreur", "Aucune image ne correspond !!", "Rechercher image");
        }
        this.view.getBibli().addAlbum(alb);
        this.view.setAlbumCourant(alb.getCategory());
        this.view.afficherAlbum(alb.getCategory());

    }

    /**
     * Suppression de l'image
     */

    public void suppressionImage(){
        try {

            File file = new File(this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).searchByPath(this.view.getImageCourante()).getPath());
            if (file.exists())
                file.delete();

            String[] img2 = this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).searchByPath(this.view.getImageCourante()).getPath().split("[.]");
            File file2 = new File((img2[0])+ "-pw.jpg");
            if (file2.exists())
                file2.delete();
        }
        catch (Exception e1){
        }

        this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).getListeImages().remove(this.view.getBibli().searchByCategory(this.view.getAlbumCourant()).searchByPath(this.view.getImageCourante()));
        this.view.afficherAlbum(this.view.getAlbumCourant());
        this.view.getBibli().save();
    }

} // ---------------------------------------------------------- Class PhotoCtrl
