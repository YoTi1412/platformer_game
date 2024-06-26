package org.yoti.utils;

import org.yoti.entities.Crabby;
import org.yoti.main.Game;
import org.yoti.objects.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.yoti.utils.Constants.EnemyConstants.*;
import static org.yoti.utils.Constants.ObjectConstants.*;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {
        if (!IsSolid(x, y, levelData))
            if (!IsSolid(x + width, y + height, levelData))
                if (!IsSolid(x + width, y, levelData))
                    if (!IsSolid(x, y + height, levelData))
                        return true;
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] levelData) {
        int maxWidth = levelData[0].length * Game.TILES_SIZE;

        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, levelData);
    }

    private static boolean IsTileSolid(int xTile, int yTile, int[][] levelData) {
        int value = levelData[yTile][xTile];

        if (value >= 48 || value < 0 || value != 11) {
            return true;
        }
        return false;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int)hitbox.x / Game.TILES_SIZE;

        if (xSpeed > 0) {
            // right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else {
            // left
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int)hitbox.y / Game.TILES_SIZE;

        if (airSpeed > 0) {
            // fall & touching floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else {
            // jump
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] levelData){
        // check the pixel below bottomleft and bottomright
        if (!IsSolid(hitbox.x,hitbox.y + hitbox.height + 1, levelData)) {
            if (!IsSolid(hitbox.x + hitbox.width,hitbox.y + hitbox.height + 1, levelData)) {
                return false;
            }
        }
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] levelData) {
        if (xSpeed > 0) {
            return IsSolid(hitbox.x + hitbox.width + xSpeed,hitbox.y + hitbox.height + 1, levelData);
        } else {
            return IsSolid(hitbox.x  + xSpeed,hitbox.y + hitbox.height + 1, levelData);
        }
    }

    public static ArrayList<Cannon> GetCannons(BufferedImage img) {
        ArrayList<Cannon> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == CANNON_LEFT || value == CANNON_RIGHT) {
                    list.add(new Cannon(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
                }
            }
        }
        return list;
    }

    public static boolean CanCannonSeePlayer(int[][] levelData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

        if (firstXTile > secondXTile) {
            return IsAllTilesClear(secondXTile, firstXTile, yTile, levelData);
        } else {
            return IsAllTilesClear(firstXTile, secondXTile, yTile, levelData);
        }
    }

    public static boolean IsAllTilesClear(int xStart, int xEnd, int y, int[][] levelData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, levelData)) {
                return false;
            }
        }
        return true;
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] levelData) {
        if (IsAllTilesClear(xStart, xEnd, y, levelData))
            for (int i = 0; i < xEnd - xStart; i++) {
                if (!IsTileSolid(xStart + i, y + 1, levelData)) {
                    return false;
                }
            }
        return true;
    }

    public static boolean IsSightClear(int[][] levelData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int tileY) {
        int firstXTile = (int)(firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int)(secondHitbox.x / Game.TILES_SIZE);

        if (firstXTile > secondXTile) {
            return IsAllTilesWalkable(secondXTile,firstXTile,tileY,levelData);
        } else {
            return IsAllTilesWalkable(firstXTile,secondXTile,tileY,levelData);
        }
    }

    public static int[][] GetLevelData(BufferedImage image) {
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

    public static ArrayList<Crabby> GetCrabs(BufferedImage image) {
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

    public static Point GetPlayerSpawn(BufferedImage image) {
        for (int j = 0; j < image.getHeight(); j++)
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == 100)
                    return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
            }
        return new Point(Game.TILES_SIZE, Game.TILES_SIZE);
    }

    public static ArrayList<Potions> GetPotions(BufferedImage image) {
        ArrayList<Potions> list = new ArrayList<>();
        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getBlue();
                if (value == RED_POTION || value == BLUE_POTION) {
                    list.add(new Potions(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
                }
            }
        }

        return list;
    }

    public static ArrayList<GameContainers> GetContainers(BufferedImage image) {
        ArrayList<GameContainers> list = new ArrayList<>();
        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getBlue();
                if (value == BOX || value == BARREL) {
                    list.add(new GameContainers(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
                }
            }
        }
        return list;
    }

    public static ArrayList<Spike> GetSpikes(BufferedImage image) {
        ArrayList<Spike> list = new ArrayList<>();
        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getBlue();
                if (value == SPIKE) {
                    list.add(new Spike(i * Game.TILES_SIZE, j * Game.TILES_SIZE, SPIKE));
                }
            }
        }
        return list;
    }

    public static boolean IsProjectileHittingLevel(Projectile p, int[][] levelData) {
        return IsSolid(p.getHitbox().x + p.getHitbox().width / 2, p.getHitbox().y + p.getHitbox().height / 2, levelData);
    }
}
