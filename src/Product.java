//Imports the UUID class which allows us to generate unique IDs easily
import java.util.UUID;


//Basic product class which allows us to store data about inventory items
public class Product {

    private int quantity;
    private double price;
    private String name, type, description, id;

    //Product constructor
    Product(String name,int quantity,double price,String type, String description){
        setName(name);
        setId(generateRandomID());
        setQuantity(quantity);
        setPrice(price);
        setType(type);
        setDescription(description);
    }
    public String toString(){
        return "Name:" +  name + "\n " + "ID: "+ id + " \n " +"Stock: " + quantity + "\n " +
                "Price: £" + price + "\n " + "Type: " + type + "\n " +"Description: " + description;
    }

    //method to generate a random unique ID
    //perhaps need to make it

    private String generateRandomID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }




    //Get/Set methods
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public double getPrice(){
        return price;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return type;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }


}
