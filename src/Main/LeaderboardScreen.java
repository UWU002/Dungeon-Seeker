package Main;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.TreeMap;

public class LeaderboardScreen extends JPanel {
   private TreeMap<LeaderboardKey, LeaderboardEntries> leaderboardMap = new TreeMap<>(LeaderboardKey.getComparator());

    public LeaderboardScreen() {
        setPreferredSize(new Dimension(1056, 576));
        setBackground(Color.BLACK);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Leaderboard");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        add(titleLabel, BorderLayout.NORTH);


        JPanel leaderboardContent = new JPanel();
        leaderboardContent.setBackground(Color.BLACK);
        leaderboardContent.setLayout(new BoxLayout(leaderboardContent, BoxLayout.Y_AXIS));


        getLeaderboardData();


        int rank = 1;
        for (LeaderboardEntries entry : leaderboardMap.values()) {
            if (rank > 5) {
                break;
            }
            String entryText = entry.toString();
            JLabel entryLabel = new JLabel(entryText);
            entryLabel.setForeground(Color.WHITE);
            entryLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            entryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            leaderboardContent.add(entryLabel);
            leaderboardContent.add(Box.createRigidArea(new Dimension(0, 10)));
            rank++;
        }

        add(leaderboardContent, BorderLayout.CENTER);
    }

    private void getLeaderboardData() {
        String db_url = "jdbc:mysql://localhost:3306/DungeonSeeker";
        String user = "root";
        String paswd = "Mysql";
        String query = "SELECT * FROM leaderboard";
        try {
            Connection con = DriverManager.getConnection(db_url, user, paswd);
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String playerType = rs.getString("playerType");
                int coins = rs.getInt("coins");
                int hp = rs.getInt("hp");
                int time = rs.getInt("time");
                int totalScore = rs.getInt("totalScore");
                LeaderboardEntries entry = new LeaderboardEntries(name, playerType, coins, hp, time, totalScore);
                LeaderboardKey key = new LeaderboardKey(totalScore, time, id);
                leaderboardMap.put(key, entry);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Conexion Failed2");
        }
    }
}

