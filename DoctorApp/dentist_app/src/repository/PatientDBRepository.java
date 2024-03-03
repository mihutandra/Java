package repository;

import domain.Patient;
import domain.ValidationException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDBRepository extends DBRepository<Patient, Integer> {

    public PatientDBRepository(String tableName) {
        super(tableName);
        getData();
    }


    @Override
    public void getData() {
        try {
            openConnection();
            String selectString = "SELECT * FROM " + tableName + ";";
            try (PreparedStatement ps = conn.prepareStatement(selectString)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String disease = resultSet.getString("disease");
                    Patient patient = new Patient(id, name, disease);
                    //data.put(id, patient);
                    repo.put(id, patient);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void addEntity(Patient elem) throws ValidationException
    {
        try
        {
            openConnection();
            conn.setAutoCommit(false);
            String insertString = "INSERT INTO " + tableName + " VALUES (?, ?, ?);";
            try (PreparedStatement ps = conn.prepareStatement(insertString)) {
                ps.setInt(1, elem.getId());
                ps.setString(2, elem.getName());
                ps.setString(3, elem.getDisease());
                ps.executeUpdate();
            }
            conn.commit();
            // Update the in-memory data
            //data.put(elem.getId(), elem);
            repo.put(elem.getId(), elem);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deleteEntity(Integer patientId) throws ValidationException {
        try {
            openConnection();
            conn.setAutoCommit(false);

            // Assuming "id" is the primary key in your patient table
            String deleteString = "DELETE FROM " + tableName + " WHERE id = ?;";
            try (PreparedStatement ps = conn.prepareStatement(deleteString)) {
                ps.setInt(1, patientId);
                ps.executeUpdate();
            }

            conn.commit();
            // Update the in-memory data
            //data.remove(patientId);
            repo.remove(patientId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void updateEntity(Integer patientId, Patient updatedPatient) throws ValidationException {
        try {
            openConnection();
            conn.setAutoCommit(false);

            // Assuming "id" is the primary key in your patient table
            String updateString = "UPDATE " + tableName + " SET name = ?, disease = ? WHERE id = ?;";
            try (PreparedStatement ps = conn.prepareStatement(updateString)) {
                ps.setString(1, updatedPatient.getName());
                ps.setString(2, updatedPatient.getDisease());
                ps.setInt(3, patientId);
                ps.executeUpdate();
            }

            conn.commit();
            // Update the in-memory data
            //data.put(patientId, updatedPatient);
            repo.put(patientId, updatedPatient);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}