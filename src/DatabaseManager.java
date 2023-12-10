import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseManager {

    private static final String JDBC_URL = "jdbc:sqlite:identifier.sqlite";

    // Load the SQLite JDBC driver when the class is initialized
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load SQLite JDBC driver");
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }
    //Unused create table method
    /*private static void createTables() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             Statement statement = connection.createStatement()) {

            String createInventoryItemsTable = "CREATE TABLE IF NOT EXISTS inventory_items (" +
                    "id TEXT PRIMARY KEY," +
                    "name TEXT," +
                    "quantity INTEGER)";

            statement.executeUpdate(createInventoryItemsTable);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create tables");
        }
    } */
}