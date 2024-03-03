package repository;

import domain.Appointment;
import domain.Patient;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AppointmentBinaryFileRepository extends FileRepository<Appointment, Integer> implements Serializable {

    public AppointmentBinaryFileRepository(String filename)
    {
        super(filename);
    }
    @Override
    public void readFromFile() {
        File file = new File(filename);
        if (!file.exists()) {
            Map<Integer,Patient > emptyMap = new HashMap<>();
            //data = new HashMap<>();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
                oos.writeObject(emptyMap);
                //oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            //data = (Map<Integer, Appointment>) ois.readObject();
            repo = (Map<Integer, Appointment>) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            //oos.writeObject(data);
            oos.writeObject(repo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

