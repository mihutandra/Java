package repository;

import domain.Appointment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AppointmentJSONRepository extends FileRepository<Appointment, Integer> {

    public AppointmentJSONRepository(String filename) {
        super(filename);
    }

    @Override
    public void readFromFile() {
        try (FileReader reader = new FileReader(filename)) {
            JSONParser jsonParser = new JSONParser();
            JSONArray appointmentsArray = (JSONArray) jsonParser.parse(reader);

            for (Object obj : appointmentsArray) {
                JSONObject appointmentObject = (JSONObject) obj;
                int id = Math.toIntExact((Long) appointmentObject.get("id"));
                String description = (String) appointmentObject.get("description");
                String date = (String) appointmentObject.get("date");

                Appointment appointment = new Appointment(id, description, date);
                //data.put(id, appointment);
                repo.put(id, appointment);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile() {
        JSONArray appointmentsArray = new JSONArray();
        for (Appointment appointment : repo.values()) {
            JSONObject appointmentObject = new JSONObject();
            appointmentObject.put("id", appointment.getId());
            appointmentObject.put("description", appointment.getDescription());
            // Format the date without escaped slashes
            String formattedDate = appointment.getDate().replace("\\/", "/");
            appointmentObject.put("date", formattedDate);

            appointmentsArray.add(appointmentObject);
        }

        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(appointmentsArray.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}