package org.yoti.entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static org.yoti.utils.Constants.Directions.*;
import static org.yoti.utils.Constants.Directions.DOWN;
import static org.yoti.utils.Constants.PlayerConstants.*;


public class Player extends Entity {
    private BufferedImage[][] animations;
    private int animationTick, animationIndex;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean playerMoving = false;
    public Player(float x, float y) {
        super(x, y);
        loadAnimation();
    }
    public void update() {
        updateAnimation();
        setAnimation();
        updatePosition();
    }
    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int) x, (int) y, 256, 160, null);
    }

    /**
     * Updates the player's position based on movement direction.
     */
    private void updatePosition() {
        if (playerMoving) {
            switch (playerDirection) {
                case LEFT:
                    x -= 1;
                    break;
                case UP:
                    y -= 1;
                    break;
                case RIGHT:
                    x += 1;
                    break;
                case DOWN:
                    y += 1;
                    break;
            }
        }
    }

    /**
     * Sets the appropriate player action based on movement.
     */
    private void setAnimation() {
        if (playerMoving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
    }

    /**
     * Updates the animation index based on animation speed.
     */
    private void updateAnimation() {
        animationTick++;
        int animationSpeed = 15;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(playerAction)) {
                animationIndex = 0;
            }
        }
    }

    /**
     * Sets the direction of player movement.
     * @param direction The direction to set for player movement.
     */
    public void setDirection(int direction) {
        this.playerDirection = direction;
        playerMoving = true;
    }

    /**
     * Sets the flag indicating whether the player is moving.
     * @param playerMoving True if player is moving, false otherwise.
     */
    public void setPlayerMoving(boolean playerMoving) {
        this.playerMoving = playerMoving;
    }

    private void loadAnimation() {
        InputStream inputStream = getClass().getResourceAsStream("/player_sprites.png");

        try {
            BufferedImage img = ImageIO.read(inputStream);

            animations = new BufferedImage[9][6];

            for (int i = 0; i < animations.length; i++) {
                for (int j = 0; j < animations[i].length; j++) {
                    animations[i][j] = img.getSubimage(j * 64, i * 40, 64, 40);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
