package view;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {
    protected String name;
    protected Menu parent;
    public static Scanner scanner;
    protected HashMap<Integer, Menu> subMenus;

    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }

    public Menu(String name, Menu parent) {
        this.name = name;
        this.parent = parent;
        subMenus = new HashMap<Integer, Menu>();
    }

    public void show() {
        System.out.println(this.name + ":");
        for (Integer integer : subMenus.keySet()) {
            System.out.println(integer + 1 + "." + subMenus.get(integer).name);
        }
        System.out.println(subMenus.keySet().size() + 1 + "Back");
        System.out.println(subMenus.keySet().size() + 2 + "Exit");
    }

    public void execute() {
        Menu nextMenu = null;
        int chosenMenu = Integer.parseInt(scanner.nextLine());
        if (chosenMenu == subMenus.size() + 1) {
            if (this.parent == null)
                System.exit(1);
            else
                nextMenu = this.parent;
        } else
            nextMenu = subMenus.get(chosenMenu);
        nextMenu.show();
        nextMenu.execute();
    }

    private static String getInputInFormat(String helpText, String regex) {
        return getInputInFormatWithError(helpText, regex, "");
    }

    private static String getInputInFormatWithError(String helpText, String regex, String error) {

        Pattern pattern = Pattern.compile(regex);
        boolean inputIsInvalid;
        String line;
        do {
            System.out.println(helpText);
            line = scanner.nextLine().trim();
            Matcher matcher = pattern.matcher(line);
            inputIsInvalid = !matcher.find();
            if (inputIsInvalid)
                System.out.print(error);
        } while (inputIsInvalid);
        return line;
    }
}

