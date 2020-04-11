package view;

import java.util.HashMap;
import java.util.Scanner;

public abstract class Menu {
    private String name;
    protected Menu parentMenu;
    protected HashMap <Integer,Menu> submenus;
    protected static Scanner scanner;

    public Menu (String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
        submenus = new HashMap <Integer, Menu> ();
    }

    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }

    public String getName () {
        return name;
    }

    public void show () {
        System.out.println(this.name + ":");
        for (Integer menuNum : submenus.keySet()) {
            System.out.println(menuNum + ". " + submenus.get(menuNum).getName());
        }
        if (this.parentMenu != null)
            System.out.println((submenus.size() + 1) + ". Back");
        else
            System.out.println((submenus.size() + 1) + ". Exit");
    }

    public  void execute () {
        Menu nextMenu = null;
        int chosenMenu = Integer.parseInt(scanner.nextLine());
        if (chosenMenu == submenus.size() + 1) {
            if (this.parentMenu == null)
                System.exit(1);
            else
                nextMenu = this.parentMenu;
        } else
            nextMenu = submenus.get(chosenMenu);
        nextMenu.show();
        nextMenu.execute();
    }

    protected Menu getHelpMenu(Menu parent) {
        return new Menu("Help", parent) {
            @Override
            public void show() {
                for (Integer submenuNumber : this.parentMenu.submenus.keySet()) {
                    System.out.println(this.parentMenu.submenus.get(submenuNumber).getName());
                }
                System.out.println("enter back to return");
                //bazgashti
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) {
                    this.parentMenu.show();
                    this.parentMenu.execute();
                } else
                    System.out.println("chizi zadi?");
            }
        };
    }


}
