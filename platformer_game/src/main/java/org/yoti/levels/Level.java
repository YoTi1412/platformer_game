package org.yoti.levels;

import org.yoti.entities.Crabby;
import org.yoti.main.Game;
import org.yoti.objects.GameContainers;
import org.yoti.objects.Potions;
import org.yoti.utils.HelpMethods;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.yoti.utils.HelpMethods.*;

public class Level {
    private BufferedImage image;
    private ArrayList<Crabby> crabbies;
    private ArrayList<Potions> potions;
    private ArrayList<GameContainers> containers;
    private int levelTilessWide;
    private int maxTilessWide;
    private int maxLevelOffsetX;
    private int[][] levelData;
    private Point playerSpawn;

    public Level(BufferedImage image) {
        this.image = image;
        creatLevelData();
        creatEnemies();
        createPotions();
        createContainers();
        calculateLevelOffsets();
        calculatePlayerSpawn();
    }

    private void createContainers() {
        containers = HelpMethods.GetContainers(image);
    }

    private void createPotions() {
        potions = HelpMethods.GetPotions(image);
    }

    private void calculatePlayerSpawn() {
        playerSpawn = GetPlayerSpawn(image);
    }

    private void calculateLevelOffsets() {
        levelTilessWide = image.getWidth();
        maxTilessWide = levelTilessWide - Game.TILES_IN_WIDTH;
        maxLevelOffsetX = Game.TILES_SIZE * maxTilessWide;
    }

    private void creatEnemies() {
        crabbies = GetCrabs(image);
    }

    private void creatLevelData() {
        levelData = GetLevelData(image);
    }

    public int getSpriteIndex(int x, int y) {
        return levelData[y][x];
    }

    public int[][] getLevelData() {
        return levelData;
    }

    public ArrayList<Crabby> getCrabbies() {
        return crabbies;
    }

    public int getMaxLevelOffsetX() {
        return maxLevelOffsetX;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public ArrayList<Potions> getPotions() {
        return potions;
    }

    public ArrayList<GameContainers> getContainers() {
        return containers;
    }
}
