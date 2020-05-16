package model;

import controller.Database;
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
                Database.editInFile ( this , "managers" , getUsername ( ) );
            else if ( this instanceof Salesperson )
                Database.editInFile ( this , "salespersons" , getUsername ( ) );
            else if ( this instanceof Customer )
                Database.editInFile ( this , "customers" , getUsername ( ) );
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
        return "Username : " + personInfo.get ( USERNAME.label ) + "\n" +
                "Password : " + "********** (I'm just kidding bro this is just some stars)\n" +
                "Name : " + personInfo.get ( FIRST_NAME.label ) + " " + personInfo.get ( LAST_NAME.label )+ "\n" +
                "Email : " + personInfo.get ( EMAIL.label ) + "\n" +
                "Phone Number : " + personInfo.get ( PHONE.label );

    }

    @Override
    public String toString() {
        return personInfo.get("username");
    }
}
