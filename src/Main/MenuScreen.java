package Main;

import javax.swing.*;
import java.awt.*;


public class MenuScreen extends JPanel {
    private JPanel buttonPanel;
    private JPanel contentPanel;

    public MenuScreen() {
        setPreferredSize(new Dimension(1056, 576));
        setBackground(Color.BLACK);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.BLACK); // Set the button panel background to black
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(190,50 , 0, 50));

        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(Color.BLACK); // Set the content panel background to black


        createButtons();

        // Create a wrapper panel for buttonPanel to align it correctly
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(buttonPanel, BorderLayout.EAST);

        // Add the buttonPanel and contentPanel to the main panel
        add(leftPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void createButtons() {
        // Define titles for buttons to make code more readable and maintainable
        String[] titles = {"Warrior", "  Mage  ", "  Priest  "};

        for (int i = 0; i < titles.length; i++) {
            JButton button = getButton(titles[i]);
            buttonPanel.add(button);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between buttons


            final int  finalI = i;
            // Add a corresponding content panel to the contentPanel
            JPanel panel = getContentPanel(titles[finalI]);
            contentPanel.add(panel, titles[finalI]);
            button.addActionListener(e -> ((CardLayout) contentPanel.getLayout()).show(contentPanel, titles[finalI]));
        }
    }

    private static JButton getButton(String title) {
        JButton button = new JButton(title);
        button.setForeground(Color.WHITE); // Text color
        button.setBackground(Color.DARK_GRAY); // Button background
        button.setFocusPainted(false); // Remove focus border
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private JPanel getContentPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Vertically aligns components
        panel.setBackground(Color.BLACK); // Sets background color


        // Example content for each class; customize as needed
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

        confirm.addActionListener(e -> {});

        return panel;
    }
}
