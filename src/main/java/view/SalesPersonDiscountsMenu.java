package view;

import controller.DiscountCodeController;
import controller.DiscountController;
import controller.ProductController;
import controller.RequestController;
import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SalesPersonDiscountsMenu extends Menu {
    private Salesperson salesperson;

    public SalesPersonDiscountsMenu(Menu parent) {
        super("Discounts Menu", parent);
        subMenus.put(1, getAddDiscountMenu());
        subMenus.put(2, getViewDiscountMenu());
        subMenus.put(3, getEditDiscountMenu());
        subMenus.put(4, getRemoveDiscountMenu());
    }

    public Menu getAddDiscountMenu() {
        return new Menu("Add Discount", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
            }

            @Override
            public void execute() {
                LocalDateTime start;
                LocalDateTime end;
                Double percentage;
                ArrayList<Product> add = new ArrayList<>();
                ArrayList<Product> remove = new ArrayList<>();
                String input;
                String choice;
                System.out.println("1. Start Time");
                input = getValidDataTim();
                if (input.equals(BACK_BUTTON))
                    return;
                start = DiscountCodeController.getInstance().changeStringTDataTime(input);
                System.out.println("2. End Time");
                input = getValidDataTim();
                if (input.equals(BACK_BUTTON))
                    return;
                end = DiscountCodeController.getInstance().changeStringTDataTime(input);
                System.out.println("3. Discount Percentage");
                input = getValidDouble(100);
                if (input.equals(BACK_BUTTON))
                    return;
                percentage = Double.parseDouble(input);
                System.out.println("4. Add Product");
                System.out.println("Enter the product number you want to add:");
                int num = Integer.parseInt(getValidMenuNumber(Integer.MAX_VALUE));
                for (int i = 0; i < num; i++) {
                    String id = getValidProductId();
                    add.add(ProductController.getInstance().searchProduct(id));
                }
                RequestController.getInstance().addDiscountRequest(add, start, end, percentage, salesperson);
            }
        };
    }

    public Menu getRemoveDiscountMenu() {
        return new Menu("Remove Discount", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                String input = getValidDiscountId(salesperson);
                if (input.equals(BACK_BUTTON))
                    return;
                DiscountCode discountCode = DiscountCodeController.getInstance().findDiscountCodeByCode(input);
            }
        };
    }

    public Menu getViewDiscountMenu(){
        return new Menu("View Discount",this) {
            @Override
            public void show() {
                System.out.println ( BACK_HELP );
                System.out.println ( "Enter Discount ID To View" );
            }

            @Override
            public void execute() {
                Salesperson salesperson = (Salesperson) PersonController.getInstance().getLoggedInPerson ();
                String input;
                Discount discount;
                while (true) {
                    input = scanner.nextLine ();
                    discount = salesperson.getDiscountWithIdSpecificSalesperson ( input );
                    if (input.equals ( BACK_BUTTON ))
                        break;
                    else if ( Discount.getDiscountByIdFromAll ( input ) == null )
                        System.out.println ( "This Discount Doesn't Exist." );
                    else if ( discount == null )
                        System.out.println ( "You Don't Own This Discount." );
                    else
                        System.out.println ( discount );
                }
            }
        };
    }

    public Menu getEditDiscountMenu() {
        return new Menu("Edit Discount", this) {
            @Override
            public void show() {
                System.out.println("1. Start Time");
                System.out.println("2. End Time");
                System.out.println("3. Discount Percentage");
                System.out.println("4. Add Product");
                System.out.println("5. Remove Product");
                System.out.println("6. Exit");
            }

            @Override
            public void execute() {
                LocalDateTime start = null;
                LocalDateTime end = null;
                Double percentage = null;
                ArrayList<Product> add = new ArrayList<>();
                ArrayList<Product> remove = new ArrayList<>();

                String choice;
                System.out.println("Enter discount code: ");
                String input = getValidDiscountId(salesperson);
                if (input.equals(BACK_BUTTON))
                    return;
                Discount discount = DiscountController.getInstance().getDiscountById(input);
                do {
                    System.out.println("Which field do you want to edit?");
                    choice = getValidMenuNumber(6);
                    switch (Integer.parseInt(choice)) {
                        case 1:
                            start = DiscountCodeController.getInstance().changeStringTDataTime(getValidDataTim());
                            break;
                        case 2:
                            end = DiscountCodeController.getInstance().changeStringTDataTime(getValidDataTim());
                            break;
                        case 3:
                            percentage = Double.parseDouble(getValidDouble(100));
                            break;
                        case 4:
                            String id = getValidProductId();
                            if (!salesperson.hasProduct(ProductController.getInstance().searchProduct(id))) {
                                System.out.println("you do not have such product.");
                                continue;
                            }
                            add.add(ProductController.getInstance().searchProduct(id));
                            break;
                        case 5:
                            id = getValidProductId();
                            if (!salesperson.hasProduct(ProductController.getInstance().searchProduct(id))) {
                                System.out.println("you do not have such product.");
                                continue;
                            }
                            remove.add(ProductController.getInstance().searchProduct(id));
                            break;
                    }

                } while (!choice.equals("6"));
                RequestController.getInstance().editDiscountRequest(discount, add, remove, start, end, percentage,salesperson);
            }
        };
    }

    public void setSalesperson(Salesperson salesperson) {
        this.salesperson = salesperson;
    }


}