package org.yoti.ui;

import org.yoti.gamestates.GameStates;
import org.yoti.gamestates.Playing;
import org.yoti.main.Game;
import org.yoti.utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static org.yoti.utils.Constants.UI.UrmButtons.*;

public class LevelCompletedOverlay {
    private Playing playing;
    private UrmButtons menu, next;
    private BufferedImage image;
    private int backgroundX, backgroundY, backgroundWidth, backgroundHeight;
    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        initImage();
        initButtons();
    }

    private void initButtons() {
        int menuX = (int) (330 * Game.SCALE);
        int nextX = (int) (445 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);
        next = new UrmButtons(nextX, y, URM_SIZE, URM_SIZE,0);
        menu = new UrmButtons(menuX, y, URM_SIZE, URM_SIZE, 2);

    }

    private void initImage() {
        image = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_COMPLETED_IMAGE);
        backgroundWidth = (int)(image.getWidth() * Game.SCALE);
        backgroundHeight = (int)(image.getHeight() * Game.SCALE);
        backgroundX = Game.GAME_WIDTH / 2 - backgroundWidth / 2;
        backgroundY = (int)(75 * Game.SCALE);
    }

    public void update(){
        next.update();
        menu.update();
    }

    public void draw(Graphics g) {
        g.drawImage(image, backgroundX, backgroundY, backgroundWidth, backgroundHeight, null);
        next.draw(g);
        menu.draw(g);
    }

    private boolean isIn(UrmButtons b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e)) {
            menu.setMousePressed(true);
        } else if (isIn(next, e)){
            next.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                playing.resetAll();
                playing.setGameStates(GameStates.MENU);
            }
        } else if (isIn(next, e)){
            if (next.isMousePressed()) {
                playing.loadNextLevel();
                playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex());
            }
        }

        menu.resetBooleans();
        next.resetBooleans();
    }

    public void mouseMoved(MouseEvent e) {
        next.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e)) {
            menu.setMouseOver(true);
        } else if (isIn(next, e)){
            next.setMouseOver(true);
        }
    }
}
