package Main;

import Characters.Skeleton;
import Characters.Warrior;
import Menus.GameHud;
import Tiles.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GamePanel extends JPanel {

    //Screen Size
    public final int originalTileSize = 16, scale = 3;
    public final int tileSize = originalTileSize * scale; //48 x 48 screen
    public final int screenTileWidth = 22, screenTileHeight = 12;
    final int boardWidth = tileSize * screenTileWidth, boardHeight = tileSize * screenTileHeight; // 1056px x 576px


    PlayerInputs pI = new PlayerInputs();
    private Timer gameTimer;


    //temp object creation
    map map = new map(this);
    Warrior warrior = new Warrior(this, pI, map);
    Skeleton skeleton = new Skeleton(this, pI, map);
    GameHud gh = new GameHud(this, warrior.getHealth());


    public GamePanel() {
        this.setPreferredSize(new Dimension(boardWidth, boardHeight + 100));
        this.setBackground(Color.gray);
        this.setSize(boardWidth, boardHeight + 100);
        this.setBackground(Color.black);
        this.setLayout(null);
        this.addKeyListener(pI);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setComponentZOrder(warrior.getWarriorLabel(), 1);
        this.setComponentZOrder(skeleton.getSkeletonLabel(), 0);
    }


    public void startGame() {
        int delay = 1000 / 60; //60 fps
        gameTimer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();
                repaint();
                updateFPS();
            }
        });
        gameTimer.start();
    }


    private void updateGame() {
        warrior.update();
        skeleton.update();
        gh.update();
        checkCollisions();
    }
    boolean hasReacted = false;
    private void checkCollisions() {
        if (skeleton.getHitbox().intersects(warrior.getHitbox())) {
            if (!hasReacted) {
                gh.setHp(warrior.getHealth() - 10);
                warrior.setHealth(warrior.getHealth() - 10);
                hasReacted=true;
            }
        } else {
            hasReacted=false;
        }
    }

    //Temporary player settings

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        Rectangle hitbox = warrior.getHitbox();
        Rectangle hitbox2= skeleton.getHitbox();
        g2.draw(hitbox);
        g2.draw(hitbox2);
    }


    public static class frameWindowListener extends WindowAdapter {
        JFrame frame;

        public frameWindowListener(JFrame frame) {
            this.frame = frame;
        }

        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);

            int conf = JOptionPane.showConfirmDialog(null,
                    "Unsaved data will be lost, Are you Sure?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (conf == JOptionPane.YES_OPTION) {
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            } else {
                frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        }
    }

    private long lastTimeCheck = System.nanoTime();
    private int frameCount = 0;

    private void updateFPS() {
        long currentTime = System.nanoTime();
        frameCount++;

        // If more than a second has passed, print the FPS and reset
        if (currentTime - lastTimeCheck >= 1000000000) { // If one second has passed
            System.out.println("FPS: " + frameCount);
            frameCount = 0; // Reset the frame count
            lastTimeCheck = currentTime; // Reset the last time check
        }
    }
}