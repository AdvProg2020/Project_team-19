import controller.CategoryController;
import model.Category;
import model.Product;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CategoryTest {

    @Test
    public void checkValidCategoryTest (  ) {
        Category food = new Category ( true , "Food" , null );
        Category sweet = new Category ( false , "Sweet" , food );
        Category bitter = new Category ( false , "Bitter" , food );
        Category sour = new Category ( false , "Sour" , food );
        Category aSweet = new Category ( false , "A" , sweet );
        Category bSweet = new Category ( false , "B" , sweet );
        Category aBitter = new Category ( false , "A" , bitter );
        Category cBitter = new Category ( false , "C" , bitter );
        Category bSour = new Category ( false , "B" , sour );
        Category cSour = new Category ( false , "C" , sour );

       // Assert.assertEquals ( false , Category.checkValidCategory ( "Food/Sweet/C" ) );

    }

    @Test
    public void checkViewCategory() throws IOException {
        Category food = new Category ( false, "Food" , null );
        Category sweet = new Category ( false , "Sweet" , food );
        Category bitter = new Category ( true , "Bitter" , food );
        Category sour = new Category ( true , "Sour" , sweet );

        HashSet<String> fields = new HashSet<>();
        fields.add("color");
        fields.add("size");
        bitter.setPropertyFields(fields);
        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("color", "yellow");
        properties1.put("size", "big");
        HashMap<String ,String> properties2 = new HashMap<>();
        properties2.put("color", "white");
        properties2.put("size", "small");


        Product product1 = new Product("1", "panir", "lighvan",
                bitter.getName(), properties1);


        Product product2 = new Product("2", "shir", "mihan",
                bitter.getName(), properties2);

        ArrayList<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        bitter.setProductList(products);
        sour.setProductList(products);
        CategoryController.viewAllCategories();
    }
}
