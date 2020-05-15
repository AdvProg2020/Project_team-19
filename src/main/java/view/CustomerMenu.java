package view;

import controller.PersonController;
import model.Customer;
import model.DiscountCode;

import java.util.ArrayList;
import java.util.HashMap;

import static controller.PersonController.increaseCustomerCredit;

public class CustomerMenu extends Menu {

    private static HashMap < DiscountCode, Integer > customersDiscountCodes = new HashMap <> ( );
    private static Customer thisGuy = (Customer) PersonController.getLoggedInPerson ( );

    public CustomerMenu ( Menu parent ) {
        super ( "Customer Menu" , parent );
        subMenus.put ( 1 , new PersonalInfoMenu ( this ) );
        subMenus.put ( 2 , new CartMenu ( this ) );
        subMenus.put ( 3 , new PurchaseMenu ( this ) );
        subMenus.put ( 4 , new CustomerOrdersMenu ( this ) );
        subMenus.put ( 5 , getViewDiscountCodesMenu ( ) );
        subMenus.put ( 6 , getViewBalanceMenu ( ) );
        subMenus.put ( 7 , getLogoutMenu ( ) );
    }

    @Override
    public void execute () { //ToDo add this to customer and salesperson and manager
        Menu nextMenu;
        int chosenMenu = Integer.parseInt ( getValidMenuNumber ( subMenus.size ( ) + 1 ) );
        if ( chosenMenu == subMenus.size ( ) + 1 ) {
            nextMenu = this.parentMenu.parentMenu;
        } else
            nextMenu = subMenus.get ( chosenMenu );
        nextMenu.run ( );
    }

    public Menu getViewBalanceMenu () {
        return new Menu ( "View Balance" , this ) {
            @Override
            public void show () {
                //ToDo htmn customer bshe vqti b inja mirse, k exception nkhorim
                System.out.println ( "Your balance is :" );
                System.out.println ( thisGuy.getCredit ( ) );
            }

            @Override
            public void execute () {
                //fk knm chizi ndre
            }
        };
    }

    public Menu getViewDiscountCodesMenu () {
        return new Menu ( "View Discount Codes" , this ) {
            @Override
            public void show () {
                customersDiscountCodes = thisGuy.getDiscountCodes ( ); //in khat tu execute bud vli fk knm intorie
                customersDiscountCodes.forEach ( ( key , value ) -> System.out.println ( key + "\n" + "Value of hashmap : " + value ) );
            }

            @Override
            public void execute () {
            }
        };
    }

    public Menu getIncreaseCreditMenu () {
        return new Menu ( "Increase Balance Menu" , this ) {
            @Override
            public void show () {
                System.out.println ( this.getName ( ) + " :" );
            }

            @Override
            public void execute () {
                if ( !PersonController.checkValidPersonType ( thisGuy.getUsername ( ) , Customer.class ) ) {
                    System.out.println ( "You should be a customer." );
                    return;
                }
                String input;
                System.out.println ( "Enter amount you want to add to your credit :" );
                while ( !(input = scanner.nextLine ( )).matches ( "\\d+(.\\d+)?" ) && !input.equals ( BACK_BUTTON ) )
                    System.out.println ( "Enter valid credit or \"..\" to back" );
                if ( input.equals ( BACK_BUTTON ) )
                    return;
                increaseCustomerCredit ( thisGuy , Double.parseDouble ( input ) );
            }
        };
    }

    public void show () {
        System.out.println ( this.getName ( ) + " :" );
    }

}
