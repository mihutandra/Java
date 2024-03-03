package repository;

import domain.Patient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PatientJSONRepository extends FileRepository<Patient, Integer> {

    public PatientJSONRepository(String filename) {
        super(filename);
    }

    @Override
    public void readFromFile() {
        try (FileReader reader = new FileReader(filename)) {
            JSONParser jsonParser = new JSONParser();
            JSONArray patientsArray = (JSONArray) jsonParser.parse(reader);

            for (Object obj : patientsArray) {
                JSONObject patientObject = (JSONObject) obj;
                int id = Math.toIntExact((Long) patientObject.get("id"));
                String name = (String) patientObject.get("name");
                String disease = (String) patientObject.get("disease");

                Patient patient = new Patient(id, name, disease);
                //data.put(id, patient);
                repo.put(id, patient);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile() {
        JSONArray patientsArray = new JSONArray();
        for (Patient patient : repo.values()) {
            JSONObject patientObject = new JSONObject();
            patientObject.put("id", patient.getId());
            patientObject.put("name", patient.getName());
            patientObject.put("disease", patient.getDisease());

            patientsArray.add(patientObject);
        }

        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(patientsArray.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}