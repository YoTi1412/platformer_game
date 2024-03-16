package org.yoti.gamestates;

import org.yoti.main.Game;
import org.yoti.ui.MenuButton;

import java.awt.event.MouseEvent;

public class States {
    protected Game game;
    public States(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public boolean isIn(MouseEvent e, MenuButton menuButton) {
        return menuButton.getBounds().contains(e.getX(), e.getY());
    }
}
