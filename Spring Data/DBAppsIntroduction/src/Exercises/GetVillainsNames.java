package Exercises;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetVillainsNames {
    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getConnection();

        PreparedStatement preparedStatement =
                connection.prepareStatement("""
                        SELECT v.name, COUNT(DISTINCT mv.minion_id) AS minions_count
                        FROM villains AS v
                        JOIN minions_villains mv on v.id = mv.villain_id
                        GROUP BY v.name
                        HAVING minions_count > ?
                        ORDER BY minions_count DESC;""");

        preparedStatement.setInt(1, 15);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d%n", resultSet.getString(1),
                    resultSet.getInt(2));
        }

        connection.close();
    }
}
