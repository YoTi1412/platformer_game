package org.yoti.main;

import org.yoti.inputs.KeyboardInputs;
import org.yoti.inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private int xDelta = 100, yDelta = 100;

    public GamePanel() {
        MouseInputs mouseInputs = new MouseInputs(this);
        KeyboardInputs keyboardInputs = new KeyboardInputs(this);

        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect(xDelta, yDelta, 200, 50);
    }

    public void changeXDelta(int value) {
        this.xDelta += value;
        repaint();
    }

    public void changeYDelta(int value) {
        this.yDelta += value;
        repaint();
    }

    public void setRectPos(int x, int y){
        this.xDelta = x;
        this.yDelta = y;
        repaint();
    }
}
