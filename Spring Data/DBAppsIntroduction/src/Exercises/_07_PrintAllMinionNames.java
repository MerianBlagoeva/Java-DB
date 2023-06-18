package Exercises;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class _07_PrintAllMinionNames {

    public static void main(String[] args) throws SQLException, IOException {
        Connection connection = Utils.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT name FROM minions;");

        ResultSet resultSet = preparedStatement.executeQuery();

        List<String> minionNames = new ArrayList<>();

        while (resultSet.next()) {
            minionNames.add(resultSet.getString("name"));
        }

        for (int i = 0; i < minionNames.size() / 2; i++) {
            System.out.println(minionNames.get(i));
            System.out.println(minionNames.get(minionNames.size() - 1 - i));
        }
    }
}
