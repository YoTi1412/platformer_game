package org.yoti.inputs;

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

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setDirection(UP);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setDirection(LEFT);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setDirection(DOWN);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setDirection(RIGHT);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_A:
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setPlayerMoving(false);
                break;
        }
    }
}
