package Exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class _06_RemoveVillain {
    private static Connection connection;
    public static void main(String[] args) throws SQLException, IOException {

        connection = Utils.getConnection();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter villain id:");
        int villainId = Integer.parseInt(reader.readLine());

        int affectedEntities = deleteMinionsByVillainId(villainId);

        if (affectedEntities == 0) {
            System.out.println("No such villain was found");
            return;
        }

        String villainName = findEntityNameById(villainId, "villains");
        deleteVillainById(villainId);

        System.out.printf("%s was deleted%n%d minions released%n", villainName, affectedEntities);
    }

    private static int deleteMinionsByVillainId(int villainId) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM minions_villains WHERE villain_id = ?");
        preparedStatement.setInt(1, villainId);

        return preparedStatement.executeUpdate();
    }

    private static String findEntityNameById(int entityId, String tableName) throws SQLException {
        String query = String.format("SELECT name FROM %s WHERE id = ?", tableName);
        PreparedStatement preparedStatement = connection
                .prepareStatement(query);
        preparedStatement.setInt(1, entityId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString(1);
        }

        return null;
    }

    private static void deleteVillainById(int villainId) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM villains WHERE id = ?");
        preparedStatement.setInt(1, villainId);

        preparedStatement.executeUpdate();
    }
}
