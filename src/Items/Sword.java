package Items;

import Characters.Entity;
import Characters.Skeleton;
import Main.GamePanel;

import javax.swing.*;
import java.awt.*;

public class Sword extends Item {
    public Sword(GamePanel gp, int x, int y) {
        super(gp, x, y);
        createLabel();
        createHitboxRectangle();
        setDefaults();
    }

    private void setDefaults() {
        statIncrease=10;
    }

    private void createLabel() {
        image= new JLabel(new ImageIcon("src/images/dungeon/sword.png"));
        image.setSize(gp.tileSize, gp.tileSize);
        image.setLocation(x,y);
        gp.add(image);
    }

    private void createHitboxRectangle() {
        hitbox= new Rectangle(image.getX(),image.getY() ,image.getWidth() ,image.getHeight());
    }

    public void contacts(Entity e){
        if (hitbox.intersects(e.getHitbox()) && !(e instanceof Skeleton)){
            e.setDamage(e.getDamage()+statIncrease);
            gp.remove(image);
            hitbox.setLocation(-1000, 1000);
        }
    };
}
