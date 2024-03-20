package org.yoti.gamestates;

import org.yoti.entities.Player;
import org.yoti.levels.LevelManager;
import org.yoti.main.Game;
import org.yoti.ui.PauseOverlay;
import org.yoti.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.yoti.main.Game.SCALE;

public class Playing extends States implements StatesMethods {
    private Player player;
    private LevelManager levelManager;
    private boolean paused = false;
    private PauseOverlay pauseOverlay;
    private int xLevelOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int levelTilessWide = LoadSave.GetLevelData()[0].length;
    private int maxTilessWide = levelTilessWide - Game.TILES_IN_WIDTH;
    private int maxLevelOffsetX = maxTilessWide * Game.TILES_SIZE;

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        player = new Player(200, 200, (int) (64 * SCALE), (int) (40 * SCALE));
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
    }

    public Player getPlayer() {
        return player;
    }

    public void windowFocusLost() {
        player.resetDirectionBoolean();
    }

    @Override
    public void update() {
        if (!paused) {
            levelManager.update();
            player.update();
            checkIfCloseToBorder();
        } else {
            pauseOverlay.update();
        }
    }

    private void checkIfCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int different = playerX - xLevelOffset;

        if (different > rightBorder) {
            xLevelOffset += different - rightBorder;
        } else if (different < leftBorder) {
            xLevelOffset += different - leftBorder;
        }

        if (xLevelOffset > maxLevelOffsetX) {
            xLevelOffset = maxLevelOffsetX;
        } else if (xLevelOffset < 0) {
            xLevelOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g, xLevelOffset);
        player.render(g, xLevelOffset);

        if (paused) {
            g.setColor(new Color(0,0,0,150));
            g.fillRect(0,0,Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.setPlayerAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused) {
            pauseOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseMoved(e);
        }
    }


    public void mouseDragged(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseDragged(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_W:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_W:
                player.setJump(false);
                break;
        }
    }

    public void unpauseGame() {
        paused = false;
    }
}
