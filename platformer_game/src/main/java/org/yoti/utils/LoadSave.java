package org.yoti.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String PLAYER_ATLAS =  "player_sprites.png";
    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;

        InputStream inputStream = LoadSave.class.getResourceAsStream("/" + fileName);

        try {
            img = ImageIO.read(inputStream);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }
}
