import controller.CartController;
import controller.Database;
import controller.ProductController;
import model.Category;
import model.Product;
import model.Salesperson;
import org.junit.Test;
import view.CartMenu;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CartMenuTest {
    @Test
    public void viewCartTest() throws IOException {
        Database.initializeAddress();
        HashSet<String> fields = new HashSet<>();
        fields.add("color");
        fields.add("size");
        Category category = new Category("labaniat", null, fields);
        category.setPropertyFields(fields);
        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("color", "yellow");
        properties1.put("size", "big");
        HashMap<String ,String> properties2 = new HashMap<>();
        properties2.put("color", "white");
        properties2.put("size", "small");

        HashMap<String,String> Info = new HashMap<>();
        Salesperson salesperson = new Salesperson(Info,null);

        Product product1 = new Product( "panir", "lighvan", category.getName(), properties1);
        Product product2 = new Product( "shir", "mihan", category.getName(), properties2);
        ProductController.allProducts.add(product1);
        ProductController.allProducts.add(product2);
        salesperson.addToOfferedProducts(product1,5,5000);
        salesperson.addToOfferedProducts(product2,5,2000);
//        CartController.getInstance().addProduct(product1,salesperson);
//        CartController.getInstance().addProduct(product2, salesperson);
        CartMenu cartMenu = new CartMenu(null);
        cartMenu.showCartTable();
    }

    @Test
    public void showProductDigestTest() throws IOException {
        Database.initializeAddress();
        HashSet<String> fields = new HashSet<>();
        fields.add("color");
        fields.add("size");
        Category category = new Category("labaniat", null, fields);
        category.setPropertyFields(fields);
        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("color", "yellow");
        properties1.put("size", "big");
        Product product1 = new Product("panir", "lighvan", category.getName(), properties1);

        HashMap<String, String> personInfo = new HashMap<>();
        personInfo.put("username", "yeki");
        personInfo.put("password", "salam");
        Salesperson seller1 = new Salesperson(personInfo,null);

        seller1.addToOfferedProducts(product1, 2, 1000);

        ArrayList<Salesperson> sellers = new ArrayList<>();
        sellers.add(seller1);

        //stock.put(product1, sellers);
        //showProductDigest(product1);
    }

}
