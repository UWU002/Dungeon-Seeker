package Items;

import Characters.Entity;
import Main.GamePanel;
import Menus.GameHud;

import javax.swing.*;
import java.awt.*;

public class Item {
    protected GamePanel gp;
    protected GameHud gh;
    protected JLabel image;
    protected Rectangle hitbox;
    protected int x, y;
    protected int statIncrease;


    public Item(GamePanel gp, GameHud gh ,int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;
    }

    public void update(){}
    public JLabel getJlabel(){return image;}
    public void contacts(Entity e){}


}
