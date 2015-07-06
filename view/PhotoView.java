/*
 *  PDA project -- UBS/IUT de Vannes -- Dept Informatique
 *
 *  $Id$
 *
 *  Copyright 2007-08 © IUT de Vannes
 *  Ce logiciel pédagogique est diffusé sous licence GPL
 */
package pda.view;

import pda.control.*;
import pda.datas.*;
import pda.datas.Image;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;


/**
 *  The simplest application in the PDA.
 *
 *  It can be used to construct other applications.
 *
 *  @author F. Merciol, D. Deveaux MAJ J-F. Kamp
 *                      <{francois.merciol|daniel.deveaux}@univ-ubs.fr>
 *  @version $Revision: 2 $
 */
public class PhotoView extends JFrame {

    /**
     * Le nom de l'album courant
     */

    private String albumCourant;

    /**
     * Le nom de l'image courante
     */

    private String imageCourante;

    /** the engine of the application */

    private PhotoDatas engine;

    /**
     * La bibliothèque courante
     */

    private static Bibli bibliotheque;

    /**
     * Associe les boutons à des réactions
     */

    private PhotoCtrl photoCtrl;

    /**
     * L'affichage du PDA
     */

    private JPanel panel;

    /**
     * L'affichage du panneau nord
     */

    private JPanel panelNorth;

    /**
     * L'affichage du panneau central
     */

    private JPanel panelCenter;

    /**
     * Le panneau actuellement visible
     */

    private JPanel panelCourant;

    /**
     * La barre de défilement
     */

    private JScrollPane jsp;

    /**
     * Image de loupe pour le bouton rechercher
     */

    private ImageIcon loupe = new ImageIcon("src/pda/img/loupe.png");

    /**
     * Image d'informations pour le bouton infos
     */

    private ImageIcon infos = new ImageIcon("src/pda/img/infos.png");

    /**
     * Image de crayon pour le bouton éditer
     */

    private ImageIcon edit = new ImageIcon("src/pda/img/edit.png");

    /**
     * Association du bouton recherche des albums avec la loupe
     */

    private JButton searchAlbums = new JButton(loupe);

    /**
     * Association du bouton recherche d'images avec la loupe
     */

    private JButton searchImages = new JButton(loupe);

    /**
     * Association du bouton recherche image avec la loupe
     */

    private JButton searchImage = new JButton(loupe);

    /**
     * Création du bouton d'ajout d'album
     */

    private JButton addAlbum = new JButton("+ Album");

    /**
     * Création du bouton d'ajout d'image
     */

    private JButton addImage = new JButton("+ Image");

    /**
     * Association du bouton d'information sur les albums avec l'îcone "infos"
     */

    private JButton infosAlbums = new JButton(infos);

    /**
     * Association du bouton d'information des images avec l'îcone "infos"
     */

    private JButton infosImages = new JButton(infos);

    /**
     * Association du bouton d'information de l'image avec l'îcone "infos"
     */

    private JButton infosImage = new JButton(infos);

    /**
     * Association du bouton d'ajout de l'information avec l'îcone "modification"
     */

    private JButton addInfos = new JButton(edit);

    /**
     * Bouton pour valider la création d'un album
     */

    private JButton boutValiderAlbum = new JButton("Valider");

    /**
     * Bouton pour valider la création d'une image
     */

    private JButton boutValiderImage = new JButton("Valider");

    /**
     * Annulation de la création d'un album
     */

    private JButton boutAnnulerAlbum = new JButton("Retour");

    /**
     * Annulation de la création d'une image
     */

    private JButton boutAnnulerImage = new JButton("Retour");

    /**
     * Bouton permettant la recherche d'une image
     */

    private JButton boutSearchPath = new JButton("Chercher une image");

    /**
     * Bouton pour valider la recherche
     */

    private JButton boutValiderSearchAlbum = new JButton("Rechercher");

    /**
     * Bouton pour annuler la recherche
     */

    private JButton boutAnnulerSearchAlbum = new JButton("Retour");

    /**
     * Bouton permettant à valider les informations d'une image
     */

    private JButton boutValiderImageInfos = new JButton("Enregistrer");

    /**
     * Bouton permettant l'annulation de la création des informations de l'image
     */

    private JButton boutAnnulerImageInfos = new JButton("Retour");

    /**
     * Bouton permettant la validation de la recherche d'un album
     */

    private JButton boutValiderSearchImages = new JButton("Rechercher");

    /**
     * Bouton permettant l'annulation de la recherche d'un album
     */

    private JButton boutAnnulerSearchImages = new JButton("Retour");

    /**
     * Bouton permettant l'annulation de la recherche d'images
     */

    private JButton boutAnnulerSearchImage = new JButton("Retour");

    /**
     * Bouton permettant le retour aux albums
     */

    private JButton boutRetourAlbum = new JButton("Retour aux albums");

    /**
     * Bouton permettant le retour aux images
     */

    private JButton boutRetourImage = new JButton("Retour aux images");

    /**
     * Bouton permettant le retour lors de l'affichage d'un message d'erreur
     */

    private JButton boutRetourMessage = new JButton("Retour");

    /**
     * Bouton permettant la suppression d'une image
     */

    private JButton supprimerImage = new JButton("Supprimer l'image");

    /**
     * Bouton permettant la suppression d'un album
     */

    private JButton supprimerAlbum = new JButton("Supprimer l'album");

    /**
     * Bouton permettant de lancer la similarité visuelle
     */

    private JButton boutSimilVisu = new JButton("Similarité Visuelle");

    /**
     * Liste des boutons associés aux album
     */

    private ArrayList<JButton> jButArray;

    /**
     * Liste des boutons associés aux images
     */

    private ArrayList<JButton> jButImageArray = new ArrayList<JButton>();

    /**
     * Le champ de saisie accueillant le nom d'un nouvel album
     */

    private JTextField textNouvelAlbum;

    /**
     * Le champ accueillant la recherche à faire dans l'album
     */

    private JTextField textSearchAlbum;

    /**
     * Le champ accueillant le nom du nouvel auteur
     */

    private JTextField newAuthor;

    /**
     * Le champ accueillant la nouvelle date
     */

    private JTextField newDate;

    /**
     * Le champ accueillant les nouveaux mots clés
     */

    private JTextField newKeywords;

    /**
     * Le champ accueillant la recherche au sein un album
     */

    private JTextField textSearchImages;

    /**
     * Liste permettant à l'utilisateur de choisir la recherche à effectuer sur les albums
     */

    private JComboBox comboSearchAlbum = new JComboBox();

    /**
     * Liste permettant à l'utilisateur de choisir la recherche à effectuer dans un album
     */

    private JComboBox comboSearchImages = new JComboBox();

    /**
     * Case à cocher pour la visibilité du tatouage numérique
     */

    private JCheckBox checkWatermark;

    	/*
     	 *  Public ressources -----------------------------------------------------
     	 *
     	 *  Constructors
     	 */

    /**
     * Construction of the Photo IHM.
     *
     * @param anEngine link to the Photo datas
     */
    public PhotoView ( PhotoDatas anEngine ) {
        try {
            this.engine = anEngine;
            this.initBdd();
            this.afficherBibli();
            this.attachReactions();
        }
        catch (Exception e){
            LoadAlbum l = new LoadAlbum();
            this.engine = anEngine;
            this.initBdd();
            this.afficherBibli();
            this.attachReactions();
        }

    } // ------------------------------------------------------------- PhotoView()

    // Private methods

    /**
     * Affiche la bibliothèque de base
     */

    private void afficherBibli(){

        // mise en place de l'ihm
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        /** Searchbar */

        panelNorth = new JPanel();
        panelNorth.setSize(50, 200);
        panelNorth.setLayout(new GridLayout(1, 3));
        panelNorth.setBackground(Color.black);
        panelNorth.add(searchAlbums);
        panelNorth.add(addAlbum);
        panelNorth.add(infosAlbums);

        /** Content */

        this.afficherAlbums();


        /** Actualisation **/

       this.panel.revalidate();
    }

    /**
     * Affichage du menu lors de l'affichage des albums
     */

    private void actualisationMenuAlbums(){

        panel.remove(panelNorth);
        panelNorth = new JPanel();
        panelNorth.setSize(50, 200);
        panelNorth.setLayout(new GridLayout(1, 3));
        panelNorth.setBackground(Color.black);
        this.searchAlbums.setText(null);
        this.searchAlbums.setIcon(loupe);
        this.addAlbum.setText("+ Album");
        this.infosAlbums.setText(null);
        this.infosAlbums.setIcon(infos);
        panelNorth.add(searchAlbums);
        panelNorth.add(addAlbum);
        panelNorth.add(infosAlbums);
        panel.add(panelNorth, BorderLayout.NORTH);
    }


    /**
     * Affichage du menu lors de l'affichage des images
     */

    private void actualisationMenuImages(){
        panel.remove(panelNorth);
        panelNorth = new JPanel();
        panelNorth.setSize(50, 200);
        panelNorth.setLayout(new GridLayout(1, 3));
        panelNorth.setBackground(Color.black);
        this.searchImages.setText(null);
        this.searchImages.setIcon(loupe);
        this.addImage.setText("+ Image");
        this.infosImages.setText(null);
        this.infosImages.setIcon(infos);
        panelNorth.add(searchImages);
        panelNorth.add(addImage);
        panelNorth.add(infosImages);
        panel.add(panelNorth, BorderLayout.NORTH);
    }

    /**
     * Affichage du menu lors de l'affichage d'UNE image
     */

    private void actualisationMenuImage(){
        panel.remove(panelNorth);
        panelNorth = new JPanel();
        panelNorth.setSize(50, 200);
        panelNorth.setLayout(new GridLayout(1, 2));
        panelNorth.setBackground(Color.black);
        panelNorth.remove(searchImages);
        this.searchImage.setText(null);
        this.searchImage.setIcon(loupe);
        panelNorth.add(searchImage);
        this.addInfos.setText(null);
        this.addInfos.setIcon(edit);
        panelNorth.add(addInfos);
        this.infosImage.setText(null);
        this.infosImage.setIcon(infos);
        panelNorth.add(infosImage);
        panel.add(panelNorth, BorderLayout.NORTH);
    }


    /**
     * Attaches les réactions au controller !
     */

    private void attachReactions(){
        this.photoCtrl = new PhotoCtrl(this);

        // Bouton "Q"
        this.searchAlbums.addActionListener(this.photoCtrl);
        this.searchImages.addActionListener(this.photoCtrl);

        // Bouton "i"
        this.infosAlbums.addActionListener(this.photoCtrl);
        this.infosImages.addActionListener(this.photoCtrl);
        this.infosImage.addActionListener(this.photoCtrl);

        // Onglet "+ Album"
        this.addAlbum.addActionListener(this.photoCtrl);
        this.boutValiderAlbum.addActionListener(this.photoCtrl);
        this.boutAnnulerAlbum.addActionListener(this.photoCtrl);

        // Onglet "+ Image"
        this.addImage.addActionListener(this.photoCtrl);
        this.boutAnnulerImage.addActionListener(this.photoCtrl);
        this.boutValiderImage.addActionListener(this.photoCtrl);
        this.boutSearchPath.addActionListener(this.photoCtrl);

        // Onglet "Image"
        this.addInfos.addActionListener(this.photoCtrl);
        this.boutValiderImageInfos.addActionListener(this.photoCtrl);
        this.boutAnnulerImageInfos.addActionListener(this.photoCtrl);

        // Supprimer une Image
        this.supprimerImage.addActionListener(this.photoCtrl);

        // Supprimer un album
        this.supprimerAlbum.addActionListener(this.photoCtrl);

        // Onglet "Q Album"
        this.boutValiderSearchAlbum.addActionListener(this.photoCtrl);
        this.boutAnnulerSearchAlbum.addActionListener(this.photoCtrl);

        // Onglet "Q Images"
        this.boutValiderSearchImages.addActionListener(this.photoCtrl);
        this.boutAnnulerSearchImages.addActionListener(this.photoCtrl);

        // Onglet "Q Image"
        this.searchImage.addActionListener(this.photoCtrl);
        this.boutSimilVisu.addActionListener(this.photoCtrl);
        this.boutAnnulerSearchImage.addActionListener(this.photoCtrl);

        // Retour en arrière
        this.boutRetourAlbum.addActionListener(this.photoCtrl);
        this.boutRetourImage.addActionListener(this.photoCtrl);
        this.boutRetourMessage.addActionListener(this.photoCtrl);

        this.attachReactionsButtonsBibli();
    }

    /**
     * Attaches les réactions aux boutons des albums
     */

    private void attachReactionsButtonsBibli() {
        for (int i = 0; i < jButArray.size(); i++) {
            this.jButArray.get(i).addActionListener(this.photoCtrl);
        }
    }

    /**
     * Attaches les réactions aux boutons des images
     */

    private void attachReactionsButtonsAlbum(){

        for (int i =0; i < jButImageArray.size();i++){
            this.jButImageArray.get(i).addActionListener(this.photoCtrl);
        }

    }

    /**
     * Initialisation des données
     */

    private void initBdd(){
        PhotoView.bibliotheque = Bibli.load();
        if(PhotoView.bibliotheque == null)
            this.afficherMessage("erreur", "Le fichier que vous avez appelé est vide !");
    }


    /**
     * Affiche tous les albums
     */

    public void  afficherAlbums(){
        this.panel.removeAll();

        try {
            this.getBibli().deleteAlbum(this.getBibli().searchByCategory("SimilVisu"));
        }
        catch (Exception e){

        }
        this.actualisationMenuAlbums();
        panelCenter = new JPanel(new WrapLayout());

        if(!this.getBibli().getListeAlbums().isEmpty()) {
            Iterator<Album> it = bibliotheque.getListeAlbums().iterator();
            Album album;
            jButArray = new ArrayList<JButton>();

            while (it.hasNext()) {
                album = it.next();
                ImageIcon imageIcon;
                if (album.getListeImages().size() != 0) {
                    Image img = album.getListeImages().get((int) (Math.random() * (album.getListeImages().size() - 1)));
                    imageIcon = new ImageIcon(img.getPath());

                } else {
                    imageIcon = new ImageIcon("src/pda/img/default.png");
                }

                java.awt.Image image = imageIcon.getImage();
                image = this.getScaledImage(image, 90, 90);
                imageIcon = new ImageIcon(image);

                JButton bout = new JButton(album.getCategory());
                bout.setPreferredSize(new Dimension(90, 115));
                bout.setIcon(imageIcon);


                bout.setHorizontalTextPosition(SwingConstants.CENTER);
                bout.setVerticalTextPosition(SwingConstants.BOTTOM);


                panelCenter.add(bout);
                jButArray.add(bout);
            }
        }

        jsp = new JScrollPane(panelCenter);
        this.panel.add(jsp,BorderLayout.CENTER);
        this.panel.revalidate();
        this.attachReactionsButtonsBibli();
        this.pack();
    }

    /**
     * Affiche la saisie d'un nouvel album
     */

    public void afficherNouvelAlbum(){
        this.panel.removeAll();
        panelCenter = new JPanel();

        panelCenter.setLayout(new GridLayout(4,1));
        JLabel label = new JLabel("Quel est le nom du nouvel album ?");
        textNouvelAlbum = new JTextField();

        panelCenter.add(label);
        panelCenter.add(textNouvelAlbum);
        panelCenter.add(boutValiderAlbum);
        panelCenter.add(boutAnnulerAlbum);

        this.panel.add(panelCenter, BorderLayout.CENTER);
        this.panel.revalidate();
        this.pack();

    }

    /**
     * Affiche la saisie d'une nouvelle image
     */

    public void afficherNouvelleImage(){
        this.panel.removeAll();
        panelCenter = new JPanel();

        panelCenter.setLayout(new GridLayout(4, 1));
        JLabel label = new JLabel("Choisir la nouvelle image :");

        panelCenter.add(label);
        panelCenter.add(boutSearchPath);
        panelCenter.add(boutValiderImage);
        panelCenter.add(boutAnnulerImage);
        this.panel.add(panelCenter, BorderLayout.CENTER);
        this.panel.revalidate();
        this.pack();

    }

    /**
     * Affiche la recherche d'un nouvel album
     */

    public void RechercherAlbum(){
        this.panel.removeAll();
        panelCenter = new JPanel();

        panelCenter.setLayout(new GridLayout(5,1));
        textNouvelAlbum = new JTextField();


        if(comboSearchAlbum.getItemCount() == 0) {
            comboSearchAlbum.addItem("Rechercher par catégorie");
        }
        JLabel label = new JLabel("Votre saisie :");
        textSearchAlbum = new JTextField();
        panelCenter.add(comboSearchAlbum);
        panelCenter.add(label);
        panelCenter.add(textSearchAlbum);
        panelCenter.add(boutValiderSearchAlbum);
        panelCenter.add(boutAnnulerSearchAlbum);
        this.panel.add(panelCenter, BorderLayout.CENTER);
        this.panel.revalidate();
        this.pack();
    }


    /**
     * Affiche la page de recherches d'images
     */

    public void RechercherImages(){
        this.panel.removeAll();
        panelCenter = new JPanel();

        panelCenter.setLayout(new GridLayout(5, 1));
        textNouvelAlbum = new JTextField();
        if(comboSearchImages.getItemCount() == 0) {
            comboSearchImages.addItem("Rechercher par auteur");
            comboSearchImages.addItem("Rechercher par mots clés");
            comboSearchImages.addItem("Rechercher par chemin");
            comboSearchImages.addItem("Rechercher par date (JJ-MM-AAAA)");
        }
        JLabel label = new JLabel("Votre saisie :");
        textSearchImages = new JTextField();

        panelCenter.add(comboSearchImages);
        panelCenter.add(label);
        panelCenter.add(textSearchImages);
        panelCenter.add(boutValiderSearchImages);
        panelCenter.add(boutAnnulerSearchImages);
        this.panel.add(panelCenter, BorderLayout.CENTER);
        this.panel.revalidate();
        this.pack();
    }

    /**
     * Affiche la page de recherche sur l'image
     */

    public void RechercherImage(){
        this.panel.removeAll();
        panelCenter = new JPanel();

        panelCenter.setLayout(new GridLayout(3,1));

        panelCenter.add(boutSimilVisu);
        panelCenter.add(boutAnnulerSearchImage);
        this.panel.add(panelCenter, BorderLayout.CENTER);
        this.panel.revalidate();
        this.pack();
    }


    /**
     * Affiche un album
     * @param category La catégorie de l'album
     */
    public void afficherAlbum(String category) {
        this.panel.removeAll();
        this.actualisationMenuImages();
        this.panel.repaint();

        Album album = this.getBibli().searchByCategory(category);

        panelCenter = new JPanel(new WrapLayout());
        jButImageArray = new ArrayList<JButton>();
        ImageIcon imageIcon = null;

        for (int i = 0; i < album.getListeImages().size(); i++) {
            Image img = album.getListeImages().get(i);
            imageIcon = new ImageIcon(img.getPath());
            JButton bout = new JButton();
            bout.setPreferredSize(new Dimension(90, 90));

            java.awt.Image image = imageIcon.getImage();
            image = this.getScaledImage(image,90,90);
            imageIcon = new ImageIcon(image);

            bout.setIcon(imageIcon);
            bout.setHorizontalTextPosition(SwingConstants.CENTER);
            bout.setVerticalTextPosition(SwingConstants.BOTTOM);

            this.panel.remove(panelCenter);
            panelCenter.add(bout);
            jButImageArray.add(bout);
        }

        this.jsp = new JScrollPane(panelCenter);
        this.jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        this.panel.add(jsp,BorderLayout.CENTER);
        this.panel.add(boutRetourAlbum, BorderLayout.SOUTH);
        this.panel.revalidate();
        this.attachReactionsButtonsAlbum();
        this.pack();

    }


    /**
     * Affiche la page sur les infos d'un album
     */

    public void infosAlbums(){
        this.panel.removeAll();
        panelCenter = new JPanel();

        panelCenter.setLayout(new GridLayout(5, 1));

        JLabel label = new JLabel("Nombre d'album(s) : " + this.getBibli().getListeAlbums().size());
        panelCenter.add(label, BorderLayout.CENTER);
        panelCenter.add(this.boutAnnulerAlbum, BorderLayout.SOUTH);
        this.panel.add(panelCenter, BorderLayout.CENTER);
        this.panel.revalidate();
        this.pack();
    }

    /**
     * Affiche la page afin d'ajouter des informations sur une image
     */

    public void addInfos(){
        this.panel.removeAll();
        panelCenter = new JPanel();
        JPanel pan = new JPanel(new GridLayout(1, 1));
        this.supprimerImage.setBackground(Color.red);
        pan.add(this.supprimerImage);

        panelCenter.setLayout(new GridLayout(7,2));

        JLabel label = new JLabel("Nom de l'album : ");
        JLabel label2 = new JLabel("Auteur de l'image : ");
        JLabel label3 = new JLabel("Date : ");
        JLabel label4 = new JLabel("Chemin de l'image : ");

        String keywords ="";
        for(int i = 0; i< this.getBibli().searchByCategory(this.albumCourant).searchByPath(this.imageCourante).getKeywords().size(); i++){
            keywords += this.getBibli().searchByCategory(this.albumCourant).searchByPath(this.imageCourante).getKeywords().get(i)+", ";
        }

        JLabel label5 = new JLabel("Mots clés : (m1, m2)");
        JLabel nomAlbum = new JLabel(this.getBibli().searchByCategory(this.albumCourant).getCategory());
        JLabel chemin = new JLabel(this.getBibli().searchByCategory(this.albumCourant).searchByPath(this.imageCourante).getPath());
        this.newAuthor = new JTextField(this.getBibli().searchByCategory(this.albumCourant).searchByPath(this.imageCourante).getAuthor());
        this.newDate = new JTextField(this.getBibli().searchByCategory(this.albumCourant).searchByPath(this.imageCourante).getDate().getTheTime());
        this.newKeywords = new JTextField(keywords);

        JLabel label6 = new JLabel("Tatouage");
        if(!this.getBibli().searchByCategory(this.albumCourant).searchByPath(this.imageCourante).getWatermark())
            this.checkWatermark = new JCheckBox("Rendre visible");
        else
            this.checkWatermark = new JCheckBox("Rendre invisible");


        panelCenter.add(label);
        panelCenter.add(nomAlbum);
        panelCenter.add(label2);
        panelCenter.add(newAuthor);
        panelCenter.add(label3);
        panelCenter.add(newDate);
        panelCenter.add(label4);
        panelCenter.add(chemin);
        panelCenter.add(label6);
        panelCenter.add(checkWatermark);
        panelCenter.add(label5);
        panelCenter.add(newKeywords);
        panelCenter.add(boutValiderImageInfos);
        panelCenter.add(boutAnnulerImageInfos);
        this.panel.add(pan, BorderLayout.SOUTH);
        this.panel.add(panelCenter, BorderLayout.CENTER);
        this.panel.revalidate();
        this.pack();

    }

    /**
     * Affiche la page sur les informations d'un album
     */

    public void infosImages(){
        this.panel.removeAll();



        panelCenter = new JPanel();
        panelCenter.setLayout(new GridLayout(6,1));

        JLabel label = new JLabel("Nom de l'album : " + this.getBibli().searchByCategory(this.albumCourant).getCategory());
        JLabel label2 = new JLabel("Nombre d'image(s) dans l'album : " + this.getBibli().searchByCategory(this.albumCourant).getListeImages().size());
        panelCenter.add(label);
        panelCenter.add(label2);
        panelCenter.add(supprimerAlbum);
        panelCenter.add(this.boutAnnulerSearchImages);
        this.panel.add(panelCenter, BorderLayout.CENTER);
        this.panel.revalidate();
        this.pack();
    }

    /**
     * Affiche la page sur les informations d'une image
     */

    public void infosImage(){
        this.panel.removeAll();
        panelCenter = new JPanel();
        panelCenter.setLayout(new GridLayout(7,1));

        JLabel label = new JLabel("Nom de l'album : " + this.getBibli().searchByCategory(this.albumCourant).getCategory());
        JLabel label2 = new JLabel("Auteur de l'image : " + this.getBibli().searchByCategory(this.albumCourant).searchByPath(this.imageCourante).getAuthor());
        JLabel label3 = new JLabel("Date : " + this.getBibli().searchByCategory(this.albumCourant).searchByPath(this.imageCourante).getDate().getTheTime());
        JLabel label4 = new JLabel("Chemin de l'image : " + this.getBibli().searchByCategory(this.albumCourant).searchByPath(this.imageCourante).getPath());
        JLabel label5 = new JLabel("Tatouage numérique : " + (this.getBibli().searchByCategory(this.albumCourant).searchByPath(this.imageCourante).getWatermark() ? "Visible" : "Invisible"));

        String keywords ="";
        for(int i = 0; i< this.getBibli().searchByCategory(this.albumCourant).searchByPath(this.imageCourante).getKeywords().size(); i++){
            keywords += this.getBibli().searchByCategory(this.albumCourant).searchByPath(this.imageCourante).getKeywords().get(i)+", ";
        }
        if(keywords.length() > 3)
            keywords = keywords.substring(0, keywords.length()-2);

        JLabel label6 = new JLabel("Mots clés : " + keywords);

        panelCenter.add(label);
        panelCenter.add(label2);
        panelCenter.add(label3);
        panelCenter.add(label4);
        panelCenter.add(label5);
        panelCenter.add(label6);
        panelCenter.add(this.boutAnnulerImageInfos);
        this.panel.add(panelCenter, BorderLayout.CENTER);
        this.panel.revalidate();
        this.pack();
    }



    /**
     * Affiche une image
     */

    public void afficherImage(){
        this.panel.removeAll();
        this.actualisationMenuImage();
        Image image = null;
        try {
            image = this.getBibli().searchByCategory(albumCourant).searchByPath(imageCourante);
            if(image == null)
                throw new FileNotFoundException("Le fichier n'existe plus !");
            panelCenter = new JPanel();
            ImageIcon imageIcon = null;
            imageIcon = new ImageIcon(image.getPath());

            if(image.getWatermark())
                imageIcon = Watermark.DrawWatermark(imageIcon, image);

            JLabel bout = new JLabel(imageIcon);

            panelCenter.add(bout);
            jsp = new JScrollPane(bout);

            this.panel.add(jsp,BorderLayout.CENTER);
            this.panel.add(boutRetourImage, BorderLayout.SOUTH);
            this.panel.repaint();
            this.pack();
        }
        catch (FileNotFoundException e){
            this.afficherMessage("Erreur", "L'image n'existe plus !", "Album");
            this.getBibli().searchByCategory(this.albumCourant).deleteImage(image);

        }
        catch (Exception e){
            this.afficherMessage("Erreur", "Erreur de chargement ! !", "Album");
            this.getBibli().searchByCategory(this.albumCourant).deleteImage(image);
        }
    }

    /**
     * Redimensionne les images
     * @param srcImg L'image à redimensionner
     * @param w La largeur
     * @param h la hauteur
     * @return La nouvelle image
     */

    private java.awt.Image getScaledImage ( java.awt.Image srcImg, int w, int h ){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    /**
     * Affiche un message
     * @param type Type : Erreur, Infos, Succès
     * @param message Message
     */

    public void afficherMessage(String type, String message){

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(4,1));
        JLabel labeltype = new JLabel(type,SwingConstants.CENTER);
        labeltype.setFont(new Font("Arial",Font.BOLD,22));
        JLabel labeltext = new JLabel(message,SwingConstants.CENTER);
        p.add(labeltype);
        p.add(labeltext);


        this.panelCourant = panel;
        panelCenter = p;
        if (panel != null) {
            this.panel.removeAll();
            this.repaint();
        }else{
            panel = new JPanel();
        }
        this.panel.add(boutRetourMessage, BorderLayout.SOUTH);
        this.panel.add(panelCenter, BorderLayout.CENTER);
        this.panel.revalidate();
        this.panel.repaint();
    }

    /**
     * Surcharge des informations
     * @param type Le type de l'information
     * @param message Le message à afficher
     * @param precedent La page précédemment visité
     */

    public void afficherMessage(String type, String message, String precedent){

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(4,1));
        JLabel labeltype = new JLabel(type,SwingConstants.CENTER);
        labeltype.setFont(new Font("Arial",Font.BOLD,22));
        JLabel labeltext = new JLabel(message,SwingConstants.CENTER);
        p.add(labeltype);
        p.add(labeltext);


        panelCenter = p;
        if (panel != null) {
            this.panel.removeAll();
            this.repaint();
        }else{
            panel = new JPanel();
        }

        if(precedent.equals("Albums"))
            this.panel.add(boutAnnulerSearchAlbum, BorderLayout.SOUTH);
        if(precedent.equals("Album"))
            this.panel.add(boutAnnulerSearchImages, BorderLayout.SOUTH);
        if(precedent.equals("Image"))
            this.panel.add(boutRetourImage, BorderLayout.SOUTH);
        if(precedent.equals("Rechercher image")){
            JButton tmp = searchImage;
            tmp.setIcon(null);
            tmp.setText("Retour");
            this.panel.add(tmp, BorderLayout.SOUTH);
        }
        if(precedent.equals("Rechercher images")){
            JButton tmp = searchImages;
            tmp.setIcon(null);
            tmp.setText("Retour");
            this.panel.add(tmp, BorderLayout.SOUTH);
        }
        if(precedent.equals("Rechercher album")){
            JButton tmp = searchAlbums;
            tmp.setIcon(null);
            tmp.setText("Retour");
            this.panel.add(tmp, BorderLayout.SOUTH);
        }
        if(precedent.equals("Nouvel album")){
            JButton tmp = addAlbum;
            tmp.setText("Retour");
            this.panel.add(tmp, BorderLayout.SOUTH);
        }
        if(precedent.equals("Nouvelle image")){
            JButton tmp = addImage;
            tmp.setText("Retour");
            this.panel.add(tmp, BorderLayout.SOUTH);
        }
        if(precedent.equals("Ajout infos image")){
            JButton tmp = addInfos;
            tmp.setText("Retour");
            this.panel.add(tmp, BorderLayout.SOUTH);
        }
        if(precedent.equals("Invisible watermark"))
            this.panel.add(supprimerImage, BorderLayout.SOUTH);

        this.panel.add(panelCenter, BorderLayout.CENTER);
        this.panel.revalidate();
        this.panel.repaint();

    }


    /* ---------------------------------------------
    ------------------------------------------------
    -------------- GETTERS / SETTERS ---------------
    ------------------------------------------------
    ------------------------------------------------
     */

    /**
     * Renvoi la fenêtre
     * @return La fenêtre
     */

    public JPanel getPanel() {
        return panel;
    }

    /**
     * Bouton de recherche des albums
     * @return le bouton
     */

    public JButton getBoutSearchAlbum() { return searchAlbums; }

    /**
     * Liste des recherches possible sur un album
     * @return la liste
     */

    public JComboBox getComboSearchAlbum() { return this.comboSearchAlbum; }

    /**
     * Le texte écrit par l'utilisateur pour rechercher un album
     * @return Le texte
     */

    public JTextField getTextSearchAlbum() { return textSearchAlbum; }

    /**
     * Le bouton de validation de la création de l'album
     * @return le bouton
     */

    public JButton getBoutValiderSearchAlbum() { return this.boutValiderSearchAlbum; }

    /**
     * Le bouton d'annulation de la création d'un album
     * @return le bouton
     */

    public JButton getBoutAnnulerSearchAlbum() {
        return this.boutAnnulerSearchAlbum;
    }

    /**
     * Le bouton permettant à rechercher des images
     * @return le bouton
     */

    public JButton getBoutSearchImages() { return searchImages;}

    /**
     * Liste des possibilitées de recherche sur des images
     * @return la liste
     */

    public JComboBox getComboSearchImages() { return this.comboSearchImages; }

    /**
     * Le texte entré par l'utilisateur afin de rechercher selon ce criètre
     * @return Le texte
     */

    public JTextField getTextSearchImages() { return this.textSearchImages; }

    /**
     * Le bouton de validation de recherche d'images
     * @return Le bouton
     */

    public JButton getBoutValiderSearchImages() { return this.boutValiderSearchImages; }

    /**
     * Le bouton d'annulation de recherche d'images
     * @return Le bouton
     */

    public JButton getBoutAnnulerSearchImages() { return this.boutAnnulerSearchImages; }

    /**
     * Le bouton permettant la recherche à partir d'une image
     * @return Le bouton
     */

    public JButton getBoutSearchImage() { return searchImage; }

    /**
     * Le bouton permettant de lancer la similarité visuelle
     * @return Le bouton
     */

    public JButton getBoutSimilVisu() { return this.boutSimilVisu; }

    /**
     * Le bouton d'annulation de recherche à partir d'une image
     * @return Le bouton
     */

    public JButton getBoutAnnulerSearchImage() { return this.boutAnnulerSearchImage; }


    /**
     * Le bouton d'informations des albums
     * @return Le bouton
     */
    public JButton getInfosAlbums() {
        return infosAlbums;
    }

    /**
     * Le bouton d'informations des images
     * @return Le bouton
     */

    public JButton getInfosImages() {
        return infosImages;
    }

    /**
     * Le bouton d'information d'une image
     * @return Le bouton
     */

    public JButton getInfosImage() {
        return infosImage;
    }

    /**
     * Le bouton de l'ajout d'un album
     * @return Le bouton
     */

    public JButton getAddAlbum() {
        return addAlbum;
    }

    /**
     * Le champ de texte du nouvel album
     * @return le champ
     */

    public JTextField getTextNouvelAlbum() {
        return textNouvelAlbum;
    }

    /**
     * Le bouton de validation de création d'un nouvel album
     * @return Le bouton
     */

    public JButton getBoutValiderAlbum() {
        return boutValiderAlbum;
    }

    /**
     * Le bouton d'annulation de création d'un nouvel album
     * @return Le bouton
     */

    public JButton getBoutAnnulerAlbum() {
        return boutAnnulerAlbum;
    }

    /**
     * Le bouton d'ajout d'une nouvelle image
     * @return Le bouton
     */

    public JButton getAddImage() { return this.addImage; }

    /**
     * Le bouton de recherche d'une image
     * @return Le bouton
     */

    public JButton getSearchImage() { return this.boutSearchPath; }

    /**
     * Le bouton de validation de création d'une image
     * @return Le bouton
     */

    public JButton getBoutValiderImage() { return this.boutValiderImage; }

    /**
     * Le bouton d'annulation de création d'une image
     * @return Le bouton
     */

    public JButton getBoutAnnulerImage() { return this.boutAnnulerImage; }

    /**
     * Bouton de validation des informations d'une image
     * @return Le bouton
     */

    public JButton getValiderImageInfos(){ return this.boutValiderImageInfos; }

    /**
     * Bouton d'annulation des informations d'une image
     * @return Le bouton
     */

    public JButton getAnnulerImageInfos(){ return this.boutAnnulerImageInfos; }

    /**
     * Le champ de texte du nouvel auteur
     * @return Le champ
     */

    public JTextField getNewAuthor() { return this.newAuthor; }

    /**
     * Le champ d'une nouvelle date
     * @return Le champ
     */

    public JTextField getNewDate() { return this.newDate; }

    /**
     * Le champ des nouveaux mots clés
     * @return Le champ
     */

    public JTextField getNewKeywords() { return this.newKeywords; }

    /**
     * Le bouton pour ajouter de nouvelles informations
     * @return Le bouton
     */

    public JButton getAddInfos(){ return this.addInfos; }

    /**
     * La bibliothèque en cours
     * @return La bibliothèque
     */

    public Bibli getBibli(){
        return PhotoView.bibliotheque;
    }

    /**
     * Les boutons des albums
     * @return Tableau de boutons
     */

    public ArrayList<JButton> getjButArray() {
        return jButArray;
    }

    /**
     * Les boutons des images
     * @return Tableau de boutons
     */

    public ArrayList<JButton> getjButImageArray() { return jButImageArray; }

    /**
     * Renvoi Image courante
     * @return Renvoi l'image courante
     */

    public String getImageCourante() { return this.imageCourante; }

    /**
     * Modification de l'image courante
     * @param imageCourante La nouvelle image courante
     */

    public void setImageCourante(String imageCourante) {
        this.imageCourante = imageCourante;
    }

    /**
     * Renvoi l'album courant
     * @return L'album courant
     */

    public String getAlbumCourant() {
        return albumCourant;
    }

    /**
     * Modification de l'album courant
     * @param albumCourant Nouvel album courant
     */

    public void setAlbumCourant(String albumCourant) {
        this.albumCourant = albumCourant;
    }

    /**
     * Bouton de retour aux album
     * @return le bouton
     */

    public JButton getBoutRetourAlbum() {
        return boutRetourAlbum;
    }

    /**
     * Bouton de retour aux images
     * @return le bouton
     */

    public JButton getBoutRetourImage() {
        return boutRetourImage;
    }

    /**
     * Bouton de suppression d'une image
     * @return Le bouton
     */

    public JButton getSupprimerImage() {
        return supprimerImage;
    }

    /**
     * Bouton de suppresion d'un album
     * @return Le bouton
     */

    public JButton getSupprimerAlbum() {
        return supprimerAlbum;
    }

    /**
     * Case à cocher pour le tatouage
     * @return la case
     */

    public JCheckBox getCheckWatermark() { return checkWatermark; }
} // ------------------------------------------------------------- Class PhotoView