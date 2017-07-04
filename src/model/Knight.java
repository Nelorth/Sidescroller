package model;

import util.Constants;
import util.Point;

import java.awt.geom.Rectangle2D;

public class Knight extends Enemy {
    private final double KNIGHT_WIDTH = 95;
    private final double KNIGHT_HEIGHT = 169;
    private int walkCount;

    public Knight(Point position) {
        this.position = position;
        hitbox = new Rectangle2D.Double(position.getX() - KNIGHT_WIDTH / 2, position.getY() - KNIGHT_HEIGHT, KNIGHT_WIDTH, KNIGHT_HEIGHT);
    }

    @Override
    public void setWalking(boolean walking) {
        if (walking)
            walkCount++;
        super.setWalking(walking);
    }

    @Override
    public void setRunning(boolean running) {
        if (running)
            walkCount++;
        super.setRunning(running);
    }

    @Override
    public void setCrouching(boolean crouching) {
        double crouchingDelta = 53;
        if (crouching && !this.crouching)
            hitbox.setRect(hitbox.x, hitbox.y + crouchingDelta, hitbox.width, hitbox.height - crouchingDelta);
        else if (!crouching && this.crouching)
            hitbox.setRect(hitbox.x, hitbox.y - crouchingDelta, hitbox.width, hitbox.height + crouchingDelta);

        super.setCrouching(crouching);
    }

    public String getImagePath() {
        if (crouching) {
            if (walkCount < 25)
                return Constants.KNIGHT_CROUCH_1_IMAGE_PATH;
            else {
                if (walkCount >= 50)
                    walkCount = 0;
                return Constants.KNIGHT_CROUCH_2_IMAGE_PATH;
            }
        }
        if (jumping)
            return Constants.KNIGHT_JUMP_IMAGE_PATH;
        if (walking) {
            if (walkCount < 25)
                return Constants.KNIGHT_WALK_1_IMAGE_PATH;
            else {
                if (walkCount >= 50)
                    walkCount = 0;
                return Constants.KNIGHT_WALK_2_IMAGE_PATH;
            }
        }
        return Constants.KNIGHT_STAND_IMAGE_PATH;
    }
    @Override
    public String toString() {
        return "Kight at " + super.toString();
    }
}
