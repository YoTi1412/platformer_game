package org.yoti;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
    public GameWindow() {
        JFrame jframe = new JFrame();

        jframe.setSize(400, 400);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);

    }
}
