package org.yoti.main;

import org.yoti.inputs.KeyboardInputs;
import org.yoti.inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static org.yoti.main.Game.GAME_HEIGHT;
import static org.yoti.main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

    private Game game;

    public GamePanel(Game game) {
        MouseInputs mouseInputs = new MouseInputs(this);
        KeyboardInputs keyboardInputs = new KeyboardInputs(this);

        this.game = game;

        setPanelSize();

        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        System.out.println("width = " + GAME_WIDTH + "| height = " + GAME_HEIGHT);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
