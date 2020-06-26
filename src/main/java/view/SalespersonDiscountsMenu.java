package view;

import controller.*;
import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SalespersonDiscountsMenu extends Menu {
    private Salesperson salesperson = (Salesperson) PersonController.getInstance().getLoggedInPerson();

    public SalespersonDiscountsMenu ( Menu parent) {
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
                fancyTitle ();
            }

            @Override
            public void execute() {
                LocalDateTime start;
                LocalDateTime end;
                Double percentage;
                ArrayList<Product> addArray = new ArrayList<>();
                String input;
                System.out.println("1. Start Time");
                input = getValidDateTime ();
                if (input.equals(BACK_BUTTON))
                    return;
                start = DiscountCodeController.getInstance().changeStringTDataTime(input);
                System.out.println("2. End Time");
                input = getValidDateTime ();
                if (input.equals(BACK_BUTTON))
                    return;
                end = DiscountCodeController.getInstance().changeStringTDataTime(input);
                System.out.println("3. Discount Percentage");
                input = getValidDouble(100);
                if (input.equals(BACK_BUTTON))
                    return;
                percentage = Double.parseDouble(input);
                System.out.println("4. Add Product (Press .. When Done)");
                while (true) {
                    System.out.println ( "Enter Product ID : " );
                    String id = getValidProductId();
                    if (id.equals ( BACK_BUTTON ))
                        break;
                    addArray.add(ProductController.getInstance().getProductById(id));
                }
                RequestController.getInstance().addDiscountRequest(addArray, start, end, percentage, salesperson);
                System.out.println("Request for discount " + RequestController.getInstance().getDiscountID() + " successfully sent.");
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
                String discountId = getValidDiscountId(salesperson);
                if (discountId.equals(BACK_BUTTON))
                    return;
                Discount discount = DiscountController.getInstance ().getDiscountByIdFromAll ( discountId );
                RequestController.getInstance ().deleteDiscountRequest (discount,  salesperson);
                System.out.println("Your request has been sent.");
            }
        };
    }

    public Menu getViewDiscountMenu(){
        return new Menu("View Discount",this) {
            @Override
            public void show() {
                System.out.println ( BACK_HELP );
                System.out.print ( "Enter Discount ID To View : " );
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
                    else if ( DiscountController.getInstance ().getDiscountByIdFromAll ( input ) == null ) {
                        System.out.println ( "This Discount Doesn't Exist." );
                        System.out.print ( "Enter Discount ID To View : " );
                    }
                    else if ( discount == null ) {
                        System.out.println ( "You Don't Own This Discount." );
                        System.out.print ( "Enter Discount ID To View : " );
                    }
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
                Discount discount = salesperson.getDiscountWithIdSpecificSalesperson ( input );
                do {
                    System.out.println("Which field do you want to edit?");
                    choice = getValidMenuNumber(1,6);
                    switch (Integer.parseInt(choice)) {
                        case 1:
                            start = DiscountCodeController.getInstance().changeStringTDataTime( getValidDateTime ());
                            break;
                        case 2:
                            end = DiscountCodeController.getInstance().changeStringTDataTime( getValidDateTime ());
                            break;
                        case 3:
                            percentage = Double.parseDouble(getValidDouble(100));
                            break;
                        case 4:
                            String id = getValidProductId();
                            if (!salesperson.hasProduct(ProductController.getInstance().getProductById(id))) {
                                System.out.println("you do not have such product.");
                                continue;
                            }
                            add.add(ProductController.getInstance().getProductById(id));
                            break;
                        case 5:
                            id = getValidProductId();
                            if (!salesperson.hasProduct(ProductController.getInstance().getProductById(id))) {
                                System.out.println("you do not have such product.");
                                continue;
                            }
                            remove.add(ProductController.getInstance().getProductById(id));
                            break;
                    }

                } while (!choice.equals("6"));
//                RequestController.getInstance().editDiscountRequest(discount, add, remove, start, end, percentage,salesperson);
            }
        };
    }

    public void setSalesperson(Salesperson salesperson) {
        this.salesperson = salesperson;
    }


}