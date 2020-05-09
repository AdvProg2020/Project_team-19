package controller;

import model.*;

import java.io.IOException;
import java.util.HashMap;

public class RegisterController {
    private static boolean firstManagerRegistered = false;

    public static void register (HashMap<String, String> personInfo) {
        if (personInfo.get("type").equals("customer")) {
            registerCustomer(personInfo);
        } else if (personInfo.get("type").equals("salesperson")) {
            registerSalesperson(personInfo);
        } else {
            registerManager(personInfo);
        }
    }

    public static void registerCustomer (HashMap<String, String> personInfo) {
        Customer newCustomer = null;
        try {
            newCustomer = new Customer(personInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PersonController.addPerson(newCustomer);
    }

    public static void registerSalesperson (HashMap<String, String> personInfo) {
//        SalespersonRequest request = new SalespersonRequest(personInfo, );
//        // taeed modir va ...
    }

    public static boolean isFirstManagerRegistered() {
        return firstManagerRegistered;
    }

    public static void registerManager (HashMap<String, String> personInfo) {
        try {
            Manager manager = new Manager(personInfo);
            firstManagerRegistered = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
