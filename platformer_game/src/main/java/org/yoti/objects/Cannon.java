package org.yoti.objects;

import org.yoti.main.Game;

public class Cannon extends GameObjects {
    private int tileY;
    public Cannon(int x, int y, int objectType) {
        super(x, y, objectType);
        tileY = y / Game.TILES_SIZE;
        initHitbox(40, 26);
        hitbox.x -= (int) (4 * Game.SCALE);
        hitbox.y += (int) (6 * Game.SCALE);
    }

    public void update() {
        if (doAnimation) {
            updateAnimationTick();
        }
    }

    public int getTileY() {
        return tileY;
    }
}
