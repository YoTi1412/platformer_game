package org.yoti.gamestates;

import org.yoti.audio.AudioPlayer;
import org.yoti.main.Game;
import org.yoti.ui.MenuButton;

import java.awt.event.MouseEvent;

import static org.yoti.gamestates.GameStates.*;

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

    public void setGameStates(GameStates states) {
        switch (states) {
            case MENU -> game.getAudioPlayer().playSong(AudioPlayer.MENU_1);
            case PLAYING -> game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
        }

        GameStates.states = states;
    }
}
