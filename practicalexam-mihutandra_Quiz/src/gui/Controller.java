package gui;
import javafx.fxml.FXML;
import domain.Quiz;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.Service;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.util.List;

public class Controller {
    private Service service;
    public Controller(Service service) {
        this.service = service;
    }

    @FXML
    private TextField answerTextField;

    @FXML
    private ListView<Quiz> quizListView;

    @FXML
    private TextField searchScoreField;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button showAllButton;
    @FXML
    private Button hintButton;
    @FXML
    private Button scoreButton;

    @FXML
    void answerToSelectedQuestion(MouseEvent event) {
        Quiz selectedItem = quizListView.getSelectionModel().getSelectedItem();
        String answer = answerTextField.getText();

        if (selectedItem != null) {
            selectedItem.setUserAnswer(answer);

            // Save the update in the database via the service
            service.updateQuiz(selectedItem);
            // Refresh the list view to reflect changes
            ObservableList<Quiz> quizzes = FXCollections.observableArrayList(service.getData());
            quizListView.setItems(quizzes);

        }
    }

    @FXML
    void showAllButtonClicked(MouseEvent event) {
        ObservableList<Quiz> quizzes = FXCollections.observableArrayList(service.getData());
        quizListView.setItems(quizzes);
    }

    @FXML
    void showFiltered(KeyEvent event) {
        int score = Integer.parseInt(searchScoreField.getText());
        String text = searchTextField.getText();
        ObservableList<Quiz> quizzes = FXCollections.observableArrayList(service.searchQuestion(text,score));
        quizListView.setItems(quizzes);
    }

    @FXML
    void provideHint(MouseEvent event) {
        Quiz selectedQuiz = quizListView.getSelectionModel().getSelectedItem();
        if (selectedQuiz != null) {
            // Display the first two letters of the correct answer as a hint
            String hint = selectedQuiz.getAnswer().substring(0, Math.min(2, selectedQuiz.getAnswer().length()));
            Alert hintAlert = new Alert(Alert.AlertType.INFORMATION);
            hintAlert.setTitle("Hint");
            hintAlert.setHeaderText(null);
            hintAlert.setContentText("Hint: " + hint);
            hintAlert.showAndWait();

            // Halve the score if a hint is provided
            selectedQuiz.setScore(selectedQuiz.getScore() / 2);

            // Save the updated score in the database via the service
            service.updateQuiz(selectedQuiz);

        }
    }
    @FXML
    void showscoreButton(MouseEvent event) {
        // Calculate total score and update GUI
        int totalscore = service.calculateAndDisplayTotalScore();
        Alert alert_Score = new Alert(Alert.AlertType.INFORMATION);
        alert_Score.setTitle("Score");
        alert_Score.setHeaderText(null);
        alert_Score.setContentText("Score: " + totalscore);
        alert_Score.showAndWait();
    }
    public void initialize(){
        service.addData();

    }

}