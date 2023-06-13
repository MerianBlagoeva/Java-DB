import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class DiabloDemo {
    private static final String SELECT_USER_GAMES_COUNT_BY_USERNAME = "SELECT first_name, last_name, COUNT(ug.game_id)\n" +
            "FROM users AS u "+
            "JOIN users_games AS ug " +
            "ON ug.user_id = u.id " +
            "WHERE user_name = ? " +
            "GROUP BY u.id;";
    public static void main(String[] args) throws SQLException {

        Connection connection1 = getMySQLConnection();

        String username = readUsername();

        PreparedStatement statement = connection1.prepareStatement(SELECT_USER_GAMES_COUNT_BY_USERNAME);
        statement.setString(1, username);

        ResultSet resultSet = statement.executeQuery();
        boolean hasRow = resultSet.next();

        if (hasRow) {
            System.out.println("User: " + username);
            System.out.printf("%s %s has played %d games",
                    resultSet.getString("first_name"), resultSet.getString("last_name"),
                    resultSet.getInt(3));
        } else {
            System.out.println("No such user exists");
        }
    }

    private static String readUsername() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username");
        return sc.nextLine();
    }

    private static Connection getMySQLConnection() throws SQLException {
        Properties userPass = new Properties();
        userPass.setProperty("user", "root");
        userPass.setProperty("password", "root");

//        Connection connection = DriverManager.getConnection
//                ("jdbc:mysql://localhost:3306/diablo", "root", "root");

        Connection connection1 = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/diablo", userPass);
        return connection1;
    }
}
