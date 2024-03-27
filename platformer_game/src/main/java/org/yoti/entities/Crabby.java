package org.yoti.entities;

import org.yoti.main.Game;

import static org.yoti.utils.Constants.Directions.LEFT;
import static org.yoti.utils.Constants.EnemyConstants.*;
import static org.yoti.utils.HelpMethods.*;

public class Crabby extends Enemy{
    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitbox(x,y,(int)(22 * Game.SCALE),(int)(19 * Game.SCALE));
    }

    public void update(int[][] levelData, Player player) {
        updateMove(levelData, player);
        updateAnimationTick();
    }

    private void updateMove(int[][] levelData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(levelData);
        }
        if (inAir) {
            updateInAir(levelData);
        } else {
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:

                    if (canSeePlayer(levelData, player)) {
                        turnTowardsPlayer(player);
                    }
                    if (isPlayerCloseToAttack(player)) {
                        newState(ATTACK);
                    }
                    move(levelData);
                    break;
            }
        }
    }
}
