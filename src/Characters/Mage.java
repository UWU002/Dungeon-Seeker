package Characters;

import Main.GamePanel;
import Main.PlayerInputs;
import Menus.GameHud;
import Tiles.map;

import javax.swing.*;
import java.awt.*;


public class Mage extends Entity {
    private JLabel playerLabel, atackEffect, explosionLabel;
    private ImageIcon effectR, effectU, effectL, effectD, explosion;
    private Rectangle explosionHitbox;
    private Timer fireballMovement;
    private int fSPX, fSPY;


    public Mage(GamePanel gp, PlayerInputs pI, map gameMap, GameHud gh) {
        super(gp, pI, gameMap, gh);
        playerLabel = new JLabel();
        loadImages();
        setDefaultValues();
        gp.add(playerLabel);
        playerLabel.setSize(gp.originalTileSize * 2, gp.originalTileSize * 2);
        hitbox = new Rectangle(x + 10, y + 10, gp.originalTileSize, gp.originalTileSize);
        atackHitbox = new Rectangle(x + 10000, y, gp.originalTileSize, gp.originalTileSize);
        explosionHitbox = new Rectangle(x - 10000, y, gp.originalTileSize * 3, gp.originalTileSize * 3);
        atackEffect = new JLabel(new ImageIcon());
        gp.add(atackEffect);
        atackEffect.setSize(gp.originalTileSize * 2, gp.originalTileSize * 2);
        explosionLabel = new JLabel(new ImageIcon());
        gp.add(explosionLabel);
        explosionLabel.setSize(gp.originalTileSize * 3, gp.originalTileSize * 3);
        gh.setHp(this.health);
        AttackFlagTimer();
        attackCooldown();
        fireballTimer();
    }

    public void setDefaultValues() {
        health = 40;
        speed = 3;
        damage = 100;
        direction = "idle";
        updateLabel();
    }


    private void fireballTimer() {
        fireballMovement = new Timer(50, e -> {
            int dx = 0, dy = 0;
            switch (prevDirection) {
                case "up":
                    dy = -2;
                    break;
                case "down":
                    dy = 2;
                    break;
                case "right":
                    dx = 2;
                    break;
                case "left":
                    dx = -2;
                    break;
            }
            atackHitbox.setLocation(atackHitbox.x + dx, atackHitbox.y + dy);
            atackEffect.setLocation((int)atackHitbox.getX(), (int)atackHitbox.getY());
            System.out.println(atackHitbox.getX() + atackHitbox.getY());

        if (fireballHit() || reachedLocation(atackHitbox.x, atackHitbox.y)) {
            fireballMovement.stop();
            explosionLabel.setIcon(explosion);
            explosionLabel.setLocation(atackHitbox.x, atackHitbox.y);
            explosionHitbox.setLocation(atackHitbox.x, atackHitbox.y);
            atackHitbox.setLocation(-10000, -10000);
        }
    });
    }

    private void attackCooldown() {
        attackCooldownTimer = new Timer(2000, e -> {
            canAtack = true;
            hasReacted = false;
            attackCooldownTimer.stop();
        });
    }

    private void AttackFlagTimer() {
        resetAttackFlagTimer = new Timer(700, e -> {
            pI.atacked = false;
            direction = "idle";
            resetAttackFlagTimer.stop();
        });
    }


    @Override
    public void update() {
        if (!dead) {
            movePlayer();
            updateLabel();
        }
    }

    private void movePlayer() {
        int newX = x, newY = y;

        if (pI.upPressed) {
            if (!resetAttackFlagTimer.isRunning()) {
                direction = "up";
            }
            prevVerticalDirection = "up";
            prevDirection = direction;
            newY -= speed;
        }
        if (pI.leftPressed) {
            if (!resetAttackFlagTimer.isRunning()) {
                direction = "left";
            }
            prevHorizontalDirection = "left";
            prevDirection = direction;
            newX -= speed;
        }
        if (pI.downPressed) {
            if (!resetAttackFlagTimer.isRunning()) {
                direction = "down";
            }
            prevVerticalDirection = "down";
            prevDirection = direction;
            newY += speed;
        }
        if (pI.rightPressed) {
            if (!resetAttackFlagTimer.isRunning()) {
                direction = "right";
            }
            prevHorizontalDirection = "right";
            prevDirection = direction;
            newX += speed;
        }


        if (pI.atacked && canAtack) {
            canAtack = false;
            direction = "atack";
            atackHitbox.setLocation((int) hitbox.getX() + calculateAttackXOffset(), (int) hitbox.getY() + calculateAttackYOffset());
            atackEffect.setLocation(atackHitbox.x, atackHitbox.y);
            hasReacted = false;
            resetAttackFlagTimer.start();
            attackCooldownTimer.start();
            fSPX= hitbox.x;
            fSPY= hitbox.y;
            fireballMovement.start();
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

    private boolean fireballHit() {


        return false;
    }

    private boolean reachedLocation(int x, int y) {
        int maxDistance, distance=50;
        switch (prevDirection) {
            case "up":
                maxDistance=fSPY - distance;
                if (y<=maxDistance){
                    return true;
                }
                break;
            case "down":
                maxDistance=fSPY + distance;
                if (y>=maxDistance){
                    return true;
                }
                break;
            case "right":
                maxDistance=fSPX + distance;
                if (x>=maxDistance){
                    return true;
                }
                break;
            case "left":
                maxDistance=fSPX - distance;
                if (x<=maxDistance){
                    return true;
                }
                break;
        }

        return false;
    }

    private int calculateAttackXOffset() {
        switch (prevDirection) {
            case "right":
                return 5;
            case "left":
                return -25;
            default:
                return -7;
        }
    }

    private int calculateAttackYOffset() {
        switch (prevDirection) {
            case "up":
                return -25;
            case "down":
                return 5;
            default:
                return -7;
        }
    }

    private boolean canMove(int newX, int newY) {
        Rectangle predictedHitbox = new Rectangle();
        if ("up".equalsIgnoreCase(prevVerticalDirection) && "Left".equalsIgnoreCase(prevHorizontalDirection)) {
            predictedHitbox = new Rectangle(newX, newY, hitbox.width, hitbox.height);
        } else if ("up".equalsIgnoreCase(prevVerticalDirection) && "right".equalsIgnoreCase(prevHorizontalDirection)) {
            predictedHitbox = new Rectangle(newX + 10, newY, hitbox.width, hitbox.height);
        } else if ("down".equalsIgnoreCase(prevVerticalDirection) && "Left".equalsIgnoreCase(prevHorizontalDirection)) {
            predictedHitbox = new Rectangle(newX, newY + 10, hitbox.width, hitbox.height);
        } else if ("down".equalsIgnoreCase(prevVerticalDirection) && "right".equalsIgnoreCase(prevHorizontalDirection)) {
            predictedHitbox = new Rectangle(newX + 10, newY + 10, hitbox.width, hitbox.height);
        }


        for (Rectangle wall : gameMap.getWallHitboxes()) {
            if (predictedHitbox.intersects(wall)) {
                return false;
            }
        }
        return true;
    }

    private void loadImages() {
        idle = new ImageIcon("src/images/wizard/wizardIdle.png");
        down = new ImageIcon("src/images/wizard/wizard_down.gif");
        left = new ImageIcon("src/images/wizard/wizard_left.gif");
        right = new ImageIcon("src/images/wizard/wizard_right.gif");
        up = new ImageIcon("src/images/wizard/wizard_up.gif");
        atackR = new ImageIcon("src/images/wizard/wizard_atack_right.png");
        atackD = new ImageIcon("src/images/wizard/wizard_atack_down.png");
        atackU = new ImageIcon("src/images/wizard/wizard_atack_up.png");
        atackL = new ImageIcon("src/images/wizard/wizard_atack_left.png");
        effectU = new ImageIcon("src/images/wizard/atackEffect_up.gif");
        effectD = new ImageIcon("src/images/wizard/atackEffect_down.gif");
        effectL = new ImageIcon("src/images/wizard/atackEffect_left.gif");
        effectR = new ImageIcon("src/images/wizard/atackEffect_right.gif");
        explosion = new ImageIcon("src/images/wizard/atackEffect_exp");


        die = new ImageIcon("src/images/wizard/wizard_die.gif");
    }


    private void updateLabel() {
        switch (direction) {
            case "up":
                playerLabel.setIcon(up);
                break;
            case "down":
                playerLabel.setIcon(down);
                break;
            case "right":
                playerLabel.setIcon(right);
                break;
            case "left":
                playerLabel.setIcon(left);
                break;
            case "atack":
                atackDirection();
                break;
            default:
                playerLabel.setIcon(idle);
                break;
        }
        playerLabel.setLocation(x, y);
        if (health <= 0) {
            dead = true;
            deathAnimation();
        }
    }

    private void deathAnimation() {
        playerLabel.setIcon(die);
    }

    private void atackDirection() {
        switch (prevDirection) {
            case "up":
                playerLabel.setIcon(atackU);
                atackEffect.setIcon(effectU);
                break;
            case "down":
                playerLabel.setIcon(atackD);
                atackEffect.setIcon(effectD);
                break;
            case "right":
                playerLabel.setIcon(atackR);
                atackEffect.setIcon(effectR);
                break;
            case "left":
                playerLabel.setIcon(atackL);
                atackEffect.setIcon(effectL);
                break;
        }
    }

    boolean hasReacted = false;

    @Override
    public void attacks(Entity e) {
        if (this.explosionHitbox.intersects(e.getHitbox())) {
            if (!hasReacted) {
                e.setHealth(e.getHealth() - damage);
                hasReacted = true;
            }
        }
    }

    @Override
    public JLabel getLabel() {
        return playerLabel;
    }

    @Override
    public JLabel getEffect() {
        return atackEffect;
    }


}
