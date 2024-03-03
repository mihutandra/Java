package main;

import domain.*;
import gui.PatientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.*;
import service.*;
import ui.Ui;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Main extends Application {

    public Main() throws IOException {
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        try (FileReader reader = new FileReader("//Users//mihutandra//IdeaProjects//a5-mihutandra//dentist_app//src//settings.properties")) {
            properties.load(reader);
        }
        catch (IOException e)
        {
            System.out.println("Error reading properties file: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        System.out.println("Properties loaded: " + properties);
        String repoType = properties.getProperty("repository_type");


        if (repoType == null) {
            System.out.println("repository type is not specified ");
            return;
        }

        repoType = repoType.trim();

        MemoryRepository<Integer, Patient> patientRepo = null;
        MemoryRepository<Integer, Appointment> appointmentRepo = null;


        switch (repoType) {
            case "inmemory":
                patientRepo = new PatientRepository();
                appointmentRepo = new AppointmentsRepository();
                break;
            case "textFile":
                patientRepo = setupTextRepositoriesP(properties);
                appointmentRepo = setupTextRepositoriesA(properties);
                break;
            case "binaryFile":
                patientRepo = setupBinaryRepositories(properties);
                appointmentRepo = setupBinaryRepositoriesA(properties);
                break;
            case "dataBase":
                patientRepo = setupDatabaseRepositoriesP(properties);
                appointmentRepo = setupDatabaseRepositoriesA(properties);
                break;
            case "json":
                patientRepo = setupJSONRepositoryP(properties);
                appointmentRepo = setupJSONRepositoryA(properties);
                break;
            case "xml":
                patientRepo = setupXMLRepositoriesP(properties);
                appointmentRepo = setupXMLRepositoriesA(properties);
                break;
            default:
                System.out.println("Invalid repository type specified in settings.properties: " + repoType);
                return;
        }

        //Create ReportService
        ReportService reportService = new ReportService(patientRepo, appointmentRepo);


        // Display reports
        reportService.displayReports();

        if (patientRepo != null && appointmentRepo != null) {
            Service service = new Service(patientRepo, appointmentRepo);
            PatientController controller = new PatientController(service);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("//Users//mihutandra//IdeaProjects//a5-mihutandra//dentist_app//src//gui//PatientGUI.fxml"));
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
    }

    // text file repo
    // PATIENT
    private static PatientFileRepository setupTextRepositoriesP(Properties properties) {
        String patientsFile = properties.getProperty("Patient_repository");

        if (patientsFile == null) {
            System.out.println("Text repository file is not properly specified in settings.properties.");
            return null;
        }

        patientsFile = patientsFile.trim();
        return new PatientFileRepository(patientsFile);
    }

    //APPOINTMENT
    private static AppointmentFileRepository setupTextRepositoriesA(Properties properties) {
        String appointmentsFile = properties.getProperty("Appointment_repository");

        if (appointmentsFile == null) {
            System.out.println("Text repository file is not properly specified in settings.properties.");
            return null;
        }

        appointmentsFile = appointmentsFile.trim();
        return new AppointmentFileRepository(appointmentsFile);
    }

    // binary file repo
    //PATIENT
    private static PatientBinaryFileRepository setupBinaryRepositories(Properties properties) {
        String patientsFile = properties.getProperty("Patient_repository");

        if (patientsFile == null) {
            System.out.println("Binary repository file is not properly specified in settings.properties.");
            return null;
        }

        patientsFile = patientsFile.trim();
        return new PatientBinaryFileRepository(patientsFile);}

    //APPOINTMENT
    private static AppointmentBinaryFileRepository setupBinaryRepositoriesA(Properties properties) {
        String appointmentsFile = properties.getProperty("Appointment_repository");

        if (appointmentsFile == null) {
            System.out.println("Binary repository file is not properly specified in settings.properties.");
            return null;
        }

        appointmentsFile = appointmentsFile.trim();
        return new AppointmentBinaryFileRepository(appointmentsFile);
    }

    // database repo
    //PATIENT
    private static PatientDBRepository setupDatabaseRepositoriesP(Properties properties) {
        String repositoryType = properties.getProperty("Patient_repository");
        //String location = properties.getProperty("Location");

        if (repositoryType == null) {
            System.out.println("Database repository settings are not properly specified in settings.properties.");
            return null;
        }

        return new PatientDBRepository(repositoryType);
    }

    // APPOINTMENT
    private static AppointmentDBRepository setupDatabaseRepositoriesA(Properties properties) {
        String repositoryType = properties.getProperty("Appointment_repository");
       // String location = properties.getProperty("Location");

        if (repositoryType == null) {
            System.out.println("Database repository settings are not properly specified in settings.properties.");
            return null;
        }

        return new AppointmentDBRepository(repositoryType);
    }

    // json repo
    // PATIENT
    private static PatientJSONRepository setupJSONRepositoryP(Properties properties) {
        //String location = properties.getProperty("Location");
        String patientsFile = properties.getProperty("Patient_repository");

        if ( patientsFile == null) {
            System.out.println("JSON repository settings are not properly specified in settings.properties.");
            return null;
        }

        return new PatientJSONRepository(patientsFile);
    }

    // APPOINTMENT
    private static AppointmentJSONRepository setupJSONRepositoryA(Properties properties) {
       // String location = properties.getProperty("Location");
        String appointmentsFile = properties.getProperty("Appointment_repository");

        if ( appointmentsFile == null) {
            System.out.println("JSON repository settings are not properly specified in settings.properties.");
            return null;
        }

        return new AppointmentJSONRepository( appointmentsFile);
    }

    // xml repo
    //PATIENT
    private static PatientXMLRepository setupXMLRepositoriesP(Properties properties) {
        String patientsFile = properties.getProperty("Patient_repository");

        if (patientsFile == null) {
            System.out.println("XML repository file is not properly specified in settings.properties.");
            return null;
        }

        patientsFile = patientsFile.trim();
        return new PatientXMLRepository(patientsFile);
    }

    //APPOINTMENT
    private static AppointmentXMLRepository setupXMLRepositoriesA(Properties properties) {
        String appointmentsFile = properties.getProperty("Appointment_repository");

        if (appointmentsFile == null) {
            System.out.println("XML repository file is not properly specified in settings.properties.");
            return null;
        }

        appointmentsFile = appointmentsFile.trim();
        return new AppointmentXMLRepository(appointmentsFile);
    }
}