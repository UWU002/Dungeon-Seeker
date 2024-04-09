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
    protected int x=50, y=50;
    protected int health, damage;
    protected int speed;
    protected Rectangle hitbox, atackHitbox;
    protected map gameMap;
    protected Timer attackCooldownTimer;
    protected Timer resetAttackFlagTimer;
    protected boolean canAtack=true, dead=false;

    protected ImageIcon up, left, down, right, idle, atackR, atackU, atackD, atackL, die;
    protected String direction = "idle", prevDirection="", prevVerticalDirection="", prevHorizontalDirection="";


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

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean getDead(){
        return dead;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
