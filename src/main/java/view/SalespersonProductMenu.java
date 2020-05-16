package view;

import controller.*;
import model.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;

import static controller.CategoryController.rootCategories;

public class SalespersonProductMenu extends Menu {

    private Salesperson salesperson = (Salesperson) PersonController.getInstance ().getLoggedInPerson ();

    public SalespersonProductMenu(Menu parent) {
        super("Manage Products", parent);
        subMenus.put(1, getViewProductMenu());
        subMenus.put(2, getAddProductMenu());
        subMenus.put(3, getEditProductMenu());
        subMenus.put(4, getRemoveProductMenu());
        subMenus.put(5, getViewBuyersMenu());
    }

    public Menu getViewProductMenu() {
        return new Menu("View Product", this) {

            @Override
            public void show () {

            }

            @Override
            public void execute() {
                String input;
                System.out.println("Enter Product ID :");
                input = getValidProductId(salesperson);
                if (input.equals(BACK_BUTTON))
                    return;
                Product product = ProductController.getInstance().searchProduct(input);
                ViewProductMenu viewProductMenu = new ViewProductMenu(this);
                viewProductMenu.setProduct(product);
            }
        };
    }

    public Menu getViewBuyersMenu() {
        return new Menu("View Buyers", this) {
            @Override
            public void show() {
                System.out.println(BACK_HELP);
                String productID;
                while (true) {
                    System.out.print("Enter Product ID To See Customers Who Bought It : ");
                    productID = getValidProductId();
                    if (productID.equals(BACK_BUTTON))
                        break;
                    Salesperson salesperson = (Salesperson) PersonController.getInstance().getLoggedInPerson();
                    for (SellLog sellLog : salesperson.getSellLogs()) {
                        if (sellLog.getProduct().getID().equals(productID))
                            System.out.println(sellLog.getBuyer().getUsername());
                    }
                }
            }

            @Override
            public void execute() {
                //byd khli bshe chun tu show back capibility drim
            }
        };
    }

    public Menu getAddProductMenu() {
        return new Menu("Add Product", this) {
            @Override
            public void show() {
                fancyTitle ();
            }

            @Override
            public void execute() {
                String id = RandomStringUtils.random ( 4 , true , true );
                System.out.print("Enter Product Name : ");
                String name = scanner.nextLine();
                if (name.equals(BACK_BUTTON))
                    return;
                System.out.print("Enter Product Brand : ");
                String brand = scanner.nextLine();
                if (brand.equals(BACK_BUTTON))
                    return;
                System.out.print("Enter Product Price : ");
                String price = getValidDouble(Double.POSITIVE_INFINITY);
                if (price.equals(BACK_BUTTON))
                    return;
                System.out.print("Enter Product Amount : ");
                String amount = getValidMenuNumber(Integer.MAX_VALUE);
                if (amount.equals(BACK_BUTTON))
                    return;
                String input;
                Category category;
                while (true) {
                    System.out.print("Enter Product Category : ");
                    if ((category = CategoryController.getInstance().getCategoryByName(input = scanner.nextLine(), rootCategories)) == null
                            && !input.equals(BACK_BUTTON)) {
                        System.out.println ( "Enter A Valid Category Name" );
                        continue;
                    }
                    assert category != null;
                    if ( !category.isLeaf ( ) ) {
                        System.out.println ( "This Is A Parent Category. Enter A Subcategory." );
                        continue;
                    }
                    break;
                }


                if (input.equals(BACK_BUTTON))
                    return;

                HashMap<String, String> properties = new HashMap<>();

                for (String field : category.getPropertyFields()) {
                    System.out.print("Enter " + field + " : ");
                    properties.put(field, scanner.nextLine());
                }
                RequestController.getInstance().addProductRequest(Double.parseDouble(price), Integer.parseInt(amount), salesperson, input, name, brand, properties);
            }
        };
    }

    public Menu getEditProductMenu() {
        return new Menu("Edit Product", this) {
            @Override
            public void show() {
                System.out.println("1. Name");
                System.out.println("2. Brand");
                System.out.println("3. Category");
                System.out.println("4. Properties");
                System.out.println("5. Price");
                System.out.println("6. Amount");
                System.out.println("7. Confirm EDIT and Exit");
            }

            @Override
            public void execute() {
                String name = null;
                String brand = null;
                String category = null;
                HashMap<String, String> properties = new HashMap<>();
                String choice = null;
                String price = null;
                String amount = null;

                String id = getValidProductId(salesperson);

                do {
                    System.out.println("Which field do you want to edit ?");
                    choice = getValidMenuNumber(5);
                    switch (Integer.parseInt(choice)) {
                        case 1:
                            System.out.println("Enter product name :");
                            name = scanner.nextLine();
                            if (name.equals(BACK_BUTTON))
                                return;
                            break;
                        case 2:
                            System.out.println("Enter you new brand :");
                            brand = scanner.nextLine();
                            if (brand.equals(BACK_BUTTON))
                                return;
                            break;
                        case 3:
                            System.out.println("Enter category you want to move :");
                            category = getValidCategoryName();
                            if (category.equals(BACK_BUTTON))
                                return;
                            break;
                        case 4:
                            System.out.println("Enter field :");
                            String field = scanner.nextLine();
                            if (field.equals(BACK_BUTTON))
                                return;
                            System.out.println("Enter value :");
                            String value = scanner.nextLine();
                            if (value.equals(BACK_BUTTON))
                                return;
                            properties.put(field, value);
                            break;
                        case 5:
                            System.out.println("Enter price :");
                            price = getValidDouble(Double.POSITIVE_INFINITY);
                            if (price.equals(BACK_BUTTON))
                                return;
                        case 6:
                            System.out.println("Enter amount :");
                            amount = getValidMenuNumber(Integer.MAX_VALUE);
                            if (amount.equals(BACK_BUTTON))
                                return;
                    }

                } while (!choice.equals("7"));

                System.out.println("Your request will be sent :~");
                RequestController.getInstance().editProductRequest(price, amount, salesperson, id, category, name, brand, properties);
            }
        };
    }

    public Menu getRemoveProductMenu() {
        return new Menu("Remove Product", this) {
            @Override
            public void show() {
                fancyTitle ();
            }

            @Override
            public void execute() {
                System.out.println("Enter product id you want to remove :");
                String productID = getValidProductId(salesperson);
                if (productID.equals(BACK_BUTTON))
                    return;

                RequestController.getInstance().deleteProductRequest(productID, salesperson);
            }
        };
    }

    public void setSalesperson(Salesperson salesperson) {
        this.salesperson = salesperson;
    }
}