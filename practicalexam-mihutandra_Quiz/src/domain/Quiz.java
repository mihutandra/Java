package domain;

public class Quiz {
    int id;
    String text;
    String answer;
    int score;
    String userAnswer;

    public Quiz(int id, String text, String answer, int score, String userAnswer) {
        this.id = id;
        this.text = text;
        this.answer = answer;
        this.score = score;
        this.userAnswer = userAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", answer='" + answer + '\'' +
                ", score=" + score +
                ", userAnswer='" + userAnswer + '\'' +
                '}';
    }
}
