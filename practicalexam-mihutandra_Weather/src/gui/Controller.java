package gui;

import domain.Weather;
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
    private ComboBox<String> descrimptionComboBox;
    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField precipitationTextField;
    @FXML
    private TextField textField;
    @FXML
    private Button showAllButton;

    @FXML
    private ListView<Weather> timeListView;

    @FXML
    void showAllButtonClicked(MouseEvent event) {
        ObservableList<Weather> allRoutesList = FXCollections.observableArrayList(service.getData());
        timeListView.setItems(allRoutesList);
    }

    @FXML
    void showWeathersWithDescipriton(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER))
            if (!descrimptionComboBox.getValue().isEmpty()) {
                populateWeatherListView();
            }
    }
    @FXML
    void showAllDescriptions(MouseEvent event) {
        ObservableList<String> descriptionList = FXCollections.observableArrayList(service.getAllDescriptions());
        descrimptionComboBox.setItems(descriptionList);

    }

    @FXML
    void updateWeather(MouseEvent event) {
        Weather selectedWeather = timeListView.getSelectionModel().getSelectedItem();
        if (selectedWeather != null) {
            // Get updated values from UI elements
            int precipitation = Integer.parseInt(precipitationTextField.getText());
            String description = descriptionTextField.getText();

            // Update the selected weather interval
            selectedWeather.setPrecipitation(precipitation);
            selectedWeather.setDescription(description);
            // Save the update in the database via the service
            service.updateWeather(selectedWeather);
            // Refresh the list view to reflect changes
            ObservableList<Weather> all = FXCollections.observableArrayList(service.getData());
            timeListView.setItems(all);
        }
    }

    @FXML
    void enterWord(KeyEvent event) { //compute nr of hours
        String description = textField.getText();
        if (event.getCode().equals(KeyCode.ENTER)) {
            int totalHours = service.getTotalHoursForDescription(description);
            if (totalHours == 0) {
                //show alert
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("ERROR");
                error.setHeaderText(null);
                error.setContentText("word not found");
                error.showAndWait();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);  //show alert for price ticket
                alert.setTitle("Total Hours");
                alert.setHeaderText(null);
                alert.setContentText("Total Hours: " + totalHours);
                alert.showAndWait();

            }

        }
    }

    //------------------------
    public void initialize(){
        service.addData();

    }
    public void populateWeatherListView(){
        String description = descrimptionComboBox.getValue();
        ObservableList<Weather> allTimes = FXCollections.observableArrayList(service.getAllFor(description));
        timeListView.setItems(allTimes);
    }

}
