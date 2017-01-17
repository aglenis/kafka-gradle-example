/**
 * Created by aglenis on 12/21/16.
 */
public class Customer {
    private Integer customerID;
    private String customerName;
    public Customer(Integer ID, String name) {
        this.customerID = ID;
        this.customerName = name;
    }
    public Integer getID() {
        return customerID;
    }
    public String getName() {
        return customerName;
    }
}
