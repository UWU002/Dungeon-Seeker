package Menus;

import Main.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GameHud {
    GamePanel gp;
    private JLabel inventory, healthBar, gold,healthNum;
    private ImageIcon hp0,hp10,hp25,hp50,hp90,hp100; 
    private int hp, coins=0, screenHeight= 12* 48;

    public GameHud(GamePanel gp) {
        this.gp = gp;
        characterHealth();
    }
    public void update(){
        updateLabel();
        keep0heath();
    }


    private void characterHealth() {
        healthBar = new JLabel();
        loadImages();
        gp.add(healthBar);
        healthBar.setLocation(gp.tileSize*17, screenHeight+20);
        healthBar.setSize(200, 70);
        healthNum= new JLabel();
        healthNum.setLocation(905,550);
        healthNum.setSize(100,100);
        healthNum.setText(hp+"hp");
        healthNum.setForeground(Color.red);
        healthNum.setFont(new Font("Arial", Font.BOLD, 20));
        gp.add(healthNum);
        update();
    }
    private void loadImages() {
        hp0 = new ImageIcon("src/images/MenuItems/0hpBar.png");
        hp10 = new ImageIcon("src/images/MenuItems/10hpBar.png");
        hp25 = new ImageIcon("src/images/MenuItems/25hpBar.png");
        hp50 = new ImageIcon("src/images/MenuItems/50hpBar.png");
        hp90 = new ImageIcon("src/images/MenuItems/90hpBar.png");
        hp100 = new ImageIcon("src/images/MenuItems/100hpBar.png");

    }
    private void updateLabel() {
        healthNum.setText(hp+"hp");

        if (hp<=100 && hp>90){
            healthBar.setIcon(hp100);
        } else if (hp<=90 && hp>50) {
            healthBar.setIcon(hp90);
        } else if (hp<=50 && hp>25) {
            healthBar.setIcon(hp50);
        } else if (hp<=25 && hp>10) {
            healthBar.setIcon(hp25);
        } else if (hp<=10 && hp>0) {
            healthBar.setIcon(hp10);
        } else if (hp<=0){
            healthBar.setIcon(hp0);
        }
    }

    private void keep0heath() {
        if (hp < 0){
            hp=0;
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
