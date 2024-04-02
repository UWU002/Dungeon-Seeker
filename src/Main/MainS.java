package Main;

import javax.swing.*;

public class MainS {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Dungeon Seeker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        MenuScreen menuScreen = new MenuScreen();
        frame.setContentPane(menuScreen);
        frame.setSize(menuScreen.getPreferredSize());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new GamePanel.frameWindowListener(frame));
    }
}


//public class MainS{
//    public static void main(String[] args) {

//    }
//}
