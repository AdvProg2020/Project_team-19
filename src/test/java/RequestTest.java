import model.*;
import controller.*;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class RequestTest {
    @Test
    public void simpleTestForCheckFilter () {
//        ArrayList<Request> requests = new ArrayList<>();
//        HashMap<String, String> personInfo1 = new HashMap<>();
//        personInfo1.put("username", "yalda");
//        HashMap<String, String> personInfo2 = new HashMap<>();
//        personInfo2.put("username", "person");
//        requests.add(new SalespersonRequest(personInfo1, UUID.randomUUID().toString()));
//        requests.add(new DiscountRequest(UUID.randomUUID().toString()));
//        requests.add(new ProductRequest(UUID.randomUUID().toString(), Request.RequestState.ADD));
//        requests.add(new SalespersonRequest(personInfo2,UUID.randomUUID().toString()));
//        Database.setAllRequests(requests);  /****fek konam bayad database ro khali konim**/
//
//        for (Request request : RequestController.getSpecificTypeOfRequests(SalespersonRequest.class)) {
//            System.out.println(request.show());
//        }
//        Database.allRequests.clear();
    }

    @Test
    public void acceptRequestsAndSellerCheck () throws IOException {
        Database.initializeAddress();
        Category category = new Category("labaniat", null, new HashSet<>());
        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("color", "yellow");
        Product product = new Product("panir", "lighvan",
                category.getName(), properties1, false);

        //stock.put(product, new ArrayList<>());
        HashMap<String, String> personInfo = new HashMap<>();
        personInfo.put("username", "yeki");
        personInfo.put("password", "salam");
        Salesperson seller1 = new Salesperson(personInfo);

        ProductRequest pr = new ProductRequest(2000, 2, seller1, product);
        RequestController.getInstance().acceptRequest(pr);

        assertEquals(seller1.getProductPrice(product), 2000.0, 0);
    }

    @Test
    public void showRequestsTest() throws IOException {
        Database.initializeAddress();
        Category category = new Category( "labaniat", null, new HashSet<>());
        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("color", "yellow");
        Product product = new Product( "panir", "lighvan",
                category.getName(), properties1, true);

        //stock.put(product, new ArrayList<>());
        HashMap<String, String> personInfo = new HashMap<>();
        personInfo.put("username", "yeki");
        personInfo.put("password", "salam");
        Salesperson seller1 = new Salesperson(personInfo);

        ProductRequest pr = new ProductRequest(2000, 2,seller1, product);
        SalespersonRequest sr = new SalespersonRequest(personInfo);
        ArrayList<Request> reqs = new ArrayList<>();
        reqs.add(sr);
        Assert.assertEquals(RequestController.getInstance().getSpecificTypeOfRequests(SalespersonRequest.class), reqs);
    }

    @Test
    public void requestTest() throws IOException {
        Database.initializeAddress();
        Category category = new Category( "labaniat", null, new HashSet<>());
        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("color", "yellow");
        Product product = new Product( "panir", "lighvan",
                category.getName(), properties1, true);

       // stock.put(product, new ArrayList<>());
        HashMap<String, String> personInfo = new HashMap<>();
        personInfo.put("username", "yeki");
        personInfo.put("password", "salam");
        Salesperson seller1 = new Salesperson(personInfo);

        ProductRequest pr = new ProductRequest(2000, 2,seller1, product);
        //SalespersonRequest sr = new SalespersonRequest(personInfo);
        //Database.read(ProductRequest.class, Database.createPath("product_requests", pr.toString()));
    }

}
