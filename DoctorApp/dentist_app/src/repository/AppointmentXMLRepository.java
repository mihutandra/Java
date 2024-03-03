package repository;

import domain.Appointment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AppointmentXMLRepository extends FileRepository<Appointment, Integer> {

    public AppointmentXMLRepository(String filename) {
        super(filename);
    }

    @Override
    public void readFromFile() {
        try {
            File inputFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList appointmentList = doc.getElementsByTagName("appointment");

            for (int temp = 0; temp < appointmentList.getLength(); temp++) {
                Element appointmentElement = (Element) appointmentList.item(temp);
                int id = Integer.parseInt(appointmentElement.getAttribute("id"));
                String description = appointmentElement.getElementsByTagName("description").item(0).getTextContent();
                String date = appointmentElement.getElementsByTagName("date").item(0).getTextContent();

                Appointment appointment = new Appointment(id, description, date);
               // data.put(id, appointment);
                repo.put(id, appointment);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile() {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write("<appointments>\n");
            for (Appointment appointment : repo.values()) {
                fileWriter.write("  <appointment id=\"" + appointment.getId() + "\">\n");
                fileWriter.write("    <description>" + appointment.getDescription() + "</description>\n");
                fileWriter.write("    <date>" + appointment.getDate() + "</date>\n");
                fileWriter.write("  </appointment>\n");
            }
            fileWriter.write("</appointments>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}