package org.yoti.entities;

import org.yoti.main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.yoti.utils.Constants.Directions.*;
import static org.yoti.utils.Constants.EnemyConstants.*;

public class Crabby extends Enemy{

    // attack Box
    private int attackBoxOffsetX;
    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitbox(22,19);
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y,(int)(82 * Game.SCALE),(int)(19 * Game.SCALE));
        attackBoxOffsetX = (int)(30 * Game.SCALE);
    }

    public void update(int[][] levelData, Player player) {
        updateBehavior(levelData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }

    private void updateBehavior(int[][] levelData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(levelData);
        }
        if (inAir) {
            updateInAir(levelData);
        } else {
            switch (state) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:

                    if (canSeePlayer(levelData, player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseToAttack(player)) {
                            newState(ATTACK);
                        }
                    }
                    move(levelData);
                    break;
                case ATTACK:
                    if (animationIndex == 0) {
                        attackChecked = false;
                    }
                    if (animationIndex == 3 && !attackChecked) {
                        checkPlayerHit(attackBox, player);
                    }
                    break;
                case HIT:
                    break;
            }
        }
    }

    public int flipX() {
        if (walkDirection == RIGHT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if (walkDirection == RIGHT)
            return -1;
        else
            return 1;
    }
}
