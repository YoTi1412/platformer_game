package org.yoti.ui;

import org.yoti.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.yoti.utils.Constants.UI.VolumeButtons.*;

public class VolumeButton extends PauseButton {
    private BufferedImage[] images;
    private BufferedImage slider;
    private int index = 0;
    private boolean mouseOver, mousePressed;
    private int buttonX, minX, maxX;
    public VolumeButton(int x, int y, int width, int height) {
        super(x + (width / 2), y, VOLUME_WIDTH, height);
        bounds.x -= VOLUME_WIDTH / 2;
        buttonX = x + (width / 2);
        this.x = x;
        this.width = width;
        minX = x + VOLUME_WIDTH / 2;
        maxX = x + width - VOLUME_WIDTH / 2;
        loadImages();
    }

    private void loadImages() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTONS);
        images = new BufferedImage[3];

        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(i * VOLUME_DEFAULTS_WIDTH,0,VOLUME_DEFAULTS_WIDTH, VOLUME_DEFAULTS_HEIGHT);
        }

        slider = temp.getSubimage(3 * VOLUME_DEFAULTS_WIDTH, 0, SLIDER_DEFAULTS_WIDTH, VOLUME_DEFAULTS_HEIGHT);
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
        g.drawImage(slider, x, y, width, height, null);
        g.drawImage(images[index], buttonX - (VOLUME_WIDTH / 2), y, VOLUME_WIDTH, height, null);
    }
    
    public void changeX(int x) {
        if (x < minX) {
            buttonX = minX;
        } else if (x > maxX) {
            buttonX = maxX;
        } else {
            buttonX = x - VOLUME_WIDTH / 2;
        }

        bounds.x = buttonX;
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
