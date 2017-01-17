/**
 * Created by aglenis on 12/21/16.
 */
public class CustomerGenerator {
        private int currId;

        public CustomerGenerator()
        {
            this.currId = 0;
        }
        public static void main(String[] args) {

        }

        public Customer getNext(){
            Customer u = new Customer(this.currId,"newCustomer");
            this.currId++;
            return u;
        }

}
