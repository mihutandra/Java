

package repository;

import domain.Patient;

import java.io.*;

public class PatientFileRepository extends FileRepository<Patient, Integer> {
    public PatientFileRepository(String filename) {
        super(filename);
    }

    @Override
    public void readFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = null;
            while ((line = br.readLine()) != null)
            {
                String[] tokens = line.split("[,]");
                if (tokens.length != 3)
                    continue;
                Integer id = Integer.parseInt(tokens[0].trim());
                String name = tokens[1].trim();
                String disease = tokens[2].trim();
                Patient b = new Patient(id, name,disease);
                //data.put(id, b);
                repo.put(id, b);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Patient b: repo.values())
            {
                bw.write(b.getId() + "," + b.getName() + "," + b.getDisease() +"\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}