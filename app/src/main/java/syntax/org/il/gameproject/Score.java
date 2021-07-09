package syntax.org.il.gameproject;

public class Score implements Comparable<Score> {
    private String name;
    private int score;
    private int trophyId;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public int getTrophyId() {
        return trophyId;
    }

    public void setTrophyId(int trophyId) {
        this.trophyId = trophyId;
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

    @Override
    public int compareTo(Score o) {
        return o.score - this.score;
    }

}
