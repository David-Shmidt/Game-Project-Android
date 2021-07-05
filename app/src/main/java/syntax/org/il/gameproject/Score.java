package syntax.org.il.gameproject;

public class Score {
    private String name;
    private int score;
    private int trophyId;

    public int getTrophyId() {
        return trophyId;
    }

    public void setTrophyId(int trophyId) {
        this.trophyId = trophyId;
    }

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
