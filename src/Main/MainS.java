package Main;

import javax.swing.*;

public class MainS {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Dungeon Seeker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        MenuScreen menuScreen = new MenuScreen(); // Initialize MenuScreen
        frame.setContentPane(menuScreen); // Set MenuScreen as the initial content pane

        // Set the frame size directly instead of using pack()
        frame.setSize(menuScreen.getPreferredSize());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Center the window

        // Assuming GamePanel.frameWindowListener is a static inner class
        frame.addWindowListener(new GamePanel.frameWindowListener(frame));
    }
}


//public class MainS{
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("MainS");
//        frame.setLayout(null);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setResizable(false);
//        GamePanel gamePanel= new GamePanel();
//        frame.setContentPane(gamePanel);
//        frame.pack();
//        frame.setVisible(true);
//        frame.setLocationRelativeTo(null);
//        frame.addWindowListener(new GamePanel.frameWindowListener(frame));
//
//        gamePanel.startGame();
//    }
//}
