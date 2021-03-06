package logic;

import model.*;
import util.Constants;

import static logic.Behavior.ATTACK;

public class AIManager {
    final CollisionHandler collisionHandler;
    private int patrolCount;

    public AIManager(CollisionHandler collisionHandler) {
        this.collisionHandler = collisionHandler;
        patrolCount = 0;
    }

    public void handleAI(Level level, Player player) {
        for (Enemy enemy : level.getEnemies()) {
            if (enemy.isDead()) {
                level.getEnemies().remove(enemy);
                continue;
            }

            switch (enemy.getBehavior()) {
                case GUARD:
                    switch (enemy.getViewingDirection()) {
                        case LEFT:
                            if (player.getX() < enemy.getX()) {
                                if (distance(player, enemy) < enemy.getViewingRange()) {
                                    if (Math.abs(player.getY() - enemy.getY()) < (enemy.getHitbox().getHeight() / 2) + (player.getHitbox().getHeight() / 2)) {
                                        if (nearestViewblocker(enemy, level).getX() < player.getX()) {
                                            enemy.setBehavior(ATTACK);
                                        }
                                    }
                                }
                            }
                            break;
                        case RIGHT:
                            if (player.getX() > enemy.getX()) {
                                if (distance(player, enemy) < enemy.getViewingRange()) {
                                    if (Math.abs(player.getY() - enemy.getY()) < (enemy.getHitbox().getHeight() / 2) + (player.getHitbox().getHeight() / 2)) {
                                        if (nearestViewblocker(enemy, level).getX() > player.getX()) {
                                            enemy.setBehavior(ATTACK);
                                        }
                                    }
                                }
                            }
                            break;
                    }
                    if (touch(enemy, player))
                        enemy.setBehavior(ATTACK);
                    break;
                case ATTACK:
                    //System.out.println("Ich bin AGRESSIV");
                    if (distance(player, enemy) < enemy.getAttackRange()) {
                        /*if (player.getX() < enemy.getX()) {
                            enemy.setViewingDirection(Direction.LEFT);
                        } else {
                            enemy.setViewingDirection(Direction.RIGHT);
                        }*/
                        attack(enemy, player);
                    }
                    if (Math.abs(player.getX() - enemy.getX()) < 6) {
                        enemy.setVelocityX(0);
                    } else if (player.getX() < enemy.getX()) {
                        enemy.setViewingDirection(Direction.LEFT);
                        moveLeft(enemy);
                    } else if (player.getX() > enemy.getX()) {
                        enemy.setViewingDirection(Direction.RIGHT);
                        moveRight(enemy);
                    }

                    break;
                case IDLE:
                    //System.out.println("Ich warte");
                    if (distance(player, enemy) < enemy.getAttackRange()) {
                        if (player.getX() < enemy.getX()) {
                            enemy.setViewingDirection(Direction.LEFT);
                        } else {
                            enemy.setViewingDirection(Direction.RIGHT);
                        }
                        attack(enemy, player);
                    }
                    break;
                case PATROL:
                    if (patrolCount > 200) {
                        if (enemy.getViewingDirection().equals(Direction.LEFT))
                            enemy.setViewingDirection(Direction.RIGHT);
                        else
                            enemy.setViewingDirection(Direction.LEFT);
                        patrolCount = 0;
                    }
                    if (distance(player, enemy) < enemy.getAttackRange()) {
                        //System.out.println("Spieler in Reichweite");
                        if (player.getX() < enemy.getX()) {
                            enemy.setViewingDirection(Direction.LEFT);
                            moveLeft(enemy);
                        } else {
                            enemy.setViewingDirection(Direction.RIGHT);
                            moveRight(enemy);
                        }
                        patrolCount = 0;
                        attack(enemy, player);
                    }
                    switch (enemy.getViewingDirection()) {
                        case LEFT:
                            //System.out.println("Ich marschiere links");
                            moveLeft(enemy);
                            break;
                        case RIGHT:
                            //System.out.println("Ich marschiere rechts");
                            moveRight(enemy);
                            break;
                    }
                    patrolCount++;
                    collisionHandler.forEnemy(enemy);
                    enemy.move();
                    break;
                case ELOPE:
                    //System.out.println("Ich hab Angst");
                    if (player.getX() < enemy.getX()) {
                        enemy.setViewingDirection(Direction.RIGHT);
                        moveRight(enemy);
                    } else {
                        enemy.setViewingDirection(Direction.LEFT);
                        moveLeft(enemy);
                    }
                    break;
                case CROSS:
                    if (touch(enemy, player)) {
                        enemy.suffer(1);
                        player.addScore(enemy.getWorthiness());
                    }
                    break;
                case ADMIN:
                    int count = 0;
                    for (Enemy e : level.getEnemies()) {
                        count++;
                    }
                    if (count == 1) {
                        for (int i = 0; i < /*(int) (Math.random() * 3 + 3)*/5; i++) {
                            switch ((int) (Math.random() * 2)) {
                                case 0:
                                    switch ((int) (Math.random() * 2)) {
                                        case 0:
                                            level.getEnemies().add(new Knight(player.getX() - 100, 100 * i, ATTACK, Direction.RIGHT));
                                            break;
                                        case 1:
                                            level.getEnemies().add(new Knight(player.getX() + 100, 100 * i, ATTACK, Direction.LEFT));
                                            break;
                                    }
                                    break;
                                case 1:
                                    switch ((int) (Math.random() * 2)) {
                                        case 0:
                                            level.getEnemies().add(new Skeleton(player.getX() - 100, 100 * i, ATTACK, Direction.RIGHT));
                                            break;
                                        case 1:
                                            level.getEnemies().add(new Skeleton(player.getX() + 100, 100 * i, ATTACK, Direction.LEFT));
                                            break;
                                    }
                                    break;
                            }
                            System.out.println("1");
                        }
                    }
                    break;
            }

            collisionHandler.forEnemy(enemy);
            if (System.nanoTime() - enemy.getLastAttackTime() > enemy.getMinTimeBetweenAttack() / 4) {
                enemy.setAttacking(false);
            }
            enemy.move();
        }
    }

    private double distance(Entity entity1, Entity entity2) {
        return Math.sqrt((entity1.getX() - entity2.getX()) * (entity1.getX() - entity2.getX()) + (entity1.getY() - entity2.getY()) * (entity1.getY() - entity2.getY()));
    }

    private boolean touch(Entity entity1, Entity entity2) {
        return Math.abs(entity1.getX() - entity2.getX()) <= entity1.getHitbox().getWidth() / 2 + entity2.getHitbox().getWidth() / 2 && Math.abs(entity1.getY() - entity2.getY()) <= entity1.getHitbox().getHeight() / 2 + entity2.getHitbox().getHeight() / 2;
    }

    private void moveLeft(Enemy enemy) {
        enemy.setVelocityX(-Constants.PLAYER_WALK_VELOCITY);
    }

    private void moveRight(Enemy enemy) {
        enemy.setVelocityX(Constants.PLAYER_WALK_VELOCITY);
    }

    private void attack(Enemy enemy, Player player) {
        if (System.nanoTime() - enemy.getLastAttackTime() > enemy.getMinTimeBetweenAttack()) {
            //System.out.println("Hey " + System.nanoTime() / 1000000000);
            player.suffer(enemy.getStrength());
            enemy.setAttacking(true);
            enemy.setLastAttackTime(System.nanoTime());
        }
    }

    private Obstacle nearestViewblocker(Enemy enemy, Level level) {
        Obstacle blocker = new Crate(-99999, -99999);
        switch (enemy.getViewingDirection()) {
            case LEFT:
                blocker = new Crate(0, -99999);
                break;
            case RIGHT:
                blocker = new Crate(level.getLength(), -99999);
                break;
        }
        for (Obstacle obstacle : level.getObstacles()) {
            if (Math.abs(obstacle.getY() - enemy.getY()) < (enemy.getHitbox().getHeight() / 2) + (obstacle.getHitbox().getHeight() / 2)) {
                switch (enemy.getViewingDirection()) {
                    case LEFT:
                        if (obstacle.getX() > blocker.getX()) {
                            if (obstacle.getX() < enemy.getX()) {
                                blocker = obstacle;
                            }
                        }
                        break;
                    case RIGHT:
                        if (obstacle.getX() < blocker.getX()) {
                            if (obstacle.getX() > enemy.getX()) {
                                blocker = obstacle;
                            }
                        }
                        break;
                }
            }
        }
        return blocker;
    }
}
