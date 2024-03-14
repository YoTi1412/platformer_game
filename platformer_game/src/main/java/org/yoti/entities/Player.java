package org.yoti.entities;

import org.yoti.utils.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static org.yoti.utils.Constants.PlayerConstants.*;


public class Player extends Entity {
    private BufferedImage[][] animations;
    private int animationTick, animationIndex;
    private int playerAction = IDLE;
    private boolean playerMoving = false, playerAttacking = false;
    private boolean left, up, right, down;
    public float playerSpeed = 1.5f;
    public Player(float x, float y) {
        super(x, y);
        loadAnimation();
    }
    public void update() {
        updatePosition();
        updateAnimation();
        setAnimation();
    }
    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int) x, (int) y, 256, 160, null);
    }

    private void updatePosition() {
        playerMoving = false;

        if (left && !right) {
            x -= playerSpeed;
            playerMoving = true;
        } else if (right && !left ){
            x += playerSpeed;
            playerMoving = true;
        }

        if (up && !down) {
            y -= playerSpeed;
            playerMoving = true;
        } else if (down && !up ){
            y += playerSpeed;
            playerMoving = true;
        }
    }

    private void setAnimation() {
        int startAnimation = playerAction;

        if (playerMoving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (playerAttacking) {
            playerAction = ATTACK_1;
        }
        
        if (startAnimation != playerAction) {
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    private void updateAnimation() {
        animationTick++;
        int animationSpeed = 20;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(playerAction)) {
                animationIndex = 0;
                playerAttacking = false;
            }
        }
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    private void loadAnimation() {
            BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

            animations = new BufferedImage[9][6];

            for (int i = 0; i < animations.length; i++) {
                for (int j = 0; j < animations[i].length; j++) {
                    animations[i][j] = img.getSubimage(j * 64, i * 40, 64, 40);
                }
            }
    }

    public void resetDirectionBoolean() {
        left = false;
        up = false;
        right = false;
        down = false;
    }

    public void setPlayerAttacking(boolean playerAttacking) {
        this.playerAttacking = playerAttacking;
    }
}
