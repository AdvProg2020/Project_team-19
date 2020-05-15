package view;

import controller.CartController;
import controller.PersonController;
import controller.ProductController;
import model.Customer;
import model.DiscountCode;

public class PurchaseMenu extends Menu {
    private String information;
    private boolean discountCodeIsUsed = false;

    public PurchaseMenu ( Menu parent ) {
        super ( "Purchase Menu" , parent );
        subMenus.put ( 1 , getReceiverInformationMenu ( ) );
        subMenus.put ( 2 , getDiscountCodeMenu ( ) );
        subMenus.put ( 3 , getPaymentMenu ( ) );
    }

    public Menu getReceiverInformationMenu () {
        return new Menu ( "Receiver Information" , this ) {
            @Override
            public void show () {
                System.out.println ( this.getName ( ) + " :" + "\n" +
                        "Enter \"..\" to back" );
            }

            @Override
            public void execute () {
                String input = "";
                System.out.println ( "Enter your address :" );
                input = scanner.nextLine ( );
                if ( input.equals ( BACK_BUTTON ) )
                    return;
                information += input;
                System.out.println ( "Enter your postal code :" );
                input = scanner.nextLine ( );
                if ( input.equals ( BACK_BUTTON ) )
                    return;
                information += "\\" + input;
                System.out.println ( "Enter additional information :" );
                input = scanner.nextLine ( );
                if ( input.equals ( BACK_BUTTON ) )
                    return;
                information += "\\" + input;
                System.out.println ( "When would you like to receive your purchase ? \n 1.8-12 AM \n 2.4-8 PM" );
                while ( (!(input = scanner.nextLine ( )).equals ( "1" ) && !input.equals ( "2" )) && !input.equals ( BACK_BUTTON ) ) {
                    System.out.println ( "What the fuck? 1 or 2 or \"..\" to back" );
                }
                if ( input.equals ( BACK_BUTTON ) )
                    return;
                information += "\\" + input;
            }
        };
    }

    public Menu getDiscountCodeMenu () {
        return new Menu ( "discount Code" , this ) {
            @Override
            public void show () {

            }

            @Override
            public void execute () {
                if ( discountCodeIsUsed ) {
                    System.out.println ( "You have already used discount code." );
                    return;
                }
                System.out.println ( "Enter discount code:" );
                String code = getDiscountCode ( );
                if ( code.equals ( BACK_BUTTON ) )
                    return;
                CartController.getInstance ( ).manageDiscountCode ( DiscountCode.findDiscountCodeByCode ( code ) );
                discountCodeIsUsed = true;
            }
        };
    }

    public Menu getPaymentMenu () {
        return new Menu ( "Payment" , this ) {
            @Override
            public void show () {
                super.show ( );
            }

            @Override
            public void execute () {
                try {
                    CartController.getInstance ( ).purchase ( );
                    System.out.println ( "" );//payam khoshgel chap konim
                } catch (Exception e) {
                    System.out.println ( e.getMessage ( ) );
                }
            }
        };
    }

    public String getDiscountCode () {
        helpMessage = "You can enter discount code here" + "\n" + "You can use discount code only one time in a purchase";
        Customer customer = (Customer) PersonController.getLoggedInPerson ( );
        boolean check;
        String input;
        do {
            input = scanner.nextLine ( );
            if ( input.equals ( BACK_BUTTON ) )
                return input;
            check = customer.isThereDiscountCodeByCode ( input );
            if ( !check ) {
                System.out.println ( "you do not have such discount code:" );
            }

        } while ( !check );
        return input;
    }
}
