package pda.tests;

import pda.datas.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe permetant de tester les images
 */

public class ImageTest {

    /**
     * Image permettant les tests
     */

    private Image img;

    /**
     * Constructeur permettant l'initialisation des tests
     */

    public ImageTest(){
        this.img = new Image("src/pda/img/test.png");
        try {
            this.testAddKeyword();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test de l'ajout de mots clés
     * @throws Exception Propage les exceptions pouvant survenir lors des tests
     */

    @Test
    public void testAddKeyword() throws Exception {
        this.img.addKeyword("Bonjour");
        if(!(this.img.getKeywords().get(0).equals("Bonjour"))){
            fail("Le mot clé n'a pas été ajouté correctement !");
        }
    }

    /**
     * Test de la suppression d'un mot clé
     * @throws Exception Propage les exceptions pouvant survenir lors des tests
     */

    @Test
    public void testDeleteKeyword() throws Exception {
        this.img.deleteKeyword("Bonjour");
        if(this.img.getKeywords().contains("Bonjour")){
            fail("Le mot clé n'a pas été supprimé correctement !");
        }
    }

    /**
     * Test de la suppression du tatouage
     * @throws Exception Propage les exceptions pouvant survenir lors des tests
     */

    @Test
    public void testDeleteWatermark() throws Exception {
        this.img.setWatermark(false);
        this.img.deleteWatermark();
        if(this.img.getWatermark()){
            fail("La protection anti-copie n'a pas été supprimé !");
        }
    }

    /**
     * Test de l'export en PDF
     * @throws Exception Propage les exceptions pouvant survenir lors des tests
     */

    @Test
    public void testExportToPdf() throws Exception {
        // A faire !
    }
}