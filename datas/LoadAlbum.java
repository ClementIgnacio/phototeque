package pda.datas;

/**
 * Charge les albums
 */
public class LoadAlbum {

    public LoadAlbum() {
        Bibli bib = new Bibli();
        Album alb = new Album("First");

        bib.addAlbum(alb);
        bib.save();

    }
}
