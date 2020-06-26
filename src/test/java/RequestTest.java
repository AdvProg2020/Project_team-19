import model.*;
import controller.*;
import org.junit.Assert;
import org.junit.Test;

import static controller.RequestController.allRequests;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class RequestTest {
    @Test
    public void testGetById () {
        Database.initializeAddress();
        ArrayList<Request> requests = new ArrayList<>();
        HashMap<String, String> personInfo1 = new HashMap<>();
        personInfo1.put("username", "yalda");
        SalespersonRequest sr = new SalespersonRequest(personInfo1);
        requests.add(sr);
        Category category = new Category("labaniat", null, new HashSet<>());
        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("color", "yellow");
        Product product = new Product("panir", "lighvan",
                category.getName(), properties1);
        HashMap<String, String> personInfo = new HashMap<>();
        personInfo.put("username", "yeki");
        personInfo.put("password", "salam");
        Salesperson seller1 = new Salesperson(personInfo);

        requests.add(new DiscountRequest(new Discount(null, null, 20, new ArrayList<>()) ,seller1, Request.RequestState.ADD));
        requests.add(new ProductRequest(seller1, product));
        requests.add(new SalespersonRequest(personInfo));

        allRequests.addAll(requests);
        Assert.assertEquals(RequestController.getInstance().getRequestById(sr.getRequestId()), sr);
    }

    @Test
    public void testForDecline() {
        Database.initializeAddress();
        HashMap<String, String> personInfo1 = new HashMap<>();
        personInfo1.put("username", "yalda");
        SalespersonRequest sr = new SalespersonRequest(personInfo1);
        RequestController.getInstance().declineRequest(sr);
        Assert.assertEquals(allRequests.size(), 0);
    }

    @Test
    public void testAcceptRequestProduct () {
        Database.initializeAddress();
        ProductController.stock = new HashMap<>();
        Category category = new Category("labaniat", null, new HashSet<>());
        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("color", "yellow");
        Product product = new Product("panir", "lighvan",
                category.getName(), properties1);

        ProductController.stock.put(product, new ArrayList<>());
        HashMap<String, String> personInfo = new HashMap<>();
        personInfo.put("username", "yeki");
        personInfo.put("password", "salam");
        Salesperson seller1 = new Salesperson(personInfo);
        seller1.addToOfferedProducts(product, 2, 2000);
        seller1.setBuildInProgress(product);
        if (!ProductController.stock.containsKey(product))
            ProductController.stock.get(product).add(seller1);
        else {
            ArrayList<Salesperson> salespeople = new ArrayList<>();
            salespeople.add(seller1);
            ProductController.stock.put(product, salespeople);
        }
        ProductRequest pr = new ProductRequest(2000, 2, seller1, product);
        RequestController.getInstance().acceptRequest(pr);

        assertEquals(seller1.getProductPrice(product), 2000.0, 0);
    }

    @Test
    public void testAcceptRequestSeller() {
        Database.initializeAddress();
        HashMap<String, String> info = new HashMap<>();
        info.put("username", "yalda");
        SalespersonRequest sr = new SalespersonRequest(info);
        RequestController.getInstance().acceptRequest(sr);
        Assert.assertNotNull(RequestController.getInstance().getSpecificTypeOfRequests(SalespersonRequest.class));
    }

    @Test
    public void testInitializeRequests(){
        Database.initializeAddress();
        HashMap<String, String> info = new HashMap<>();
        info.put("username", "yalda");
        SalespersonRequest sr = new SalespersonRequest(info);
        RequestController.getInstance().initializeRequests();
        boolean check = allRequests.contains(sr);
        assertTrue(check);
    }

    @Test
    public void testForFilter() {
        Database.initializeAddress();
        Category category = new Category( "labaniat", null, new HashSet<>());
        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("color", "yellow");
        Product product = new Product( "panir", "lighvan",
                category.getName(), properties1);

        ProductController.stock = new HashMap<>();
        ProductController.stock.put(product, new ArrayList<>());
        HashMap<String, String> personInfo = new HashMap<>();
        personInfo.put("username", "yeki");
        personInfo.put("password", "salam");

        SalespersonRequest sr = new SalespersonRequest(personInfo);
        ArrayList<Request> reqs = new ArrayList<>();
        reqs.add(sr);
        Assert.assertEquals(RequestController.getInstance().getSpecificTypeOfRequests(SalespersonRequest.class), reqs);
    }

    @Test
    public void testAddProductRequest() {
        Database.initializeAddress();
        ProductController.stock = new HashMap<>();
        HashMap<String, String> personInfo = new HashMap<>();
        personInfo.put("username", "yeki");
        personInfo.put("password", "salam");
        HashSet<String> properties1 = new HashSet<>();
        properties1.add("color");
        Category category = new Category( "labaniat", null, properties1);
        Salesperson seller = new Salesperson(personInfo);
        HashMap<String,String> properties = new HashMap<>();
        RequestController.getInstance().addProductRequest(2000.0, 2, seller, category.getName(), "panir", null, properties);
        Assert.assertNotNull(allRequests);
    }

    @Test
    public void testEditProductTest() {
        Database.initializeAddress();
        ProductController.stock = new HashMap<>();
        HashMap<String, String> personInfo = new HashMap<>();
        personInfo.put("username", "yeki");
        personInfo.put("password", "salam");
        HashSet<String> properties1 = new HashSet<>();
        properties1.add("color");
        Category category = new Category( "labaniat", null, properties1);
        Salesperson seller = new Salesperson(personInfo);
        Product product = new Product( "panir", "lighvan",
                category.getName(), new HashMap<>());
        ProductRequest pr = new ProductRequest(2000, 2 ,seller, product);
        seller.addToOfferedProducts(product, 2, 2000);
        seller.setBuildInProgress(product);
        if (ProductController.stock.containsKey(product))
            ProductController.stock.get(product).add(seller);
        else {
            ArrayList<Salesperson> salespeople = new ArrayList<>();
            salespeople.add(seller);
            ProductController.stock.put(product, salespeople);
        }
        RequestController.getInstance().acceptRequest(pr);
        RequestController.getInstance().editProductRequest("3000", "3", seller,product.getID(), category.getName()
                ,"shir", "loghvan", new HashMap<>());
        assertEquals(allRequests.size(), 1);
    }
}
