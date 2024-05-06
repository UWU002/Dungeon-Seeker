package Characters;

import Main.GamePanel;
import Main.PlayerInputs;
import Menus.GameHud;
import Tiles.Map;

import javax.swing.*;
import java.awt.*;

public class Entity {
    protected GamePanel gp;
    protected PlayerInputs pI;
    protected GameHud gh;
    protected int x=100, y=100;
    protected int health, intialHealth ,damage, speed;
    protected Rectangle hitbox, atackHitbox;
    protected Map gameMap;
    protected Timer attackCooldownTimer;
    protected Timer resetAttackFlagTimer;
    protected boolean canAtack=true, dead=false;
    protected int potionCount=0;

    protected ImageIcon up, left, down, right, idle, atackR, atackU, atackD, atackL, die;
    protected String direction = "idle", prevDirection="", prevVerticalDirection="", prevHorizontalDirection="";


    public Entity(GamePanel gp, PlayerInputs pI , Map gameMap, GameHud gh){
        this.gp=gp;
        this.pI=pI;
        this.gh=gh;
        this.gameMap=gameMap;
    }
    public Entity(GamePanel gp, PlayerInputs pI, Map gameMap, GameHud gh, int x, int y){
        this.gp=gp;
        this.pI=pI;
        this.gameMap=gameMap;
        this.gh=gh;
        this.x=x;
        this.y=y;
    }


    public void update(){}
    public int getInitialHealth(){
        return intialHealth;
    }
    public JLabel getEffect(){
        return null;
    }
    public JLabel getLabel(){
        return null;
    }
    public JLabel getEffect2(){return  null;}
    public boolean getCanAtack(){
        return canAtack;
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

    public boolean getDead(){
        return dead;
    }

    public int getPotionCount() {
        return potionCount;
    }

    public void setPotionCount(int potionCount) {
        this.potionCount = potionCount;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
