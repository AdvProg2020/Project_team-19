package view;

import controller.ProductController;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {
    private String name;
    protected Menu parentMenu;
    protected HashMap <Integer,Menu> subMenus;
    public static Scanner scanner;

    public Menu (String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
        subMenus = new HashMap <Integer, Menu> ();
    }

    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }

    public String getName () {
        return name;
    }

    public void show () {
        System.out.println(this.name + ":");
        for (Integer menuNum : subMenus.keySet()) {
            System.out.println(menuNum + ". " + subMenus.get(menuNum).getName());
        }
        if (this.parentMenu != null)
            System.out.println((subMenus.size() + 1) + ". Back");
        else
            System.out.println((subMenus.size() + 1) + ". Exit");
    }

    public void execute () {
        Menu nextMenu = null;
        int chosenMenu = Integer.parseInt(scanner.nextLine());
        if (chosenMenu == subMenus.size() + 1) {
            if (this.parentMenu == null)
                System.exit(1);
            else
                nextMenu = this.parentMenu;
        } else
            nextMenu = subMenus.get(chosenMenu);
        nextMenu.show();
        nextMenu.execute();
    }

    protected Menu getHelpMenu(Menu parent) {
        return new Menu("Help", parent) {
            @Override
            public void show() {
                for (Integer submenuNumber : this.parentMenu.subMenus.keySet()) {
                    System.out.println(this.parentMenu.subMenus.get(submenuNumber).getName());
                }
                System.out.println("Enter back to return");
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

    protected void run (Menu menu) {
        menu.show ();
        menu.execute ();
    }

    public String getValidMenuNumber(int most)  {
        String menuNum;
        Pattern numPattern = Pattern.compile("[0-9]");
        boolean check =false;
        do {
            menuNum = scanner.nextLine();
            if (numPattern.matcher(menuNum).matches() && Integer.parseInt(menuNum)<=most){
                check = true;
            }else {
                System.out.println("Your input number must be between 1 to" + most);
            }
        }while (!check);
        return menuNum;
    }

    public static class WrongMenuNumberException extends Exception {
        String massage;
        public WrongMenuNumberException(int most){
            massage = "Your input number must be between 1 to" + most;
        }

    }

    public String getValidProductId () {
        boolean check;
        String input;
        do {
            input = scanner.nextLine ( );
            check = ProductController.isThereProductById(input);
            if (!check){
                System.out.println("There no product with such username. Please enter product id again:");
            }

        }while (!check);
        return input;
    }


}
