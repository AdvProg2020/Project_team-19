import controller.ProductController;
import model.Category;
import model.Product;
import org.junit.Test;
import view.ProductMenu;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class productMenuTest {
    private Object ProductState;

    @Test
    public void viewProductTest() throws IOException {
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

        Product product1 = new Product("1", "panir", "lighvan", category.getName(), properties1);
        ProductController.allProducts.add(product1);
        ProductMenu productMenu = new ProductMenu(null);
        productMenu.viwProduct(product1.getID());

    }
}
