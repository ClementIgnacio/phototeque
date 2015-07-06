package pda.tests;

import org.junit.Test;
import pda.datas.Album;
import pda.datas.Image;
import pda.datas.Date;

import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Classe de test pour les albums.
 */
public class AlbumTest {

    /**
     * L'album à tester
     */

    private Album alb;

    /**
     * Constructeur permettant les tests
     */

    public AlbumTest(){

        alb = new Album("Sport et chansons");
        Image img1 = null;
        try {
            img1 = new Image("src/pda/img/test.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        img1.addKeyword("code");
        img1.addKeyword("dur");
        img1.setAuthor("Clement");
        img1.setDate(new Date(01,12,1994));

        Image img3 = null;
        try {
            img3 = new Image("src/pda/img/test.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        img3.setAuthor("Clement");
        img3.addKeyword("dur");
        img3.addKeyword("lol");
        img3.setDate(new Date(01,12,1994));
        Image img2 = null;
        try {
            img2 = new Image("src/pda/img/test.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        img2.setAuthor("Thomas");
        img2.addKeyword("oui");
        img2.addKeyword("non");
        img2.setDate(new Date(03,06,1996));
        this.alb.addImage(img1);
        this.alb.addImage(img2);
        this.alb.addImage(img3);
    }

    /**
     * Test de la recherche par auteur
     * @throws Exception Propage les exceptions pouvant survenir lors des tests
     */

    @Test
    public void testSearchByAuthor() throws Exception {
        ArrayList<Image> imageArrayList = this.alb.searchByAuthor("Clement");
        if(imageArrayList.size() != 2){
            fail("La recherche par auteur n'a pas renvoye les bons resultats");
        }
    }

    /**
     * Test de la recherche par mots clés
     * @throws Exception Propage les exceptions pouvant survenir lors des tests
     */

    @Test
    public void testSearchByKeywords() throws Exception {
        ArrayList<Image> imageArrayList = this.alb.searchByKeywords("dur");
        if (imageArrayList.size() != 2){
            fail("La recherche par mots cles n'a pas renvoye les bons resultats");
        }
    }

    /**
     * Test de la recherche par date
     * @throws Exception Propage les exceptions pouvant survenir lors des tests
     */

    @Test
    public void testSearchByDate() throws Exception {
        ArrayList<Image> imageArrayList = this.alb.searchByDate(new Date(01,12,1994));
        if (imageArrayList.size() != 2){
            fail("La recherche par date n'a pas renvoye les bons resultats");
        }
    }

    /**
     * Test de l'ajout d'une image
     * @throws Exception Propage les exceptions pouvant survenir lors des tests
     */

    @Test
    public void testAddImage() throws Exception {
        int i = this.alb.getListeImages().size();
        Image img4 = new Image("src/pda/img/test.png");
        this.alb.addImage(img4);
        if (this.alb.getListeImages().size() != i+1){
            fail("L'image ne s'est pas ajoutee correctement");
        }
    }

    /**
     * Test de la suppression de l'image
     * @throws Exception Propage les exceptions pouvant survenir lors des tests
     */

    @Test
    public void testDeleteImage() throws Exception {
        int i = this.alb.getListeImages().size();
        this.alb.deleteImage(alb.getListeImages().get(0));
        if (this.alb.getListeImages().size() != i-1){
            fail("L'image ne s'est pas supprimee correctement");
        }
    }
}