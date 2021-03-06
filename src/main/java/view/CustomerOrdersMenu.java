package view;

import controller.PersonController;
import controller.ProductController;
import model.BuyLog;
import model.Customer;
import model.Product;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerOrdersMenu extends Menu {

    private static final Pattern showOrderPattern = Pattern.compile ( "show order ([0-9]+)" );
    private static final Pattern rateProductPattern = Pattern.compile ( "rate ([0-9]+) ([1-5])" );

    public CustomerOrdersMenu(Menu parent){
        super("View Orders",parent);
    }

    @Override
    public void show () {
        Customer customer = null;// (Customer) PersonController.getInstance ().getLoggedInPerson ();
        System.out.println ( customer.getBuyLogs () );
        System.out.println (
                "These commands are available :\n" +
                "show order [orderId]\n" +
                "rate [productId] [1-5]\n" +
                "You can rate each product once per buy");
        System.out.println ( BACK_HELP );

    }

    @Override
    public void execute() {
        String input;
        Matcher matcher;
        Customer customer = null;// = (Customer) PersonController.getInstance ().getLoggedInPerson ();
        do {
            input = scanner.nextLine();
            if (input.equals (BACK_BUTTON)) {
                return;
            } else if ((matcher = showOrderPattern.matcher ( input )).matches ()) {
                BuyLog buyLog = customer.findBuyLogById (matcher.group ( 1 ));
                if (buyLog != null)
                    System.out.println ( buyLog.getEverythingString () );
                else
                    System.out.println ( "There is no such order with such ID." );
            } else if ((matcher = rateProductPattern.matcher ( input )).matches ()) {
                String productId = matcher.group ( 1 );
                int rating = Integer.parseInt ( matcher.group ( 2 ) );
                if ( !ProductController.getInstance ().isThereProductById(productId) ) { //
                    System.out.println ( "There is no such product with such ID." );
                } else if (!customer.isProductBought ( ProductController.getInstance().getProductById ( productId ) )) {
                    System.out.println ( "You have not bought this product! You should buy before giving a rating." );
                } else {
                    customer.setScore ( ProductController.getInstance().getProductById ( productId ) , rating ); //ToDo az unvar drs nis
                }
            }
        } while (true);
    }
}
