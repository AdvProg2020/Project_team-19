import controller.CartController;
import controller.ProductController;
import model.Cart;
import model.Customer;
import model.Product;
import model.Salesperson;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class purchaseTest {
    @Test
    public void purchaseTest() throws IOException {
        HashMap<String,String> Info = new HashMap<>();
        Customer customer = new Customer(Info);
        customer.setCredit(100000);
        Product product1 = new Product("lip stick","beauty","",Info, false);
        Product product2 = new Product("comb","beauty","",Info, false);
        Salesperson salesperson = new Salesperson(Info);
        salesperson.addToOfferedProducts(product1,5,5000);
        salesperson.addToOfferedProducts(product2,5,2000);
        customer.getCart().addProduct(product1,salesperson);
        customer.getCart().addProduct(product2,salesperson);
       // customer.getCart().setProductCount(product1,3);
        Cart.purchase(customer);
    }
}
