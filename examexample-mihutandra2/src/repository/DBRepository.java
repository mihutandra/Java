package repository;


import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DBRepository extends RouteRepository {

    protected final String URL = "jdbc:sqlite://Users//mihutandra//IdeaProjects//examexample-mihutandra2//data//test_db.db";
    protected String tableName;
    protected Connection conn = null;

    public DBRepository(String tableName) {
        this.tableName = tableName;
    }

    public abstract void getData();

    public void openConnection() throws SQLException {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(URL);
        if (conn == null || conn.isClosed())
            conn = dataSource.getConnection();
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }
}