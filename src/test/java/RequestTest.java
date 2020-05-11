import model.*;
import controller.*;
import org.junit.Test;


import static org.junit.Assert.*;
import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;
import static model.Product.stock;

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
        Category category = new Category(true, "labaniat", null);
        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("color", "yellow");
        Product product = new Product("1", "panir", "lighvan",
                category.getName(), properties1);

        stock.put(product, null);
        HashMap<String, String> personInfo = new HashMap<>();
        personInfo.put("username", "yeki");
        personInfo.put("password", "salam");
        Salesperson seller1 = new Salesperson(personInfo);

        ProductRequest pr = new ProductRequest("idrequest", 2000, 2, Request.RequestState.ADD,seller1, product);
        RequestController.acceptRequest(pr);

        assertEquals(seller1.getProductPrice(product), 2000.0, 0);
    }

}
