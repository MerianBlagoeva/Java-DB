package Exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

public class GetMinionNames {
    private static Connection connection;
    public static void main(String[] args) throws SQLException, IOException {
        connection = Utils.getConnection();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter villain id: ");
        int villainId = Integer.parseInt(reader.readLine());

//        String villainName = findVillainNameById(villainId);

        String villainName = findEntityNameById(villainId);

        if (villainName != null) {
            System.out.println("Villain: " + villainName);
        }

        Set<String> allMinionsByVillainId = getAllMinionsByVillainId(villainId);

        if (allMinionsByVillainId != null) {
            allMinionsByVillainId.forEach(System.out::println);
        }

    }
    private static String findEntityNameById(int entityId) throws SQLException {
        String query = String.format("SELECT name FROM %s WHERE id = ?", "villains");
        PreparedStatement preparedStatement = connection
                .prepareStatement(query);
        preparedStatement.setInt(1, entityId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString(1);
        }

        return null;
    }

    private static Set<String> getAllMinionsByVillainId(int villainId) throws SQLException, IOException {
        Set<String> result = new LinkedHashSet<>();
        PreparedStatement preparedStatement = connection
                .prepareStatement("""
                        SELECT m.name, m.age FROM minions m
                        JOIN minions_villains mv on m.id = mv.minion_id
                        WHERE mv.villain_id = ?;""");

        preparedStatement.setInt(1, villainId);

        ResultSet resultSet = preparedStatement.executeQuery();

        int counter = 1;

        if (resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                result.add(String.format("%d. %s %d",
                        counter++, resultSet.getString("name"),
                        resultSet.getInt("age")));
            }
        } else {
            System.out.println("No villain with ID " + villainId + " exists in the database.");
            return null;
        }

        return result;
    }
}
