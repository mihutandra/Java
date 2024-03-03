package gui;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.Service;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;

import java.util.List;

public class Controller {
    private Service service;

    public Controller(Service service) {
        this.service = service;
    }
    @FXML
    private TextField activityField;

    @FXML
    private Button addActivityButton;

    @FXML
    private TextField dateTextF;

    @FXML
    private TextField filterTextField;

    @FXML
    private ListView<Fitness> fitnessListView;

    @FXML
    private TextField hoursSleepField;

    @FXML
    private TextField nrStepsField;

    @FXML
    private Button showAllButton;

    @FXML
    private Button showNrStepsButton;
    @FXML
    private TextField showNrStepsField;

    @FXML
    void addActivity(MouseEvent event) {
        String date = dateTextF.getText();
        int nrSteps = Integer.parseInt(nrStepsField.getText());
        int hours = Integer.parseInt(hoursSleepField.getText());
        String activity = activityField.getText();

        Fitness fitness = new Fitness(nrSteps,hours,activity,date);
        service.addFitnessActivity(fitness);

    }

    @FXML
    void showAllButtonClicked(MouseEvent event) {
        ObservableList<Fitness> fitnesses = FXCollections.observableArrayList(service.getData());
        fitnessListView.setItems(fitnesses);
    }

    @FXML
    void showFiltered(KeyEvent event) {
        String minutesText = filterTextField.getText();
        int minMoveMinutes = 0; // Default value if the text field is empty

        if (!minutesText.isEmpty()) {
            minMoveMinutes = Integer.parseInt(minutesText);
        }

        ObservableList<Fitness> filteredList = FXCollections.observableArrayList(service.search(minMoveMinutes));
        fitnessListView.setItems(filteredList);
    }


    @FXML
    void showNrSteps(MouseEvent event) {
        String month = showNrStepsField.getText();
        int total = service.getTotalStepsForMonth(month);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("NUMBER STEPS");
        alert.setHeaderText(null);
        alert.setContentText("Nr. Steps: " + total);
        alert.showAndWait();
    }

    public void initialize(){
        service.addData();

    }

}