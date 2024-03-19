package Main;

import javax.swing.*;


public class MainS{
    public static void main(String[] args) {
        JFrame frame = new JFrame("MainS");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        GamePanel gamePanel= new GamePanel();
        frame.setContentPane(gamePanel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new GamePanel.frameWindowListener(frame));

        gamePanel.startGame();
    }
}
