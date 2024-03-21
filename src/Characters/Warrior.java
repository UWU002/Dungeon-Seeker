package Characters;

import Main.GamePanel;
import Main.PlayerInputs;
import Tiles.map;

import javax.swing.*;
import java.awt.*;


public class Warrior extends Entity {
    private JLabel warriorLabel;


    public Warrior(GamePanel gp, PlayerInputs pI, map gameMap) {
        super(gp,pI,gameMap);
        warriorLabel = new JLabel();
        loadImages();
        setDefaultValues();
        gp.add(warriorLabel);
        warriorLabel.setSize(gp.originalTileSize * 2, gp.originalTileSize * 2);
        hitbox = new Rectangle(x + 10, y + 10, gp.originalTileSize, gp.originalTileSize);
    }


    public void setDefaultValues() {
        x = 50;
        y = 600;
        health=100;
        speed = 2;
        direction = "idle";
        updateLabel();
    }

    public void update() {
        movePlayer();
        updateLabel();
    }

    private void movePlayer() {
        int newX=x, newY=y;

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
        if ("idle".equals(direction)) {
            hitbox.setLocation(x+3, y+7);
        }else {
            hitbox.setLocation(x + 10, y + 10);
        }

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
        idle = new ImageIcon("src/images/warrior/warriorIdle.png");
        down = new ImageIcon("src/images/warrior/warrior_down.gif");
        left = new ImageIcon("src/images/warrior/warrior_left.gif");
        right = new ImageIcon("src/images/warrior/warrior_right.gif");
        up = new ImageIcon("src/images/warrior/warrior_up.gif");

    }


    private void updateLabel() {
        switch (direction) {
            case "up":
                warriorLabel.setIcon(up);
                break;
            case "down":
                warriorLabel.setIcon(down);
                break;
            case "right":
                warriorLabel.setIcon(right);
                break;
            case "left":
                warriorLabel.setIcon(left);
                break;
            default:
                warriorLabel.setIcon(idle);
        }
        warriorLabel.setLocation(x, y);
    }


    public JLabel getWarriorLabel() {
        return warriorLabel;
    }


}
