package repository;

import domain.Quiz;
import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Repository {
    private static final String JDBC_URL = "jdbc:sqlite:data/test_db.db";

    private static Connection conn = null;

    protected ArrayList<Quiz> data = new ArrayList<>();

    public static Connection getConnection() {
        if (conn == null)
            openConnection();
        return conn;
    }

    private static void openConnection() {
        try
        {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(JDBC_URL);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void closeConnection() {
        try
        {
            conn.close();
            conn = null;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //-------------------

    public void addData() {
        try
        {
            openConnection();
            String selectString = "SELECT * FROM " + "Quiz" + ";";
            try (PreparedStatement ps = conn.prepareStatement(selectString))
            {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next())
                {
                    int id = Integer.parseInt(resultSet.getString("id"));
                    String text = resultSet.getString("text");
                    String answer = resultSet.getString("answer");
                    int score = Integer.parseInt(resultSet.getString("score"));
                    String userAnswer = resultSet.getString("userAnswer");

                    Quiz quiz = new Quiz(id,text,answer,score,userAnswer);
                    if(!data.contains(quiz))
                        data.add(quiz);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            closeConnection();
        }
    }

    public ArrayList<Quiz> getData(){
        return data;
    }

    public List<Quiz> searchQuestion(String searchText, int minScore) {
        return data.stream()
                .filter(question -> question.getText().toLowerCase().contains(searchText.toLowerCase())) // Case-insensitive search
                .filter(question -> question.getScore() >= minScore)
                .collect(Collectors.toList());
    }
    public void updateQuiz(Quiz updateQuiz) {
        try {
            openConnection();
            conn.setAutoCommit(false);

            String updateString = "UPDATE Quiz SET text = ?, answer = ?, score = ?, userAnswer = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateString)) {
                ps.setString(1, updateQuiz.getText());
                ps.setString(2, updateQuiz.getAnswer());
                ps.setInt(3, updateQuiz.getScore());
                ps.setString(4, updateQuiz.getUserAnswer());
                ps.setInt(5, updateQuiz.getId());
                ps.executeUpdate();
            }

            conn.commit();

            // Update the data list with the modified quiz
            for (Quiz quiz : data) {
                if (quiz.getId() == updateQuiz.getId()) {
                    quiz.setAnswer(updateQuiz.getAnswer()); // update answer
                    break;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }



}
