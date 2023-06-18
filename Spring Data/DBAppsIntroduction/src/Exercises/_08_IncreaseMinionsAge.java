package Exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class _08_IncreaseMinionsAge {
    private static Connection connection;

    public static void main(String[] args) throws SQLException, IOException {
        connection = Utils.getConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter minion IDs: ");

        int[] IDs = getMinionIds(reader);

        updateMinions(IDs);

        printMinionsNameAndAge();
    }

    private static int[] getMinionIds(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static void printMinionsNameAndAge() throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT name, age FROM minions");

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getString("name") + " " + resultSet.getInt("age"));
        }
    }

    private static void updateMinions(int[] IDs) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE minions\n" +
                        "SET age = age + 1,\n" +
                        "name = LOWER(name)\n" +
                        "WHERE id = ?;");

        for (int id : IDs) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
