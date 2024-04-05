package org.yoti.gamestates;

import org.yoti.main.Game;
import org.yoti.ui.AudioOptions;
import org.yoti.ui.PauseButton;
import org.yoti.ui.UrmButtons;
import org.yoti.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static org.yoti.utils.Constants.UI.UrmButtons.*;

public class GameOptions extends States implements StateMethods {
    private AudioOptions audioOptions;
    private BufferedImage backgroundImage, optionsBackgroundImage;
    private int backgroundX, backgroundY, backgroundWidth, backgroundHeight;
    private UrmButtons menuButton;

    public GameOptions(Game game) {
        super(game);
        loadImages();
        loadButtons();
        audioOptions = game.getAudioOptions();
    }

    private void loadButtons() {
        int menuX = (int) (387 * Game.SCALE);
        int menuY = (int) (325 * Game.SCALE);

        menuButton = new UrmButtons(menuX, menuY, URM_SIZE, URM_SIZE, 2);
    }

    private void loadImages() {
        backgroundImage = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMAGE);
        optionsBackgroundImage = LoadSave.GetSpriteAtlas(LoadSave.OPTIONS_MENU);

        backgroundWidth = (int) (optionsBackgroundImage.getWidth() * Game.SCALE);
        backgroundHeight = (int) (optionsBackgroundImage.getHeight() * Game.SCALE);
        backgroundX = Game.GAME_WIDTH / 2 - backgroundWidth / 2;
        backgroundY = (int) (33 * Game.SCALE);
    }

    @Override
    public void update() {
        menuButton.update();
        audioOptions.update();

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(optionsBackgroundImage, backgroundX, backgroundY, backgroundWidth, backgroundHeight, null);

        menuButton.draw(g);
        audioOptions.draw(g);

    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuButton)) {
            menuButton.setMousePressed(true);
        } else
            audioOptions.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuButton)) {
            if (menuButton.isMousePressed())
                GameStates.states = GameStates.MENU;
        } else
            audioOptions.mouseReleased(e);

        menuButton.resetBooleans();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuButton.setMouseOver(false);

        if (isIn(e, menuButton))
            menuButton.setMouseOver(true);
        else
            audioOptions.mouseMoved(e);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            GameStates.states = GameStates.MENU;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
