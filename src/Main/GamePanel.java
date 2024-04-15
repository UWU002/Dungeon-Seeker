package Main;

import Characters.*;
import Items.Item;
import Items.Mitre;
import Items.Potion;
import Items.Sword;
import Menus.GameHud;
import Tiles.map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel {

    Random r= new Random();
    //Screen Size
    public final int originalTileSize = 16, scale = 3;
    public final int tileSize = originalTileSize * scale; //48 x 48 screen
    public final int screenTileWidth = 22, screenTileHeight = 12;
    final int boardWidth = tileSize * screenTileWidth, boardHeight = tileSize * screenTileHeight; // 1056px x 576px
    //Tutorial Hitboxes
    Rectangle swordHelp, potionHelp, mitreHelp;
    // Objects Arrays
    ArrayList<Skeleton> skeletons = new ArrayList<>();
    ArrayList<Item> items= new ArrayList<>();


    PlayerInputs pI = new PlayerInputs();
    private Timer gameTimer;

    map map = new map(this);
    GameHud gh = new GameHud(this);
    public Entity player = new Entity(this, pI, map, gh);

    //Tutorial Item creation
    Sword sword= new Sword(this, 200, 50);
//  Potion potion= new Potion(this, 250, 50);
//  Mitre mitre= new Mitre(this, 300, 50);



    public void characterSelection(String characterType) {
        if ("Warrior".equalsIgnoreCase(characterType)) {
            player = new Warrior(this, pI, map, gh);
        } else if ("Mage".equalsIgnoreCase(characterType)) {
            player = new Mage(this, pI, map, gh);
        } else if ("Priest".equalsIgnoreCase(characterType)) {
            player = new Priest(this, pI, map, gh);
        }
        zIndexPlacement();
    }

    public GamePanel() {
        this.setPreferredSize(new Dimension(boardWidth, boardHeight + 100));
        this.setBackground(Color.gray);
        this.setSize(boardWidth, boardHeight + 100);
        this.setBackground(Color.black);
        this.setLayout(null);
        this.addKeyListener(pI);
        this.setFocusable(true);
        this.requestFocusInWindow();
        loadSkeletons();
        loadItems();
        backgroundMusic();
    }

    private void loadItems() {
        int rndm;
        for (int i= 0; i < map.getItemSpawns().length; i++){
            int x= map.getItemSpawns()[i][0];
            int y= map.getItemSpawns()[i][1];
            if (r.nextInt(10) > 3){
                rndm=r.nextInt(3);
                switch (rndm){
                    case 0:
                        items.add(new Sword(this, x, y));
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                }
            }
        }
        loadTutorialTextBoxes();
    }

    private void loadTutorialTextBoxes() {
        sword= new Rectangle(64,64, 200,50);
    }

    private void loadSkeletons() {
        for (int i = 0; i < map.getSkeletons().length; i++) {
            int x = map.getSkeletons()[i][0];
            int y = map.getSkeletons()[i][1];
            skeletons.add(new Skeleton(this, pI, map, gh, x, y));
        }
    }

    private void zIndexPlacement() {
        this.setComponentZOrder(player.getLabel(), 1);
        this.setComponentZOrder(player.getEffect(), 1);
        if (player.getEffect2() != null) {
            this.setComponentZOrder(player.getEffect2(), 1);
        }
        for (Skeleton s : skeletons) {
            this.setComponentZOrder(s.getSkeletonLabel(), 1);
            this.setComponentZOrder(s.getSkeletonHealth(), 1);
        }


        this.setComponentZOrder(sword.getJlabel(), 1);
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
        player.update();
        for (Skeleton s : skeletons) {
            s.update();
        }
        gh.update();
        checkCollisions();



        sword.contacts(player);
    }

    private void checkCollisions() {
        for (Skeleton s : skeletons) {
            s.attacks(player);
            player.attacks(s);
        }
        if (player instanceof Mage){
            player.attacks(player);
        }

    }

    //Temporary player settings
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
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


    private void backgroundMusic(){
        try {
        String filePath= "src/Sounds/NeonDrops.wav";
        AudioInputStream audio= AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        try{

            Clip clip= AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            FloatControl gainControl= (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float volumeReduction = -5.0f;
            gainControl.setValue(volumeReduction);

            clip.start();
        } catch (Exception e){}
        } catch (Exception e){}
    }

    public ArrayList<Skeleton> getSkeletons() {
        return skeletons;
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