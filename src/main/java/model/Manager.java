package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Manager extends Person  {
    public static ArrayList<Manager> managers;

    public Manager() {
        managers.add(this);
    }
}
