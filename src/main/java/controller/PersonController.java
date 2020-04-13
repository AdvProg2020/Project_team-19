package controller;

import model.*;

public class PersonController {

    public boolean isTherePersonByUsername (String username) {
        for (Person person : Database.allPeople) {
            if (person.getUsername().equals(username))
                return false;
        }
        return true;
    }

    public boolean 
}
