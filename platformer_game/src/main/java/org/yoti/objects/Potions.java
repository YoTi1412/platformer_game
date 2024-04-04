package org.yoti.objects;

import org.yoti.main.Game;

public class Potions extends GameObjects {
    private float hoverOffset;
    private int maxHoverOffset, hoverDirection = 1;

    public Potions(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;

        initHitbox(7, 14);

        xDrawOffset = (int) (3 * Game.SCALE);
        yDrawOffset = (int) (2 * Game.SCALE);

        maxHoverOffset = (int) (10 * Game.SCALE);
    }

    public void update() {
        updateAnimationTick();
        updateHover();
    }

    private void updateHover() {
        hoverOffset += (0.075f * Game.SCALE * hoverDirection);

        if (hoverOffset >= maxHoverOffset) {
            hoverDirection = -1;
        } else if (hoverOffset < 0) {
            hoverDirection = 1;
        }

        hitbox.y = y + hoverOffset;
    }
}
