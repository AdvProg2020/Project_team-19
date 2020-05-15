import controller.*;
import model.*;
import org.junit.Test;
import view.CartMenu;
import view.ViewProductMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static model.Product.stock;

public class CartMenuTest {
    @Test
    public void viewCartTest() throws IOException {
        Database.initializeAddress();
        Category category = new Category(true, "labaniat", null);
        HashSet<String> fields = new HashSet<>();
        fields.add("color");
        fields.add("size");
        category.setPropertyFields(fields);
        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("color", "yellow");
        properties1.put("size", "big");
        HashMap<String ,String> properties2 = new HashMap<>();
        properties2.put("color", "white");
        properties2.put("size", "small");

        HashMap<String,String> Info = new HashMap<>();
        Salesperson salesperson = new Salesperson(Info);

        Product product1 = new Product("1", "panir", "lighvan", category.getName(), properties1);
        Product product2 = new Product("2", "shir", "mihan", category.getName(), properties2);
        ProductController.allProducts.add(product1);
        ProductController.allProducts.add(product2);
        salesperson.addToOfferedProducts(product1,5,5000);
        salesperson.addToOfferedProducts(product2,5,2000);
        CartController.getInstance().addProduct(product1,salesperson);
        CartController.getInstance().addProduct(product2, salesperson);
        CartMenu cartMenu = new CartMenu(null);
        cartMenu.showCartTable();
    }

    @Test
    public void purchaseTest() throws IOException, CartController.AccountIsNotCustomerException, CartController.NotEnoughCreditMoney, CartController.NoLoggedInPersonException {
        Database.initializeAddress();
        Category category = new Category(true, "labaniat", null);
        HashSet<String> fields = new HashSet<>();
        fields.add("color");
        fields.add("size");
        category.setPropertyFields(fields);
        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("color", "yellow");
        properties1.put("size", "big");
        HashMap<String ,String> properties2 = new HashMap<>();
        properties2.put("color", "white");
        properties2.put("size", "small");
        HashMap<String ,String> info = new HashMap<>();
        Customer customer = new Customer(info);
        customer.setCredit(10000);
        info.put("username", "ye seller");
        Salesperson salesperson = new Salesperson(info);
        PersonController.setLoggedInPerson(customer);
        Product product1 = new Product("1", "panir", "lighvan", category.getName(), properties1);
        Product product2 = new Product("2", "shir", "mihan", category.getName(), properties2);
        ProductRequest pr = new ProductRequest("idrequest", 2000, 2, Request.RequestState.ADD,salesperson, product1);
        ProductRequest p2 = new ProductRequest("idrequest2", 2000, 2, Request.RequestState.ADD,salesperson, product2);
        RequestController.acceptRequest(pr);
        RequestController.acceptRequest(p2);
        CartController.getInstance().addProduct(product1, salesperson);
        CartController.getInstance().addProduct(product2, salesperson);
        CartMenu cartMenu = new CartMenu(null);
        System.out.println(customer.getCredit());
        System.out.println(customer.getCart().calculateTotalPrice());
        CartController.getInstance().purchase();

    }

    @Test
    public void showProductDigestTest() throws IOException {
        Database.initializeAddress();
        Category category = new Category(true, "labaniat", null);
        HashSet<String> fields = new HashSet<>();
        fields.add("color");
        fields.add("size");
        category.setPropertyFields(fields);
        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("color", "yellow");
        properties1.put("size", "big");
        Product product1 = new Product("100", "panir", "lighvan", category.getName(), properties1);

        HashMap<String, String> personInfo = new HashMap<>();
        personInfo.put("username", "yeki");
        personInfo.put("password", "salam");
        Salesperson seller1 = new Salesperson(personInfo);

        seller1.addToOfferedProducts(product1, 2, 1000);

        ArrayList<Salesperson> sellers = new ArrayList <> ();
        sellers.add(seller1);

        stock.put(product1, sellers);
        ViewProductMenu viewProductMenu = new ViewProductMenu ( null );
        viewProductMenu.setProduct ( product1 );
        viewProductMenu.showProductDigest();
    }

}
