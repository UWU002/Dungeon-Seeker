package Main;

import Characters.*;
import Items.Item;
import Items.Mitre;
import Items.Potion;
import Items.Sword;
import Menus.GameHud;
import Tiles.Map;

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
    Random r = new Random();
    //Screen Size
    public final int originalTileSize = 16, scale = 3;
    public final int tileSize = originalTileSize * scale; //48 x 48 screen
    public final int screenTileWidth = 22, screenTileHeight = 12;
    private final int boardWidth = tileSize * screenTileWidth, boardHeight = tileSize * screenTileHeight; // 1056px x 576px
    //Item Tutorial
    private Rectangle swordHelp, potionHelp, mitreHelp, tutorialHelpMove;
    private JLabel tutorial;

    // Objects Arrays
    private ArrayList<Skeleton> skeletons = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();

    //Game Necessities
    private Timer nextLevelTimer;
    private boolean exited = false;
    PlayerInputs pI = new PlayerInputs();
    private Timer gameTimer;
    Map map = new Map(this);
    GameHud gh = new GameHud(this);
    public Entity player = new Entity(this, pI, map, gh);

    //Tutorial Item creation
    Sword tutorialSword = new Sword(this, gh, 200, 50);
    Potion tutorialPotion = new Potion(this, gh, 400, 50);
    Mitre tutorialMitre = new Mitre(this, gh, 600, 50);


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
        createNextLevelTimer();
        loadTutorialTextBoxes();
        createTutorialLabel();
        backgroundMusic();
        addTutorialItemsToArray();
    }

    private void createNextLevelTimer() {
        nextLevelTimer = new Timer(100, e -> {
            for (Skeleton s : skeletons) {
                s.removeFromGame();
            }
            for (Item i : items) {
                i.removeFromGame();
            }
            map.nextLevel();
            nextLevelTimer.stop();
        });
    }

    private void addTutorialItemsToArray() {
        items.add(tutorialSword);
        items.add(tutorialPotion);
        items.add(tutorialMitre);
    }

    private void loadItems() {
        int rndm;
        for (int i = 0; i < map.getItemSpawns().length; i++) {
            int x = map.getItemSpawns()[i][0] * originalTileSize;
            int y = map.getItemSpawns()[i][1] * originalTileSize;
            if (r.nextInt(10) > 7) {
                rndm = r.nextInt(3);
                switch (rndm) {
                    case 0:
                        items.add(new Sword(this, gh, x, y));
                        break;
                    case 1:
                        items.add(new Potion(this, gh, x, y));
                        break;
                    case 2:
                        items.add(new Mitre(this, gh, x, y));
                        break;
                }
            }
        }
    }

    private void loadTutorialTextBoxes() {
        swordHelp = new Rectangle(200, 50, 64, 120);
        potionHelp = new Rectangle(400, 50, 64, 120);
        mitreHelp = new Rectangle(600, 50, 64, 120);
        tutorialHelpMove = new Rectangle(700, 50, 64, 120);
    }

    private void loadSkeletons() {
        for (int i = 0; i < map.getSkeletons().length; i++) {
            int x = map.getSkeletons()[i][0] * originalTileSize;
            int y = map.getSkeletons()[i][1] * originalTileSize;
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
        for (Item i : items) {
            this.setComponentZOrder(i.getJlabel(), 0);
        }
        this.setComponentZOrder(tutorialSword.getJlabel(), 0);
        this.setComponentZOrder(tutorialPotion.getJlabel(), 0);
        this.setComponentZOrder(tutorialMitre.getJlabel(), 0);
        this.setComponentZOrder(tutorial, 0);
    }


    public void startGame() {
        int delay = 1000 / 60; //60 fps
        gameTimer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();
                repaint();
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
        checkForDeath();
    }

    private void checkCollisions() {
        entityAtacks();
        itemCollisions();
        selfInflictedDamage();
        enterTpTiles();
        enterExitTile();
    }

    private void enterExitTile() {
        for (Rectangle r : map.getExitBlocks()) {
            if (player.getHitbox().intersects(r) && !exited) {
                exited = true;
                /// End game
            }
        }
    }

    private void checkForDeath() {
        if (player.getHealth() <= 0) {
            JLabel death = new JLabel();
            death.setSize(500, 500);
            death.setLocation(280, 10);
            ImageIcon deathIcon = new ImageIcon("src/images/MenuItems/GameOver.png");
            death.setIcon(deathIcon);
            this.add(death);
            this.setComponentZOrder(death, 0);
            //Restart Game Here
            gameTimer.stop();
            cleanup();
            SwingUtilities.invokeLater(() -> {
                JFrame currentFrame = (JFrame) SwingUtilities.getRoot(this);
                currentFrame.dispose();
                openLeaderBoard();
            });
        }
    }

    private void cleanup() {
        clip.stop();
        clip.close();
        for (Skeleton s : skeletons){
            s.setDead(true);
        }
        removeKeyListener(pI);
    }

    private void openLeaderBoard() {
        //Here I will open the leaderboard instead of the Main Menu
//        JFrame menuFrame = new JFrame("Dungeon Seeker");
//        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        menuFrame.setResizable(false);
//        MenuScreen menuScreen = new MenuScreen();
//        menuFrame.setContentPane(menuScreen);
//        menuFrame.pack();
//        menuFrame.setVisible(true);
//        menuFrame.setLocationRelativeTo(null);
    }

    private void enterTpTiles() {
        for (Rectangle r : map.getTps()) {
            if (player.getHitbox().intersects(r)) {
                nextLevelTimer.start();
            }
        }
    }

    private void entityAtacks() {
        for (Skeleton s : skeletons) {
            s.attacks(player);
            player.attacks(s);
        }
    }

    private void selfInflictedDamage() {
        if (player instanceof Mage) {
            player.attacks(player);
        }
    }

    private void itemCollisions() {
        for (Item i : items) {
            i.contacts(player);
        }
        if (map.getLevel() == 0) {
            itemTutorials();
        }
    }

    private void itemTutorials() {
        tutorialSword.contacts(player);
        tutorialPotion.contacts(player);
        tutorialMitre.contacts(player);

        if (swordHelp.intersects(player.getHitbox())) {
            tutorial.setText("The sword will increase the Player's damage by 10");
            tutorial.setLocation(200, 80);
        } else if (potionHelp.intersects(player.getHitbox())) {
            tutorial.setText("The potion will increase the Player's health by 10");
            tutorial.setLocation(350, 80);
        } else if (mitreHelp.intersects(player.getHitbox())) {
            tutorial.setText("The mitre will set the Player's health to the original number");
            tutorial.setLocation(550, 80);
        } else if (tutorialHelpMove.intersects(player.getHitbox())) {
            this.remove(tutorial);
        }
    }

    private void createTutorialLabel() {
        tutorial = new JLabel();
        tutorial.setLocation(200, 80);
        tutorial.setSize(500, 50);
        tutorial.setForeground(Color.WHITE);
        tutorial.setBackground(Color.GRAY);
        this.add(tutorial);
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
                    "If you dont finish the run it will be lost, Are you Sure?",
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

    private Clip clip;
    private void backgroundMusic() {
        try {
            String filePath = "src/Sounds/NeonDrops.wav";
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            try {
                clip = AudioSystem.getClip();
                clip.open(audio);
                clip.loop(Clip.LOOP_CONTINUOUSLY);

                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float volumeReduction = -5.0f;
                gainControl.setValue(volumeReduction);

                clip.start();
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }

    public ArrayList<Skeleton> getSkeletons() {
        return skeletons;
    }
}