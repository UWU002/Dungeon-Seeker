package Main;

import javax.swing.*;
import java.awt.*;


public class MenuScreen extends JPanel {
    private JPanel buttonPanel;
    private JPanel contentPanel;
    private JTextField playerNameField;


    public MenuScreen() {
        setPreferredSize(new Dimension(1056, 576));
        setBackground(Color.BLACK);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(190,50 , 0, 50));

        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(Color.BLACK);

        JLabel nameLabel = new JLabel("Player Name:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(nameLabel);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        playerNameField = new JTextField();
        playerNameField.setMaximumSize(new Dimension(200, 30));
        playerNameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(playerNameField);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        createButtons();


        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(buttonPanel, BorderLayout.EAST);


        add(leftPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void createButtons() {
        String[] titles = {"Warrior", "  Mage  ", "  Priest  "};

        for (int i = 0; i < titles.length; i++) {
            JButton button = getButton(titles[i]);
            buttonPanel.add(button);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));


            final int  finalI = i;
            JPanel panel = getContentPanel(titles[finalI]);
            contentPanel.add(panel, titles[finalI]);
            button.addActionListener(e -> ((CardLayout) contentPanel.getLayout()).show(contentPanel, titles[finalI]));
        }
    }

    private static JButton getButton(String title) {
        JButton button = new JButton(title);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private JPanel getContentPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);


        JLabel text;
        switch (title) {
            case "Warrior":
                text= new JLabel("Attack [50]  Health: [200]  Speed: [20]");
                text.setBounds(200, 200, 700, 50);
                text.setForeground(Color.white);
                panel.add(text);
                break;
            case "  Mage  ":
                text= new JLabel("Attack [100]  Health: [100]  Speed: [40");
                text.setBounds(200, 200, 700, 50);
                text.setForeground(Color.white);
                panel.add(text);
                break;
            case "  Priest  ":
                text= new JLabel("Attack [75]  Health: [150]  Speed: [30]");
                text.setBounds(200, 200, 700, 50);
                text.setForeground(Color.white);
                panel.add(text);
                break;
        }
        JButton confirm = new JButton("Confirm");
        confirm.setForeground(Color.WHITE);
        confirm.setBackground(Color.DARK_GRAY);
        confirm.setFocusPainted(false);
        confirm.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirm.setBounds(310, 450, 100, 35);
        panel.add(confirm);


        confirm.addActionListener(e -> {
            JFrame frame = new JFrame("MainS");
            String playerName = playerNameField.getText().trim();
            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a player name", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            frame.setLayout(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            GamePanel gamePanel= new GamePanel();
            gamePanel.characterSelection(title.trim());
            gamePanel.setPlayerName(playerName);
            gamePanel.startGame();
            frame.setContentPane(gamePanel);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            frame.addWindowListener(new GamePanel.frameWindowListener(frame));
            JFrame f= (JFrame) SwingUtilities.getRoot(this);
            f.dispose();
        });

        return panel;
    }
}
