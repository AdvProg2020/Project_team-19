import model.DiscountRequest;
import model.ProductRequest;
import model.Request;
import model.SalespersonRequest;
import controller.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

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
//        System.out.print(RequestController.getSpecificTypeOfRequests(SalespersonRequest.class));
//        Database.allRequests.clear();
    }

    @Test
    public void TestForEnumProduct () {
        Request request = new ProductRequest(UUID.randomUUID().toString(), Request.RequestState.EDIT);
        request.doThis();
    }
}
