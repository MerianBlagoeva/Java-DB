package Exercises;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class _04_AddMinion {
    private static Connection connection;

    public static void main(String[] args) throws SQLException, IOException {
        connection = Utils.getConnection();
        Scanner sc = new Scanner(System.in);

        String[] minionInfo = sc.nextLine().split("\\s+");
        String minionName = minionInfo[1];
        int minionAge = Integer.parseInt(minionInfo[2]);
        String minionTown = minionInfo[3];
        String villainName = sc.nextLine().split("\\s+")[1];


        addTownIfItDoesntExist(minionTown);
        addVillainIfItDoesntExist(villainName);
        addMinion(minionName, minionAge, minionTown);
        setVillainToMinion(minionName, villainName);
    }

    private static void setVillainToMinion(String minionName, String villainName) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO minions_villains VALUES (?, ?);");
        int minionId = findEntityIdByName(minionName, "minions");
        int villainId = findEntityIdByName(villainName, "villains");
        preparedStatement.setInt(1, minionId);
        preparedStatement.setInt(2, villainId);
        preparedStatement.executeUpdate();

        System.out.printf("Successfully added %s to be minion of %s.%n", minionName, villainName);
    }

    private static void addMinion(String minionName, int minionAge, String minionTown) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO minions(name, age, town_id) VALUES(?, ?, ?)");

        preparedStatement.setString(1, minionName);
        preparedStatement.setInt(2, minionAge);
        preparedStatement.setInt(3, findEntityIdByName(minionTown, "towns"));

        preparedStatement.executeUpdate();
    }

    private static int findEntityIdByName(String name, String tableName) throws SQLException {
        String query = String.format("SELECT DISTINCT id FROM %s WHERE name = ?", tableName);
        PreparedStatement preparedStatement = connection
                .prepareStatement(query);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

        return -1;
    }

    private static void addVillainIfItDoesntExist(String villainName) throws SQLException {
        if (!checkIfExists(villainName, "villains")) {
            PreparedStatement addVillain = connection
                    .prepareStatement("INSERT INTO villains(name, evilness_factor) VALUES(?, ?)");
            addVillain.setString(1, villainName);
            addVillain.setString(2, "evil");

            addVillain.executeUpdate();

            System.out.printf("Villain %s was added to the database.%n", villainName);
        }
    }

    private static void addTownIfItDoesntExist(String minionTown) throws SQLException {
        if (!checkIfExists(minionTown, "towns")) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO towns(name) VALUES(?);");
            preparedStatement.setString(1, minionTown);

            preparedStatement.executeUpdate();

            System.out.printf("Town %s was added to the database.%n", minionTown);
        }
    }

    private static boolean checkIfExists(String columnName, String tableName) throws SQLException { //Returns true if the entity exists, false if it doesn't
        String query = String.format("SELECT COUNT(*) FROM %s WHERE name = ?", tableName);

        PreparedStatement preparedStatement = connection
                .prepareStatement(query);
        preparedStatement.setString(1, columnName);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt(1) == 1;
        }

        return false;
    }
}