package Exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Utils {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";

    private static final String DATABASE_NAME = "minions_db";

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    static Connection getConnection() throws SQLException, IOException {
        System.out.println("Enter user: ");
        String user = reader.readLine();
        System.out.println("Enter password: ");
        String password = reader.readLine();

        //If you don't want to enter your password each time, comment out this code and write it directly in the properties.setProperty

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        return DriverManager.getConnection(CONNECTION_STRING + DATABASE_NAME, properties);

    }
}
