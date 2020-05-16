package controller;

import model.*;

import java.io.IOException;
import java.util.HashMap;

public class RegisterController {
    private static boolean firstManagerRegistered = false;
    private static RegisterController single_instance = null;

    private RegisterController() {
    }

    public static RegisterController getInstance() {
        if (single_instance == null)
            single_instance = new RegisterController();

        return single_instance;
    }

    public void register (HashMap<String, String> personInfo) {
        personInfo.put ( "type" , changeTypeToStandardForm ( personInfo.get ( "type" ) ) );
        if (personInfo.get("type").equals("customer")) {
            registerCustomer(personInfo);
        } else if (personInfo.get("type").equals("salesperson")) {
            registerSalesperson(personInfo);
        } else {
            registerManager(personInfo);
        }
    }

    public void registerCustomer (HashMap<String, String> personInfo) {
        Customer newCustomer = null;
        newCustomer = new Customer(personInfo);
    }

    public void registerSalesperson (HashMap<String, String> personInfo) {
        SalespersonRequest request = new SalespersonRequest(personInfo);
    }

    public boolean isFirstManagerRegistered() {
        return firstManagerRegistered;
    }

    public void registerManager (HashMap<String, String> personInfo) {
        Manager manager = new Manager(personInfo);
        firstManagerRegistered = true;
    }

    private String changeTypeToStandardForm (String type) {
        if (type.equalsIgnoreCase ( "customer" ))
            return "customer";
        if (type.equalsIgnoreCase ( "salesperson" ))
            return "salesperson";
        if (type.equalsIgnoreCase ( "manager" ))
            return "manager";
        return "null";
    }

}
