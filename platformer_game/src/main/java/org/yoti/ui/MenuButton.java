package org.yoti.ui;

import org.yoti.gamestates.GameStates;
import org.yoti.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.yoti.utils.Constants.UI.Buttons.B_WIDTH;
import static org.yoti.utils.Constants.UI.Buttons.*;

public class MenuButton {
    private int xPos, yPos, rowIndex, index;
    private final int xOffsetCenter = B_WIDTH / 2;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;
    private GameStates states;
    private BufferedImage[] images;
    public MenuButton(int xPos, int yPos, int rowIndex, GameStates states) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.states = states;
        loadImages();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    private void loadImages() {
        images = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(images[index], (xPos - xOffsetCenter), yPos, B_WIDTH, B_HEIGHT, null);
    }

    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
        }
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void applyGameState() {
        GameStates.states = states;
    }

    public void resetBooleans(){
        mouseOver = false;
        mousePressed = false;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
