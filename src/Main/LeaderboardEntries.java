package Main;

public class LeaderboardEntries {
    private int coins,hp,time,totalScore;
    private String name, playerType;


    public LeaderboardEntries(String name, String playerType, int coins, int hp, int time, int totalScore) {
        this.coins = coins;
        this.hp = hp;
        this.time = time;
        this.totalScore = totalScore;
        this.name = name;
        this.playerType = playerType;
    }




    @Override
    public String toString() {
        return name +
                "  ||  " + playerType +
                "  ||  Coins: " + coins +
                "  ||  Remaining HP: " + hp +
                "  ||  Time" + time +
                "  ||  Total Score: " + totalScore;
    }
}
