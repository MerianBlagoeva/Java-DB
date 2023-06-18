package Exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

public class Main {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db";
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Connection connection;

    public static void main(String[] args) throws SQLException, IOException {

        connection = getConnection();

        System.out.println("Enter exercise number");
        int exerciseNumber = Integer.parseInt(reader.readLine());

        switch (exerciseNumber) {
            case 2 -> exerciseTwo();
            case 3 -> exerciseThree();
            case 4 -> exerciseFour();
            case 5 -> exerciseFive();
            case 6 -> exerciseSix();
            case 7 -> exerciseSeven();
            case 9 -> exerciseNine();
        }
    }

    private static void exerciseTwo() throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("""
                        SELECT v.name, COUNT(DISTINCT mv.minion_id) AS m_count
                        FROM villains AS v
                        JOIN minions_villains mv on v.id = mv.villain_id
                        GROUP BY v.name
                        HAVING m_count > ?
                        ORDER BY m_count DESC;""");

        preparedStatement.setInt(1, 15);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d%n", resultSet.getString(1),
                    resultSet.getInt(2));
        }
    }

    private static void exerciseThree() throws IOException, SQLException {
        System.out.println("Enter villain id: ");
        int villainId = Integer.parseInt(reader.readLine());

//        String villainName = findVillainNameById(villainId);

        String villainName = findEntityNameById(villainId, "villains");

        if (villainName != null) {
            System.out.println("Villain: " + villainName);
        }

        Set<String> allMinionsByVillainId = getAllMinionsByVillainId(villainId);

        if (allMinionsByVillainId != null) {
            allMinionsByVillainId.forEach(System.out::println);
        }
    }

    private static void exerciseFour() {

    }

    private static void exerciseFive() throws IOException, SQLException {
        System.out.println("Enter country name:");
        String countryName = reader.readLine();

        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE towns SET name = UPPER(name) WHERE country = ?");

        preparedStatement.setString(1, countryName);

        int rowsAffected = preparedStatement.executeUpdate();

        System.out.println(rowsAffected == 0 ? "No town names were affected." : rowsAffected + " town names were affected.");

        PreparedStatement preparedStatementTowns = connection
                .prepareStatement("SELECT name FROM towns WHERE country = ?");

        preparedStatementTowns.setString(1, countryName);
        ResultSet resultSet = preparedStatementTowns.executeQuery();

        List<String> towns = new ArrayList<>();
        while (resultSet.next()) {
            towns.add(resultSet.getString("name"));
        }

        if (rowsAffected != 0) {
            System.out.println("[" + String.join(", ", towns) + "]");
        }
    }

    private static void exerciseSix() throws IOException, SQLException {
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

    private static void exerciseSeven() throws SQLException {
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

    private static Connection getConnection() throws IOException, SQLException {
//        System.out.println("Enter user: ");
//        String user = reader.readLine();
//        System.out.println("Enter password: ");
//        String password = reader.readLine();

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");


        return DriverManager.getConnection(CONNECTION_STRING + DATABASE_NAME, properties);
    }

    private static void exerciseNine() throws IOException, SQLException {
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

    private static String findVillainNameById(int villainId) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT name FROM villains WHERE id = ?;");

        preparedStatement.setInt(1, villainId);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        if (!resultSet.isBeforeFirst()) {
            return resultSet.getString("name");
        }

        return null;
    }

    private static Set<String> getAllMinionsByVillainId(int villainId) throws SQLException {
        Set<String> result = new LinkedHashSet<>();
        PreparedStatement preparedStatement = connection
                .prepareStatement("""
                        SELECT m.name, m.age FROM minions m
                        JOIN minions_villains mv on m.id = mv.minion_id
                        WHERE mv.villain_id = ?;""");

        preparedStatement.setInt(1, villainId);

        ResultSet resultSet = preparedStatement.executeQuery();

        int counter = 1;

        if (!resultSet.next()) {
            System.out.println("No villain with ID " + villainId + "exists in the database.");
            return null;
        }

        while (resultSet.next()) {
            result.add(String.format("%d. %s %d",
                    counter++, resultSet.getString("name"),
                    resultSet.getInt("age")));
        }

        return result;
    }

    private static int deleteMinionsByVillainId(int villainId) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM minions_villains WHERE villain_id = ?");
        preparedStatement.setInt(1, villainId);

        return preparedStatement.executeUpdate();
    }

    private static void deleteVillainById(int villainId) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM villains WHERE id = ?");
        preparedStatement.setInt(1, villainId);

        preparedStatement.executeUpdate();
    }
}
