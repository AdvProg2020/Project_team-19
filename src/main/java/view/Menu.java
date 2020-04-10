package view;

import java.util.HashMap;

public abstract class Menu {
    protected String name;
    protected Menu parent;
    protected HashMap <Integer,Menu> subMenus;

    public Menu (String name, Menu parent) {
        this.name = name;
        this.parent = parent;
        subMenus = new HashMap < Integer, Menu > ( );
    }

    public void show () {

    }

    public void execute () {

    }
}
