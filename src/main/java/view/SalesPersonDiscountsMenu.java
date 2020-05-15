package view;

import controller.PersonController;
import model.Discount;
import model.Salesperson;

public class SalesPersonDiscountsMenu extends Menu {
    public SalesPersonDiscountsMenu(Menu parent){
        super("Discounts Menu",parent);
        subMenus.put(1,getAddDiscountMenu());
        subMenus.put(2,getViewDiscountMenu());
        subMenus.put(3,getEditDiscountMenu());
    }

    public Menu getAddDiscountMenu(){
        return new Menu("Add Discount",this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
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
                Salesperson salesperson = (Salesperson) PersonController.getLoggedInPerson ();
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

    public Menu getEditDiscountMenu(){
        return new Menu("Edit Discount",this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }
}
