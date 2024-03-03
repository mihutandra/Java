package ui;

import domain.Appointment;
import domain.Patient;
import domain.ValidationException;
import service.Service;

import java.io.IOException;
import java.util.Scanner;

import static service.Service.getRepositoryType;

public class Ui
{
    private Service service;
    private String repoType;

    public Ui(Service serv) throws IOException
    {
        this.service = serv;
        this.repoType = getRepositoryType("//Users//mihutandra//IdeaProjects//a5-mihutandra//dentist_app//src//settings.properties");
    }

    public void printMenu()
    {
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+");
        System.out.println("| 0 - Exit");
        System.out.println("| 1 - List all patients");
        System.out.println("| 2 - Add a patient");
        System.out.println("| 3 - Delete a patient by ID");
        System.out.println("| 4 - Update a patient");
        System.out.println("| - - - - - - - - - - -");
        System.out.println("| 5 - List all appointments.");
        System.out.println("| 6 - Add an appointment.");
        System.out.println("| 7 - Cancel an appointment.");
        System.out.println("| 8 - Update an appointment.");
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+");
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Please input your option: ");

            if (scanner.hasNextInt()) {
                int command = scanner.nextInt();

                switch (command) {
                    case 0:
                        exit();
                        break;
                    case 1:
                        listAllPatients();
                        break;
                    case 2:
                        addingPatientCase(scanner);
                        break;
                    case 3:
                        deletePatientCase(scanner);
                        break;
                    case 4:
                        updatePatientCase(scanner);
                        break;
                    case 5:
                        listAllAppointments();
                        break;
                    case 6:
                        addAppointmentCase(scanner);
                        break;
                    case 7:
                        cancelAppointmentCase(scanner);
                        break;
                    case 8:
                        updateAppointmentCase(scanner);
                        break;
                    default:
                        invalidChoice();
                }
            } else {
                System.out.println("Please enter a valid integer option.");
                scanner.next();
            }
        }
    }
    private void exit() {
        System.out.println("GOODBYE!");
        System.exit(0);
    }

    private void listAllPatients() {
        for (Object p : service.getAllPatients()) {
            System.out.println(p.toString());
        }
    }

    private void addingPatientCase(Scanner scanner) {
        try {
            System.out.println("Please enter the following data:\n");
            System.out.println("Patient's id: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Patient's name: ");
            String n = scanner.nextLine();
            System.out.println("Patient's disease: ");
            String d = scanner.next();
            service.addPatient(new Patient(id, n, d));
        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deletePatientCase(Scanner scanner) {
        try {
            System.out.print("Enter the ID of the patient to delete: ");
            int patientId = scanner.nextInt();
            service.deletePatient(patientId);
            System.out.println("Patient with ID " + patientId + " deleted successfully.");
        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updatePatientCase(Scanner scanner) {
        try {
            System.out.println("Please enter the ID of the patient to update:");
            int idUp = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Please enter the following data for the updated patient:");
            System.out.println("New Patient's name: ");
            String newN = scanner.nextLine();
            System.out.println("New Patient's disease: ");
            String newD = scanner.nextLine();
            Patient updatedPatient = new Patient(idUp, newN, newD);
            service.updatePatient(updatedPatient);
        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listAllAppointments() {
        for (Object a : service.getAllAppointments()) {
            System.out.println(a.toString());
        }
    }

    private void addAppointmentCase(Scanner scanner) {
        try {
            System.out.println("Please enter the following data:\n");
            System.out.println("Appointment's id: ");
            int idA = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Appointment's description: ");
            String des = scanner.nextLine();
            System.out.println("Appointment's date: ");
            String date = scanner.next();
            service.addAppointment(new Appointment(idA, des, date));
        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void cancelAppointmentCase(Scanner scanner) {
        try {
            System.out.print("Enter the ID of the appointment to cancel: ");
            int appointmentId = scanner.nextInt();
            service.deleteAppointment(appointmentId);
            System.out.println("Appointment with ID " + appointmentId + " deleted successfully.");
        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateAppointmentCase(Scanner scanner) {
        try {
            System.out.println("Please enter de id of the appointment to update:\n");
            int idApp = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Please enter the following data:\n");
            System.out.println("New Appointment's description: ");
            String newD = scanner.nextLine();
            System.out.println("New Appointment's date: ");
            String newDate = scanner.next();
            Appointment updatedAppointment = new Appointment(idApp, newD, newDate);
            service.updateAppointment(idApp, updatedAppointment);
        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void invalidChoice() {
        System.out.println("Not a valid choice.");
    }
}
