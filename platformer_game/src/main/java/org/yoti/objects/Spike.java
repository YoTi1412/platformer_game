package org.yoti.objects;

import org.yoti.main.Game;

public class Spike extends GameObjects {

    public Spike(int x, int y, int objectType) {
        super(x, y, objectType);

        initHitbox(32, 16);
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * 16);
        hitbox.y += yDrawOffset;

    }

}
