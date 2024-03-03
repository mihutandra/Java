package repository;

import domain.Route;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*CREATE TABLE Route(
        sourceCity varchar(30),
        destinationCity varchar(30),
        departureTime varchar(30),
        arrivalTime varchar(30),
        totalSeats int,
        ticketPrice double
        )*/
public class RouteDBRepository extends DBRepository {

    public RouteDBRepository(String tableName) {
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
                while (resultSet.next()) { // we take the columns
                    String sourceCity = resultSet.getString("sourceCity");
                    String destinationCity = resultSet.getString("destinationCity");
                    String departureTime = resultSet.getString("departureTime");
                    String arrivalTime = resultSet.getString("arrivalTime");
                    int totalSeats = resultSet.getInt("totalSeats");
                    double ticketPrice = resultSet.getDouble("ticketPrice");
                    Route route = new Route(sourceCity,destinationCity,departureTime,arrivalTime,totalSeats,ticketPrice);
                    routes.add(route);
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
    public void addRoute(Route elem)
    {
        try
        {
            openConnection();
            conn.setAutoCommit(false);
            String insertString = "INSERT INTO " + tableName + " VALUES (?, ?, ?);";
            try (PreparedStatement ps = conn.prepareStatement(insertString)) {
                ps.setString(1, elem.getSourceCity());
                ps.setString(2, elem.getDestinationCity());
                ps.setString(3, elem.getDepartureTime());
                ps.setString(3, elem.getArrivalTime());
                ps.setInt(1, elem.getTotalSeats());
                ps.setDouble(1, elem.getTicketPrice());
                ps.executeUpdate();
            }
            conn.commit();
            // Update the in-memory data
            routes.add(elem);

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
    public void removeRoute(Route elem) {
        try {
            openConnection();
            conn.setAutoCommit(false);

            String deleteString = "DELETE FROM " + tableName + " WHERE sourceCity = ? AND destinationCity = ?;";
            try (PreparedStatement ps = conn.prepareStatement(deleteString)) {
                //ps.setInt(1, patientId);
                ps.executeUpdate();
            }
            conn.commit();
            routes.remove(elem);

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