package model;

import logic.Behavior;

import java.awt.geom.Rectangle2D;

public class Knight extends Enemy {
    private final double SKELETON_WIDTH = 95;
    private final double SKELETON_HEIGHT = 230;

    public Knight(double x, double y, Behavior behavior, Direction viewingDirection) {
        super(x, y, behavior, viewingDirection);
        hitbox = new Rectangle2D.Double(x - SKELETON_WIDTH / 2, y - SKELETON_HEIGHT, SKELETON_WIDTH, SKELETON_HEIGHT);
        viewingRange = 1000;
        attackRange = 200;
        health = 500;
        strength = 2;
        worthiness = 10;
    }

    @Override
    public int getMaxHealth() {
        return 500;
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
        return "images/char/char_stand.png";
    }

    @Override
    public String toString() {
        return "Kight at " + super.toString();
    }
}
