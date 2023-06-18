package Exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class _05_ChangeTownNamesCasing {
    private static Connection connection;

    public static void main(String[] args) throws IOException, SQLException {
        connection = Utils.getConnection();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter country name:");
        String countryName = reader.readLine();

        int rowsAffected = getRowsAffected(connection, countryName);

        System.out.println(rowsAffected == 0 ? "No town names were affected." : rowsAffected + " town names were affected.");

        List<String> towns = getUpdatedTowns(countryName);

        if (rowsAffected != 0) {
            System.out.println("[" + String.join(", ", towns) + "]");
        }
    }

    private static List<String> getUpdatedTowns(String countryName) throws SQLException {
        PreparedStatement preparedStatementTowns = connection
                .prepareStatement("SELECT name FROM towns WHERE country = ?");

        preparedStatementTowns.setString(1, countryName);
        ResultSet resultSet = preparedStatementTowns.executeQuery();

        List<String> towns = new ArrayList<>();
        while (resultSet.next()) {
            towns.add(resultSet.getString("name"));
        }
        return towns;
    }

    private static int getRowsAffected(Connection connection, String countryName) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE towns SET name = UPPER(name) WHERE country = ?");

        preparedStatement.setString(1, countryName);

        return preparedStatement.executeUpdate();
    }
}
