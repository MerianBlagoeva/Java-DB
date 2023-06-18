package Exercises;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum Utils {
    ;
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";

    private static final String DATABASE_NAME = "minions_db";


    static Connection getConnection() throws SQLException {
//        System.out.println("Enter user: ");
//        String user = reader.readLine();
//        System.out.println("Enter password: ");
//        String password = reader.readLine();

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");

        return DriverManager.getConnection(CONNECTION_STRING + DATABASE_NAME, properties);

    }
}
