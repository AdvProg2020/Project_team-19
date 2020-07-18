package model;

import controller.Database;

import java.util.ArrayList;
import java.util.HashMap;

public class Support extends Person {

    ArrayList <Support> allSupports = new ArrayList <> (  );

    public Support ( HashMap < String, String > personInfo ) {
        super ( personInfo );
        Database.saveToFile(this, Database.createPath("supports", personInfo.get("username")));
    }
}
