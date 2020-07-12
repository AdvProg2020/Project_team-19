package model;

import controller.Database;
import controller.PersonController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Manager extends Person  {
    public static ArrayList<Manager> managers = new ArrayList <> (  );

    public Manager(HashMap<String, String> personInfo) {
        super(personInfo);
        managers.add(this);
        System.out.println(Database.address.get("managers"));
        Database.saveToFile(this,Database.createPath("managers", personInfo.get("username")));
    }
}
