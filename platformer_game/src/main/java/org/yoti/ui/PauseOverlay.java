package org.yoti.ui;

import org.yoti.gamestates.GameStates;
import org.yoti.gamestates.Playing;
import org.yoti.main.Game;
import org.yoti.utils.Constants;
import org.yoti.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static org.yoti.utils.Constants.UI.PauseButtons.*;
import static org.yoti.utils.Constants.UI.UrmButtons.*;
import static org.yoti.utils.Constants.UI.VolumeButtons.*;

public class PauseOverlay {
    private Playing playing;
    private BufferedImage backgroundImage;
    private int backgroundX, backgroundY, backgroundWidth, backgroundHeight;
    private UrmButtons menuButton, replyButton, unpauseButton;
    private AudioOptions audioOptions;
    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        audioOptions = playing.getGame().getAudioOptions();
        creatUrmButtons();
    }

    private void creatUrmButtons() {
        int menuX = (int)(313 * Game.SCALE);
        int replyX = (int)(387 * Game.SCALE);
        int unpauseX = (int)(462 * Game.SCALE);
        int buttonsY = (int)(325 * Game.SCALE);

        menuButton = new UrmButtons(menuX, buttonsY, URM_SIZE, URM_SIZE, 2);
        replyButton = new UrmButtons(replyX, buttonsY, URM_SIZE, URM_SIZE, 1);
        unpauseButton = new UrmButtons(unpauseX, buttonsY, URM_SIZE, URM_SIZE, 0);

    }

    private void loadBackground() {
        backgroundImage = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        backgroundWidth = (int)(backgroundImage.getWidth() * Game.SCALE);
        backgroundHeight = (int)(backgroundImage.getHeight() * Game.SCALE);
        backgroundX = Game.GAME_WIDTH / 2 - backgroundWidth / 2;
        backgroundY = (int) (25 * Game.SCALE);
    }

    public void update() {
        menuButton.update();
        replyButton.update();
        unpauseButton.update();
        audioOptions.update();
    }
    public void draw(Graphics g) {
        // background
        g.drawImage(backgroundImage, backgroundX, backgroundY, backgroundWidth, backgroundHeight, null);

        // urm buttons
        menuButton.draw(g);
        replyButton.draw(g);
        unpauseButton.draw(g);
        audioOptions.draw(g);
    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuButton))
            menuButton.setMousePressed(true);
        else if (isIn(e, replyButton))
            replyButton.setMousePressed(true);
        else if (isIn(e, unpauseButton))
            unpauseButton.setMousePressed(true);
        else
            audioOptions.mousePressed(e);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuButton)) {
            if (menuButton.isMousePressed()) {
                playing.resetAll();
                playing.setGameStates(GameStates.MENU);
                playing.unpauseGame();
            }
        } else if (isIn(e, replyButton)) {
            if (replyButton.isMousePressed()) {
                playing.resetAll();
                playing.unpauseGame();
            }
        } else if (isIn(e, unpauseButton)) {
            if (unpauseButton.isMousePressed())
                playing.unpauseGame();
        } else
            audioOptions.mouseReleased(e);

        menuButton.resetBooleans();
        replyButton.resetBooleans();
        unpauseButton.resetBooleans();

    }

    public void mouseMoved(MouseEvent e) {
        menuButton.setMouseOver(false);
        replyButton.setMouseOver(false);
        unpauseButton.setMouseOver(false);

        if (isIn(e, menuButton))
            menuButton.setMouseOver(true);
        else if (isIn(e, replyButton))
            replyButton.setMouseOver(true);
        else if (isIn(e, unpauseButton))
            unpauseButton.setMouseOver(true);
        else
            audioOptions.mouseMoved(e);
    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }


}
