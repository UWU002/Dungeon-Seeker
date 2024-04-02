package Characters;

import Main.GamePanel;
import Main.PlayerInputs;
import Menus.GameHud;
import Tiles.map;

import javax.swing.*;
import java.awt.*;

public class Entity {
    protected GamePanel gp;
    protected PlayerInputs pI;
    protected GameHud gh;
    protected int x, y;
    protected int speed, health, damage;
    protected Rectangle hitbox, atackHitbox;
    protected map gameMap;
    protected Timer attackCooldownTimer;
    protected Timer resetAttackFlagTimer;
    protected boolean canAtack=true;

    protected ImageIcon up, left, down, right, idle, atackR, atackU, atackD, atackL, die;
    protected String direction = "idle", prevDirection="right";


    public Entity(GamePanel gp, PlayerInputs pI ,map gameMap, GameHud gh){
        this.gp=gp;
        this.pI=pI;
        this.gh=gh;
        this.gameMap=gameMap;
    }
    public Entity(GamePanel gp, PlayerInputs pI,map gameMap,GameHud gh, int x, int y){
        this.gp=gp;
        this.pI=pI;
        this.gameMap=gameMap;
        this.gh=gh;
        this.x=x;
        this.y=y;
    }

    public void update(){}
    public JLabel getEffect(){
        return null;
    }
    public JLabel getLabel(){
        return null;
    }
    public void attacks(Entity e){}

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
