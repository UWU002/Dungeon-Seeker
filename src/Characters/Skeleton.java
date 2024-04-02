package Characters;

import Main.GamePanel;
import Main.PlayerInputs;
import Menus.GameHud;
import Tiles.map;

import javax.swing.*;
import java.awt.*;

public class Skeleton extends Entity {
    private JLabel skeletonLabel, skeletonHealth;
    boolean death = false;


    public Skeleton(GamePanel gp, PlayerInputs pI, map gameMap,GameHud gh , int x, int y) {
        super(gp, pI,gameMap, gh, x ,y);
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
    }


    public void setDefaultValues() {
        x = 100;
        y = 50;
        health = 100;
        speed = 2;
        updateLabel();
    }


    @Override
    public void update() {
        if (!death) {
            movePlayer();
            updateLabel();
        }
    }

    private void deathAnimation() {
        skeletonLabel.setIcon(die);
        skeletonHealth.setText("");
        new Timer(700, e -> {
            skeletonHealth.setLocation(-1000, -1000);
            skeletonLabel.setLocation(-1000, -1000);
            hitbox.setLocation(-1000,-1000);
        }).start();
    }

    private void movePlayer() {
        int newX = x, newY = y;
        //Movement System

        hitbox.setLocation(x + 5, y + 7);
        skeletonHealth.setLocation(x + 4, y - 50);

        if (canMove(newX, newY)) {
            x = newX;
            y = newY;
        } else {
            direction = "idle";
        }

    }

    private boolean canMove(int newX, int newY) {
        Rectangle predictedHitbox = new Rectangle(newX + 10, newY + 10, hitbox.width, hitbox.height);

        for (Rectangle wall : gameMap.getHitboxes()) {
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
