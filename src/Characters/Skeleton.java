package Characters;

import Main.GamePanel;
import Main.PlayerInputs;
import Menus.GameHud;
import Tiles.map;

import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Skeleton extends Entity {
    private JLabel skeletonLabel, skeletonHealth;
    private Timer movementTimer, refreshAtack;
    private Random r = new Random();
    private final int detectionRange = 7;
    private int check = 0, randomNum = 50;
    boolean death = false;


    public Skeleton(GamePanel gp, PlayerInputs pI, map gameMap, GameHud gh, int x, int y) {
        super(gp, pI, gameMap, gh, x, y);
        skeletonLabel = new JLabel();
        gp.add(skeletonLabel);
        skeletonLabel.setSize(gp.originalTileSize * 2, gp.originalTileSize * 2);
        hitbox = new Rectangle(x + 5, y + 7, 20, 25);
        skeletonHealth = new JLabel();
        skeletonHealth.setLocation(905, 550);
        skeletonHealth.setSize(100, 100);
        skeletonHealth.setText(health + "");
        skeletonHealth.setForeground(Color.red);
        gp.add(skeletonHealth);
        loadImages();
        setDefaultValues();
        movementTimerInitialize();
        hasReactedRefresh();
    }


    public void setDefaultValues() {
        damage = 20;
        health = 100;
        speed = 1;
        updateLabel();
    }


    private void hasReactedRefresh() {
        refreshAtack = new Timer(3000, e -> {
            hasReacted = false;
            refreshAtack.stop();
        });
    }

    private void movementTimerInitialize() {
        movementTimer = new Timer(18, e -> isPlayerInRange());
        movementTimer.start();
    }

    private void isPlayerInRange() {
        if (!death) {
            if (playerVisible() && !gp.player.getDead()) {
                moveToPlayer();
            } else {
                if (check >= randomNum) {
                    check = 0;
                    randomMove();
                    randomNum = r.nextInt(30, 200);
                }
                check++;
            }
        }

    }

    private boolean playerVisible() {
        Entity player = gp.player;
        int distanceX = Math.abs(player.getX() - this.x);
        int distanceY = Math.abs(player.getY() - this.y);
        if (distanceX <= detectionRange * gp.originalTileSize && distanceY <= detectionRange * gp.originalTileSize) {
            return !isWallBetween(this.x, this.y, player.getX(), player.getY());
        }
        return false;
    }

    private boolean isWallBetween(int startX, int startY, int endX, int endY) {
        List<Point> linePoints = lineToPlayer(startX / gp.originalTileSize, startY / gp.originalTileSize, endX / gp.originalTileSize, endY / gp.originalTileSize);
        for (Point point : linePoints) {
            int checkX = point.x * gp.originalTileSize;
            int checkY = point.y * gp.originalTileSize;
            Rectangle checkRect = new Rectangle(checkX, checkY, gp.originalTileSize, gp.originalTileSize);
            for (Rectangle wall : gameMap.getWallHitboxes()) {
                if (checkRect.intersects(wall)) {
                    return true;
                }
            }
            for (Rectangle MC : gameMap.getMonsterContainers()) {
                if (checkRect.intersects(MC)) {
                    return true;
                }
            }
        }
        return false;
    }


    private void randomMove() {
        int dir = r.nextInt(4); // 0: up, 1: down, 2: left, 3: right
        switch (dir) {
            case 0:
                direction = "up";
                break;
            case 1:
                direction = "down";
                break;
            case 2:
                direction = "left";
                break;
            case 3:
                direction = "right";
                break;
        }

        move();
    }

    private void moveToPlayer() {
        Entity player = gp.player;
        direction = "idle";
        int playerX = player.x;
        int playerY = player.y;

        int newX = this.x, newY = this.y;
        int margin = 10;
        boolean verticalMovement = false;

        if (Math.abs(playerY - this.y) > margin) {
            if (playerY > this.y) {
                newY += speed;
                direction = "down";
                verticalMovement = true;
            } else if (playerY < this.y) {
                newY -= speed;
                direction = "up";
                verticalMovement = true;
            }
        }

        if (!verticalMovement) {
            if (playerX > this.x) {
                newX += speed;
                direction = "right";
            } else if (playerX < this.x) {
                newX -= speed;
                direction = "left";
            }
        }


        if (canMove(newX, newY)) {
            x = newX;
            y = newY;
            updateLabel();
        }

        updateLabel();
    }


    @Override
    public void update() {
        if (!death) {
            move();
            updateLabel();
        }
    }

    private void deathAnimation() {
        skeletonLabel.setIcon(die);
        skeletonHealth.setText("");
        new Timer(700, e -> {
            skeletonHealth.setLocation(-1000, -1000);
            skeletonLabel.setLocation(-1000, -1000);
            hitbox.setLocation(-1000, -1000);
            gp.remove(skeletonHealth);
            gp.remove(skeletonLabel);
            gp.getSkeletons().remove(this);
        }).start();
    }

    private void move() {
        int newX = x, newY = y;
        switch (direction) {
            case "up":
                newY -= speed;
                break;
            case "down":
                newY += speed;
                break;
            case "left":
                newX -= speed;
                break;
            case "right":
                newX += speed;
                break;
        }


        hitbox.setLocation(x + 5, y + 7);
        skeletonHealth.setLocation(x + 4, y - 50);

        if (canMove(newX, newY)) {
            x = newX;
            y = newY;
        } else {
            randomMove();
        }
        updateLabel();
    }

    private boolean canMove(int newX, int newY) {
        Rectangle predictedHitbox = new Rectangle();
        if ("up".equalsIgnoreCase(direction) || "left".equalsIgnoreCase(direction)) {
            predictedHitbox = new Rectangle(newX, newY, hitbox.width, hitbox.height);
        } else if ("right".equalsIgnoreCase(direction)) {
            predictedHitbox = new Rectangle(newX + 10, newY, hitbox.width, hitbox.height);
        } else if ("down".equalsIgnoreCase(direction)) {
            predictedHitbox = new Rectangle(newX, newY + 10, hitbox.width, hitbox.height);
        }

        for (Rectangle wall : gameMap.getWallHitboxes()) {
            if (predictedHitbox.intersects(wall)) {
                return false;
            }
        }
        for (Rectangle MC : gameMap.getMonsterContainers()) {
            if (predictedHitbox.intersects(MC)) {
                return false;
            }
        }
        return true;
    }

    private void loadImages() {
        idle = new ImageIcon("src/images/skeleton/idle_down.gif");
        down = new ImageIcon("src/images/skeleton/skeleton_down.gif");
        left = new ImageIcon("src/images/skeleton/skeleton_left.gif");
        right = new ImageIcon("src/images/skeleton/skeleton_right.gif");
        up = new ImageIcon("src/images/skeleton/skeleton_up.gif");
        die = new ImageIcon("src/images/skeleton/deathExplosion.gif");
    }


    private void updateLabel() {
        skeletonHealth.setText(health + "");
        switch (direction) {
            case "up":
                skeletonLabel.setIcon(up);
                break;
            case "down":
                skeletonLabel.setIcon(down);
                break;
            case "right":
                skeletonLabel.setIcon(right);
                break;
            case "left":
                skeletonLabel.setIcon(left);
                break;
            default:
                skeletonLabel.setIcon(idle);
        }
        skeletonLabel.setLocation(x, y);
        if (health <= 0) {
            death = true;
            movementTimer.stop();
            deathAnimation();
        }
    }

    boolean hasReacted = false;

    @Override
    public void attacks(Entity player) {
        int dmgInstance = damage;
        if (!death) {
            if (this.hitbox.intersects(player.getHitbox())) {
                if (!hasReacted) {
                    if (player instanceof Warrior) {
                        dmgInstance = damage / 2;
                    }
                    gh.setHp(player.getHealth() - dmgInstance);
                    player.setHealth(player.getHealth() - dmgInstance);
                    hasReacted = true;
                    refreshAtack.start();
                }
            } else {
                hasReacted = false;
            }
        }
    }


    // Bresenhams Line algorithm
    private List<Point> lineToPlayer(int x0, int y0, int x1, int y1) {
        List<Point> line = new ArrayList<>();

        int dx = Math.abs(x1 - x0);
        int dy = -Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx + dy, e2; /* error value e_xy */

        while (true) {
            line.add(new Point(x0, y0));
            if (x0 == x1 && y0 == y1) break;
            e2 = 2 * err;
            if (e2 >= dy) { /* e_xy+e_x > 0 */
                err += dy;
                x0 += sx;
            }
            if (e2 <= dx) { /* e_xy+e_y < 0 */
                err += dx;
                y0 += sy;
            }
        }

        return line;
    }


    public boolean getDead() {
        return death;
    }

    public JLabel getSkeletonLabel() {
        return skeletonLabel;
    }

    public JLabel getSkeletonHealth() {
        return skeletonHealth;
    }

    public void removeFromGame() {
        death = true;
        gp.remove(skeletonLabel);
        gp.remove(skeletonHealth);
        hitbox.setLocation(10000, 9);
    }

}
