package org.yoti.entities;

import org.yoti.gamestates.Playing;
import org.yoti.levels.Level;
import org.yoti.utils.LoadSave;
import static org.yoti.utils.Constants.EnemyConstants.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] crabbyArray;
    private ArrayList<Crabby> crabbies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImages();
    }

    public void loadEnemies(Level level) {
        crabbies = level.getCrabbies();
    }

    public void update(int[][] levelData, Player player) {
        boolean isAnyActive = false;

        for (Crabby c : crabbies) {
            if (c.isActive()) {
                c.update(levelData, player);
                isAnyActive = true;
            }
        }

        if (!isAnyActive) {
            playing.setLevelCompleted(true);
        }
    }

    public void draw(Graphics g, int xLevelOffset) {
        drawCrabs(g, xLevelOffset);
    }

    private void drawCrabs(Graphics g, int xLvlOffset) {
        for (Crabby c : crabbies) {
            if (c.isActive()) {
                g.drawImage(crabbyArray[c.getState()][c.getAnimationIndex()],
                        (int) c.getHitbox().x - xLvlOffset - CRABBY_DRAW_OFFSET_X + c.flipX(),
                        (int) (c.getHitbox().y - CRABBY_DRAW_OFFSET_Y),
                        CRABBY_WIDTH * c.flipW(),
                        CRABBY_HEIGHT, null);
                // c.drawHitbox(g, xLvlOffset);
                // c.drawAttackHitbox(g, xLvlOffset);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Crabby c : crabbies) {
            if (c.isActive()) {
                if (attackBox.intersects(c.getHitbox())) {
                    c.hurt(10);
                    return;
                }
            }
        }
    }

    private void loadEnemyImages() {
        crabbyArray = new BufferedImage[5][9];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE);

        for (int j = 0; j < crabbyArray.length; j++) {
            for (int i = 0; i < crabbyArray[j].length; i++) {
                crabbyArray[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
            }
        }
    }

    public void resetAllEnemies() {
        for (Crabby c : crabbies) {
            c.resetEnemy();
        }
    }
}
