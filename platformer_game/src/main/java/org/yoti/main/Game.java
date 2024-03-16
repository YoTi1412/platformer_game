package org.yoti.main;

import org.yoti.gamestates.GameMenu;
import org.yoti.gamestates.GameStates;
import org.yoti.gamestates.Playing;

import java.awt.*;

public class Game implements Runnable {
    private final GamePanel gamePanel;
    private Playing playing;
    private GameMenu gameMenu;
    public static final int TILES_DEFAULT_SIZE = 32;
    public static final float SCALE = 1.5f;
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;


    public Game() {
        initClasses();

        gamePanel = new GamePanel(this);
        GameWindow gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        startGameLoop();
    }

    private void initClasses() {
        gameMenu = new GameMenu(this);
        playing = new Playing(this);
    }

    public void startGameLoop() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        switch (GameStates.states) {
            case MENU:
                gameMenu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
            case QUIT:
            default:
                System.exit(0);
                break;
        }
    }

    public void render(Graphics g) {
        switch (GameStates.states) {
            case MENU:
                gameMenu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        int FPS_SET = 120;
        double timePerFrame = 1_000_000_000.0 / FPS_SET;
        int UPS_SET = 200;
        double timePerUpdate = 1_000_000_000.0 / UPS_SET;
        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS = " + frames + " | UPS = " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }


    public void windowFocusLost() {
        if (GameStates.states == GameStates.PLAYING) {
            playing.getPlayer().resetDirectionBoolean();
        }
    }

    public Playing getPlaying() {
        return playing;
    }

    public GameMenu getGameMenu() {
        return gameMenu;
    }
}
