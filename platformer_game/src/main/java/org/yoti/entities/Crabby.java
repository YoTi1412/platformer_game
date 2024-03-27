package org.yoti.entities;

import org.yoti.main.Game;

import static org.yoti.utils.Constants.EnemyConstants.*;

public class Crabby extends Enemy{
    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitbox(x,y,(int)(22 * Game.SCALE),(int)(19 * Game.SCALE));


    }
}
