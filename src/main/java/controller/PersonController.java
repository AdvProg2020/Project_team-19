package controller;

import model.*;
import model.wagu.Block;
import model.wagu.Board;
import model.wagu.Table;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PersonController {
    public static ArrayList<Person> allPersons = new ArrayList<>();
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
        for (File file : Database.returnListOfFiles(Database.address.get("supports"))) {
            allPersons.add((Support) Database.read(Support.class, file.getAbsolutePath()));
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
        Database.deleteFile(Database.createPath(getTypeFromList(person.getUsername())+"s", person.getUsername()));
        allPersons.remove(person);
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

    public void login(String username,String password,Cart cart) throws Exception {
        if (!PersonController.getInstance ( ).isTherePersonByUsername ( username ))
            throw new Exception ( "You Don't Exist. Go Make Yourself." );
        else checkPassword(password, username);
        Person person = PersonController.getInstance().getPersonByUsername(username);
        if (person.getType().equalsIgnoreCase("customer")) {
            Customer customer = (Customer) person;
            //customer.getCart().setCartAfterLogIn(cart);
            //Database.saveToFile(customer,Database.createPath("customers",customer.getUsername()));
        }
    }


    public void checkPassword(String password, String username) throws Exception {
        if (!getPersonByUsername (username).getPassword().equals(password))
            throw new Exception("Incorrect password");
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

    public String getEveryone () {
        List <String> headersList = Arrays.asList("Username", "Type");
        List < List <String>> rowsList = new ArrayList <> (  );
        for (Person person : allPersons) {
            List <String> row = new ArrayList <> ( 2 );
            row.add ( person.getUsername () );
            String type = person.getType ();
            type = type.substring(0,1).toUpperCase() + type.substring(1).toLowerCase();
            row.add ( type );
            rowsList.add ( row );
        }
        Board board = new Board (75);
        Table table = new Table (board, 75, headersList, rowsList);
        List<Integer> colAlignList = Arrays.asList(
                Block.DATA_CENTER,
                Block.DATA_CENTER);
        table.setColAlignsList(colAlignList);
        Block tableBlock = table.tableToBlocks();
        board.setInitialBlock(tableBlock);
        board.build();
        return board.getPreview();
    }
}


