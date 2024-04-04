package org.yoti.objects;

import org.yoti.main.Game;

import static org.yoti.utils.Constants.ObjectConstants.*;

public class GameContainers extends GameObjects {
    public GameContainers(int x, int y, int objType) {
        super(x, y, objType);
        createHitbox();
    }

    private void createHitbox() {
        if (objectType == BOX) {
            initHitbox(25, 18);

            xDrawOffset = (int) (7 * Game.SCALE);
            yDrawOffset = (int) (12 * Game.SCALE);

        } else {
            initHitbox(23, 25);
            xDrawOffset = (int) (8 * Game.SCALE);
            yDrawOffset = (int) (5 * Game.SCALE);
        }

        hitbox.y += yDrawOffset + (int) (Game.SCALE * 2);
        hitbox.x += (float) xDrawOffset / 2; // casting to float
    }

    public void update() {
        if (doAnimation) {
            updateAnimationTick();
        }
    }
}
