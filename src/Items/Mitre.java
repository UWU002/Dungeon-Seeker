package Items;

import Characters.Entity;
import Characters.Skeleton;
import Main.GamePanel;
import Menus.GameHud;

import javax.swing.*;
import java.awt.*;

public class Mitre extends Item {
    public Mitre(GamePanel gp, GameHud gh, int x, int y) {
        super(gp, gh,x, y);
        createLabel();
        createHitboxRectangle();
        setDefaults();
    }

    private void setDefaults() {
        statIncrease = 10;
    }

    private void createLabel() {
        image = new JLabel(new ImageIcon("src/images/dungeon/mitra.png"));
        image.setSize(gp.tileSize, gp.tileSize);
        image.setLocation(x, y);
        gp.add(image);
    }

    private void createHitboxRectangle() {
        hitbox = new Rectangle(x + 20, y + 20, 16, 16);
    }

    public void contacts(Entity e) {
        if (hitbox.intersects(e.getHitbox()) && !(e instanceof Skeleton)) {
            if (e.getHealth() < e.getInitialHealth()) {
                e.setHealth(e.getInitialHealth());
            }
            gp.remove(image);
            hitbox.setLocation(-1000, 1000);
        }
    }
}
