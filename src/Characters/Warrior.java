package Characters;

import Main.GamePanel;
import Main.PlayerInputs;

import javax.swing.*;
import java.awt.*;

public class Warrior extends Entity {
    private GamePanel gp;
    private PlayerInputs pI;
    private JLabel warriorLabel;



    public Warrior(GamePanel gp, PlayerInputs pI) {
        this.gp = gp;
        this.pI = pI;
        warriorLabel= new JLabel();
        loadImages();
        setDefaultValues();
        gp.add(warriorLabel);
        warriorLabel.setSize(gp.tileSize*2, gp.tileSize*2);
    }

    public Rectangle getHitbox() {
        return new Rectangle(x+10, y, gp.tileSize, gp.tileSize);
    }
    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 2;
        direction = "idle";
        updateLabel();
    }

    public void update() {
        movePlayer();
        updateLabel();
    }

    private void movePlayer() {
        if (pI.upPressed && !pI.rightPressed && !pI.downPressed && !pI.leftPressed) {
            direction="up";
            y -= speed;
        } else if (pI.downPressed && !pI.rightPressed && !pI.leftPressed && !pI.upPressed) {
            direction="down";
            y += speed;
        } else if (pI.leftPressed && !pI.rightPressed && !pI.downPressed && !pI.upPressed) {
            direction="left";
            x -= speed;
        } else if (pI.rightPressed && !pI.upPressed && !pI.leftPressed && !pI.downPressed) {
            direction="right";
            x += speed;
        } else if (pI.upPressed && pI.rightPressed) {
            direction="up";
            x += speed;
            y -= speed;
        } else if (pI.upPressed && pI.leftPressed) {
            direction="up";
            x -= speed;
            y -= speed;
        } else if (pI.downPressed && pI.rightPressed) {
            direction="down";
            x += speed;
            y += speed;
        } else if (pI.downPressed && pI.leftPressed) {
            direction="down";
            x -= speed;
            y += speed;
        } else if (!pI.rightPressed && !pI.downPressed && !pI.leftPressed && !pI.upPressed){
            direction="idle";
        }
    }

    private void loadImages() {
        idle = new ImageIcon("src/images/warrior/warriorIdle.png");
        down= new ImageIcon("src/images/warrior/warrior_down.gif");
        left= new ImageIcon("src/images/warrior/warrior_left.gif");
        right= new ImageIcon("src/images/warrior/warrior_right.gif");
        up= new ImageIcon("src/images/warrior/warrior_up.gif");

    }
    private void updateLabel() {
        switch (direction){
            case "up":
                warriorLabel.setIcon(up);
                break;
            case "down":
                warriorLabel.setIcon(down);
                break;
            case "right":
                warriorLabel.setIcon(right);
                break;
            case "left":
                warriorLabel.setIcon(left);
                break;
            default:
                warriorLabel.setIcon(idle);
        }
        warriorLabel.setLocation(x,y);
    }
}
