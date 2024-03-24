package org.yoti.entities;

import org.yoti.gamestates.Playing;
import org.yoti.utils.LoadSave;
import static org.yoti.utils.Constants.EnemyConstants.*;

import java.awt.*;
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

    public void update() {
        for (Crabby crabby : crabbies) {
            crabby.update();
        }
    }

    public void draw(Graphics g) {
        drawCrabs(g);
    }

    private void drawCrabs(Graphics g) {
        for (Crabby crabby : crabbies) {
            g.drawImage(crabbyArray[crabby.getEnemyState()][crabby.getAnimationIndex()], (int)(crabby.getHitbox().x), (int)(crabby.getHitbox().y), CRABBY_WIDTH, CRABBY_HEIGHT, null);
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
}
