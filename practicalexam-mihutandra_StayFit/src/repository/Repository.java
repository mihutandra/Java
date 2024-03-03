package repository;

import domain.Fitness;
import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Repository {
    private static final String JDBC_URL = "jdbc:sqlite:data/test_db.db";

    private static Connection conn = null;

    protected ArrayList<Fitness> data = new ArrayList<>();

    public static Connection getConnection() {
        if (conn == null)
            openConnection();
        return conn;
    }

    private static void openConnection() {
        try
        {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(JDBC_URL);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void closeConnection() {
        try
        {
            conn.close();
            conn = null;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //-------------------

    public void addData() {
        try
        {
            openConnection();
            String selectString = "SELECT * FROM " + "Fitness" + ";";
            try (PreparedStatement ps = conn.prepareStatement(selectString))
            {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next())
                {
                    String date = resultSet.getString("date");
                    int nrSteps = Integer.parseInt(resultSet.getString("nrSteps"));
                    int sleepHours = Integer.parseInt(resultSet.getString("sleepHours"));
                    String activitiesMin = resultSet.getString("activitiesMin");

                  Fitness fitness = new Fitness(nrSteps,sleepHours,activitiesMin,date);
                    if(!data.contains(fitness))
                        data.add(fitness);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            closeConnection();
        }
    }

    public ArrayList<Fitness> getData(){
        return data;
    }


    public List<Fitness> search(int value) {
        return data.stream()
                .filter(item ->
                        item.getMinutesOfAnActivity()>value)
                .collect(Collectors.toList());
    }


    public int getTotalStepsForMonth(String month) {
        int totalSteps = 0;
        for (Fitness fitness : data) { //2023.01.01
            String monthFromDate = fitness.getDate().substring(5, 7);
            if (monthFromDate.equals(month)) {
                totalSteps += fitness.getNrSteps();
            }
        }
        return totalSteps;
    }

    public void addFitnessActivity(Fitness fitness) {
        try {
            openConnection();
            conn.setAutoCommit(false);

            String insertString = "INSERT INTO Fitness (date, nrSteps, sleepHours, activitiesMin) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertString)) {
                ps.setString(1, fitness.getDate());
                ps.setInt(2, fitness.getNrSteps());
                ps.setInt(3, fitness.getSleepHours());
                ps.setString(4, fitness.getActivitiesMin());
                ps.executeUpdate();
            }

            conn.commit();

            // Update the data list with the new fitness activity
            data.add(fitness);

            // Update the total number of steps for the corresponding month
            String month = fitness.getDate().substring(0, 7); // Extract the month from the date
            int totalStepsForMonth = getTotalStepsForMonth(month);
            // Display or store the totalStepsForMonth as needed

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }


}

