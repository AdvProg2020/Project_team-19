package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Manager extends Person  {
    public static ArrayList<Manager> managers;

    public Manager(HashMap<String,String> personInfo) {
        super(personInfo);
        managers.add(this);
    }
}
