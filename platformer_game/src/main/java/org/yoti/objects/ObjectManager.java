package org.yoti.objects;

import org.yoti.entities.Player;
import org.yoti.gamestates.Playing;
import org.yoti.levels.Level;
import org.yoti.main.Game;
import org.yoti.utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.yoti.utils.Constants.ObjectConstants.*;
import static org.yoti.utils.Constants.Projectiles.*;
import static org.yoti.utils.HelpMethods.*;

public class ObjectManager {
    private Playing playing;
    private BufferedImage[][] potionImages, containerImages;
    private BufferedImage[] cannonImages;
    private BufferedImage spikeImage, cannonBallImage;
    private ArrayList<Potions> potions;
    private ArrayList<GameContainers> containers;
    private ArrayList<Spike> spikes;
    private ArrayList<Cannon> cannons;
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImages();
    }

    public void checkSpikesTouched(Player p) {
        for (Spike s : spikes)
            if (s.getHitbox().intersects(p.getHitbox()))
                p.kill();
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        for (Potions p : potions) {
            if (p.isActive()) {
                if (hitbox.intersects(p.getHitbox())) {
                    p.setActive(false);
                    applyEffectToPlayer(p);
                }
            }
        }
    }

    public void applyEffectToPlayer(Potions p) {
        if (p.getObjectType() == RED_POTION) {
            playing.getPlayer().changeHealth(RED_POTION_VALUE);
        }
        else {
            playing.getPlayer().changePower(BLUE_POTION_VALUE);
        }
    }

    public void checkObjectHit(Rectangle2D.Float attackbox) {
        for (GameContainers gc : containers) {
            if (gc.isActive() && !gc.doAnimation) {
                if (gc.getHitbox().intersects(attackbox)) {
                    gc.setDoAnimation(true);
                    int type = 0;
                    if (gc.getObjectType() == BARREL) {
                        type = 1;
                    }
                    potions.add(new Potions((int) (gc.getHitbox().x + gc.getHitbox().width / 2), (int) (gc.getHitbox().y - gc.getHitbox().height / 2), type));
                    return;
                }
            }
        }
    }

    public boolean isPlayerInRange(Cannon c, Player player) {
        int absoluteValue = (int) Math.abs(player.getHitbox().x - c.getHitbox().x);
        return absoluteValue <= Game.TILES_SIZE * 5;
    }

    public boolean isPlayerInfrontOfCannon(Cannon c, Player player) {
        if (c.getObjectType() == CANNON_LEFT) {
            if (c.getHitbox().x > player.getHitbox().x) {
                return true;
            }
        } else if (c.getHitbox().x < player.getHitbox().x) {
            return true;
        }
        return false;
    }

    public void updateCannons(int[][] levelData, Player player) {
        for (Cannon c : cannons) {
            if (!c.doAnimation) {
                if (c.getTileY() == player.getTileY()) {
                    if (isPlayerInRange(c, player)) {
                        if (isPlayerInfrontOfCannon(c, player)) {
                            if (CanCannonSeePlayer(levelData, player.getHitbox(), c.getHitbox(), c.getTileY())) {
                                c.setAnimation(true);
                            }
                        }
                    }
                }
            }
            c.update();
            if (c.getAnimationIndex() == 4 && c.getAnimationTick() == 0) {
                shootCannon(c);
            }
        }
    }

    public void shootCannon(Cannon c) {
        int direction = 1;
        if (c.getObjectType() == CANNON_LEFT)
            direction = -1;

        projectiles.add(new Projectile((int) c.getHitbox().x, (int) c.getHitbox().y, direction));
    }

    public void drawCannons(Graphics g, int xLvlOffset) {
        for (Cannon c : cannons) {
            int x = (int) (c.getHitbox().x - xLvlOffset);
            int width = CANNON_WIDTH;

            if (c.getObjectType() == CANNON_RIGHT) {
                x += width;
                width *= -1;
            }

            g.drawImage(cannonImages[c.getAnimationIndex()], x, (int) (c.getHitbox().y), width, CANNON_HEIGHT, null);
        }

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

        spikeImage = LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);

        cannonImages = new BufferedImage[7];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CANNON_ATLAS);

        for (int i = 0; i < cannonImages.length; i++) {
            cannonImages[i] = temp.getSubimage(i * 40, 0, 40, 26);
        }

        cannonBallImage = LoadSave.GetSpriteAtlas(LoadSave.CANNON_BALL);
    }

    public void update(int[][] levelData, Player player) {
        for (Potions p : potions) {
            if (p.isActive()) {
                p.update();
            }
        }
        for (GameContainers gc : containers) {
            if (gc.isActive()) {
                gc.update();
            }
        }
        updateCannons(levelData, player);
        updateProjectiles(levelData, player);
    }

    private void updateProjectiles(int[][] lvlData, Player player) {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                p.updatePos();
                if (p.getHitbox().intersects(player.getHitbox())) {
                    player.changeHealth(-25);
                    p.setActive(false);
                } else if (IsProjectileHittingLevel(p, lvlData)) {
                    p.setActive(false);
                }
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
        drawTraps(g, xLvlOffset);
        drawCannons(g, xLvlOffset);
        drawProjectiles(g, xLvlOffset);
    }

    private void drawProjectiles(Graphics g, int xLvlOffset) {
        for (Projectile p : projectiles) {
            if (p.isActive()){
                g.drawImage(cannonBallImage, (int) (p.getHitbox().x - xLvlOffset), (int) (p.getHitbox().y), CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);
            }
        }
    }


    private void drawTraps(Graphics g, int xLevelOffset) {
        for (Spike s : spikes) {
            g.drawImage(spikeImage, (int) (s.getHitbox().x - xLevelOffset), (int) (s.getHitbox().y - s.getYDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);
        }
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

    public void loadObjects(Level newLevel) {
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
        spikes = newLevel.getSpikes();
        cannons = newLevel.getCannons();
        projectiles.clear();
    }

    public void resetAllObjects() {
        loadObjects(playing.getLevelManager().getCurrentLevel());

        for (Potions p : potions) {
            p.reset();
        }
        for (GameContainers gc : containers) {
            gc.reset();
        }
        for (Cannon c : cannons) {
            c.reset();
        }
    }
}
