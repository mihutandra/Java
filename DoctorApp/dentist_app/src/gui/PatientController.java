package gui;

import domain.Appointment;
import domain.Patient;
import domain.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import service.Service;

public class PatientController {

    private Service service;

    public PatientController(Service service) {
        this.service = service;
    }

    //LISTS
    @FXML
    private ListView<Appointment> appointmentsListView;

    @FXML
    private ListView<Patient> patientsListView;

    //TEXTFIELDS
    @FXML
    private TextField searchField;
    //patient
    @FXML
    private TextField findPatientField;
    @FXML
    private TextField diseaseField;
    @FXML
    private TextField patientIDField;
    @FXML
    private TextField patientNameTF;
    //appointment
    @FXML
    private TextField appointmenDescriptionTF;
    @FXML
    private TextField appointmenIdTF;
    @FXML
    private TextField appointmentDateTF;


    //BUTTONS
    @FXML
    private Button buttonAddApp;
    @FXML
    private Button buttonAddPatient;
    @FXML
    private Button buttonDeleteApp;
    @FXML
    private Button buttonDeletePatient;
    @FXML
    private Button buttonUpdateApp;
    @FXML
    private Button buttonUpdatePatient;



    public void populatePatientsList(){
        ObservableList<Patient> patientList = FXCollections.observableArrayList(service.getAllPatients());
        patientsListView.setItems(patientList);
    }

    public void populateAppointmentsList(){
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList(service.getAllAppointments());
        appointmentsListView.setItems(appointmentList);
    }

    public void initialize(){
        populateAppointmentsList();
        populatePatientsList();
    }

    @FXML
    void onAppointmentSearch(KeyEvent event) throws ValidationException {
        String searchedText = searchField.getText();
        if( searchedText.isEmpty()){ //populate
            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList(service.getAllAppointments());
            appointmentsListView.setItems(appointmentList);
        }
       else {
            try {
                Integer id = Integer.parseInt(searchedText);
                ObservableList<Appointment> appointmentList = FXCollections.observableArrayList(service.findAppointmentByID(id));
                appointmentsListView.setItems(appointmentList);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            } catch (ValidationException e){
                appointmentsListView.setItems(null);
            }
        }
    }

    @FXML
    void onPatientSearch(KeyEvent event) {
        String searchedText = findPatientField.getText();
        if( searchedText.isEmpty()){
            ObservableList<Patient> patientsList = FXCollections.observableArrayList(service.getAllPatients());
            patientsListView.setItems(patientsList);
        }
        else {
            try {
                Integer id = Integer.parseInt(searchedText);
                ObservableList<Patient> patientsList = FXCollections.observableArrayList(service.findPatientByID(id));
                patientsListView.setItems(patientsList);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            } catch (ValidationException e){
                patientsListView.setItems(null);
            }
        }
    }

    @FXML
    void addAppointmentClicked(MouseEvent event) {
        String textID = appointmenIdTF.getText();
        String description = appointmenDescriptionTF.getText();
        String date = appointmentDateTF.getText();

        if(textID.isEmpty() || description.isEmpty() || date.isEmpty()){
            Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setTitle("Error");
            dialog.setContentText("Empty fields");
            dialog.show();
        }
        else {
            try {
                int id = Integer.parseInt(textID);

                Appointment app = new Appointment(id, description, date);
                service.addAppointment(app);
                ObservableList<Appointment> appointmentList = FXCollections.observableArrayList(service.getAllAppointments());
                appointmentsListView.setItems(appointmentList);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                Alert dialog = new Alert(Alert.AlertType.WARNING);
                dialog.setTitle("Error");
                dialog.setContentText("Id field should contain a valid integer");
                dialog.show();
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void addPatientClicked(MouseEvent event) {
        String textID = patientIDField.getText();
        String disease = diseaseField.getText();
        String name = patientNameTF.getText();

        if(textID.isEmpty() || disease.isEmpty() || name.isEmpty()){
            Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setTitle("Error");
            dialog.setContentText("Empty fields");
            dialog.show();
        }
        else {
            try {
                int id = Integer.parseInt(textID);

                Patient p = new Patient(id, name, disease);
                service.addPatient(p);
                ObservableList<Patient> patientList = FXCollections.observableArrayList(service.getAllPatients());
                patientsListView.setItems(patientList);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                Alert dialog = new Alert(Alert.AlertType.WARNING);
                dialog.setTitle("Error");
                dialog.setContentText("Id field should contain a valid integer");
                dialog.show();
            } catch (ValidationException e) {
                Alert dialog = new Alert(Alert.AlertType.WARNING);
                dialog.setTitle("Error");
                dialog.setContentText("Empty fields");
                dialog.show();
            }
        }
    }

    @FXML
    void deleteAppointmentClicked(MouseEvent event) {
        String textId = appointmenIdTF.getText();
        if(textId.isEmpty()){
            Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setTitle("Error");
            dialog.setContentText("The ID filed cannot be empty");
            dialog.show();
        }
        else {
            try {
                int id = Integer.parseInt(textId);

                service.deleteAppointment(id);
                ObservableList<Appointment> appointmentList = FXCollections.observableArrayList(service.getAllAppointments());
                appointmentsListView.setItems(appointmentList);

            } catch (ValidationException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                Alert dialog = new Alert(Alert.AlertType.WARNING);
                dialog.setTitle("Error");
                dialog.setContentText("ID field should contain a valid integer");
                dialog.show();
            }
        }
    }

    @FXML
    void deletePatientClicked(MouseEvent event) {
        String textId = patientIDField.getText();
        if(textId.isEmpty()){
            Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setTitle("Error");
            dialog.setContentText("The ID field cannot be empty");
            dialog.show();
        }
        else {
            try {
                int id = Integer.parseInt(textId);

                service.deletePatient(id);
                ObservableList<Patient> patientList =
                        FXCollections.observableArrayList(service.getAllPatients());
                patientsListView.setItems(patientList);

            } catch (ValidationException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                Alert dialog = new Alert(Alert.AlertType.WARNING);
                dialog.setTitle("Error");
                dialog.setContentText("Id field should contain a valid number");
                dialog.show();
            }
        }
    }

    @FXML
    void updateAppointmentClicked(MouseEvent event) {
        String textID = appointmenIdTF.getText();
        String description = appointmenDescriptionTF.getText();
        String date = appointmentDateTF.getText();

        if(textID.isEmpty() || description.isEmpty() || date.isEmpty()){
            Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setTitle("Error");
            dialog.setContentText("Empty fields ");
            dialog.show();
        }
        else {
            try {
                int id = Integer.parseInt(textID);

                Appointment app = new Appointment(id, description, date);
                service.updateAppointment(id,app);
                ObservableList<Appointment> appointmentList = FXCollections.observableArrayList(service.getAllAppointments());
                appointmentsListView.setItems(appointmentList);

            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                Alert dialog = new Alert(Alert.AlertType.WARNING);
                dialog.setTitle("Error");
                dialog.setContentText("ID field should contain a valid integer");
                dialog.show();
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
                Alert dialog = new Alert(Alert.AlertType.WARNING);
                dialog.setTitle("Error");
                dialog.setContentText("ID not found");
                dialog.show();
            }
        }
    }


    @FXML
    void updatePatientClicked(MouseEvent event) {
        String textID = patientIDField.getText();
        String name = patientNameTF.getText();
        String disease = diseaseField.getText();

        if(textID.isEmpty() || name.isEmpty() || disease.isEmpty()){
            Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setTitle("Error");
            dialog.setContentText("Empty fields ");
            dialog.show();
        }
        else {
            try {
                int id = Integer.parseInt(textID);

                Patient p = new Patient(id, name, disease);
                service.updatePatient(p);
                ObservableList<Patient> patientList =
                        FXCollections.observableArrayList(service.getAllPatients());
                patientsListView.setItems(patientList);

            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                Alert dialog = new Alert(Alert.AlertType.WARNING);
                dialog.setTitle("Error");
                dialog.setContentText("ID field should contain a valid number");
                dialog.show();
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
                Alert dialog = new Alert(Alert.AlertType.WARNING);
                dialog.setTitle("Error");
                dialog.setContentText("ID not found");
                dialog.show();
            }
        }
    }
}
