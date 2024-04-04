package org.yoti.objects;

import org.yoti.main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.yoti.utils.Constants.ANIMATION_SPEED;
import static org.yoti.utils.Constants.ObjectConstants.*;

public class GameObjects {
    protected int x,y, objectType;
    protected Rectangle2D.Float hitbox;
    protected boolean doAnimation, active = true;
    protected int animationTick, animationIndex;
    protected int xDrawOffset, yDrawOffset;

    public GameObjects(int x, int y, int objectType) {
        this.x = x;
        this.y = y;
        this.objectType = objectType;
    }

    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int)(width * Game.SCALE), (int)(height * Game.SCALE));
    }

    public void drawHitbox(Graphics g, int xLevelOffset) {
        g.setColor(Color.red);
        g.drawRect((int)hitbox.x - xLevelOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
    }

    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= ANIMATION_SPEED) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(objectType)) {
                animationIndex = 0;
                if (objectType == BARREL || objectType == BOX) {
                    doAnimation = false;
                    active = false;
                }
            }
        }
    }


    public void reset() {
        animationTick = 0;
        animationIndex = 0;
        active = true;
        if (objectType == BARREL || objectType == BOX) {
            doAnimation = false;
        } else {
            doAnimation = true;
        }
    }

    public int getObjectType() {
        return objectType;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public boolean isActive() {
        return active;
    }

    public int getXDrawOffset() {
        return xDrawOffset;
    }

    public int getYDrawOffset() {
        return yDrawOffset;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setDoAnimation(boolean doAnimation) {
        this.doAnimation = doAnimation;
    }
}
