package controller;

import model.*;

public class PersonController {
    private static Person loggedInPerson = null;

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
