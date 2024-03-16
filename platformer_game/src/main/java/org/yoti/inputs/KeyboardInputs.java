package org.yoti.inputs;

import org.yoti.gamestates.GameStates;
import org.yoti.main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static org.yoti.utils.Constants.Directions.*;

public class KeyboardInputs implements KeyListener {
    private final GamePanel gamePanel;
    public KeyboardInputs(GamePanel gamePanel1) {

        this.gamePanel = gamePanel1;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameStates.states) {
            case MENU:
                gamePanel.getGame().getGameMenu().keyPressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameStates.states) {
            case MENU:
                gamePanel.getGame().getGameMenu().keyReleased(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;
            default:
                break;
        }
    }
}
