package domain;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class BinaryFileWriter {

    public static void main(String[] args) {
        // Example objects to be written to the binary file
        SerializableObject obj1 = new SerializableObject(2001, "Weekly check for diabetes", "10/11/2024");
        SerializableObject obj2 = new SerializableObject(2002, "Annual ophthalmological consultation" ,"27/03/21");

        // Specify the path to the binary file
        String filePath = "example.bin";

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            // Write objects to the binary file
            objectOutputStream.writeObject(obj1);
            objectOutputStream.writeObject(obj2);

            System.out.println("Objects written to the binary file successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Example class that implements Serializable
    static class SerializableObject implements Serializable {

        private final int id;
        private final String name;
        private final String disease;

        public SerializableObject(int id, String name, String disease) {
            this.id = id;
            this.name = name;
            this.disease = disease;
        }

        @Override
        public String toString() {
            return "SerializableObject{" +
                    "id='" + id + '\'' +
                    ", name=" + name +
                    ", disease=" + disease +
                    '}';
        }
    }
}
