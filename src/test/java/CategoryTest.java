import controller.CategoryController;
import model.Category;
import model.Product;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static controller.CategoryController.rootCategories;

public class CategoryTest {

    @Test
    public void checkValidCategoryTest (  ) {
//        Category food = new Category ( "Food" , null , new HashSet<>());
//        Category sweet = new Category (  "Sweet" , food , new HashSet<>());
//        Category bitter = new Category ( "Bitter" , food, new HashSet<>() );
//        Category sour = new Category ("Sour" , food, new HashSet<>() );
//        Category aSweet = new Category (  "A" , sweet, new HashSet<>() );
//        Category bSweet = new Category ( "B" , sweet , new HashSet<>());
//        Category aBitter = new Category (  "A" , bitter, new HashSet<>() );
//        Category cBitter = new Category (  "C" , bitter, new HashSet<>() );
//        Category bSour = new Category (  "B" , sour, new HashSet<>() );
//        Category cSour = new Category (  "C" , sour , new HashSet<>());

       // Assert.assertEquals ( false , Category.checkValidCategory ( "Food/Sweet/C" ) );

    }

    @Test
    public void checkGetCategory() {
//        Category food = new Category ( "Food" , null, new HashSet<>() );
//        Category sweet = new Category (  "Sweet" , food , new HashSet<>());
//        Category bitter = new Category (  "Bitter" , food , new HashSet<>());
//        Category sour = new Category (  "Sour" , sweet , new HashSet<>());
//        rootCategories.add(food);
//        Assert.assertEquals(sour, CategoryController.getInstance().getCategoryByName("Sour", rootCategories));
    }

    @Test
    public void checkViewCategory() throws IOException {
//        Category food = new Category ( "Food" , null, new HashSet<>() );
//        Category sweet = new Category (  "Sweet" , food , new HashSet<>());
//        Category bitter = new Category (  "Bitter" , food , new HashSet<>());
//        Category sour = new Category (  "Sour" , sweet , new HashSet<>());
//
//        HashSet<String> fields = new HashSet<>();
//        fields.add("color");
//        fields.add("size");
//        bitter.setPropertyFields(fields);
//        HashMap<String, String> properties1 = new HashMap<>();
//        properties1.put("color", "yellow");
//        properties1.put("size", "big");
//        HashMap<String ,String> properties2 = new HashMap<>();
//        properties2.put("color", "white");
//        properties2.put("size", "small");
//
//
//        Product product1 = new Product( "panir", "lighvan",
//                bitter.getName(), properties1,false);
//
//
//        Product product2 = new Product( "shir", "mihan",
//                bitter.getName(), properties2,false);
//
//        ArrayList<Product> products = new ArrayList<>();
//        products.add(product1);
//        products.add(product2);
//
//        bitter.setProductList(products);
//        sour.setProductList(products);
        //CategoryController.viewAllCategories();
    }
}
