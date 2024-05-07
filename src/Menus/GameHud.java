package Menus;

import Main.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GameHud {
    GamePanel gp;
    private JLabel inventorySlot1,inventorySlot2,inventorySlot3, healthBar, gold, healthNum, atackAvailable, speed, damage;
    private ImageIcon hp0, hp10, hp25, hp50, hp90, hp100, check, cross, invSlotEmpty, invSlotPotion;
    private int hp, coins = 0, screenHeight = 12 * 48;

    public GameHud(GamePanel gp) {
        this.gp = gp;
        characterStats();
        loadInventory();
        canAtack();
    }


    public void update() {
        updateLabel();
        updateHealth();
        keep0heath();
        atackCooldownVisual();
        updateInventory();
    }

    private void loadInventory() {
        inventorySlot1 = new JLabel();
        loadImages();
        gp.add(inventorySlot1);
        inventorySlot1.setLocation(gp.tileSize * 2 , screenHeight + 12);
        inventorySlot1.setSize(80, 80);
        inventorySlot1.setIcon(invSlotPotion);

        inventorySlot2 = new JLabel();
        gp.add(inventorySlot2);
        inventorySlot2.setLocation(gp.tileSize * 4, screenHeight + 12);
        inventorySlot2.setSize(80, 80);
        inventorySlot2.setIcon(invSlotEmpty);

        inventorySlot3 = new JLabel();
        gp.add(inventorySlot3);
        inventorySlot3.setLocation(gp.tileSize * 6, screenHeight + 12);
        inventorySlot3.setSize(80, 80);
        inventorySlot3.setIcon(invSlotEmpty);
    }

    private void updateInventory() {
        if (gp.player != null) {
            switch (gp.player.getPotionCount()) {
                case 0:
                    inventorySlot1.setIcon(invSlotEmpty);
                    inventorySlot2.setIcon(invSlotEmpty);
                    inventorySlot3.setIcon(invSlotEmpty);
                    break;
                case 1:
                    inventorySlot1.setIcon(invSlotPotion);
                    inventorySlot2.setIcon(invSlotEmpty);
                    inventorySlot3.setIcon(invSlotEmpty);
                    break;
                case 2:
                    inventorySlot1.setIcon(invSlotPotion);
                    inventorySlot2.setIcon(invSlotPotion);
                    inventorySlot3.setIcon(invSlotEmpty);
                    break;
                case 3:
                    inventorySlot1.setIcon(invSlotPotion);
                    inventorySlot2.setIcon(invSlotPotion);
                    inventorySlot3.setIcon(invSlotPotion);
                    break;

            }
        }
    }

    private void updateHealth() {
        if (gp.player != null) {
            hp = gp.player.getHealth();
        }
    }

    private void characterStats() {
        loadImages();
        healthBarLable();
        healthNumLable();
        statsNumbers();
        update();
    }

    private void statsNumbers() {
        speed = new JLabel();
        speed.setLocation(650, 560);
        speed.setSize(100, 100);
        speed.setForeground(Color.red);
        speed.setFont(new Font("Arial", Font.BOLD, 15));
        gp.add(speed);
        damage = new JLabel();
        damage.setLocation(650, 590);
        damage.setSize(100, 100);
        damage.setForeground(Color.red);
        damage.setFont(new Font("Arial", Font.BOLD, 15));
        gp.add(damage);
        gold = new JLabel();
        gold.setLocation(710, 575);
        gold.setSize(100, 100);
        gold.setForeground(Color.YELLOW);
        gold.setFont(new Font("Arial", Font.BOLD, 15));
        gp.add(gold);
    }

    private void healthNumLable() {
        healthNum = new JLabel();
        healthNum.setLocation(905, 550);
        healthNum.setSize(100, 100);
        healthNum.setText(hp + "hp");
        healthNum.setForeground(Color.red);
        healthNum.setFont(new Font("Arial", Font.BOLD, 20));
        gp.add(healthNum);
    }

    private void healthBarLable() {
        healthBar = new JLabel();
        gp.add(healthBar);
        healthBar.setLocation(gp.tileSize * 17, screenHeight + 20);
        healthBar.setSize(200, 70);
    }

    private void canAtack() {
        atackAvailable = new JLabel();
        loadImages();
        gp.add(atackAvailable);
        atackAvailable.setLocation(gp.tileSize * 10, screenHeight + 12);
        atackAvailable.setSize(80, 80);
        atackAvailable.setIcon(check);
    }

    private void atackCooldownVisual() {
        if (gp.player != null && gp.player.getCanAtack()) {
            atackAvailable.setIcon(check);
        } else if (gp.player != null && !gp.player.getCanAtack()) {
            atackAvailable.setIcon(cross);
        }
    }

    private void loadImages() {
        hp0 = new ImageIcon("src/images/MenuItems/0hpBar.png");
        hp10 = new ImageIcon("src/images/MenuItems/10hpBar.png");
        hp25 = new ImageIcon("src/images/MenuItems/25hpBar.png");
        hp50 = new ImageIcon("src/images/MenuItems/50hpBar.png");
        hp90 = new ImageIcon("src/images/MenuItems/90hpBar.png");
        hp100 = new ImageIcon("src/images/MenuItems/100hpBar.png");
        check = new ImageIcon("src/images/MenuItems/check.png");
        cross = new ImageIcon("src/images/MenuItems/cross.png");
        invSlotEmpty = new ImageIcon("src/images/MenuItems/invSlotEmpty.png");
        invSlotPotion = new ImageIcon("src/images/MenuItems/invSlotPotion.png");
    }

    private void updateLabel() {
        if (gp.player != null) {
            speed.setText("Spd: " + gp.player.getSpeed() * 10);
            damage.setText("Atk : " + gp.player.getDamage());
            gold.setText("Gold: "+ gp.player.getGold());
        }
        healthNum.setText(hp + "hp");

        if (hp <= 100 && hp > 90) {
            healthBar.setIcon(hp100);
        } else if (hp <= 90 && hp > 50) {
            healthBar.setIcon(hp90);
        } else if (hp <= 50 && hp > 25) {
            healthBar.setIcon(hp50);
        } else if (hp <= 25 && hp > 10) {
            healthBar.setIcon(hp25);
        } else if (hp <= 10 && hp > 0) {
            healthBar.setIcon(hp10);
        } else if (hp <= 0) {
            healthBar.setIcon(hp0);
        } else if (hp > 100) {
            healthBar.setIcon(hp100);
        }
    }

    private void keep0heath() {
        if (hp < 0) {
            hp = 0;
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
