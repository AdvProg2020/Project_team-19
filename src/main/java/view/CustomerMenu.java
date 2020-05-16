package view;

import controller.PersonController;
import model.Customer;
import model.DiscountCode;

import java.util.HashMap;

public class CustomerMenu extends Menu {

    private HashMap < DiscountCode, Integer > customersDiscountCodes = new HashMap <> ( );
    private Customer customer = (Customer) PersonController.getInstance().getLoggedInPerson ( );

    public CustomerMenu ( Menu parent ) {
        super ( "Customer Menu" , parent );
        subMenus.put ( 1 , new PersonalInfoMenu ( this ) );
        subMenus.put ( 2 , new CartMenu ( this ) );
        subMenus.put ( 3 , new PurchaseMenu ( this ) );
        subMenus.put ( 4 , new CustomerOrdersMenu ( this ) );
        subMenus.put ( 5 , getViewDiscountCodesMenu ( ) );
        subMenus.put ( 6 , getViewBalanceMenu ( ) );
        subMenus.put ( 7 , getIncreaseCreditMenu ( ) );
        subMenus.put ( 8 , getLogoutMenu ( ) );
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
                System.out.println ( customer.getCredit ( ) );
            }

            @Override
            public void execute () {
                //fk knm chizi ndre
            }
        };
    }

    public Menu getIncreaseCreditMenu() {
        return new Menu("Increase Balance Menu", this) {
            @Override
            public void show() {
                fancyTitle ();
            }

            @Override
            public void execute() {
                if (!PersonController.getInstance().checkValidPersonType( customer.getUsername(), Customer.class)) {
                    System.out.println("You Should Be A Customer.");
                    return;
                }
                String input;
                System.out.println("Enter Amount You Want to Add to Your Credit :");
                while(!(input = scanner.nextLine()).matches("\\d+(.\\d+)?") && !input.equals(".."))
                    System.out.println("Enter Valid Credit or \"..\" to Back");
                if (input.equals(".."))
                    return;

                PersonController.getInstance().increaseCustomerCredit( customer , Double.parseDouble(input));
            }
        };
    }

    public Menu getViewDiscountCodesMenu () {
        return new Menu ( "View Discount Codes" , this ) {
            @Override
            public void show () {
                customersDiscountCodes = customer.getDiscountCodes ( ); //in khat tu execute bud vli fk knm intorie
                customersDiscountCodes.forEach ( ( key , value ) -> System.out.println ( key + "\n" + "Value of hashmap : " + value ) );
            }

            @Override
            public void execute () {
            }
        };
    }

}
