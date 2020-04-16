package controller;

public class Controller {

    public boolean isANewUserName (String newUserName) {
        for (String userName : Database.getAllUsernames()) {
            if (newUserName.equals(userName))
                return false;
        }
        return true;
    }







}
