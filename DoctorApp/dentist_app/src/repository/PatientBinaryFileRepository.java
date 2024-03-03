package repository;

import domain.Patient;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PatientBinaryFileRepository extends FileRepository<Patient, Integer> implements Serializable {

    public PatientBinaryFileRepository(String filename)
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
      //  Map<Integer,Patient > emptyMap = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = ois.readObject();
            //data = (Map<Integer, Patient>) ois.readObject();
            //data = (Map<Integer, Patient>) obj;
            repo = (Map<Integer, Patient>) obj;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile() {
        //TO DO
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            //oos.writeObject(data);
            oos.writeObject(repo);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

