package org.yoti.entities;

import org.yoti.main.Game;
import org.yoti.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.yoti.utils.Constants.PlayerConstants.*;
import static org.yoti.utils.HelpMethods.CanMoveHere;


public class Player extends Entity {
    private BufferedImage[][] animations;
    private int animationTick, animationIndex;
    private int playerAction = IDLE;
    private boolean playerMoving = false, playerAttacking = false;
    private boolean left, up, right, down;
    public float playerSpeed = 1.5f;
    private int[][] levelData;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimation();
        initHitbox(x, y, 20 * (int)Game.SCALE, 28 * (int)Game.SCALE);
    }
    public void update() {
        updatePosition();
        updateAnimation();
        setAnimation();
    }
    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), width, height, null);
        drawHitbox(g);
    }

    private void updatePosition() {
        playerMoving = false;

        if (!left && !right && !up && !down) {
            return;
        }

        float xSpeed = 0, ySpeed = 0;

        if (left && !right) {
            xSpeed = -playerSpeed;
        } else if (right && !left ){
            xSpeed = playerSpeed;
        }

        if (up && !down) {
            ySpeed = -playerSpeed;
        } else if (down && !up ){
            ySpeed = playerSpeed;
        }

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
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

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
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
