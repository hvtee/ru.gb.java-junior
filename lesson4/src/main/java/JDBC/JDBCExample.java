package JDBC;

import com.github.javafaker.Faker;

import java.sql.*;
import java.util.UUID;

public class JDBCExample {
    public static void main(String[] args) throws SQLException {
        Connection connection = openConnection();
        Statement statement = connection.createStatement();

        try {
            createTable(statement);
            fillTable(statement);
            printAllBooks(executeStatement(statement, "SELECT * FROM java_junior_dz4.books_jdbc;"));
            deleteTable(statement);
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.printf("Error: %s", exception.getMessage());
        }

        closeConnection(connection);
    }


    public static Connection openConnection() {
        Connection connection = null;
        try {

            String url = "jdbc:mysql://localhost:3306/java_junior_dz4";
            String username = "arteml_admin";
            //todo Delete password!!!
            String password = ;

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection opened");
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Connection not opened");
            System.out.println();
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
            System.out.println("Connection closed");
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Connection not closed");
            System.out.println();
            e.printStackTrace();
        }
    }

    public static void createTable(Statement statement) throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS `java_junior_dz4`.`books_jdbc` (\n" +
                "  `book_id` VARCHAR(36) NOT NULL,\n" +
                "  `book_name` VARCHAR(45) NOT NULL,\n" +
                "  `book_author` VARCHAR(45) NOT NULL,\n" +
                "  PRIMARY KEY (`book_id`));\n");
        System.out.println("Table was created");
        System.out.println();
    }

    public static void deleteTable(Statement statement) throws SQLException {
        statement.execute("DROP TABLE `java_junior_dz4`.`books_jdbc`;");
        System.out.println("Table was deleted");
        System.out.println();
    }

    public static void fillTable(Statement statement) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            UUID uuid = UUID.randomUUID();
            String bookName = faker.book().title();
            String authorName = faker.book().author();

            // Using a prepared statement
            PreparedStatement preparedStatement = statement.getConnection().prepareStatement(
                    "INSERT INTO `java_junior_dz4`.`books_jdbc` (`book_id`, `book_name`, `book_author`) VALUES (?, ?, ?)");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, bookName);
            preparedStatement.setString(3, authorName);

            preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.printf("%s: Added row: %s, %s, %s\n", i, uuid, bookName, authorName);
        }
        System.out.println();
    }

    public static ResultSet executeStatement(Statement statement, String query) throws SQLException {
        ResultSet resultSet = statement.executeQuery(query);
        System.out.printf("The following statement was executed: %s\n", query);
        return resultSet;
    }

    public static void printAllBooks(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String bookId = resultSet.getString("book_id");
            String bookName = resultSet.getString("book_name");
            String bookAuthor = resultSet.getString("book_author");

            System.out.printf("Book ID: %s, Book Name: %s, Book Author: %s\n", bookId, bookName, bookAuthor);
        }
        System.out.println();
    }

}
