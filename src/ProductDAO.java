import java.sql.*;

//This class is the interface between the Database and the Product class
//The methods contained in this class handle the actions performed in the database
//They will be called from the UserInterface class with valid input.
public class ProductDAO {
    private static final String INSERT_ITEM_SQL = "INSERT INTO inventory_items (id, name, quantity, price, type, description) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ITEM_SQL = "SELECT id, name, quantity, price, type, description " + "FROM inventory_items WHERE name = ?";
    private static final String DELETE_ITEM_SQL = "DELETE FROM inventory_items WHERE name = ?";
    private static final String SELECT_ALL_SQL = "SELECT name, quantity, price FROM inventory_items";
    private final DatabaseManager databaseManager;

    public ProductDAO(DatabaseManager databaseManager) {
    this.databaseManager = databaseManager;
    }

    public void addItemToDatabase(Product product) {
            try (Connection connection = databaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ITEM_SQL)) {

                //Set parameters for the PreparedStatement
                preparedStatement.setString(1, product.getId());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setInt(3, product.getQuantity());
                preparedStatement.setDouble(4,product.getPrice());
                preparedStatement.setString(5, product.getType());
                preparedStatement.setString(6, product.getDescription());

                //Execute the update
                preparedStatement.executeUpdate();

                System.out.println("Item added to the database.");
            }catch(SQLException e) {
                e.printStackTrace();
            }
                //Execute the update
    }
    public void selectItemFromDatabase(String name) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ITEM_SQL))
        {
            preparedStatement.setString(1, name);

            //ResultSet is a class included in SQL that represents a set of data
            // Contains rows and columns to hold the requested data elements
            //Moves the results of the select item query into the result set
            ResultSet rs = preparedStatement.executeQuery();

            //Format output nicely into columns
            System.out.printf("%-20s %-10s %-10s %-15s %-20s %-10s\n",
                    "Name", "Quantity", "Price", "Type", "Description", "ID");

            //rs.next() moves the cursor forward one row
            //if the row is empty, this means that item was not found
            if (!rs.next()) {
                System.out.println("Item not found in the database.");
                return;
            }

            //By using the rs.get methods we can retrieve information from our result set
            do {
                System.out.printf("%-20s %-10d £%-9.2f %-15s %-20s %-10s\n",
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("id"));
            } while (rs.next()); //While a value exists in resultSet

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Selects all items and prints out their name quantity and price
    public void selectAllItems() {
        System.out.printf("%-20s %-10s %-10s\n", "Name", "Quantity", "Price");

        try (Connection connection = databaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SELECT_ALL_SQL)) {

            while (rs.next()) {
                System.out.printf("%-20s %-10d £%-9.2f\n",
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteItemFromDatabase(String name){
        try (Connection connection = databaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ITEM_SQL))
        {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();

        }catch(SQLException e){
        throw new RuntimeException(e);
        }
    }

    }

