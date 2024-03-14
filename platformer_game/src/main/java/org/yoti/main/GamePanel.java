package org.yoti.main;

import org.yoti.inputs.KeyboardInputs;
import org.yoti.inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the game panel where the game graphics are displayed and interacted with.
 */
public class GamePanel extends JPanel {

    private Game game;

    /**
     * Constructs a new GamePanel object.
     */
    public GamePanel(Game game) {
        MouseInputs mouseInputs = new MouseInputs(this);
        KeyboardInputs keyboardInputs = new KeyboardInputs(this);

        this.game = game;

        setPanelSize();

        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }


    /**
     * Sets the size of the game panel.
     */
    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    /**
     * Paints the game components onto the panel.
     * @param g The Graphics object used for painting.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }


    /**
     * Updates the game state.
     */
    public void updateGame() {

    }

    public Game getGame() {
        return game;
    }
}
