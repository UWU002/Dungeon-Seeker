package Items;

import Characters.Entity;
import Characters.Skeleton;
import Main.GamePanel;

import javax.swing.*;
import java.awt.*;

public class Potion extends Item{
    public Potion(GamePanel gp, int x, int y) {
        super(gp, x, y);
        createLabel();
        createHitboxRectangle();
        setDefaults();
    }

    private void setDefaults() {
        statIncrease=10;
    }

    private void createLabel() {
        image= new JLabel(new ImageIcon("src/images/dungeon/potion.png"));
        image.setSize(gp.tileSize, gp.tileSize);
        image.setLocation(x,y);
        gp.add(image);
    }

    private void createHitboxRectangle() {
        hitbox= new Rectangle(x+20,y+20 ,16 ,16);
    }

    public void contacts(Entity e){
        if (hitbox.intersects(e.getHitbox()) && !(e instanceof Skeleton)){
            e.setHealth(e.getHealth()+statIncrease);

            gp.remove(image);
            hitbox.setLocation(-1000, 1000);
        }
    }
}
