package model;

import controller.Database;
import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;

import static controller.PersonController.allPersons;
import static view.LoginMenu.PersonInfo.*;

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
        try {
            if ( this instanceof Manager )
                Database.saveToFile ( this , Database.createPath("managers", getUsername()));
            else if ( this instanceof Salesperson )
                Database.saveToFile ( this , Database.createPath("salespersons", getUsername()) );
            else if ( this instanceof Customer )
                Database.saveToFile( this , Database.createPath("customers", getUsername()) );
        } catch (Exception e) {
            System.out.println ( "Couldn't save the change to file.\n" + e.getMessage () );
        }

    }



    public String getUsername() {
        return personInfo.get("username");
    }

    public String getType() {
        return personInfo.get ( "type" );
    }

    public HashMap < String, String > getPersonInfo () {
        return personInfo;
    }

    public String getPersonalInfo() {
        String type = personInfo.get ( "type" );
        type = type.substring(0,1).toUpperCase() + type.substring(1).toLowerCase();
        if (this instanceof Salesperson)
            return "Username : " + personInfo.get ( USERNAME.label ) + "\n" +
                    "Type : " + type + "\n" +
                    "Name : " + personInfo.get ( FIRST_NAME.label ) + " " + personInfo.get ( LAST_NAME.label )+ "\n" +
                    "Email : " + personInfo.get ( EMAIL.label ) + "\n" +
                    "Phone Number : " + personInfo.get ( PHONE.label ) + "\n" +
                    "Company : " + personInfo.get ( COMPANY.label ) + "\n" +
                    "Dar Surate Vjud Sayere Moshkhsat : " + personInfo.get ( SAYERE_MOSHAKHASAT.label );
        return "Username : " + personInfo.get ( USERNAME.label ) + "\n" +
                "Type : " + type + "\n" +
                "Name : " + personInfo.get ( FIRST_NAME.label ) + " " + personInfo.get ( LAST_NAME.label )+ "\n" +
                "Email : " + personInfo.get ( EMAIL.label ) + "\n" +
                "Phone Number : " + personInfo.get ( PHONE.label );

    }

    @Override
    public String toString() {
        return personInfo.get("username");
    }

}
