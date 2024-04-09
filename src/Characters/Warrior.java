package Characters;

import Main.GamePanel;
import Main.PlayerInputs;
import Menus.GameHud;
import Tiles.map;

import javax.swing.*;
import java.awt.*;


public class Warrior extends Entity {
    private JLabel playerLabel, atackEffect;
    private ImageIcon effectR, effectU, effectL, effectD;

    public Warrior(GamePanel gp, PlayerInputs pI, map gameMap, GameHud gh) {
        super(gp, pI, gameMap, gh);
        playerLabel = new JLabel();
        loadImages();
        setDefaultValues();
        gp.add(playerLabel);
        playerLabel.setSize(gp.originalTileSize * 2, gp.originalTileSize * 2);
        hitbox = new Rectangle(x + 10, y + 10, gp.originalTileSize, gp.originalTileSize);
        atackHitbox = new Rectangle(x + 10000, y, gp.originalTileSize, gp.originalTileSize);
        atackEffect = new JLabel(new ImageIcon());
        gp.add(atackEffect);
        atackEffect.setSize(gp.originalTileSize * 2, gp.originalTileSize * 2);
        gh.setHp(this.health);

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
        health = 100;
        speed = 2;
        damage = 50;
        direction = "idle";
        updateLabel();
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
            prevVerticalDirection=direction;
            prevDirection = direction;
            newY -= speed;
        }
        if (pI.leftPressed) {
            if (!resetAttackFlagTimer.isRunning()) {
                direction = "left";
            }
            prevHorizontalDirection=direction;
            prevDirection = direction;
            newX -= speed;
        }
        if (pI.downPressed) {
            if (!resetAttackFlagTimer.isRunning()) {
                direction = "down";
            }
            prevVerticalDirection=direction;
            prevDirection = direction;
            newY += speed;
        }
        if (pI.rightPressed) {
            if (!resetAttackFlagTimer.isRunning()) {
                direction = "right";
            }
            prevHorizontalDirection=direction;
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
            atackEffect.setLocation((int) atackHitbox.getX() - 5, (int) atackHitbox.getY() - 10);
        } else if ("down".equalsIgnoreCase(prevDirection)) {
            atackEffect.setLocation((int) atackHitbox.getX() - 15, (int) atackHitbox.getY() - 7);
        } else if ("up".equalsIgnoreCase(prevDirection)) {
            atackEffect.setLocation((int) atackHitbox.getX() - 15, (int) atackHitbox.getY() - 10);
        } else {
            atackEffect.setLocation((int) atackHitbox.getX() - 10, (int) atackHitbox.getY() - 10);
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
        Rectangle predictedHitbox=new Rectangle();
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
        die = new ImageIcon("src/images/warrior/warrior_die.gif");
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
        if (this.atackHitbox.intersects(e.getHitbox())) {
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
