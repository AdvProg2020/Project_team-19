package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Category { //should this be singleton?
    static String name;
    static Category parent;
    static ArrayList <Category> children;
    static HashMap<Integer,String> properties;
    static ArrayList< Product > productList;
}
