package service;

import domain.Appointment;
import domain.Identifiable;
import domain.Patient;
import domain.ValidationException;
import repository.MemoryRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Service<T extends Identifiable<ID>, ID> {
    private final MemoryRepository<Integer, Patient> repoPatients;
    private final MemoryRepository<Integer, Appointment> repoAppointments;

    public Service(MemoryRepository<Integer, Patient> rP, MemoryRepository<Integer, Appointment> rA) {
        this.repoPatients = rP;
        this.repoAppointments = rA;
    }
    public static String getRepositoryType(String filePath) throws IOException {
        try (FileReader fr = new FileReader(filePath)) {
            Properties props = new Properties();
            props.load(fr);
            return props.getProperty("repository_type").trim();
        }
    }

    public void addPatient(Patient patient) throws ValidationException {
        repoPatients.addEntity(patient);
    }
    public ArrayList<Patient> getAllPatients() {
        return repoPatients.getAll();
    }

    public void deletePatient(Integer patientId) throws ValidationException {
        repoPatients.deleteEntity(patientId);
    }

    public void updatePatient(Patient updatedPatient) throws ValidationException {
        repoPatients.updateEntity(updatedPatient.getId(), updatedPatient);
    }

    public Patient findPatientByID(Integer id) throws ValidationException {
        return repoPatients.findById(id);
    }

    public void addAppointment(Appointment appointment) throws ValidationException {
        repoAppointments.addEntity(appointment);
    }
    public ArrayList<Appointment> getAllAppointments() {
        return repoAppointments.getAll();
    }
    public void deleteAppointment(Integer appId) throws ValidationException {
        repoAppointments.deleteEntity(appId);
    }

    public void updateAppointment(Integer appId, Appointment updatedAppointment) throws ValidationException {
        repoAppointments.updateEntity(appId, updatedAppointment);
    }

    public Appointment findAppointmentByID(Integer id) throws ValidationException {
        return repoAppointments.findById(id);
    }
}
