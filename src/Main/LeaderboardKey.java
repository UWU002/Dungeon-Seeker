package Main;

import java.util.Comparator;

public class LeaderboardKey {
    private int totalScore;
    private int time;
    private int id;

    public LeaderboardKey(int totalScore, int time, int id) {
        this.totalScore = totalScore;
        this.time = time;
        this.id = id;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getTime() {
        return time;
    }

    public int getId() {
        return id;
    }


    public static Comparator<LeaderboardKey> getComparator() {
        return new Comparator<LeaderboardKey>() {
            @Override
            public int compare(LeaderboardKey o1, LeaderboardKey o2) {
                if (o1.totalScore != o2.totalScore) {
                    return Integer.compare(o2.totalScore, o1.totalScore);
                } else if (o1.time != o2.time) {
                    return Integer.compare(o1.time, o2.time);
                } else {
                    return Integer.compare(o1.id, o2.id);
                }
            }
        };
    }
}
