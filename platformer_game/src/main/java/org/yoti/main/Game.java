package org.yoti.main;

public class Game implements Runnable {
    private final GamePanel gamePanel;

    // Constructor initializes the game panel, creates a game window, sets focus to the game panel, and starts the game loop.
    public Game() {
        this.gamePanel = new GamePanel();
        GameWindow gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    // Method to start the game loop in a new thread.
    public void startGameLoop() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    // Method to update the game state.
    public void update() {
        gamePanel.updateGame();
    }

    // Runnable interface implementation method containing the main game loop.
    @Override
    public void run() {
        // Frames per second and updates per second configurations.
        int FPS_SET = 120;
        double timePerFrame = 1_000_000_000.0 / FPS_SET;
        int UPS_SET = 200;
        double timePerUpdate = 1_000_000_000.0 / UPS_SET;
        long previousTime = System.nanoTime();

        // Variables to keep track of frames, updates, and timing.
        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
        double deltaU = 0;
        double deltaF = 0;

        // Main game loop
        while (true) {
            long currentTime = System.nanoTime();

            // Calculate time elapsed since the last update and frame.
            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            // Update the game state if the time for an update has passed.
            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            // Render the game if the time for a frame has passed.
            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            // Check and display frames and updates per second every second.
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS = " + frames + " | UPS = " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
}
