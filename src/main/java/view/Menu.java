package view;

import controller.CategoryController;
import controller.ProductController;
import model.Category;
import model.Product;
import model.Salesperson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import controller.PersonController;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

import static controller.CategoryController.rootCategories;
import static view.ProductMenu.viewAllCategories;
import static view.ProductMenu.viewCategory;

public abstract class Menu {
    private String name;
    Menu parentMenu;
    public static Menu mainMenu;
    public static UserMenu userMenu;
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
        fancyTitle();
        for (Integer menuNum : subMenus.keySet()) {
            System.out.println(menuNum + ". " + subMenus.get(menuNum).getName());
        }
        System.out.println((subMenus.size() + 1) + ". User Menu");
        if (this.parentMenu != null)
            System.out.println((subMenus.size() + 2) + ". Back");
        else
            System.out.println((subMenus.size() + 2) + ". Exit");
        System.out.println("?. Help");
    }

    public void execute() {
        Menu nextMenu = null;
        int chosenMenu = Integer.parseInt(getValidMenuNumber(1, subMenus.size() + 2));
        if (chosenMenu == subMenus.size() + 1) {
            nextMenu = userMenu;
        } else if (chosenMenu == subMenus.size() + 2) {
            if (this.parentMenu == null)
                System.exit(1);
            else {
                if (this instanceof ViewProductMenu){
                    ((ViewProductMenu)this).setProduct(null);
                }
                return;
            }
        } else if (chosenMenu == subMenus.size() + 3) {
            nextMenu = getHelpMenu(this);
        } else
            nextMenu = subMenus.get(chosenMenu);
        assert nextMenu != null;
        nextMenu.run();
        if (nextMenu instanceof UserMenu && !PersonController.getInstance().isThereLoggedInPerson())
            nextMenu.parentMenu.run();
        else
            this.run();
    }

    public Menu getCategoryMenu(Menu parent) {
        return new Menu("Category Menu", parent) {
            @Override
            public void show() {
                fancyTitle();
                viewAllCategories();
            }

            @Override
            public void execute() {
                String input;
                do {
                    System.out.println("Enter category name you want to see or \"..\" to return :");
                    input = getValidCategoryName();
                    if (input.equals(BACK_BUTTON))
                        return;
                    Category category = CategoryController.getInstance().getCategoryByName(input, rootCategories);
                    assert category != null;
                    viewCategory(category);
                } while (true);
            }
        };
    }

    protected Menu getHelpMenu(Menu parent) {
        return new Menu("Help", parent) {
            @Override
            public void show() {
                for (Integer submenuNumber : this.parentMenu.subMenus.keySet()) {
                    System.out.println(this.parentMenu.subMenus.get(submenuNumber).toString());
                }
            }

            @Override
            public void execute() {
            }
        };
    }

    protected Menu getSearchMenu() {
        return new Menu("Search", this) {
            @Override
            public void show() {
                fancyTitle();
            }

            @Override
            public void execute() {
                System.out.println("Enter product id :");
                String input = scanner.nextLine();
                if (input.equals(BACK_BUTTON))
                    return;

                Product product;
                if ((product = ProductController.getInstance().getProductById(input)) != null) {
                    ViewProductMenu.showProduct(product);
                } else
                    System.out.println("Could not find any matches");
            }
        };
    }

    public void run() {
        this.show();
        this.execute();
    }

    public String getValidMenuNumber(int min, int most) {
        String menuNum;
        Pattern numPattern = Pattern.compile("[0-9]+");
        boolean check = false;
        do {
            menuNum = scanner.nextLine();
            if (menuNum.equals("?"))
                return String.valueOf(most + 1);
            if (numPattern.matcher(menuNum).matches() && Integer.parseInt(menuNum) <= most && Integer.parseInt(menuNum) >= min) {
                check = true;
            } else {
                System.out.println("Your input number must be between 1 to " + most);
            }
        } while (!check);
        return menuNum;
    }

    public String getValidDiscountId(Salesperson salesperson) {
        boolean check;
        String input;
        do {
            input = scanner.nextLine();
            check = salesperson.getDiscountWithIdSpecificSalesperson(input) != null;
            if (!check) {
                System.out.println("You do not have such discount. Please enter discount id again:");
            }

        } while (!check);
        return input;
    }

    protected Menu getLogoutMenu() {
        return new Menu("Logout", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                PersonController.getInstance().logOut();
            }
        };
    }

    public String getValidProductId() {
        boolean check;
        String input;
        do {
            input = scanner.nextLine();
            if (input.equals(BACK_BUTTON))
                break;
            check = ProductController.getInstance().isThereProductById(input);
            if (!check) {
                System.out.println("There is no product with such id. Please enter product id again:");
            }

        } while (!check);
        return input;
    }

    public String getValidProductId(Salesperson salesperson) {
        boolean check;
        String input;
        do {
            input = scanner.nextLine();
            if (input.equals(BACK_BUTTON))
                break;
            check = ProductController.getInstance().isThereProductById(input);
            if (!check) {
                System.out.println("There is no product with such id. Please enter id again:");
                continue;
            }
            check = ProductController.getInstance().doesSellerHasProduct(ProductController.getInstance().getProductById(input), salesperson);
            if (!check) {
                System.out.println("You do not have such product. Please enter discount id again:");
            }

        } while (!check);
        return input;
    }

    public String getValidCustomer() {
        boolean check;
        String input;
        do {
            input = scanner.nextLine();
            if (input.equals(BACK_BUTTON))
                return input;
            check = PersonController.getInstance().isLoggedInPersonCustomer();
            if (!check) {
                System.out.println("There is no customer with that username. Please enter username again:");
            }

        } while (!check);
        return input;
    }

    public String getValidCategoryName() {
        boolean check;
        String input;
        do {
            input = scanner.nextLine();
            if (input.equals(BACK_BUTTON) || input.equalsIgnoreCase("root"))
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
                System.out.println("Your input number must be between 1 to " + most);
            }
        } while (!check);
        return num;
    }

    public String getValidDateTime() {
        System.out.print("Year : ");
        String year = getValidMenuNumber(1, 2025);
        if (year.equals(BACK_BUTTON))
            return year;
        System.out.print("Month : ");
        String month = getValidMenuNumber(1, 12);
        if (month.equals(BACK_BUTTON))
            return month;
        System.out.print("Day : ");
        String day = getValidMenuNumber(1, 31);
        if ((day.equals(BACK_BUTTON)))
            return day;
        System.out.print("Hour : ");
        String hour = getValidMenuNumber(0, 24);
        if (hour.equals(BACK_BUTTON))
            return hour;
        System.out.print("Minute : ");
        String minute = getValidMenuNumber(0, 59);
        if (minute.equals(BACK_BUTTON))
            return minute;

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute)).format(format);
    }

    public String assertDeletion() {
        boolean check;
        String input;
        do {
            System.out.println("Are you sure you want to remove?(Y|N)");
            input = scanner.nextLine();
            check = (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n"));
        } while (!check);
        return input;
    }

    protected void fancyTitle() {
        System.out.printf("\u2014\u2014\u2014|%s|\u2014\u2014\u2014\n",
                StringUtils.center(this.getName(), 10));
    }

    void eachUserShowMenu() {
        fancyTitle();
        for (Integer menuNum : subMenus.keySet()) {
            System.out.println(menuNum + ". " + subMenus.get(menuNum).getName());
        }
        if (this.parentMenu != null)
            System.out.println((subMenus.size() + 1) + ". Back");
        else
            System.out.println((subMenus.size() + 1) + ". Exit");
    }

    void eachUserExecuteMenu() {
        Menu nextMenu;
        int chosenMenu = Integer.parseInt(getValidMenuNumber(1, subMenus.size() + 1));
        if (chosenMenu == subMenus.size() + 1) {
            return;
        } else
            nextMenu = subMenus.get(chosenMenu);
        assert nextMenu != null;
        nextMenu.run();
        if (!(this instanceof LoginMenu) && PersonController.getInstance().getLoggedInPerson() != null)
            this.run();
    }

//    public static void clearScreen(int x, int y) throws AWTException {
//        Robot bot = new Robot();
//        bot.mouseMove(x, y);
//        bot.mousePress( InputEvent.BUTTON1_MASK);
//        bot.mouseRelease(InputEvent.BUTTON1_MASK);
//    }

    @Override
    public String toString() {
//        return name+": "+helpMessage; //ToDo fln fqt name, k null nde
        return name;
    }
}