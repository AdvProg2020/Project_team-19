import controller.ProductController;
import model.Category;
import model.Product;
import model.Salesperson;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ProductTest {
    @Test
    public void filterByPropertyTest () {
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

        Product product1 = new Product("1", "panir", "lighvan",
                Product.ProductState.BUILD_IN_PROGRESS, category.getName(), properties1);

        Product product2 = new Product("2", "shir", "mihan",
                Product.ProductState.BUILD_IN_PROGRESS, category.getName(), properties2);

        ArrayList<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        category.setProductList(products);

        System.out.println(ProductController.filterByField("color", "yellow", category).get(0).equals(product1));

    }

    @Test
    public void filterByPriceTest () throws IOException {
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

        Product product1 = new Product("1", "panir", "lighvan",
                Product.ProductState.BUILD_IN_PROGRESS, category.getName(), properties1);


        Product product2 = new Product("2", "shir", "mihan",
                Product.ProductState.BUILD_IN_PROGRESS, category.getName(), properties2);


        seller1.addToOfferedProducts(product1, 2, 2000);
        seller2.addToOfferedProducts(product1, 4, 2500);
        seller2.addToOfferedProducts(product2, 3, 1000);

        Product.stock.put(product1, owners1);
        Product.stock.put(product2, owners2);

        ArrayList<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        category.setProductList(products);


        System.out.println(ProductController.getSellersNameByPriceFiltering(500, 2200, product1));
        System.out.println(ProductController.filterACategoryByPrice(500, 2200, category));
        Product.stock.clear();

    }

    @Test
    public void sortByPrice() throws IOException {
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

        Product product1 = new Product("1", "panir", "lighvan",
                Product.ProductState.BUILD_IN_PROGRESS, category.getName(), properties1);


        Product product2 = new Product("2", "shir", "mihan",
                Product.ProductState.BUILD_IN_PROGRESS, category.getName(), properties2);


        seller1.addToOfferedProducts(product1, 2, 2500);
        seller2.addToOfferedProducts(product1, 4, 2000);
        seller2.addToOfferedProducts(product2, 3, 1000);

        Product.stock.put(product1, owners1);
        Product.stock.put(product2, owners2);

        ArrayList<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        category.setProductList(products);

        Iterator iterator = ProductController.sortProductByPrice(product1).iterator();
        while (iterator.hasNext())
            System.out.println(iterator.next());
    }
}