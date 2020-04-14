package model;

import java.util.HashMap;

public class SalespersonRequest extends Request {
    private String company;
    private String username;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String password;


    public SalespersonRequest(HashMap<String, String> personInfo) {
        this.username = personInfo.get("username");
        this.firstName = personInfo.get("firstName");
        this.lastName = personInfo.get("lastName");
        this.emailAddress = personInfo.get("email");
        this.phoneNumber = personInfo.get("phoneNumber");
        this.password = personInfo.get("password");
        this.company = personInfo.get("company");
    }
}
