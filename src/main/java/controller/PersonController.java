package controller;

import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PersonController {
    private static ArrayList<Person> allPersons = new ArrayList<Person>();
    private static Person loggedInPerson = null;

    public static void initializePersons() throws FileNotFoundException {
        for (File file : Database.returnListOfFiles(Database.address.get("customer"))) {
            allPersons.add((Customer) Database.read(Customer.class,file.getAbsolutePath()));
        }
        for (File file : Database.returnListOfFiles(Database.address.get("manager"))) {
            allPersons.add((Manager) Database.read(Manager.class,file.getAbsolutePath()));
        }
        for (File file : Database.returnListOfFiles(Database.address.get("salesperson"))) {
            allPersons.add((Salesperson) Database.read(Salesperson.class,file.getAbsolutePath()));
        }
    }

    public static boolean isThereLoggedInPerson(){
        return loggedInPerson!=null;
    }

    public static boolean isTherePersonByUsername(String username) {
        return findPersonByUsername(username) != null;
    }

    public static Person findPersonByUsername (String username) {
        for (Person person : Database.allPeople) {
            if (person.getUsername().equals(username))
                return person;
        }
        return null;
    }

    public void editPersonalInfo(String filedName,String newValue){
        loggedInPerson.setField(filedName,newValue);
    }

    public void LogIn(String username,String password) throws UsernameNotFoundException,WrongPasswordException{
        if(!isTherePersonByUsername(username)){
            throw new UsernameNotFoundException();
        }else if(!checkPassword(password,username)){
            throw new WrongPasswordException();
        }else {
            loggedInPerson = findPersonByUsername(username);
        }
    }

    public void LogOut(){
        loggedInPerson = null;
    }

    public boolean checkPassword(String password,String username){
        return findPersonByUsername(username).getPassword().equals(password);
    }

    public static class UsernameNotFoundException extends Exception{
        String message="There is no account whit such username";
    }

    public static class WrongPasswordException extends Exception{
        String message="Password is wrong";
    }

    public static Person getLoggedInPerson() {
        return loggedInPerson;
    }

    public static void setLoggedInPerson(Person loggedInPerson) {
        PersonController.loggedInPerson = loggedInPerson;
    }

    public static boolean isLoggedInPersonCustomer(){
        return loggedInPerson instanceof Customer;
    }

}
