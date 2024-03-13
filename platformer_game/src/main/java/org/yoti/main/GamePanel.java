package org.yoti.main;

import org.yoti.inputs.KeyboardInputs;
import org.yoti.inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static org.yoti.utils.Constants.PlayerConstants.*;
import static org.yoti.utils.Constants.Directions.*;

/**
 * Represents the game panel where the game graphics are displayed and interacted with.
 */
public class GamePanel extends JPanel {
    private float xDelta = 100, yDelta = 100;
    private final int frames = 0;
    private final long lastCheck = 0;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int animationTick, animationIndex;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean playerMoving = false;

    /**
     * Constructs a new GamePanel object.
     */
    public GamePanel() {
        MouseInputs mouseInputs = new MouseInputs(this);
        KeyboardInputs keyboardInputs = new KeyboardInputs(this);

        importImage();
        loadAnimation();
        setPanelSize();

        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    /**
     * Loads the animation frames from the image.
     */
    private void loadAnimation() {
        animations = new BufferedImage[9][6];

        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 64, i * 40, 64, 40);
            }
        }
    }

    /**
     * Imports the player sprite image.
     */
    private void importImage() {
        InputStream inputStream = getClass().getResourceAsStream("/player_sprites.png");

        try {
            img = ImageIO.read(inputStream);
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

    /**
     * Sets the size of the game panel.
     */
    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
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

    /**
     * Paints the game components onto the panel.
     * @param g The Graphics object used for painting.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(animations[playerAction][animationIndex], (int) xDelta, (int) yDelta, 256, 160, null);
    }

    /**
     * Updates the player's position based on movement direction.
     */
    private void updatePosition() {
        if (playerMoving) {
            switch (playerDirection) {
                case LEFT:
                    xDelta -= 5;
                    break;
                case UP:
                    yDelta -= 5;
                    break;
                case RIGHT:
                    xDelta += 5;
                    break;
                case DOWN:
                    yDelta += 5;
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
     * Updates the game state.
     */
    public void updateGame() {
        updateAnimation();
        setAnimation();
        updatePosition();
    }
}
