package Characters;

import Main.GamePanel;
import Main.PlayerInputs;
import Tiles.map;

import javax.swing.*;
import java.awt.*;

public class Skeleton extends Entity {
    private JLabel skeletonLabel;


    public Skeleton(GamePanel gp, PlayerInputs pI, map gameMap) {
        super(gp, pI, gameMap);
        skeletonLabel = new JLabel();
        loadImages();
        setDefaultValues();
        gp.add(skeletonLabel);
        skeletonLabel.setSize(gp.originalTileSize * 2, gp.originalTileSize * 2);

        hitbox = new Rectangle(x + 5, y + 7, 20, 25);
    }


    public void setDefaultValues() {
        x = 100;
        y = 600;
        health = 100;
        speed = 2;
        updateLabel();
    }

    public void update() {
        movePlayer();
        updateLabel();
    }


    private void movePlayer() {
        int newX = x, newY = y;

        if (pI.upPressed && !pI.rightPressed && !pI.downPressed && !pI.leftPressed) {
            direction = "up";
            newY -= speed;
        } else if (pI.downPressed && !pI.rightPressed && !pI.leftPressed && !pI.upPressed) {
            direction = "down";
            newY += speed;
        } else if (pI.leftPressed && !pI.rightPressed && !pI.downPressed && !pI.upPressed) {
            direction = "left";
            newX -= speed;
        } else if (pI.rightPressed && !pI.upPressed && !pI.leftPressed && !pI.downPressed) {
            direction = "right";
            newX += speed;
        } else if (pI.upPressed && pI.rightPressed) {
            direction = "up";
            newX += speed;
            newY -= speed;
        } else if (pI.upPressed && pI.leftPressed) {
            direction = "up";
            newX -= speed;
            newY -= speed;
        } else if (pI.downPressed && pI.rightPressed) {
            direction = "down";
            newX += speed;
            newY += speed;
        } else if (pI.downPressed && pI.leftPressed) {
            direction = "down";
            newX -= speed;
            newY += speed;
        } else if (!pI.rightPressed && !pI.downPressed) {
            direction = "idle";
        }
//            if ("idle".equals(direction)) {
//                hitbox.setLocation(x+5, y+7);
//            }else {
//                hitbox.setLocation(x+5,y+7);
//            }
        hitbox.setLocation(x + 5, y + 7);

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

    }


    private void updateLabel() {
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
    }


    public JLabel getSkeletonLabel() {
        return skeletonLabel;
    }

}
