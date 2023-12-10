import java.util.Scanner;

//This class presents the user with options upon starting the program, and handles user input
//It contains the code to display the output of the functions of the program

public class UserInterface {

    /*While this is not good practice, having your permanent objects as static in a small project
    will greatly reduce the number of lines, increasing readability. */

    //create an instance of the Scanner object to handle user input
    private static final Scanner input = new Scanner(System.in);

    //Create an instance of the DatabaseManager a class which
    private static final DatabaseManager databaseManager = new DatabaseManager();
    //Create an instance of the ProductDAO and pass the DatabaseManager
    private static final ProductDAO dao = new ProductDAO(databaseManager);


    public static void startSequence()
    {
        System.out.println("""
                Hi, welcome to the inventory management system
                Please type the number of the option you would like to select then press enter.
                At any point you may enter q to return to the menu""");
        displayMenu();

    }
    public static void printOptions(){
        System.out.println("""
                1. Check inventory status.\s
                2. Add an item.\s
                3. Remove an item.\s
                4. Place an order.\s
                5. Exit.""");

    }
    public static void displayMenu(){
        String choice;
        printOptions();
        while(true){
            if(input.hasNextInt()){
                choice = input.nextLine();
                switch(choice){
                    case "1" -> inventoryStatus();
                    case "2" -> createProduct();
                    case "3" -> deleteProduct();
                    case "4" -> System.out.println("4");
                    case "5" -> {System.out.println("Bye bye!");
                        return;
                    }
                    default -> System.out.println("You gave a bad input, try again.");
                }
            }else{ return;}
        }

    }
    public static void createProduct() {
        do {
            //Prompt for and store new product information

            String name = processUserInput("Enter the product name:",false);
            int quantity = (int) Double.parseDouble(processUserInput("Enter the product's quantity:",true));
            double price = Double.parseDouble(processUserInput("Enter the product's price:",true));
            String type = processUserInput("Enter the product type:",false);
            String description = processUserInput("Enter the product's description:",false);

            //Create new product from inputs
            Product newProduct = new Product(name, quantity, price, type, description);

            //Add new product to the database
            dao.addItemToDatabase(newProduct);

            System.out.println(newProduct);
        }while(restart("Would you like to add another item? yes/no",UserInterface::printOptions));
    }
    //used to allow users to delete a product from the database
    public static void deleteProduct() {
        do {
            dao.selectAllItems();
            String name = processUserInput("Please enter the name of the product you would like to delete.",false);

            dao.deleteItemFromDatabase(name);

            System.out.println("You have deleted the item called: " + name + "\n");


        }while(restart("Would you like to delete another item? yes/no",UserInterface::printOptions));
    }

    //used to allow the user to check the status of the database, and details about specific items
    public static void inventoryStatus() {

        dao.selectAllItems();

        System.out.println();

        String name = processUserInput("Please enter the name of the product you would like more information about.",false);
        do {
            dao.selectItemFromDatabase(name);
            }while(restart("Would you like information about another product? yes/no?",UserInterface::printOptions));

            }

            //functionality
        //used to allow users to restart a function with a custom message
        //Runnable enables us to repeat the method that we passed to it in the argument, allowing us to restart methods from inside
    private static boolean restart(String message,Runnable backToMenu) {
        System.out.print(message);
        String choice = input.nextLine().toLowerCase();

        switch (choice) {
            case "yes" -> {
                return true;
            }
            case "q", "no" -> {
                backToMenu.run();
                return false;
            }
            default -> {
                System.out.println("Invalid input, please try again.");
                return restart(message, backToMenu);
            }
        }
    }

    //Performs a check by using isValidInput to see if the user input was empty, and allows user to enter q to return to the menu at any time
    //if requireNumeric is set to true, will check if the input is a number
    private static String processUserInput(String message, boolean requireNumeric) {
        try {
            // Get user input
            System.out.print(message);
            String userInput = input.nextLine();

            // Check if the user wants to quit
            if ("q".equalsIgnoreCase(userInput)) {
                System.out.println("Leaving to menu");
                displayMenu();
            }

            // Validate user input
            if (isValidInput(userInput,requireNumeric)) {
                // Process or store the valid input
                return capitalizeFirstLetter(userInput.toLowerCase());
            } else {
                // Handle invalid input
                System.out.println("Invalid input. Please try again. Or enter q to quit.");
                return processUserInput(message,requireNumeric);
            }
        } catch (Exception e) {
            // Handle unexpected exceptions
            System.out.println("An error occurred. Please try again. Or enter q to quit.");
            return processUserInput(message,requireNumeric);
        }
    }

    private static boolean isValidInput(String input, boolean requireNumeric) {
        // non-empty string
        if (input == null || input.trim().isEmpty()) {
            return false;
        }

        // Capitalize the first letter
        input = capitalizeFirstLetter(input);

        if (requireNumeric) {
            try {
                Double.parseDouble(input);
                return true; // input is a valid double
            } catch (NumberFormatException e) {
                return false; // input is not a valid double
            }
        }

        return true; // Input is a non-empty string
    }
    private static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
    public static void main(String[] args){

        startSequence();
    }

}


