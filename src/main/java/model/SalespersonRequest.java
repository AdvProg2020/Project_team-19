package model;

import controller.Database;

import java.io.IOException;
import java.util.HashMap;

public class SalespersonRequest extends Request {
    private HashMap<String, String> personInfo;

    public SalespersonRequest(HashMap<String, String> personInfo, String requestId) throws IOException {
        super(requestId);
        this.personInfo = personInfo;
        Database.saveToFile(this, Database.createPath("salesPersonRequests", requestId));
    }

    @Override
    public void doThis() {
        try {
            Salesperson salesperson = new Salesperson(personInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //add be all salesperson
        //add be file
    }

    @Override
    public String show() {
        //ba tavajoh be saligh etaghireah bedim
        return personInfo.get("username") + " for salesperson" + "\n" + "request id = " + getRequestId();
    }
}
