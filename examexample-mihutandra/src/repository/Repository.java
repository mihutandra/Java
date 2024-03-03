package repository;

import domain.Route;
import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.ArrayList;

public class Repository {
    protected ArrayList<Route> data;

    private static final String JDBC_URL = "jdbc:sqlite:data/test_db.db";

    private static Connection conn = null;


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

    public Repository() {
        this.data = new ArrayList<>();
    }

    public void addData() {
        try
        {
            openConnection();
            String selectString = "SELECT * FROM " + "availableRoutes" + ";";
            try (PreparedStatement ps = conn.prepareStatement(selectString))
            {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next())
                {
                    String startCity = resultSet.getString("source_city");
                    String destination = resultSet.getString("destination_city");
                    String departure = resultSet.getString("departure_time");
                    String arrival = resultSet.getString("arrival_time");
                    int seats = resultSet.getInt("total_seats");
                    double price = resultSet.getDouble("ticket_price");
                    Route Route = new Route(startCity,destination,departure,arrival,seats,price);
                    data.add(Route);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            closeConnection();
        }

    }

    public ArrayList<Route> getData(){
        return data;
    }

    public ArrayList<String> getSourceCities(){
        ArrayList<String> result = new ArrayList<>();
        for(Route r: data)
            if(!result.contains(r.getSourceCity())) // ensures no duplicates
                result.add(r.getSourceCity());

        return result;
    }

    public ArrayList<String> getDestinationCitiesFor(String sourceCity){
        ArrayList<String> result = new ArrayList<>();
        for(Route r: data) {
            if(r.getSourceCity().equals(sourceCity)) {
                result.add(r.getDestinationCity());
            }
        }
        return result;
    }

    public ArrayList<Route> getAllRoutesFor(String sourceCity, String destination){
        ArrayList<Route> result = new ArrayList<>();
        for(Route r: data) {
            if(r.getSourceCity().equals(sourceCity) && r.getDestinationCity().equals(destination)) {
                result.add(r);
            }
        }
        return result;
    }
}
