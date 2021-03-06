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
        personInfo.put ( "type" , ( personInfo.get ( "type" ) ).toLowerCase () );
        switch (personInfo.get ( "type" )) {
            case "customer":
                registerCustomer ( personInfo, bankId );
                break;
            case "salesperson":
                registerSalesperson ( personInfo );
                break;
            case "support":
                registerSupport ( personInfo );
                break;
            default:
                registerManager ( personInfo );
                break;
        }
    }

    public void registerCustomer (HashMap<String, String> personInfo, String bankId) {
        Customer customer = new Customer(personInfo, bankId);
    }

    public void registerSalesperson (HashMap<String, String> personInfo) {
        SalespersonRequest request = new SalespersonRequest(personInfo);
    }

    public void registerSupport (HashMap<String, String> personInfo) {
        Support support = new Support ( personInfo );
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

}
