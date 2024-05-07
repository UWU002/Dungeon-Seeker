package Tiles;

import Main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Map {
    private GamePanel gp;
    private JLabel exit;
    private int[][] tutorialZoneWalls, level1Walls, level2Walls;
    private int[][] tutorialZoneTP, level1TP, level2TP;
    private int[][] tutorialZoneMC, level1MC, level2MC;
    private int[][] tutorialZoneMonsters, level1Monsters, level2Monsters;
    private int[][] tutorialSetItemSpawns, level1SetItemSpawns, level2SetItemSpawns;
    private Rectangle wallHitbox, monsterContainer, tp;
    private ArrayList<JLabel> tileLabels = new ArrayList<>();
    private ArrayList<Rectangle> wallHitboxes = new ArrayList<>();
    private ArrayList<Rectangle> monsterContainers = new ArrayList<>();
    private ArrayList<Rectangle> tps = new ArrayList<>();
    private ArrayList<Rectangle> exitBlocks= new ArrayList<>();
    private int level = 0, lastLevel=1;

    public Map(GamePanel gp) {
        this.gp = gp;
        generateMap();
    }


    private void setItemSpawns() {
        tutorialSetItemSpawns = new int[][]{
                {13, 30}, {40, 17}, {22, 14}, {39, 30}, {3, 25}, {50, 15}, {9, 20}, {25, 13}, {25, 19},
                {2, 23}, {46, 26}, {29, 22}, {56, 15}, {46, 19}, {24, 22}, {24, 28}, {55, 12}, {18, 27},
                {5, 14}, {27, 20}, {44, 15}, {27, 29}, {48, 18}
        };
        level1SetItemSpawns = new int[][]{};
        level2SetItemSpawns = new int[][]{};
    }

    private void setMonsterPositions() {
        tutorialZoneMonsters = new int[][]{
                {55, 15}, {8, 12},
                {31, 23}, {8, 25}
        };
        level1Monsters = new int[][]{

        };
        level2Monsters = new int[][]{};
    }

    private void setMonsterContainersPositions() {
        tutorialZoneMC = new int[][]{
                {63, 25}, {63, 26}, {63, 27}, {63, 28}, {63, 29}, {63, 30},
                {50, 11}, {51, 11}, {52, 11}, {53, 11}, {54, 11}, {55, 11}, {56, 11}, {57, 11}, {58, 11}, {59, 11}, {60, 11}, {62, 11}, {61, 11},
                {3, 22}, {4, 22}, {5, 22}, {6, 22}, {7, 22}, {8, 22}, {9, 22}, {10, 22}, {11, 22}, {12, 22}, {13, 22}, {14, 22}, {15, 22}
        };
        level1MC = new int[][]{
                {17, 5}, {17, 6}, {50, 5}, {50, 6}, {41, 24}, {42, 24}, {30, 18}, {31, 18}
        };
        level2MC = new int[][]{};
    }

    private void setTpPositions() {
        tutorialZoneTP = new int[][]{
                {3, 3}, {65, 25}, {65, 26}, {65, 27}, {65, 28}, {65, 29}, {65, 30}
        };
        level1TP = new int[][]{};
        level2TP = new int[][]{};
    }

    private void setWallPositions() {
        tutorialZoneWalls = new int[][]{
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
        level1Walls = new int[][]{
                {2, 2}, {3, 2}, {4, 2}, {5, 2}, {6, 2}, {7, 2}, {8, 2}, {9, 2}, {10, 2}, {11, 2}, {12, 2},
                {13, 2}, {14, 2}, {15, 2}, {16, 2}, {17, 2}, {18, 2}, {19, 2}, {20, 2}, {21, 2}, {22, 2},
                {23, 2}, {24, 2}, {25, 2}, {26, 2}, {27, 2}, {28, 2}, {29, 2}, {30, 2}, {31, 2}, {32, 2},
                {33, 2}, {34, 2}, {35, 2}, {36, 2}, {37, 2}, {38, 2}, {39, 2}, {40, 2}, {41, 2}, {42, 2},
                {43, 2}, {44, 2}, {45, 2}, {46, 2}, {47, 2}, {48, 2}, {49, 2}, {50, 2}, {51, 2}, {52, 2},
                {53, 2}, {54, 2}, {55, 2}, {56, 2}, {57, 2}, {58, 2}, {59, 2}, {60, 2}, {61, 2}, {62, 2},
                {63, 2}, {63, 3}, {63, 4}, {63, 5}, {63, 6}, {63, 7}, {63, 8}, {63, 9}, {63, 10}, {63, 11},
                {63, 12}, {63, 13}, {63, 14}, {63, 15}, {63, 16}, {63, 17}, {63, 18}, {63, 19}, {63, 20},
                {63, 21}, {63, 22}, {63, 23}, {63, 24}, {63, 25}, {63, 26}, {63, 27}, {63, 28}, {63, 29}, {63, 30},
                {63, 31}, {63, 31}, {63, 32}, {63, 33}, {62, 33}, {61, 33}, {60, 33}, {59, 33}, {58, 33},
                {57, 33}, {56, 33}, {55, 33}, {54, 33}, {53, 33}, {52, 33}, {51, 33}, {50, 33}, {49, 33},
                {48, 33}, {47, 33}, {46, 33}, {45, 33}, {44, 33}, {43, 33}, {42, 33}, {41, 33}, {40, 33},
                {39, 33}, {38, 33}, {37, 33}, {36, 33}, {35, 33}, {34, 33}, {33, 33}, {32, 33}, {31, 33},
                {30, 33}, {29, 33}, {28, 33}, {27, 33}, {26, 33}, {25, 33}, {24, 33}, {23, 33}, {22, 33},
                {21, 33}, {20, 33}, {19, 33}, {18, 33}, {17, 33}, {16, 33}, {15, 33}, {14, 33}, {13, 33},
                {12, 33}, {11, 33}, {10, 33}, {9, 33}, {8, 33}, {7, 33}, {6, 33}, {5, 33}, {4, 33}, {3, 33},
                {2, 33}, {2, 32}, {2, 31}, {2, 30}, {2, 29}, {2, 28}, {2, 27}, {2, 26}, {2, 25}, {2, 24},
                {2, 23}, {2, 22}, {2, 21}, {2, 20}, {2, 19}, {2, 18}, {2, 17}, {2, 16}, {2, 15}, {2, 14},
                {2, 13}, {2, 12}, {2, 11}, {2, 10}, {2, 9}, {2, 8}, {2, 7}, {2, 6}, {2, 5}, {2, 4}, {2, 3},


                {29, 13},{30, 13}, {31, 13}, {32, 13}, {33, 13}, {34, 13},
                {29, 14}, {29, 15}, {29, 16}, {29, 17}, {29, 18},
                {34, 14}, {34, 15}, {34, 16}, {34, 17}, {34, 18},
                {29, 19}, {30, 19}, {33, 19}, {34, 19},
                {18, 19}, {19, 19}, {20, 19}, {21, 19}, {22, 19}, {23, 19}, {24, 19}, {25, 19}, {26, 19}, {27, 19}, {28, 19}, {35, 19}, {36, 19}, {37, 19}, {38, 19}, {39, 19}, {40, 19}, {41, 19}, {42, 19}, {43, 19}, {44, 19},
                {18, 18}, {18, 17}, {18, 16}, {18, 15}, {18, 14}, {18, 13}, {18, 12}, {18, 11}, {18, 10}, {18, 9}, {18, 8}, {18, 4}, {18, 3},

                {12, 10}, {12, 11}, {11, 10}, {10, 10}, {9, 10}, {8, 10}, {7, 10}, {6, 10}, {5, 10}, {4, 10}, {3, 10}, {12, 12}, {12, 17}, {12, 16}, {12, 15}, {12, 14}, {12, 13}, {12, 12}, {12, 18}, {12, 19}, {12, 20}, {12, 21}, {12, 22}, {12, 23}, {12, 24},
                {19, 24}, {20, 24}, {21, 24}, {22, 24}, {23, 24}, {24, 24}, {25, 24}, {26, 24}, {27, 24}, {28, 24}, {29, 24}, {30, 24}, {31, 24}, {32, 24}, {32, 24}, {33, 24}, {34, 24}, {35, 24}, {36, 24}, {37, 24}, {38, 24}, {39, 24}, {40, 24}, {44, 24}, {13, 24}, {14, 24}, {15, 24}, {16, 24}, {17, 24}, {18, 24}, {44, 23}, {44, 22}, {44, 21}, {44, 20},
                {19, 10}, {20, 10}, {21, 10}, {22, 10}, {23, 10}, {24, 10}, {25, 10}, {26, 10}, {27, 10}, {28, 10}, {29, 10}, {30, 10}, {31, 10}, {32, 10}, {32, 10}, {33, 10}, {34, 10}, {35, 10}, {36, 10}, {37, 10}, {38, 10}, {39, 10}, {40, 10}, {41, 10}, {42, 10}, {43, 10}, {44, 10}, {45, 10}, {46, 10}, {47, 10}, {48, 10}, {49, 10}, {50, 10}, {51, 10}, {52, 14}, {53, 14}, {54, 14}, {55, 14}, {56, 14}, {57, 14}, {58, 14}, {59, 14}, {60, 14}, {61, 14}, {62, 14},
                {44, 11}, {44, 12}, {44, 13}, {44, 14}, {44, 15}, {44, 16}, {44, 17}, {44, 18},
                {51, 3}, {51, 4}, {51, 8}, {51, 9}, {51, 11}, {51, 12}, {51, 13}, {51, 14},

        };
        level2Walls = new int[][]{};
    }

    //{x,y}{w,h}
    private void generateMap() {
        setWallPositions();
        setTpPositions();
        setMonsterContainersPositions();
        setMonsterPositions();
        setItemSpawns();
        for (int i = 0; i < gp.screenTileWidth * gp.scale; i++) {
            for (int j = 0; j < gp.screenTileHeight * gp.scale; j++) {
                if (isWall(i, j)) {
                    placeTile("src/images/dungeon/tile004.png", i, j);
                    placeWallHitbox(i, j);
                } else {
                    placeTile("src/images/dungeon/tile001.png", i, j);
                }
                if (isTp(i, j)) {
                    placeTP(i, j);
                }
                if (isMC(i, j)) {
                    placeMonsterContainer(i, j);
                }
            }
        }
        if (level==lastLevel){
            createExit();
        }
    }


    // Exit locations: {30,16},{31,16},{32,16},{33,16},
    private void createExit() {
        JLabel exit= new JLabel();
        ImageIcon exitIcon= new ImageIcon("src/images/MenuItems/exit.png");
        exit.setSize(64,64);
        exit.setIcon(exitIcon);
        exit.setLocation(481,236);
        gp.add(exit);
        gp.setComponentZOrder(exit, 0);
        for (int i = 30; i < 33; i++) {
            Rectangle exitHitbox= new Rectangle(i*gp.originalTileSize,16*gp.originalTileSize,16 ,16 );
            exitBlocks.add(exitHitbox);
        }
    }

    private boolean checkPosition(int x, int y, int[][] positions) {
        for (int[] position : positions) {
            if (position[0] == x && position[1] == y) {
                return true;
            }
        }
        return false;
    }

    private boolean isMC(int x, int y) {
        switch (level) {
            case 0:
                return checkPosition(x, y, tutorialZoneMC);
            case 1:
                return checkPosition(x, y, level1MC);
            case 2:
                return checkPosition(x, y, level2MC);
            default:
                return false;
        }
    }

    private boolean isTp(int x, int y) {
        switch (level) {
            case 0:
                return checkPosition(x, y, tutorialZoneTP);
            case 1:
                return checkPosition(x, y, level1TP);
            case 2:
                return checkPosition(x, y, level2TP);
            default:
                return false;
        }
    }

    private boolean isWall(int x, int y) {
        switch (level) {
            case 0:
                return checkPosition(x, y, tutorialZoneWalls);
            case 1:
                return checkPosition(x, y, level1Walls);
            case 2:
                return checkPosition(x, y, level2Walls);
            default:
                return false;
        }
    }


    private void placeWallHitbox(int x, int y) {
        wallHitbox = new Rectangle(x * gp.originalTileSize, y * gp.originalTileSize, gp.originalTileSize, gp.originalTileSize);
        wallHitboxes.add(wallHitbox);
    }

    private void placeMonsterContainer(int x, int y) {
        monsterContainer = new Rectangle(x * gp.originalTileSize, y * gp.originalTileSize, gp.originalTileSize, gp.originalTileSize);
        monsterContainers.add(monsterContainer);
    }

    private void placeTP(int x, int y) {
        tp = new Rectangle(x * gp.originalTileSize, y * gp.originalTileSize, gp.originalTileSize, gp.originalTileSize);
        tps.add(tp);
    }

    private void placeTile(String imagePath, int x, int y) {
        JLabel tile = new JLabel(new ImageIcon(imagePath));
        tile.setSize(gp.originalTileSize, gp.originalTileSize);
        tile.setLocation(x * gp.originalTileSize, y * gp.originalTileSize);
        gp.add(tile);
        tileLabels.add(tile);
    }

    public ArrayList<Rectangle> getExitBlocks() {
        return exitBlocks;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<Rectangle> getWallHitboxes() {
        return wallHitboxes;
    }

    public ArrayList<Rectangle> getMonsterContainers() {
        return monsterContainers;
    }

    public ArrayList<Rectangle> getTps() {
        return tps;
    }

    public JLabel getExit() {
        return exit;
    }

    public int[][] getItemSpawns() {
        switch (level) {
            case 0:
                return tutorialSetItemSpawns;
            case 1:
                return level1SetItemSpawns;
            case 2:
                return level2SetItemSpawns;
        }
        return null;
    }

    public int[][] getSkeletons() {
        switch (level) {
            case 0:
                return tutorialZoneMonsters;
            case 1:
                return level1Monsters;
            case 2:
                return level2Monsters;
        }
        return null;
    }


    public void nextLevel() {
        level++;
        switch (level) {
            case 1:
                gp.player.setX(50);
                gp.player.setY(50);
                break;
            case 2:
                break;
        }
        wallHitboxes.clear();
        monsterContainers.clear();
        tps.clear();
        for (JLabel j : tileLabels) {
            gp.remove(j);
        }
        tileLabels.clear();
        generateMap();
    }
}
