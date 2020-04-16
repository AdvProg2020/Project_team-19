package controller;

import model.*;

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
        Customer newCustomer = new Customer(personInfo);
        Database.getAllPeople().add(newCustomer);
        // bayad be hame customera add she
    }

    private static void registerSalesperson (HashMap<String, String> personInfo) {
//        SalespersonRequest request = new SalespersonRequest(personInfo, );
//        // taeed modir va ...
    }

    private static void registerManager (HashMap<String, String> personInfo) {
        Manager manager = new Manager(personInfo);
        // write on file
    }

    public static boolean checkUserNameAuthenticity ( String username) {
        if (username.matches("\\w+")) {
            return username.length() >= 4;
        }
        return false;
    }



}
