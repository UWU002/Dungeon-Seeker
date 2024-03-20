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
    protected Rectangle hitbox;
    protected map gameMap;

    protected ImageIcon up, left, down, right, idle;
    protected String direction = "idle";

    public Entity(GamePanel gp, PlayerInputs pI, map gameMap){
        this.gp=gp;
        this.pI=pI;
        this.gameMap=gameMap;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
