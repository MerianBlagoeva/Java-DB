package Exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class _09_IncreaseAgeStoredProcedure {

    public static void main(String[] args) throws SQLException, IOException {
        Connection connection = Utils.getConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("Enter minion id:");
        int minionId = Integer.parseInt(reader.readLine());

        CallableStatement callableStatement = connection
                .prepareCall("CALL usp_get_older(?)");

        callableStatement.setInt(1, minionId);

        callableStatement.execute();

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT name, age FROM minions WHERE id = ?");

        preparedStatement.setInt(1, minionId);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " " + resultSet.getInt(2));
        }

    }
}
