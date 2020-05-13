package controller;

import model.*;
import view.CustomerMenu;
import view.LoginMenu;
import view.MainMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PersonController {
    public static ArrayList<Person> allPersons = new ArrayList<>();
    private static Person loggedInPerson = null;

    public static void initializePersons() throws FileNotFoundException {
        for (File file : Database.returnListOfFiles(Database.address.get("customers"))) {
            allPersons.add((Customer) Database.read(Customer.class,file.getAbsolutePath()));
        }
        for (File file : Database.returnListOfFiles(Database.address.get("managers"))) {
            allPersons.add((Manager) Database.read(Manager.class,file.getAbsolutePath()));
            RegisterController.setFirstManagerRegistered ( true );
        }
        for (File file : Database.returnListOfFiles(Database.address.get("salespersons"))) {
            allPersons.add((Salesperson) Database.read(Salesperson.class,file.getAbsolutePath()));
        }
    }

    public static <T> boolean checkValidPersonType(String username, Class<T> personType){
        for (Person person : filterByRoll(personType)) {
            if(person.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public static void increaseCustomerCredit(Customer customer, double credit) {
        customer.increaseCredit(credit);
    }

    public static void addPerson (Person person) {
        allPersons.add(person);
    }

    public static void removePersonFromAllPersons (Person person) {
        allPersons.remove(person);
    }

    public static boolean isThereLoggedInPerson(){
        return loggedInPerson!=null;
    }

    public static boolean isTherePersonByUsername(String username) {
        if(findPersonByUsername(username) == null)
            return false;
        return true;
    }

    public static Person findPersonByUsername (String username){
        for (Person person : allPersons) {
            if (person.getUsername().equals(username))
                return person;
        }
        return null;
    }

    public void editPersonalInfo(String filedName,String newValue){
        loggedInPerson.setField(filedName,newValue);
    }

    public static void login(String username,String password) throws Exception{
        if ( !Pattern.compile ( "\\w{3,}" ).matcher ( username ).matches ( ) ) //ToDo put this in view
            throw new Exception ( "Username should contain more than 3 characters." );
        else if(!isTherePersonByUsername(username)){
            throw new UsernameNotFoundException("This username does not exist");
        }else if(!checkPassword(password,username)){
            throw new WrongPasswordException("Incorrect password");
        }else {
            loggedInPerson = findPersonByUsername(username);
            if(isLoggedInPersonCustomer()){
                CartController.getInstance().setLoggedInPersonCart();
            }
            goToMenu();
        }
    }

    public void LogOut(){
        loggedInPerson = null;
    }

    public static boolean checkPassword ( String password , String username ) throws WrongPasswordException{
        if (!findPersonByUsername(username).getPassword().equals(password))
            throw new WrongPasswordException("Incorrect password");
        return true;
    }

    public static class UsernameNotFoundException extends Exception{
        UsernameNotFoundException ( String message ) {
            super ( message );
        }
    }

    public static class WrongPasswordException extends Exception{
        public WrongPasswordException ( String message ) {
            super ( message );
        }
    }

    public static Person getLoggedInPerson() {
        return loggedInPerson;
    }

    public static void setLoggedInPerson(Person loggedInPerson) {
        PersonController.loggedInPerson = loggedInPerson;
        if(isLoggedInPersonCustomer()){
            CartController.getInstance().setLoggedInPersonCart();
        }
    }

    public static boolean isLoggedInPersonCustomer(){
        return loggedInPerson instanceof Customer;
    }

    public static <T> ArrayList<Person> filterByRoll(Class<T> personType) {
        return allPersons.stream().filter(personType::isInstance).collect(Collectors.toCollection(ArrayList::new));
    }

    private static void goToMenu () {
//        if (loggedInPerson instanceof Customer)
//            CustomerMenu
    }

    public static String getTypeFromList (String username) {
        for (Person person : allPersons) {
            if (person.getUsername ().equals ( username ))
                return person.getType ();
        }
        return null;
    }
}

