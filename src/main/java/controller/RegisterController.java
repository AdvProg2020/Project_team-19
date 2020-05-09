package controller;

import model.*;

import java.io.IOException;
import java.util.HashMap;

public class RegisterController {
    public static boolean firstManagerRegistered = false;

    public static void register (HashMap<String, String> personInfo) {
        if (personInfo.get("type").equals("customer")) {
            registerCustomer(personInfo);
        } else if (personInfo.get("type").equals("salesperson")) {
            registerSalesperson(personInfo);
        } else {
            registerManager(personInfo);
        }
    }

    private static void registerCustomer (HashMap<String, String> personInfo) {
        Customer newCustomer = null;
        try {
            newCustomer = new Customer(personInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PersonController.addPerson(newCustomer);
        // bayad be hame customera add she
    }

    private static void registerSalesperson (HashMap<String, String> personInfo) {
//        SalespersonRequest request = new SalespersonRequest(personInfo, );
//        // taeed modir va ...
    }

    private static void registerManager (HashMap<String, String> personInfo) {
        try {
            Manager manager = new Manager(personInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // write on file
    }

    public static boolean checkUserNameAuthenticity ( String username) {
        if (username.matches("\\w+")) {
            return username.length() >= 4;
        }
        return false;
    }



}
