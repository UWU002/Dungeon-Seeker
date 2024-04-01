package Characters;

import Main.GamePanel;
import Main.PlayerInputs;
import Tiles.map;

import javax.swing.*;
import java.awt.*;


public class Warrior extends Entity {
    private JLabel warriorLabel, atackEffect;
    private ImageIcon effectR, effectU, effectL, effectD;

    public Warrior(GamePanel gp, PlayerInputs pI, map gameMap) {
        super(gp, pI, gameMap);
        warriorLabel = new JLabel();
        loadImages();
        setDefaultValues();
        gp.add(warriorLabel);
        warriorLabel.setSize(gp.originalTileSize * 2, gp.originalTileSize * 2);
        hitbox = new Rectangle(x + 10, y + 10, gp.originalTileSize, gp.originalTileSize);
        atackHitbox = new Rectangle(x + 10000, y, gp.originalTileSize, gp.originalTileSize);
        atackEffect = new JLabel(new ImageIcon());
        gp.add(atackEffect);
        atackEffect.setSize(gp.originalTileSize * 2, gp.originalTileSize * 2);


        resetAttackFlagTimer = new Timer(700, e -> {
            pI.atacked = false;
            direction = "idle";
            atackHitbox.setLocation(-10000, -10000);
            atackEffect.setLocation(-10000, -10000);
            resetAttackFlagTimer.stop();
        });
        attackCooldownTimer = new Timer(1500, e -> {
            canAtack = true;
            hasReacted = false;
            attackCooldownTimer.stop();
        });
    }


    public void setDefaultValues() {
        x = 50;
        y = 50;
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
            if (!resetAttackFlagTimer.isRunning()) {
                direction = "up";
            }
            prevDirection = direction;
            newY -= speed;
        }
        if (pI.leftPressed) {
            if (!resetAttackFlagTimer.isRunning()) {
                direction = "left";
            }
            prevDirection = direction;
            newX -= speed;
        }
        if (pI.downPressed) {
            if (!resetAttackFlagTimer.isRunning()) {
                direction = "down";
            }
            prevDirection = direction;
            newY += speed;
        }
        if (pI.rightPressed) {
            if (!resetAttackFlagTimer.isRunning()) {
                direction = "right";
            }
            prevDirection = direction;
            newX += speed;
        }
        if (pI.atacked && canAtack) {
            canAtack = false;
            direction = "atack";
            atackHitbox.setLocation((int) hitbox.getX() + calculateAttackXOffset(), (int) hitbox.getY() + calculateAttackYOffset());
            atackEffect.setLocation((int) atackHitbox.getX(), (int) atackHitbox.getY());
            atackEffectDirection();
            hasReacted = false;
            resetAttackFlagTimer.start();
            attackCooldownTimer.start();
        } else {
            atackHitbox.setLocation(-10000, -10000);
        }
        if (!pI.downPressed && !pI.rightPressed && !pI.leftPressed && !pI.upPressed && !pI.atacked) {
            direction = "idle";
        }

        if (canMove(newX, newY)) {
            x = newX;
            y = newY;
        } else {
            direction = "idle";
        }

        hitbox.setLocation(x + 10, y + 10);
        if (!resetAttackFlagTimer.isRunning()) {
            atackHitbox.setLocation(-10000, -10000);
        }


    }

    private void atackEffectDirection() {
        if ("right".equalsIgnoreCase(prevDirection)) {
            atackEffect.setLocation((int) atackHitbox.getX() - 5, (int) atackHitbox.getY()- 10);
        } else if ("down".equalsIgnoreCase(prevDirection)) {
            atackEffect.setLocation((int) atackHitbox.getX()- 15, (int) atackHitbox.getY() - 7);
        } else if ("up".equalsIgnoreCase(prevDirection)) {
            atackEffect.setLocation((int) atackHitbox.getX()- 15, (int) atackHitbox.getY() - 10);
        } else {
            atackEffect.setLocation((int) atackHitbox.getX() - 10, (int) atackHitbox.getY()- 10);
        }
    }

    private int calculateAttackXOffset() {
        switch (prevDirection) {
            case "right":
                return 15;
            case "left":
                return -15;
            default:
                return 0;
        }
    }

    private int calculateAttackYOffset() {
        switch (prevDirection) {
            case "up":
                return -15;
            case "down":
                return 15;
            default:
                return 0;
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
        effectU = new ImageIcon("src/images/warrior/atackEffect_up.gif");
        effectD = new ImageIcon("src/images/warrior/atackEffect_down.gif");
        effectL = new ImageIcon("src/images/warrior/atackEffect_left.gif");
        effectR = new ImageIcon("src/images/warrior/atackEffect_right.gif");
        die = new ImageIcon("src/images/warrior/warrior_die.gif.gif");
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
                break;
        }
        warriorLabel.setLocation(x, y);
    }

    private void atackDirection() {
        switch (prevDirection) {
            case "up":
                warriorLabel.setIcon(atackU);
                atackEffect.setIcon(effectU);
                break;
            case "down":
                warriorLabel.setIcon(atackD);
                atackEffect.setIcon(effectD);
                break;
            case "right":
                warriorLabel.setIcon(atackR);
                atackEffect.setIcon(effectR);
                break;
            case "left":
                warriorLabel.setIcon(atackL);
                atackEffect.setIcon(effectL);
                break;
        }
    }

    boolean hasReacted = false;

    public void Attacks(Entity e) {
        if (this.atackHitbox.intersects(e.getHitbox())) {
            if (!hasReacted) {
                e.setHealth(e.getHealth() - 10);
                hasReacted = true;
            }
        }
    }


    public JLabel getWarriorLabel() {
        return warriorLabel;
    }

    public JLabel getAtackEffect() {
        return atackEffect;
    }
}
