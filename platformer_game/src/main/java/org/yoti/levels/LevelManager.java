package org.yoti.levels;

import org.yoti.main.Game;
import org.yoti.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.yoti.main.Game.*;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;


    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levelOne = new Level(LoadSave.GetLevelData());
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas((LoadSave.LEVEL_ATLAS));
        levelSprite = new BufferedImage[48];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                int index = i*12 + j;
                levelSprite[index] = img.getSubimage(j * 32, i *32,32,32);
            }
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < TILES_IN_HEIGHT; i++) {
            for (int j = 0; j < TILES_IN_WIDTH; j++) {
                int index = levelOne.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], TILES_SIZE * j,TILES_SIZE * i, TILES_SIZE,TILES_SIZE, null);
            }
        }
    }

    public void update(){

    }
}
