package service;

import domain.Appointment;
import domain.Patient;
import repository.GenericRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ReportService {
    private final GenericRepository<Patient, Integer> patientRepository;
    private final GenericRepository<Appointment, Integer> appointmentRepository;

    public ReportService(GenericRepository<Patient, Integer> patientRepository, GenericRepository<Appointment, Integer> appointmentRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public void displayReports() {
        System.out.println("-+-+-+-+-+-+- Reports -+-+-+-+-+-+-");
        printPatientsWithDisease("Control");
        printPatientsWithInitial('A');
        printAppointmentsAfterDate("03/10/2022");
        printAppointmentDescriptionById(4);
        printAppointmentsFromDateWithDescription("22/10/2023");
        printPatientsSortedAlphabetically();
        System.out.println("-+-+-+-+-+-+--+-+-+-+-+-+--+-+-+-+-+-+-");

    }

    // Report: List of patients with a specific disease
    public List<Patient> getPatientsWithDisease(String disease) {
        return toStream(patientRepository.getAll())
                .filter(patient -> patient.getDisease().equalsIgnoreCase(disease))
                .collect(Collectors.toList());
    }

    // Report: Patients whose names start with a specific letter
    public List<Patient> getPatientsWithInitial(char initial) {
        return toStream(patientRepository.getAll())
                .filter(patient -> patient.getName().toUpperCase().charAt(0) == Character.toUpperCase(initial))
                .collect(Collectors.toList());
    }

    // Report: Appointments after a specific date
    public List<Appointment> getAppointmentsAfterDate(String date) {
        return toStream(appointmentRepository.getAll())
                .filter(appointment -> compareDates(appointment.getDate(), date) > 0)
                .collect(Collectors.toList());
    }

    // Report: Description of a certain appointment (given by id)
    public String getAppointmentDescriptionById(int appointmentId) {
        return toStream(appointmentRepository.getAll())
                .filter(appointment -> appointment.getId() == appointmentId)
                .map(Appointment::getDescription)
                .findFirst()
                .orElse("Appointment not found");
    }

    // Report: All appointments from a certain date and their description
    public List<String> getAppointmentsFromDateWithDescription(String date) {
        return toStream(appointmentRepository.getAll())
                .filter(appointment -> appointment.getDate().equals(date))
                .map(appointment -> String.format("ID: %d, Description: %s", appointment.getId(), appointment.getDescription()))
                .collect(Collectors.toList());
    }

    // Report: List of patients sorted alphabetically by name
    public List<Patient> getPatientsSortedAlphabetically() {
        return toStream(patientRepository.getAll())
                .sorted(Comparator.comparing(Patient::getName))
                .collect(Collectors.toList());
    }

    // Helper method to compare dates in the format "dd/MM/yyyy"
    private int compareDates(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate localDate1 = LocalDate.parse(date1, formatter);
        LocalDate localDate2 = LocalDate.parse(date2, formatter);

        return localDate1.compareTo(localDate2);
    }

    // Display: List of patients with a specific disease
    public void printPatientsWithDisease(String disease) {
        System.out.println("Patients with disease '" + disease + "':");
        getPatientsWithDisease(disease).forEach(System.out::println);
        System.out.println();
    }

    // Display: Patients whose names start with a specific letter
    public void printPatientsWithInitial(char initial) {
        System.out.println("Patients with name starting with '" + initial + "':");
        getPatientsWithInitial(initial).forEach(System.out::println);
        System.out.println();
    }

    // Display: Appointments after a specific date
    public void printAppointmentsAfterDate(String date) {
        System.out.println("Appointments after date '" + date + "':");
        getAppointmentsAfterDate(date).forEach(System.out::println);
        System.out.println();
    }

    // Display: Appointments' description by id
    public void printAppointmentDescriptionById(int appointmentId) {
        System.out.println("Description of appointment with ID '" + appointmentId + "':");
        System.out.println(getAppointmentDescriptionById(appointmentId));
        System.out.println();
    }

    // Display: All appointments from a certain date and their description
    public void printAppointmentsFromDateWithDescription(String date) {
        List<String> appointmentsWithDescription = getAppointmentsFromDateWithDescription(date);
        System.out.println("Appointments from date '" + date + "' and their description:");
        appointmentsWithDescription.forEach(System.out::println);
        System.out.println();
    }

    // Display: List of patients sorted alphabetically by name
    public void printPatientsSortedAlphabetically() {
        System.out.println("Patients sorted alphabetically by name:");
        getPatientsSortedAlphabetically().forEach(System.out::println);
        System.out.println();
    }


    // Convert Iterable to Stream
    private <T> Stream<T> toStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }


}
