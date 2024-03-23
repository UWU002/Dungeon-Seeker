package Characters;

import Main.GamePanel;
import Main.PlayerInputs;
import Tiles.map;

import javax.swing.*;
import java.awt.*;


public class Warrior extends Entity {
    private JLabel warriorLabel;


    public Warrior(GamePanel gp, PlayerInputs pI, map gameMap) {
        super(gp, pI, gameMap);
        warriorLabel = new JLabel();
        loadImages();
        setDefaultValues();
        gp.add(warriorLabel);
        warriorLabel.setSize(gp.originalTileSize * 2, gp.originalTileSize * 2);
        hitbox = new Rectangle(x + 10, y + 10, gp.originalTileSize, gp.originalTileSize);
        atackHitbox= new Rectangle(x+10000, y, gp.originalTileSize, gp.originalTileSize);

        resetAttackFlagTimer = new Timer(700, e -> {
            pI.atacked = false;
            atackHitbox.setLocation(x+10000, y);
            resetAttackFlagTimer.stop();
        });
        attackCooldownTimer = new Timer(1500, e -> {
            canAtack = true;
            attackCooldownTimer.stop();
        });
    }


    public void setDefaultValues() {
        x = 50;
        y = 600;
        health = 100;
        speed = 2;
        direction = "idle";
        updateLabel();
    }

    public void update() {
        movePlayer();
        updateLabel();
    }

    private void movePlayer() {
        int newX = x, newY = y;

        if (pI.upPressed) {
            if (!pI.atacked) {
                direction = "up";
            }
            prevDirection = direction;
            newY -= speed;
        }
        if (pI.leftPressed) {
            if (!pI.atacked) {
                direction = "left";
            }
            prevDirection = direction;
            newX -= speed;
        }
        if (pI.downPressed) {
            if (!pI.atacked) {
                direction = "down";
            }
            prevDirection = direction;
            newY += speed;
        }
        if (pI.rightPressed) {
            if (!pI.atacked) {direction = "right";}
            prevDirection = direction;
            newX += speed;
        }
        if (pI.atacked && canAtack) {
            canAtack = false;
            resetAttackFlagTimer.start();
            attackCooldownTimer.start();
            direction = "atack";
        }
        if (!pI.downPressed && !pI.rightPressed && !pI.leftPressed && !pI.upPressed && !pI.atacked) {
            direction = "idle";
        }

        hitbox.setLocation(x + 10, y + 10);

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
        atackR = new ImageIcon("src/images/warrior/warrior_atack_right.gif");
        atackD = new ImageIcon("src/images/warrior/warrior_atack_down.gif");
        atackU = new ImageIcon("src/images/warrior/warrior_atack_up.gif");
        atackL = new ImageIcon("src/images/warrior/warrior_atack_left.gif");
        die = new ImageIcon("src/images/warrior/warrior_die.gif");
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
            case "atack":
                atackDirection();
                break;
            default:
                warriorLabel.setIcon(idle);
        }
        warriorLabel.setLocation(x, y);
    }

    private void atackDirection() {
        switch (prevDirection) {
            case "up":
                warriorLabel.setIcon(atackU);
                atackHitbox.setLocation((int)hitbox.getX(),(int) hitbox.getY()+5);
                break;
            case "down":
                warriorLabel.setIcon(atackD);
                atackHitbox.setLocation((int)hitbox.getX(),(int) hitbox.getY()-5);
                break;
            case "right":
                warriorLabel.setIcon(atackR);
                atackHitbox.setLocation((int)hitbox.getX()+5,(int) hitbox.getY());
                break;
            case "left":
                warriorLabel.setIcon(atackL);
                atackHitbox.setLocation((int)hitbox.getX()-10,(int) hitbox.getY());
                break;
        }
    }


    public JLabel getWarriorLabel() {
        return warriorLabel;
    }


}
