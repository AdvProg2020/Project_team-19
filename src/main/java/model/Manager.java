package model;

import java.util.ArrayList;

public class Manager extends Person  {
    static ArrayList<Manager> managers;

    public Manager() {
        managers.add(this);
    }
}
