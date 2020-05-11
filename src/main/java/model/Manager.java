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
    public static ArrayList<Manager> managers;

    public Manager(HashMap<String, String> personInfo) throws IOException {
        super(personInfo);
        managers.add(this);
        Database.saveToFile(this,Database.createPath("manager", personInfo.get("username")));
    }
}
