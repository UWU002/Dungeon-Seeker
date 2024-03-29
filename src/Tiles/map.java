package Tiles;

import Main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class map {
    private GamePanel gp;
    private int[][] tutorialZone, level1, level2;
    private int[][] tutorialZoneTP, level1TP, level2TP;
    private Rectangle wallHitbox;
    private ArrayList<Rectangle> hitboxes = new ArrayList<>();
    private int level = 0;

    public map(GamePanel gp) {
        this.gp = gp;
        generateMap();
    }

    private void setTpPositions(){
        tutorialZoneTP= new int [][]{};
        level1TP= new int [][]{};
        level2TP= new int [][]{};
    }

    private void setWallPositions() {
        tutorialZone = new int[][]{
                {2, 2}, {3, 2}, {4, 2}, {5, 2}, {6, 2}, {7, 2}, {8, 2}, {9, 2}, {10, 2}, {11, 2}, {12, 2},
                {13, 2}, {14, 2}, {15, 2}, {16, 2}, {17, 2}, {18, 2}, {19, 2}, {20, 2}, {21, 2}, {22, 2},
                {23, 2}, {24, 2}, {25, 2}, {26, 2}, {27, 2}, {28, 2}, {29, 2}, {30, 2}, {31, 2}, {32, 2},
                {33, 2}, {34, 2}, {35, 2}, {36, 2}, {37, 2}, {38, 2}, {39, 2}, {40, 2}, {41, 2}, {42, 2},
                {43, 2}, {44, 2}, {45, 2}, {46, 2}, {47, 2}, {48, 2}, {49, 2}, {50, 2}, {51, 2}, {52, 2},
                {53, 2}, {54, 2}, {55, 2}, {56, 2}, {57, 2}, {58, 2}, {59, 2}, {60, 2}, {61, 2}, {62, 2},
                {63, 2}, {63, 3}, {63, 4}, {63, 5}, {63, 6}, {63, 7}, {63, 8}, {63, 9}, {63, 10}, {63, 11},
                {63, 12}, {63, 13}, {63, 14}, {63, 15}, {63, 16}, {63, 17}, {63, 18}, {63, 19}, {63, 20},
                {63, 21}, {63, 22}, {63, 23}, {63, 24}, {64, 24}, {65, 24}, {64, 31}, {65, 31},
                {63, 31}, {63, 32}, {63, 33}, {62, 33}, {61, 33}, {60, 33}, {59, 33}, {58, 33},
                {57, 33}, {56, 33}, {55, 33}, {54, 33}, {53, 33}, {52, 33}, {51, 33}, {50, 33}, {49, 33},
                {48, 33}, {47, 33}, {46, 33}, {45, 33}, {44, 33}, {43, 33}, {42, 33}, {41, 33}, {40, 33},
                {39, 33}, {38, 33}, {37, 33}, {36, 33}, {35, 33}, {34, 33}, {33, 33}, {32, 33}, {31, 33},
                {30, 33}, {29, 33}, {28, 33}, {27, 33}, {26, 33}, {25, 33}, {24, 33}, {23, 33}, {22, 33},
                {21, 33}, {20, 33}, {19, 33}, {18, 33}, {17, 33}, {16, 33}, {15, 33}, {14, 33}, {13, 33},
                {12, 33}, {11, 33}, {10, 33}, {9, 33}, {8, 33}, {7, 33}, {6, 33}, {5, 33}, {4, 33}, {3, 33},
                {2, 33}, {2, 32}, {2, 31}, {2, 30}, {2, 29}, {2, 28}, {2, 27}, {2, 26}, {2, 25}, {2, 24},
                {2, 23}, {2, 22}, {2, 21}, {2, 20}, {2, 19}, {2, 18}, {2, 17}, {2, 16}, {2, 15}, {2, 14},
                {2, 13}, {2, 12}, {2, 11}, {2, 10}, {2, 9}, {2, 8}, {2, 7}, {2, 6}, {2, 5}, {2, 4}, {2, 3},
                {2, 11}, {3, 11}, {4, 11}, {5, 11}, {6, 11}, {7, 11}, {8, 11}, {9, 11}, {10, 11}, {11, 11}, {12, 11}, {13, 11}, {14, 11}, {15, 11}, {16, 11}, {17, 11}, {18, 11}, {19, 11}, {20, 11}, {21, 11}, {22, 11}, {23, 11}, {24, 11}, {25, 11}, {26, 11}, {27, 11}, {28, 11}, {29, 11}, {30, 11}, {31, 11}, {32, 11}, {33, 11}, {34, 11}, {35, 11}, {36, 11}, {37, 11}, {38, 11}, {39, 11}, {40, 11}, {41, 11}, {42, 11}, {43, 11}, {44, 11}, {45, 11}, {46, 11}, {47, 11}, {48, 11}, {49, 11}, {16, 22}, {17, 22}, {18, 22}, {19, 22}, {20, 22}, {21, 22}, {22, 22}, {23, 22}, {24, 22}, {25, 22}, {26, 22}, {27, 22}, {28, 22}, {29, 22}, {30, 22}, {31, 22}, {32, 22}, {33, 22}, {34, 22}, {35, 22}, {36, 22}, {37, 22}, {38, 22}, {39, 22}, {40, 22}, {41, 22}, {42, 22}, {43, 22}, {44, 22}, {45, 22}, {46, 22}, {47, 22}, {48, 22}, {49, 22}, {50, 22}, {51, 22}, {52, 22}, {53, 22}, {54, 22}, {55, 22}, {56, 22}, {57, 22}, {58, 22}, {59, 22}, {60, 22}, {61, 22}, {62, 22}, {63, 22}};
        level1 = new int[][]{};
        level2 = new int[][]{};
    }

    private void generateMap() {
        setWallPositions();
        for (int i = 0; i < gp.screenTileWidth * gp.scale; i++) {
            for (int j = 0; j < gp.screenTileHeight * gp.scale; j++) {
                if (isWall(i, j)) {
                    placeTile("src/images/dungeon/tile004.png", i, j);
                    placeWallHitbox(i, j);
                } else {
                    placeTile("src/images/dungeon/tile001.png", i, j);
                }
            }
        }
    }

    private boolean isWall(int x, int y) {
        if (level == 0) {
            for (int[] position : tutorialZone) {
                if (position[0] == x && position[1] == y) {
                    return true;
                }
            }
        } else if (level==1) {
            for (int[] position : level1) {
                if (position[0] == x && position[1] == y) {
                    return true;
                }
            }
        } else if (level==2) {
            for (int[] position : level2) {
                if (position[0] == x && position[1] == y) {
                    return true;
                }
            }
        }
        return false;
    }


    private void placeWallHitbox(int x, int y) {
        wallHitbox = new Rectangle(x * gp.originalTileSize, y * gp.originalTileSize, gp.originalTileSize, gp.originalTileSize);
        hitboxes.add(wallHitbox);
    }

    private void placeTile(String imagePath, int x, int y) {
        JLabel tile = new JLabel(new ImageIcon(imagePath));
        tile.setSize(gp.originalTileSize, gp.originalTileSize);
        tile.setLocation(x * gp.originalTileSize, y * gp.originalTileSize);
        gp.add(tile);
    }


    public ArrayList<Rectangle> getHitboxes() {
        return hitboxes;
    }
}
