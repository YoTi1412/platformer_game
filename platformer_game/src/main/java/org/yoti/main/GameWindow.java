package org.yoti.main;

import org.yoti.main.GamePanel;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
    public GameWindow(GamePanel gamePanel) {
        JFrame jframe = new JFrame();

        jframe.setSize(400, 400);
        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);

        jframe.setVisible(true);
    }
}
