import controller.CategoryController;
import controller.Database;
import controller.ProductController;
import model.Cart;
import model.Category;
import model.Product;
import model.Salesperson;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static controller.CategoryController.rootCategories;
import static controller.ProductController.allProducts;

public class ProductTest {
    @Test
    public void filterByPropertyTest () {
        Database.initializeAddress();
        Category category = new Category("labaniat", null, new HashSet <> (  ));
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

        Product product1 = new Product( "panir", "lighvan",
                category.getName(), properties1);

        Product product2 = new Product( "shir", "mihan",
                category.getName(), properties2);

        ArrayList<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        category.setProductList(products);

        Assert.assertEquals(ProductController.getInstance().filterByField("color", "yellow", allProducts).get(0), product1);

    }

    @Test
    public void testSortProduct (){
        Database.initializeAddress();
        Category category = new Category("labaniat", null, new HashSet <> (  ));
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

        HashMap<String, String> personInfo = new HashMap<>();
        personInfo.put("username", "yeki");
        personInfo.put("password", "salam");

        HashMap<String, String> personInfo2 = new HashMap<>();
        personInfo2.put("username", "yeki dige");
        personInfo2.put("password", "salam");

        Salesperson seller1 = new Salesperson(personInfo);
        Salesperson seller2 = new Salesperson(personInfo2);
        ArrayList<Salesperson> owners1 = new ArrayList<>();
        owners1.add(seller1);
        owners1.add(seller2);

        ArrayList<Salesperson> owners2 = new ArrayList<>();
        owners2.add(seller2);

        Product product1 = new Product("shir", "zighvan",
                category.getName(), properties1);


        Product product2 = new Product( "shir", "mihan",
                category.getName(), properties2);


        seller1.addToOfferedProducts(product1, 2, 2000);
        seller2.addToOfferedProducts(product1, 4, 2500);
        seller2.addToOfferedProducts(product2, 3, 1000);

        ProductController.stock = new HashMap<>();
        ProductController.stock.put(product1, owners1);
        ProductController.stock.put(product2, owners2);

        ArrayList<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        category.setProductList(products);

        ProductController.getInstance().sortProduct(products,true, true, true, false);
        Assert.assertEquals(products.get(0).getBrand(), "mihan");

    }

    @Test
    public void testRemoveForManager() {
        Database.initializeAddress();
        Category category = new Category("labaniat", null, new HashSet <> (  ));
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

        HashMap<String, String> personInfo = new HashMap<>();
        personInfo.put("username", "yeki");
        personInfo.put("password", "salam");

        HashMap<String, String> personInfo2 = new HashMap<>();
        personInfo2.put("username", "yeki dige");
        personInfo2.put("password", "salam");

        Salesperson seller1 = new Salesperson(personInfo);
        Salesperson seller2 = new Salesperson(personInfo2);
        ArrayList<Salesperson> owners1 = new ArrayList<>();
        owners1.add(seller1);
        owners1.add(seller2);

        ArrayList<Salesperson> owners2 = new ArrayList<>();
        owners2.add(seller2);

        Product product1 = new Product("shir", "zighvan",
                category.getName(), properties1);


        Product product2 = new Product( "shir", "mihan",
                category.getName(), properties2);


        seller1.addToOfferedProducts(product1, 2, 2000);
        seller2.addToOfferedProducts(product1, 4, 2500);
        seller2.addToOfferedProducts(product2, 3, 1000);

        ProductController.stock = new HashMap<>();
        ProductController.stock.put(product1, owners1);
        ProductController.stock.put(product2, owners2);

        ArrayList<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        category.setProductList(products);
        ProductController.getInstance().removeProductForManager(product1);
        Assert.assertNull(seller2.getOfferedProducts().get(product1));
    }

    @Test
    public void sortByPrice() {
        Database.initializeAddress();
        Category category = new Category("labaniat", null, new HashSet <> (  ));
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

        HashMap<String, String> personInfo = new HashMap<>();
        personInfo.put("username", "yeki");
        personInfo.put("password", "salam");

        HashMap<String, String> personInfo2 = new HashMap<>();
        personInfo2.put("username", "yeki dige");
        personInfo2.put("password", "salam");

        Salesperson seller1 = new Salesperson(personInfo);
        Salesperson seller2 = new Salesperson(personInfo2);
        ArrayList<Salesperson> owners1 = new ArrayList<>();
        owners1.add(seller1);
        owners1.add(seller2);

        ArrayList<Salesperson> owners2 = new ArrayList<>();
        owners2.add(seller2);

        Product product1 = new Product("panir", "lighvan",
                category.getName(), properties1);


        Product product2 = new Product( "shir", "mihan",
                category.getName(), properties2);


        seller1.addToOfferedProducts(product1, 2, 2500);
        seller2.addToOfferedProducts(product1, 4, 2000);
        seller2.addToOfferedProducts(product2, 3, 1000);

        ProductController.stock = new HashMap<>();
        ProductController.stock.put(product1, owners1);
        ProductController.stock.put(product2, owners2);

        ArrayList<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        category.setProductList(products);

        Assert.assertEquals(ProductController.getInstance ().sortProductByPrice(product1).get(0).getPrice(), 2000, 0);

    }

    @Test
    public void sortTest() {
        Database.initializeAddress();
        Category category = new Category("labaniat", null, new HashSet <> (  ));
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

        Product product1 = new Product( "panir", "lighvan",
                category.getName(), properties1);

        Product product2 = new Product( "shir", "mihan",
                category.getName(), properties2);

        Product product3 = new Product( "abt", "mihan",
                category.getName(), properties1);

        Product product4 = new Product( "panir", "ab",
                category.getName(), properties2);

        ArrayList<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);

        ProductController.getInstance().sortProduct(products, true, true, true, false);
        Assert.assertEquals(products.get(2), product1);
    }
}