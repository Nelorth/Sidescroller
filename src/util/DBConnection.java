package util;

import java.sql.*;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    private DBConnection() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if (instance == null)
            instance = new DBConnection();
        return instance;
    }

    public ResultSet query(String query) throws SQLException {
        open();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        statement.close();
        close();
        return rs;
    }

    public void update(String update) throws SQLException {
        open();
        PreparedStatement statement = connection.prepareStatement(update);
        statement.executeUpdate();
        statement.close();
        close();
    }

    public void open() throws SQLException {
        connection = DriverManager.getConnection("jdbc:ucanaccess://highscores.accdb");
    }

    public void close() throws SQLException {
        connection.close();
    }
}
