package Characters;

import Main.GamePanel;
import Main.PlayerInputs;
import Tiles.map;

import javax.swing.*;
import java.awt.*;

public class Entity {
    protected GamePanel gp;
    protected PlayerInputs pI;
    protected int x, y;
    protected int speed;
    protected Rectangle hitbox, atackHitbox;
    protected map gameMap;
    protected int health;
    protected Timer attackCooldownTimer;
    protected Timer resetAttackFlagTimer;
    protected boolean canAtack=true;

    protected ImageIcon up, left, down, right, idle, atackR, atackU, atackD, atackL, die;
    protected String direction = "idle", prevDirection="right";

    public Entity(GamePanel gp, PlayerInputs pI, map gameMap){
        this.gp=gp;
        this.pI=pI;
        this.gameMap=gameMap;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public Rectangle getAtackHitbox(){return atackHitbox;}

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
