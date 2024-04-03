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
    private SoundButton musicButton, sfxButton;
    private UrmButtons menuButton, replyButton, unpauseButton;
    private VolumeButton volumeButton;

    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        creatSoundButtons();
        creatUrmButtons();
        creatVolumeButton();
    }

    private void creatVolumeButton() {
        int volumeX = (int)(309 * Game.SCALE);
        int volumeY = (int)(278 * Game.SCALE);
        volumeButton = new VolumeButton(volumeX, volumeY, SLIDER_WIDTH, VOLUME_HEIGHT);
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

    private void creatSoundButtons() {
        int soundX = (int) (450 * Game.SCALE);
        int musicY = (int) (140 * Game.SCALE);
        int sfxY = (int) (186 * Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    private void loadBackground() {
        backgroundImage = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        backgroundWidth = (int)(backgroundImage.getWidth() * Game.SCALE);
        backgroundHeight = (int)(backgroundImage.getHeight() * Game.SCALE);
        backgroundX = Game.GAME_WIDTH / 2 - backgroundWidth / 2;
        backgroundY = (int) (25 * Game.SCALE);
    }

    public void update() {
        musicButton.update();
        sfxButton.update();
        menuButton.update();
        replyButton.update();
        unpauseButton.update();
        volumeButton.update();
    }
    public void draw(Graphics g) {
        // background
        g.drawImage(backgroundImage, backgroundX, backgroundY, backgroundWidth, backgroundHeight, null);

        // sound buttons
        musicButton.draw(g);
        sfxButton.draw(g);

        // urm buttons
        menuButton.draw(g);
        replyButton.draw(g);
        unpauseButton.draw(g);

        // volume slider
        volumeButton.draw(g);
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        } else if (isIn(e, menuButton)) {
            menuButton.setMousePressed(true);
        } else if (isIn(e, replyButton)) {
            replyButton.setMousePressed(true);
        } else if (isIn(e, unpauseButton)) {
            unpauseButton.setMousePressed(true);
        } else if (isIn(e, volumeButton)) {
            volumeButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }
        } else if (isIn(e, sfxButton)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
            }
        } else if (isIn(e, menuButton)) {
            if (menuButton.isMousePressed()) {
                GameStates.states = GameStates.MENU;
                playing.unpauseGame();
            }
        } else if (isIn(e, replyButton)) {
            if (replyButton.isMousePressed()) {
                playing.resetAll();
                playing.unpauseGame();
            }
        } else if (isIn(e, unpauseButton)) {
            if (unpauseButton.isMousePressed()) {
                playing.unpauseGame();
            }
        }

        musicButton.resetBooleans();
        sfxButton.resetBooleans();
        menuButton.resetBooleans();
        replyButton.resetBooleans();
        unpauseButton.resetBooleans();
        volumeButton.resetBooleans();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuButton.setMouseOver(false);
        replyButton.setMouseOver(false);
        unpauseButton.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if (isIn(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        } else if (isIn(e, menuButton)) {
            menuButton.setMouseOver(true);
        } else if (isIn(e, replyButton)) {
            replyButton.setMouseOver(true);
        } else if (isIn(e, unpauseButton)) {
            unpauseButton.setMouseOver(true);
        } else if (isIn(e, volumeButton)) {
            volumeButton.setMouseOver(true);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (volumeButton.isMousePressed()) {
            volumeButton.changeX(e.getX());
        }
     }

    private boolean isIn(MouseEvent e, PauseButton buttons) {
        return buttons.getBounds().contains(e.getX(), e.getY());
    }


}
