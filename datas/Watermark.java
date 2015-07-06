package pda.datas;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Ecrit le tatouage sur l'image
 */
public class Watermark {

    /**
     * Ecrit sur l'image passé en paramètre le nom de l'auteur
     * @param imageicon L'image physique
     * @param image Les données sur l'image
     * @return La nouvelle image physique
     */

    public static ImageIcon DrawWatermark(ImageIcon imageicon, Image image) {
        String[] img = image.getPath().split("[.]");
        ImageIcon rendu = imageicon;
        if (!(new File(img[0] + "-pw.jpg").exists())) {
            ImageIcon imageicon2 = imageicon;
            BufferedImage bufferedImage = new BufferedImage(imageicon2.getIconWidth(),
                    imageicon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            // create graphics object and add original image to it
            Graphics graphics = bufferedImage.getGraphics();
            graphics.drawImage(imageicon2.getImage(), 0, 0, null);
            // set font for the watermark text
            graphics.setFont(new Font("Arial", Font.BOLD, imageicon.getIconWidth()/15));
            graphics.setColor(Color.black);

            String watermark = "\u00a9" + image.getAuthor();
            //unicode characters for (c) is \u00a9
            if (watermark.equals("\u00a9"))
                watermark = "\u00a9" + "Photothèque";

            // add the watermark text
            graphics.drawString(watermark, (int)(imageicon2.getIconWidth() /3), imageicon2.getIconHeight() / 2);
            graphics.dispose();

            try {
                ImageIO.write(bufferedImage, "jpg", new File(img[0] + "-pw.jpg"));
                rendu = imageicon2;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            rendu = new ImageIcon(img[0]+"-pw.jpg");
        }
        return rendu;
    }

}