package view;

import controller.ProductController;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public abstract class Menu {
    private String name;
    Menu parentMenu;
    protected HashMap <Integer,Menu> subMenus;
    static Scanner scanner;
    static final String BACK_BUTTON = "..";
    static final String BACK_HELP = "You can type \"..\" to cancel the process";
    static boolean BACK_PRESSED;

    public Menu (String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
        subMenus = new HashMap <> ( );
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
        int chosenMenu = Integer.parseInt ( getValidMenuNumber ( subMenus.size () + 1 ) );
        if ( chosenMenu == subMenus.size ( ) + 1 ) {
            if ( this.parentMenu == null )
                System.exit ( 1 );
            else
                return;
        } else
            nextMenu = subMenus.get ( chosenMenu );
        nextMenu.run ( );
        this.run ();
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

    public void run () {
        this.show ();
        this.execute ();
    }

//    public void goBack () {
//        this.parentMenu.run ( );
//    }

    public String getValidMenuNumber(int most)  {
        String menuNum;
        Pattern numPattern = Pattern.compile("[0-9]");
        boolean check =false;
        do {
            menuNum = scanner.nextLine();
            if (numPattern.matcher(menuNum).matches() && Integer.parseInt(menuNum)<=most){
                check = true;
            }else {
                System.out.println("Your input number must be between 1 to " + most);
            }
        }while (!check);
        return menuNum;
    }

    public static class WrongMenuNumberException extends Exception {
        public WrongMenuNumberException(int most){
            super ("Your input number must be between 1 to" + most);
        }

    }

    public String getValidProductId () {
        boolean check;
        String input;
        do {
            input = scanner.nextLine ( );
            if (input.equals ( BACK_BUTTON ))
                break;
            check = ProductController.isThereProductById(input);
            if (!check){
                System.out.println("There no product with such username. Please enter product id again:");
            }

        }while (!check);
        return input;
    }


}
