package view;

import controller.CategoryController;
import controller.ProductController;
import model.Product;
import model.Salesperson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public abstract class Menu {
    private String name;
    Menu parentMenu;
    protected HashMap<Integer, Menu> subMenus;
    static Scanner scanner;
    static final String BACK_BUTTON = "..";
    protected static final String LINE = "+\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014" +
            "\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014" +
            "+" +
            "\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014" +
            "\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014+";
    protected static final String STRAIGHT_LINE = "+\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014" +
            "\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014" +
            "\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014" +
            "\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014+";

    static final String BACK_HELP = "You can type \"..\" to cancel the process";
    static boolean BACK_PRESSED;
    protected String helpMessage;

    public Menu(String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
        subMenus = new HashMap<>();
    }

    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }

    public String getName() {
        return name;
    }

    public void show() {
        System.out.println(this.name + ":");
        for (Integer menuNum : subMenus.keySet()) {
            System.out.println(menuNum + ". " + subMenus.get(menuNum).getName());
        }
        if (this.parentMenu != null)
            System.out.println((subMenus.size() + 1) + ". Back");
        else
            System.out.println((subMenus.size() + 1) + ". Exit");
    }

    public void execute() {
        Menu nextMenu = null;
        int chosenMenu = Integer.parseInt(scanner.nextLine());
        if (chosenMenu == subMenus.size() + 1) {
            if (this.parentMenu == null)
                System.exit(1);
            else
                return;
        } else
            nextMenu = subMenus.get(chosenMenu);
        nextMenu.run();
        this.run();
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

    protected Menu getSearchMenu() {
        return new Menu("Search", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
            }

            @Override
            public void execute() {
                System.out.println("Enter product id :");
                String input = scanner.nextLine();
                if (input.equals(BACK_BUTTON))
                    return;

                Product product;
                if ((product = ProductController.getInstance().searchProduct(input)) != null) {
                    ViewProductMenu.showProductDigest(product);
                } else
                    System.out.println("Could not find any matches");
            }
        };
    }

    public void run() {
        this.show();
        this.execute();
    }

    public void goBack() {
        this.parentMenu.run();
    }

    public String getValidMenuNumber(int most) {
        String menuNum;
        Pattern numPattern = Pattern.compile("[0-9]");
        boolean check = false;
        do {
            menuNum = scanner.nextLine();
            if (numPattern.matcher(menuNum).matches() && Integer.parseInt(menuNum) <= most) {
                check = true;
            } else {
                System.out.println("Your input number must be between 1 to" + most);
            }
        } while (!check);
        return menuNum;
    }

    public String getValidDiscountId(Salesperson salesperson) {
        boolean check;
        String input;
        do {
            input = scanner.nextLine();
            check = salesperson.getDiscountById(input) != null;
            if (!check) {
                System.out.println("You do not have such discount. Please enter discount id again:");
            }

        } while (!check);
        return input;
    }

    public static class WrongMenuNumberException extends Exception {
        String massage;

        public WrongMenuNumberException(int most) {
            massage = "Your input number must be between 1 to" + most;
        }

    }

    public String getValidProductId() {
        boolean check;
        String input;
        do {
            input = scanner.nextLine();
            check = ProductController.getInstance().isThereProductById(input);
            if (!check) {
                System.out.println("There is no product with such id. Please enter product id again:");
            }

        } while (!check);
        return input;
    }

    public String getValidCategoryName() {
        boolean check;
        String input;
        do {
            input = scanner.nextLine();
            if (input.equals(BACK_BUTTON))
                return input;
            check = CategoryController.getInstance().getCategoryByName(input, CategoryController.rootCategories) != null;
            if (!check) {
                System.out.println("There is no category with that name. Please enter category name again:");
            }

        } while (!check);
        return input;
    }

    public String getValidDouble(double most) {
        String num;
        Pattern numPattern = Pattern.compile("\\d+(.\\d+)?");
        boolean check = false;
        do {
            num = scanner.nextLine();
            if (num.equals(".."))
                return num;
            if (numPattern.matcher(num).matches() && Double.parseDouble(num) <= most) {
                check = true;
            } else {
                System.out.println("Your input number must be between 1 to" + most);
            }
        } while (!check);
        return num;
    }

    public String getValidDataTim() {
        System.out.println("year:");
        String year = getValidMenuNumber(2025);
        System.out.println("month:");
        String month = getValidMenuNumber(12);
        System.out.println("day");
        String day = getValidMenuNumber(31);
        System.out.println("hour");
        String hour = getValidMenuNumber(12);
        System.out.println("minute");
        String minute = getValidMenuNumber(59);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute)).format(format);
    }

}
