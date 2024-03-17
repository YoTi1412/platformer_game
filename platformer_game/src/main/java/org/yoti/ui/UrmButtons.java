package org.yoti.ui;

import org.yoti.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.yoti.utils.Constants.UI.UrmButtons.*;

public class UrmButtons extends PauseButton {
    private BufferedImage[] images;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;
    public UrmButtons(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImages();
    }

    private void loadImages() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTONS);
        images = new BufferedImage[3];
        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(i * URM_DEFAULTS_SIZE, rowIndex * URM_DEFAULTS_SIZE, URM_DEFAULTS_SIZE, URM_DEFAULTS_SIZE);

        }
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
    public void draw(Graphics g) {
        g.drawImage(images[index], x, y, URM_SIZE, URM_SIZE, null);
    }

    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
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
}
