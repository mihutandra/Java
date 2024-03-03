package gui;
import domain.Route;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.Service;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;

public class Controller {

    private Service service;

    public Controller(Service service) {
        this.service = service;
    }

    // OBJECTS
        @FXML
        private ComboBox<String> destinationComboBox;

        @FXML
        private Button exitButton;

        @FXML
        private TextField nrTicketsTextField;

        @FXML
        private ListView<Route> routeListView;

        @FXML
        private Button showAll;

        @FXML
        private Button sortButton;

        @FXML
        private ComboBox<String> sourceComboBox;

        // FUNCTIONS
        @FXML
        void destinationComboBoxClicked(MouseEvent event) {
            String sourceCity = sourceComboBox.getValue();
            ObservableList<String> destinationCityList = FXCollections.observableArrayList(service.getAllDestinationsFor(sourceCity));
            destinationComboBox.setItems(destinationCityList);
        }

        @FXML
        void destinationComboBoxKeyPress(KeyEvent event) {
            if(event.getCode().equals(KeyCode.ENTER))
                if (!sourceComboBox.getValue().isEmpty() && !destinationComboBox.getValue().isEmpty()) {
                    populateRoutesListView(); //routes with specific source and destination
                }
        }

        @FXML
        void onexitButtonClicked(MouseEvent event) {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            Alert priceAlert = new Alert(Alert.AlertType.WARNING);
            priceAlert.setTitle(null);
            priceAlert.setHeaderText(null);
            priceAlert.setContentText("EXITED");
            priceAlert.show();
            stage.close();
        }

        @FXML
        void showAllClicked(MouseEvent event) {
            ObservableList<Route> allRoutesList = FXCollections.observableArrayList(service.getData());
            routeListView.setItems(allRoutesList);
        }

        @FXML
        void sortButtonClicked(MouseEvent event) {
            ObservableList<Route> sortedRoutesList = FXCollections.observableArrayList(service.sortByDeparture());
            routeListView.setItems(sortedRoutesList);
        }

        @FXML
        void sourceComboBoxClicked(MouseEvent event) {
            ObservableList<String> sourceCityList = FXCollections.observableArrayList(service.getAllSourceCities());
            sourceComboBox.setItems(sourceCityList);
        }

        @FXML
        void enterTickets(KeyEvent event) {
        try {
            int ticketsNr = Integer.parseInt(nrTicketsTextField.getText());
            Route selectedRout = routeListView.getSelectionModel().getSelectedItem();
            int currentSeats = selectedRout.getTotalSeats();
            double totalPrice = selectedRout.getTicketPrice()*ticketsNr;
            if (event.getCode().equals(KeyCode.ENTER)) {
                if (ticketsNr <= currentSeats) {
                    selectedRout.setTotalSeats(currentSeats - ticketsNr);

                    populateRoutesListView(); //update text for data base

                    Alert priceAlert = new Alert(Alert.AlertType.INFORMATION);  //show alert for price ticket
                    priceAlert.setTitle("Total Price");
                    priceAlert.setHeaderText(null);
                    priceAlert.setContentText("Total Price: " + String.format("%.2f", totalPrice) + " $");
                    priceAlert.showAndWait();

                }else{//show alert for INSUFFICIENT ticket
                    Alert insufficientTickets = new Alert(Alert.AlertType.ERROR);
                    insufficientTickets.setTitle("Insufficient Tickets");
                    insufficientTickets.setHeaderText(null);
                    insufficientTickets.setContentText("Insufficient Tickets" + '\n' + "Available Tickets: " + String.format("%d",currentSeats));
                    insufficientTickets.showAndWait();
                }

            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    public void initialize(){
        service.addData();
    }
    public void populateRoutesListView(){
        String sourceCity = sourceComboBox.getValue();
        String destination = destinationComboBox.getValue();
        ObservableList<Route> allRouts = FXCollections.observableArrayList(service.getAllRoutsFor(sourceCity,destination));
        routeListView.setItems(allRouts);
    }
}
