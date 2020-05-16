import controller.*;
import model.*;
import org.junit.Test;
import view.CartMenu;
import view.Menu;
import view.ProductMenu;
import view.ViewProductMenu;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class productMenuTest {
    private Object ProductState;

    @Test
    public void viewProductTest() throws IOException {
        Database.initializeAddress();
        Category category = new Category("labaniat", null, new HashSet<>());
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

        Product product1 = new Product( "panir", "lighvan", category.getName(), properties1, false);
        ProductController.allProducts.add(product1);
        ProductMenu productMenu = new ProductMenu(null);
        ViewProductMenu viewProductMenu = new ViewProductMenu(null);
    }

    @Test
    public void commentTest() throws IOException, CartController.AccountIsNotCustomerException, CartController.NotEnoughCreditMoney, CartController.NoLoggedInPersonException {
//        Database.initializeAddress();
//        Category category = new Category(true, "labaniat", null);
//        HashSet<String> fields = new HashSet<>();
//        fields.add("color");
//        fields.add("size");
//        category.setPropertyFields(fields);
//        HashMap<String, String> properties1 = new HashMap<>();
//        properties1.put("color", "yellow");
//        properties1.put("size", "big");
//        HashMap<String ,String> properties2 = new HashMap<>();
//        properties2.put("color", "white");
//        properties2.put("size", "small");
//        HashMap<String ,String> info = new HashMap<>();
//        Customer customer = new Customer(info);
//        customer.setCredit(10000);
//        info.put("username", "ye seller");
//        Salesperson salesperson = new Salesperson(info);
//        PersonController.setLoggedInPerson(customer);
//        Product product1 = new Product("1", "panir", "lighvan", category.getName(), properties1);
//        Product product2 = new Product("2", "shir", "mihan", category.getName(), properties2);
//        ProductRequest pr = new ProductRequest("idrequest", 2000, 2, Request.RequestState.ADD,salesperson, product1);
//        ProductRequest p2 = new ProductRequest("idrequest2", 2000, 2, Request.RequestState.ADD,salesperson, product2);
//        RequestController.acceptRequest(pr);
//        RequestController.acceptRequest(p2);
//        CartController.getInstance().addProduct(product1, salesperson);
//        CartController.getInstance().addProduct(product2, salesperson);
//        ViewProductMenu viewProductMenu = new ViewProductMenu(null);
//        StoreMain.main(null);
//        viewProductMenu.setProduct(product1);
//        viewProductMenu.getCommentMenu().run();
    }

}
