package controller;

import model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PersonController {
    public static ArrayList<Person> allPersons = new ArrayList<>();
    private static Person loggedInPerson = null;
    private static PersonController single_instance = null;

    private PersonController() {
    }

    public static PersonController getInstance() {
        if (single_instance == null)
            single_instance = new PersonController();

        return single_instance;
    }

    public void initializePersons() {
        for (File file : Database.returnListOfFiles(Database.address.get("customers"))) {
            allPersons.add((Customer) Database.read(Customer.class, file.getAbsolutePath()));
        }
        for (File file : Database.returnListOfFiles(Database.address.get("managers"))) {
            allPersons.add((Manager) Database.read(Manager.class, file.getAbsolutePath()));
        }
        for (File file : Database.returnListOfFiles(Database.address.get("salespersons"))) {
            allPersons.add((Salesperson) Database.read(Salesperson.class, file.getAbsolutePath()));
        }
    }

    public <T> boolean checkValidPersonType(String username, Class<T> personType) {
        for (Person person : filterByRoll(personType)) {
            if (person.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public void increaseCustomerCredit(Customer customer, double credit) {
        customer.increaseCredit(credit);
    }

    public void addPerson(Person person) {
        allPersons.add(person);
    }

    public void removePersonFromAllPersons(Person person) throws IOException {
        allPersons.remove(person);
        Database.deleteFile(Database.createPath(getTypeFromList(person.getUsername()), person.getUsername()));
    }

    public boolean isThereLoggedInPerson() {
        return loggedInPerson != null;
    }

    public boolean isTherePersonByUsername(String username) {
        return getPersonByUsername (username) != null;
    }

    public Person getPersonByUsername ( String username) {
        for (Person person : allPersons) {
            if (person.getUsername().equals(username))
                return person;
        }
        return null;
    }

    public void login(String username) {
        loggedInPerson = getPersonByUsername (username);
        if (isLoggedInPersonCustomer()) {
            CartController.getInstance().setLoggedInPersonCart();
        }
    }

    public void logOut() {
        loggedInPerson = null;
    }

    public void checkPassword(String password, String username) throws WrongPasswordException {
        if (!getPersonByUsername (username).getPassword().equals(password))
            throw new WrongPasswordException("Incorrect password");
    }

    public static class WrongPasswordException extends Exception {
        public WrongPasswordException(String message) {
            super(message);
        }
    }

    public Person getLoggedInPerson() {
        return loggedInPerson;
    }

    public boolean isLoggedInPersonCustomer() {
        return loggedInPerson instanceof Customer;
    }

    public <T> ArrayList<Person> filterByRoll(Class<T> personType) {
        return allPersons.stream().filter(personType::isInstance).collect(Collectors.toCollection(ArrayList::new));
    }

    public String getTypeFromList(String username) {
        for (Person person : allPersons) {
            if (person.getUsername().equals(username))
                return person.getType();
        }
        return null;
    }
}


