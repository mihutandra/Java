package repository;

import domain.Identifiable;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DBRepository<T extends Identifiable<ID>, ID> extends MemoryRepository<ID, T> {

    protected final String URL = "jdbc:sqlite://Users//mihutandra//IdeaProjects//a5-mihutandra//dentist_app//src//data//Dentist.db";

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