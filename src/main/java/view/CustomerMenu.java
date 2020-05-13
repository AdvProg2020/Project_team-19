package view;

import controller.PersonController;
import model.Customer;
import model.DiscountCode;

import java.util.ArrayList;
import java.util.HashMap;

import static controller.PersonController.increaseCustomerCredit;

public class CustomerMenu extends Menu {

    private static HashMap < DiscountCode, Integer > customersDiscountCodes = new HashMap <> (  );
    private static Customer thisGuy = (Customer) PersonController.getLoggedInPerson ();

    public CustomerMenu ( Menu parent ) {
        super ( "Customer Menu" , parent );
        subMenus.put(1,new PersonalInfoMenu(this));
        subMenus.put(2,new CartMenu(this));
        subMenus.put(3,new PurchaseMenu(this));
        subMenus.put(4,new CustomerOrdersMenu(this));
        subMenus.put(5,getViewDiscountCodesMenu());
        subMenus.put(6,getViewBalanceMenu());
    }

    public Menu getViewBalanceMenu(){
        return new Menu("View Balance",this) {
            @Override
            public void show() {
                //htmn customer bshe vqti b inja mirse, k exception nkhorim
                System.out.println ( "Your balance is :" );
                System.out.println ( thisGuy.getCredit () );
            }

            @Override
            public void execute() {
                //fk knm chizi ndre
            }
        };
    }

    public Menu getViewDiscountCodesMenu(){
        return new Menu("View Discount Codes",this) {
            @Override
            public void show() {
                customersDiscountCodes.forEach ( ( key , value ) -> System.out.println ( key + "\n" + "Value of hashmap : " + value ) );
            }

            @Override
            public void execute() {
                customersDiscountCodes = thisGuy.getDiscountCodes ();
            }
        };
    }

    public Menu getIncreaseCreditMenu() {
        return new Menu("Increase Balance Menu", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
            }

            @Override
            public void execute() {
                if (!PersonController.checkValidPersonType(thisGuy.getUsername(), Customer.class)) {
                    System.out.println("You Should Be A Customer.");
                    return;
                }
                String input;
                System.out.println("Enter Amount You Want to Add to Your Credit :");
                while(!(input = scanner.nextLine()).matches("\\d+(.\\d+)?") && !input.equals(".."))
                    System.out.println("Enter Valid Credit or \"..\" to Back");
                if (input.equals(".."))
                    return;
                increaseCustomerCredit(thisGuy, Double.parseDouble(input));
            }
        };
    }

    public void show() {
        System.out.println ( this.getName () + " :" );
    }

    public void execute() {

    }
}
