package controller;

import fxmlController.WalletMenu;
import model.*;

import java.io.IOException;
import java.util.HashMap;

public class RegisterController {
    private static RegisterController single_instance = null;

    private RegisterController() {
    }

    public static RegisterController getInstance() {
        if (single_instance == null)
            single_instance = new RegisterController();

        return single_instance;
    }

    public void register (HashMap<String, String> personInfo, String bankId) {
        personInfo.put ( "type" , changeTypeToStandardForm ( personInfo.get ( "type" ) ) );
        if (personInfo.get("type").equals("customer")) {
            registerCustomer(personInfo, bankId);
        } else if (personInfo.get("type").equals("salesperson")) {
            registerSalesperson(personInfo);
        } else {
            registerManager(personInfo);
        }
    }

    public void registerCustomer (HashMap<String, String> personInfo, String bankId) {
        Customer customer = new Customer(personInfo, bankId, WalletController.MIN_BALANCE);
    }

    public void registerSalesperson (HashMap<String, String> personInfo) {
        SalespersonRequest request = new SalespersonRequest(personInfo);
    }

    public boolean isFirstManagerRegistered() {
        for (Person person : PersonController.allPersons) {
            if (person instanceof Manager)
                return true;
        }
        return false;
    }

    public void registerManager (HashMap<String, String> personInfo) {
        Manager manager = new Manager(personInfo);
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
