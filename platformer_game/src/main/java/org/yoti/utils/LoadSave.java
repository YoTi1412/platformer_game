package org.yoti.utils;

import org.yoti.entities.Crabby;
import org.yoti.main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.yoti.utils.Constants.EnemyConstants.*;

public class LoadSave {
    public static final String PLAYER_ATLAS =  "player_sprites.png";
    public static final String LEVEL_ATLAS =  "outside_sprites.png";

    // public static final String LEVEL_ONE_DATA =  "level_one_data.png";
    public static final String LEVEL_ONE_DATA =  "level_one_data_long.png";
    public static final String MENU_BUTTONS =  "button_atlas.png";
    public static final String MENU_BACKGROUND =  "menu_background.png";
    public static final String PAUSE_BACKGROUND =  "pause_menu.png";
    public static final String SOUND_BUTTON =  "sound_button.png";
    public static final String URM_BUTTONS =  "urm_buttons.png";
    public static final String VOLUME_BUTTONS =  "volume_buttons.png";
    public static final String MENU_BACKGROUND_IMAGE =  "background_menu.png";
    public static final String PLAYING_BACKGROUND_IMAGE =  "playing_bg_img.png";
    public static final String BIG_CLOUDS =  "big_clouds.png";
    public static final String SMALL_CLOUDS =  "small_clouds.png";
    public static final String CRABBY_SPRITE =  "crabby_sprite.png";

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

    public static ArrayList<Crabby> GetCrabs() {
        BufferedImage image = GetSpriteAtlas(LEVEL_ONE_DATA);
        ArrayList<Crabby> list = new ArrayList<>();

        for (int j = 0; j < image.getHeight(); j++)
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == CRABBY) {
                    list.add((new Crabby(i * Game.TILES_SIZE, j * Game.TILES_SIZE)));
                }
            }
        return list;
    }

    public static int[][] GetLevelData() {
        BufferedImage image = GetSpriteAtlas(LEVEL_ONE_DATA);
        int[][] levelData = new int[image.getHeight()][image.getWidth()];

        for (int j = 0; j < image.getHeight(); j++)
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                levelData[j][i] = value;
            }
        return levelData;

    }
}
