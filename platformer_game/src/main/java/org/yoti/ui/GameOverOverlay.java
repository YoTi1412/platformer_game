package org.yoti.ui;

import org.yoti.gamestates.GameStates;
import org.yoti.gamestates.Playing;
import org.yoti.main.Game;
import org.yoti.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static org.yoti.utils.Constants.UI.UrmButtons.*;

public class GameOverOverlay {
    private Playing playing;
    private BufferedImage image;
    private int imageX, imageY, imageWidth, imageHeight;
    private UrmButtons menu, play;

    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        createImage();
        createButtons();
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0,0,0,200));
        g.fillRect(0,0, Game.GAME_WIDTH,Game.GAME_HEIGHT);
        g.setColor(Color.white);
        g.drawImage(image, imageX, imageY, imageWidth, imageHeight, null);
        menu.draw(g);
        play.draw(g);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            GameStates.states = GameStates.MENU;
        }
    }

    public void update() {
        menu.update();
        play.update();
    }

    private void createImage() {
        image = LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN);
        imageWidth = (int) (image.getWidth() * Game.SCALE);
        imageHeight = (int) (image.getHeight() * Game.SCALE);
        imageX = Game.GAME_WIDTH / 2 - imageWidth / 2;
        imageY = (int) (100 * Game.SCALE);

    }

    private void createButtons() {
        int menuX = (int) (335 * Game.SCALE);
        int playX = (int) (440 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);
        play = new UrmButtons(playX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButtons(menuX, y, URM_SIZE, URM_SIZE, 2);

    }

    private boolean isIn(UrmButtons b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        play.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e))
            menu.setMouseOver(true);
        else if (isIn(play, e))
            play.setMouseOver(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                playing.resetAll();
                GameStates.states = GameStates.MENU;
            }
        } else if (isIn(play, e))
            if (play.isMousePressed())
                playing.resetAll();

        menu.resetBooleans();
        play.resetBooleans();
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e))
            menu.setMousePressed(true);
        else if (isIn(play, e))
            play.setMousePressed(true);
    }
}
