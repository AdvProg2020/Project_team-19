package model;

import java.util.HashMap;

public class SalespersonRequest extends Request {
    private HashMap<String, String> personInfo;

    public SalespersonRequest(HashMap<String, String> personInfo, String requestId) {
        super(requestId);
        this.personInfo = personInfo;
    }

    @Override
    public void doThis() {
        Salesperson salesperson = new Salesperson(personInfo);
        //add be all salesperson
        //add be file
    }

    @Override
    public String show() {
        //ba tavajoh be saligh etaghireah bedim
        return personInfo.get("username") + " for salesperson" + "\n" + "request id = " + getRequestId();
    }
}
