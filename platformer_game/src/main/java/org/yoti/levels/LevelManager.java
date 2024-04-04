package org.yoti.levels;

import org.yoti.gamestates.GameStates;
import org.yoti.main.Game;
import org.yoti.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int levelIndex = 0;


    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();

        for (BufferedImage image : allLevels) {
            levels.add(new Level(image));
        }
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++)
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
    }

    public void draw(Graphics g, int xLevelOffset) {
        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
            for (int i = 0; i < levels.get(levelIndex).getLevelData()[0].length; i++) {
                int index = levels.get(levelIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], Game.TILES_SIZE * i - xLevelOffset, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levels.get(levelIndex);
    }

    public int getAmountOfLevels() {
        return levels.size();
    }

    public void loadNextLevel() {
        levelIndex++;
        if (levelIndex >= levels.size()) {
            levelIndex = 0;
            System.out.println("No more Levels! Game Completed");
            GameStates.states = GameStates.MENU;
        }

        Level newLevel = levels.get(levelIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLevelData(newLevel.getLevelData());
        game.getPlaying().setMaxLevelOffsetX(newLevel.getMaxLevelOffsetX());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }
}
