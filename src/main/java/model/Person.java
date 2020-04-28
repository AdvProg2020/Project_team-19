package model;

import java.util.HashMap;

abstract public class Person {
    private boolean isLoggedIn;
    private HashMap<String, String> personInfo;
    private String username;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String password;

    public Person(HashMap<String, String> personInfo) {
        this.personInfo = new HashMap<String, String>(personInfo);
    }

    public String getPassword() {
        return password;
    }

    public void setField(String field, String newValue) {
        personInfo.put(field, newValue);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
}
