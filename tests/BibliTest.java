package pda.tests;

import org.junit.Test;
import pda.datas.Album;
import pda.datas.Bibli;
import pda.datas.Image;

import static org.junit.Assert.*;

/**
 * Classe BibliTest permettant le test de la classe Bibli
 */

public class BibliTest {

    /**
     * Bibliothèque permettant les tests
     */

    private Bibli bibli;

    /**
     * Constructeur permettant d'initaliser les tests
     */

    public BibliTest(){
        this.bibli = new Bibli();
        this.bibli.addAlbum(new Album("Jazz"));
        this.bibli.addAlbum(new Album("Test"));

        this.bibli.getListeAlbums().get(0).addImage(new Image("src/pda/img/test.png", "Clément Ignacio"));
    }

    /**
     * Test de la recherche par catégorie
     * @throws Exception Propage les exceptions pouvant survenir lors des tests
     */

    @Test
    public void testSearchByCategory() throws Exception {
        Album album = new Album("");
        album = this.bibli.searchByCategory("Jazz");
        if(!album.getCategory().equals("Jazz")){
            fail("La recherche par catégorie n'a pas renvoyé les bons resultats");
        }
    }

    /**
     * Test de la sauvegarde et du chargement de la bibliothèque
     * @throws Exception Propage les exceptions pouvant survenir lors des tests
     */

    @Test
    public void testSaveAndLoad() throws Exception {
        this.bibli.save();
        Bibli b = Bibli.load();
        if(b.getListeAlbums().get(0).searchByAuthor("Clément Ignacio").size() != this.bibli.getListeAlbums().get(0).searchByAuthor("Clément Ignacio").size())
            fail("La sauvegarde n'a pas été correctement effectuée");
        if(!(b.getListeAlbums().get(0).getListeImages().get(0).getAuthor().equals("Clément Ignacio")))
            fail("Le chargement n'a pas été effectué correctement !");
    }

    /**
     * Test de l'addition d'un album
     * @throws Exception Propage les exceptions pouvant survenir lors des tests
     */

    @Test
    public void testAddAlbum() throws Exception {
        this.bibli.addAlbum(new Album("Funk"));
        if(this.bibli.getListeAlbums().contains("Funk")){
            fail("CD");
        }
    }

    /**
     * Test de la suppression d'un album
     * @throws Exception Propage les exceptions pouvant survenir lors des tests
     */

    @Test
    public void testDeleteAlbum() throws Exception {
        this.bibli.deleteAlbum(this.bibli.getListeAlbums().get(1));
        if(this.bibli.getListeAlbums().size() > 1)
            fail("La supression n'a pas été effectué correctement");
    }

    /**
     * Test de la récupération du nombre de catégorie
     * @throws Exception Propage les exceptions pouvant survenir lors des tests
     */

    @Test
    public void testGetCategory() throws Exception{
       int i = this.bibli.getCategory("Jazz");
        if(!this.bibli.getListeAlbums().get(i).getCategory().equals("Jazz")){
            fail("La méthode a bien renvoyé le bon entier !");
        }
    }

}