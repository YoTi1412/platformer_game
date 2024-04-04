package org.yoti.objects;

import org.yoti.main.Game;

import java.awt.geom.Rectangle2D;

import static org.yoti.utils.Constants.Projectiles.*;

public class Projectile {
    private Rectangle2D.Float hitbox;
    private int direction;
    private boolean active = true;

    public Projectile(int x, int y, int direction) {
        int xOffset = (int) (-3 * Game.SCALE);
        int yOffset = (int) (5 * Game.SCALE);

        if (direction == 1)
            xOffset = (int) (29 * Game.SCALE);

        hitbox = new Rectangle2D.Float(x + xOffset, y + yOffset, CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT);
        this.direction = direction;
    }

    public void updatePos() {
        hitbox.x += direction * SPEED;
    }

    public void setPos(int x, int y) {
        hitbox.x = x;
        hitbox.y = y;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
