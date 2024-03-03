package repository;

import domain.Weather;
import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Repository {
    private static final String JDBC_URL = "jdbc:sqlite:data/test_db.db";

    private static Connection conn = null;

    protected ArrayList<Weather> data = new ArrayList<>();

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
            String selectString = "SELECT * FROM " + "WEATHER" + ";";
            try (PreparedStatement ps = conn.prepareStatement(selectString))
            {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next())
                {
                    int startHour = resultSet.getInt("startHour");
                    int endHour = resultSet.getInt("endHour");
                    int temperature = resultSet.getInt("temperature");
                    int precipitation = resultSet.getInt("precipitation");
                    String description = resultSet.getString("description");
                    Weather document = new Weather(startHour,endHour,temperature,precipitation,description);
                    if(!data.contains(document))
                        data.add(document);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            closeConnection();
        }
    }

    public ArrayList<Weather> getData(){
        return data;
    }

    public ArrayList<Weather> getAllFor(String description){
        ArrayList<Weather> result = new ArrayList<>();
        for(Weather w: data) {
            if(w.getDescription().equals(description)) {
                result.add(w);
            }
        }
        result.stream().sorted(Comparator.comparing(Weather::getStartHour));
        return result;
    }

    public ArrayList<String> getAllDescriptions(){
        ArrayList<String> result = new ArrayList<>();
        for(Weather r: data)
            if(!result.contains(r.getDescription())) // ensures no duplicates
                result.add(r.getDescription());
        return result;
    }

    public void updateWeather(Weather updatedWeather) {
        try {
            openConnection();
            conn.setAutoCommit(false);

            String updateString = "UPDATE WEATHER SET precipitation = ?, description = ? WHERE startHour = ? AND endHour = ?;";
            try (PreparedStatement ps = conn.prepareStatement(updateString)) {
                ps.setInt(1, updatedWeather.getPrecipitation());
                ps.setString(2, updatedWeather.getDescription());
                ps.setInt(3, updatedWeather.getStartHour());
                ps.setInt(4, updatedWeather.getEndHour());
                ps.executeUpdate();
            }

            conn.commit();

            // Update the data list with the modified weather
            for (Weather w : data) {
                if (w.getStartHour() == updatedWeather.getStartHour() && w.getEndHour() == updatedWeather.getEndHour()) {
                    w.setPrecipitation(updatedWeather.getPrecipitation()); // update precipitation
                    w.setDescription(updatedWeather.getDescription()); // update description
                    break;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }
}


