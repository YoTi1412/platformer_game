package org.yoti.entities;

import org.yoti.gamestates.Playing;
import org.yoti.main.Game;
import org.yoti.utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static org.yoti.utils.Constants.*;
import static org.yoti.utils.Constants.PlayerConstants.*;
import static org.yoti.utils.HelpMethods.*;


public class Player extends Entity {
    private BufferedImage[][] animations;
    private boolean playerMoving = false, playerAttacking = false;
    private boolean left, up, right, jump;
    private int[][] levelData;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;

    // Jump / Gravity
    private float jumpSpedd = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;

    // status bar UI
    private BufferedImage statusBarImage;
    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);
    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (34 * Game.SCALE);
    private int healthBarYStart = (int) (14 * Game.SCALE);
    private int healthWidth = healthBarWidth;

    // attack Box
    private int flipX = 0;
    private int flipW = 1;
    private boolean attackChecked;
    private Playing playing;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        this.state = IDLE;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        this.walkSpeed = 1.0f * Game.SCALE;
        loadAnimation();
        initHitbox(20, 27);
        initAttackBox();
    }

    public void setSpawn(Point spawn) {
        this.x = spawn.x;
        this.y = spawn.y;
        hitbox.x = x;
        hitbox.y = y;
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y, (int)(20 * Game.SCALE), (int)(20 * Game.SCALE));
    }

    public void update() {
        updateHealthBar();

        if(currentHealth <= 0) {
            playing.setGameOver(true);
            return;
        }

        updateAttackBox();

        updatePosition();
        if (playerMoving) {
            checkPotionTouched();
        }

        if (playerAttacking) {
            checkAttack();
        }
        updateAnimation();
        setAnimation();
    }

    private void checkPotionTouched() {
        playing.checkPotionTouched(hitbox);
    }

    private void checkAttack() {
        if (attackChecked || animationIndex != 1) {
            return;
        }

        attackChecked = true;
        playing.checkEnemyHit(attackBox);
        playing.checkObjectHit(attackBox);
    }

    private void updateAttackBox() {
        if (right) {
            attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE * 10);
        } else if (left) {
            attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE * 10);
        }
        attackBox.y = hitbox.y + (Game.SCALE * 10);
    }

    private void updateHealthBar() {
        healthWidth = (int)((currentHealth / (float)maxHealth) * healthBarWidth);
    }

    public void render(Graphics g, int xLevelOffset) {
        g.drawImage(animations[state][animationIndex],
                (int)(hitbox.x - xDrawOffset) - xLevelOffset + flipX,
                (int)(hitbox.y - yDrawOffset),
                width * flipW, height, null);
        // drawHitbox(g, xLevelOffset);
        // drawAttackBox(g, xLevelOffset);

        drawUI(g);
    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImage,statusBarX,statusBarY,statusBarWidth,statusBarHeight,null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY,healthWidth,healthBarHeight);
    }

    private void updatePosition() {
        playerMoving = false;

        if (jump) {
            jump();
        }

        if (!inAir) {
            if (!left && !right || (right && left)) {
                return;
            }
        }

        float xSpeed = 0;

        if (left) {
            xSpeed -= walkSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right){
            xSpeed += walkSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir) {
            if (!IsEntityOnFloor(hitbox, levelData)) {
                inAir = true;
            }
        }

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += airSpeed;
                airSpeed += GRAVITY;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }

        playerMoving = true;
    }

    private void jump() {
        if (inAir) {
            return;
        }
        inAir = true;
        airSpeed = jumpSpedd;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y , hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    private void setAnimation() {
        int startAnimation = state;

        if (playerMoving) {
            state = RUNNING;
        } else {
            state = IDLE;
        }

        if (inAir) {
            if (airSpeed < 0) {
                state = JUMP;
            } else {
                state = FALLING;
            }
        }

        if (playerAttacking) {
            state = ATTACK;
            if (startAnimation != ATTACK) {
                animationIndex = 1;
                animationTick = 0;
                return;
            }
        }
        
        if (startAnimation != state) {
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    private void updateAnimation() {
        animationTick++;
        if (animationTick >= ANIMATION_SPEED) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(state)) {
                animationIndex = 0;
                playerAttacking = false;
                attackChecked = false;
            }
        }
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
        if (!IsEntityOnFloor(hitbox, levelData)) {
            inAir = true;
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

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    private void loadAnimation() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[7][8];

        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 64, i * 40, 64, 40);
            }
        }

        statusBarImage = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
    }

    public void changeHealth(int value) {
        currentHealth += value;
        currentHealth = Math.max(Math.min(currentHealth, maxHealth), 0); // same code but simpler
    }

    public void changePower(int value) {
        System.out.println("Added power!");
    }

    public void resetDirectionBoolean() {
        left = false;
        up = false;
        right = false;
    }

    public void setPlayerAttacking(boolean playerAttacking) {
        this.playerAttacking = playerAttacking;
    }

    public void resetAll() {
        resetDirectionBoolean();
        inAir = false;
        playerAttacking = false;
        playerMoving = false;
        state = IDLE;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;

        if (!IsEntityOnFloor(hitbox, levelData)) {
            inAir = true;
        }
    }
}
