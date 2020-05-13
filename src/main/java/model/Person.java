package model;

import java.util.HashMap;

import static controller.PersonController.allPersons;

abstract public class Person {
    protected HashMap<String, String> personInfo;

    public Person(HashMap<String, String> personInfo1) {
        this.personInfo = personInfo1; //hatman to menu new she
        allPersons.add(this);
    }

    public String getPassword() {
        return personInfo.get("password");
    }

    public void setField(String field, String newValue) {
        personInfo.put(field, newValue);
    }

    public String getUsername() {
        return personInfo.get("username");
    }

    public String getType() {
        return personInfo.get ( "type" );
    }

    @Override
    public String toString() {
        return personInfo.get("username");
    }
}
