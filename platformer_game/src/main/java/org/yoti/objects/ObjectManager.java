package org.yoti.objects;

import org.yoti.gamestates.Playing;
import org.yoti.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.yoti.utils.Constants.ObjectConstants.*;

public class ObjectManager {
    private Playing playing;
    private BufferedImage[][] potionImages, containerImages;
    private ArrayList<Potions> potions;
    private ArrayList<GameContainers> containers;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImages();
    }

    private void loadImages() {
        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
        potionImages = new BufferedImage[2][7];

        for (int j = 0; j < potionImages.length; j++) {
            for (int i = 0; i < potionImages[j].length; i++) {
                potionImages[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);
            }
        }

        BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
        containerImages = new BufferedImage[2][8];

        for (int j = 0; j < containerImages.length; j++) {
            for (int i = 0; i < containerImages[j].length; i++) {
                containerImages[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);
            }
        }
    }

    public void update() {
        for (Potions p : potions)
            if (p.isActive())
                p.update();

        for (GameContainers gc : containers)
            if (gc.isActive())
                gc.update();
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
    }

    private void drawContainers(Graphics g, int xLevelOffset) {
        for (GameContainers gc : containers) {
            if (gc.isActive()) {
                int type = 0;
                if (gc.getObjectType() == BARREL) {
                    type = 1;
                }
                g.drawImage(containerImages[type][gc.getAnimationIndex()], (int) (gc.getHitbox().x - gc.getXDrawOffset() - xLevelOffset), (int) (gc.getHitbox().y - gc.getYDrawOffset()), CONTAINER_WIDTH,
                        CONTAINER_HEIGHT, null);
            }
        }
    }

    private void drawPotions(Graphics g, int xLevelOffset) {
        for (Potions p : potions) {
            if (p.isActive()) {
                int type = 0;
                if (p.getObjectType() == RED_POTION)
                    type = 1;
                g.drawImage(potionImages[type][p.getAnimationIndex()], (int) (p.getHitbox().x - p.getXDrawOffset() - xLevelOffset), (int) (p.getHitbox().y - p.getYDrawOffset()), POTION_WIDTH, POTION_HEIGHT,
                        null);
            }
        }
    }
}
