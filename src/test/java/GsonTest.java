import com.google.gson.reflect.TypeToken;
import controller.Database;
import controller.ProductController;
import model.Category;
import model.Product;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import static controller.ProductController.allProducts;

public class GsonTest {
//    public void GsonTest {
//        ArrayList <Test> tests = new ArrayList<Test>();
//        tests.add(new Test("yalda", 1));
//        tests.add(new Test("solale",2));
//        tests.add(new Test("alireza",3));
//        Database.setAddress(System.getProperty("user.dir") + "text2.json");
//        Type collectionType = new TypeToken<Collection<Test>>() {}.getType();
//        Database.write(tests, collectionType);
//        System.out.println(Database.handleJsonArray("password"));
////        ArrayList<Test> allTests = new ArrayList<Test>();
////        allTests.add(new Test("hello", "1"));
////        allTests.add(new Test("salam", "2"));
////        Database.setAddress(System.getProperty("user.dir") + "text.json");
////
////        Type collectionType = new TypeToken<Collection<Test>>() {}.getType();
////
////        Database.write(allTests, collectionType);
////        System.out.println(allTests = Database.read(collectionType));
////        allTests.add(new Test("goodbye", "1"));
////        Database.write(allTests, new TypeToken<ArrayList<Test>>() {}.getType());
////        System.out.println(Database.read(collectionType));
//    }
//    class Test {
//        public String name;
//        public int password;
//
//        public Test(String name, int password) {
//            this.name = name;
//            this.password = password;
//        }
//    }

    @Test
    public void fileTest() {
        try {
            System.out.println(Database.deleteFile("C:\\Users\\HAMID\\Desktop\\json\\json.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void stock() {
        Database.initializeAddress();
        ProductController.getInstance().initializeStock();

    }

    @Test
    public void testForGSONObjInObj () {

        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("color", "yellow");
        properties1.put("size", "big");
        Category category = new Category("labaniat", null, new HashSet<>());
        Product product = new Product( "panir", "lighvan",
                 category.getName(), properties1);

        //aval properties category ro comment kon bad test kon

            Database.write(product, "C:\\Users\\HAMID\\Desktop\\yalda.json");


    }

    @Test
    public void deleteFile () {
        File temp = new File ("C:\\Users\\HAMID\\Desktop\\json\\json.json");
        try {
            if (temp.createNewFile()) {
                System.out.println("file created");
            } else {
                System.out.println("file exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void appendFile() {
        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("color", "yellow");
        properties1.put("size", "big");
        Category category = new Category("labaniat", null, new HashSet<>());

        Product product = new Product("panir", "lighvan",
                category.getName(), properties1);

        Product product1 = new Product("shir", "mihan", category.getName(),
                properties1);
        //Database.writeAppend(product, "C:\\Users\\HAMID\\Desktop\\Product.json");
        System.out.println(Database.read(Product.class, "C:\\Users\\HAMID\\Desktop\\Product.json"));
    }

    @Test
    public void getProperty() {
        String path = System.getProperty("user.dir");

        System.out.println("Working Directory = " + path);
    }

    @Test
    public void edit()  {
        Database.initializeAddress();
        Category category = new Category("labaniat", null, new HashSet<>());
        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("color", "yellow");
        Product product = new Product( "panir", "lighvan",
                category.getName(), properties1);
        product.setName("shiiir");
        Database.editInFile(product, "products", product.getID());
    }
}