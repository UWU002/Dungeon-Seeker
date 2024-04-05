package Characters;

import Main.GamePanel;
import Main.PlayerInputs;
import Menus.GameHud;
import Tiles.map;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Skeleton extends Entity {
    private JLabel skeletonLabel, skeletonHealth;
    private Timer movementTimer;
    private Random r = new Random();
    private final int detectionRange = 7;
    private int check=0;
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
    }

    public void setDefaultValues() {
        x = 500;
        y = 50;
        health = 100;
        speed = 1;
        updateLabel();
    }


    private void movementTimerInitialize() {
        movementTimer = new Timer(18, e -> isPlayerInRange());
        movementTimer.start();
    }

    private void isPlayerInRange() {
        if (!death) {
            if (playerVisible()) {
                moveToPlayer();
            } else {
                if (check >= 50){
                    check=0;
                    randomMove();
                }
                check++;
            }
        }

    }

    private boolean playerVisible() {
        Entity player = gp.player;
        int distanceX = Math.abs(player.x - this.x);
        int distanceY = Math.abs(player.y - this.y);


        if (distanceX <= detectionRange * gp.originalTileSize && distanceY <= detectionRange * gp.originalTileSize) {
            return !isWallBetween(player.x, player.y);
        }
        return false;
    }

    private boolean isWallBetween(int x, int y) {

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


        if (playerY > this.y) {
            newY += speed;
            direction = "down";
        } else if (playerY < this.y) {
            newY -= speed;
            direction = "up";
        }

            if (playerX > this.x) {
                newX += speed;
                direction = "right";
            } else if (playerX < this.x) {
                newX -= speed;
                direction = "left";
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
        Rectangle predictedHitbox = new Rectangle(newX + 10, newY + 10, hitbox.width, hitbox.height);

        for (Rectangle wall : gameMap.getWallHitboxes()) {
            if (predictedHitbox.intersects(wall)) {
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
        if (this.hitbox.intersects(player.getHitbox())) {
            if (!hasReacted) {
                gh.setHp(player.getHealth() - 10);
                player.setHealth(player.getHealth() - 10);
                hasReacted = true;
            }
        } else {
            hasReacted = false;
        }
    }


    public JLabel getSkeletonLabel() {
        return skeletonLabel;
    }

    public JLabel getSkeletonHealth() {
        return skeletonHealth;
    }
}
