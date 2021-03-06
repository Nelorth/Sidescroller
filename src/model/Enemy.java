package model;

import logic.Behavior;

import java.awt.geom.Rectangle2D;

public class Enemy extends Entity implements Cloneable {
    protected Behavior behavior;

    protected double viewingRange;
    protected double attackRange;
    protected int worthiness;
    protected long lastAttackTime; //Zeit des letzten Angriffs
    protected long minTimeBetweenAttack = 1500000000; //Zeit in ns zwischen zwei Angriffen (1 s = 1.000.000.000 ns)

    protected boolean paintWeapon = true; //Wird standartmäßig als wahr angenommen
    protected Rectangle2D.Double weapon;
    protected boolean attacking;

    public Enemy(double x, double y, Behavior behavior, Direction viewingDirection) {
        this.x = x;
        this.y = y;
        this.behavior = behavior;
        this.viewingDirection = viewingDirection;
        walkCount = 0;
    }

    /**
     * Copy-Konstruktor
     *
     * @param enemy Zu kopierender Gegner
     */
    public Enemy(Enemy enemy) {
        x = enemy.getX();
        y = enemy.getY();
        velocityX = enemy.getVelocityX();
        velocityY = enemy.getVelocityY();
        behavior = enemy.getBehavior();
        viewingDirection = enemy.getViewingDirection();
        attackRange = enemy.getAttackRange();
        health = enemy.getHealth();
        onGround = enemy.isOnGround();
        hitbox = new Rectangle2D.Double(enemy.getHitbox().getX(), enemy.getHitbox().getY(),
                enemy.getHitbox().getWidth(), enemy.getHitbox().getHeight());
        walking = enemy.isWalking();
        running = enemy.isRunning();
        jumping = enemy.isJumping();
        crouching = enemy.isCrouching();
        walkCount = 0;
    }

    public Behavior getBehavior() {
        return behavior;
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    public double getViewingRange() {
        return viewingRange;
    }

    public double getAttackRange() {
        return attackRange;
    }

    public int getWorthiness() {
        return worthiness;
    }

    @Override
    public String getImagePath() {
        return "";
    }

    public String getWeaponImagePath(boolean attacking) {
        return attacking ? "images/sword/sword_giant_strike.png" : "images/sword/sword_giant.png";
    }

    protected int walkCount;

    //Für die Regulierung der Angriffe
    public long getLastAttackTime() {
        return lastAttackTime;
    }

    public void setLastAttackTime(long lastAttackTime) {
        this.lastAttackTime = lastAttackTime;
    }

    public long getMinTimeBetweenAttack() {
        return minTimeBetweenAttack;
    }

    public boolean hasSword() {
        return paintWeapon;
    }

    public Rectangle2D.Double getWeapon() {
        return weapon;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }
}
