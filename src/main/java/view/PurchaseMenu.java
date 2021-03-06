package view;

import controller.CartController;
import controller.DiscountCodeController;
import controller.PersonController;
import controller.ProductController;
import model.Customer;
import model.DiscountCode;

public class PurchaseMenu extends Menu {
    private String information;
    private boolean discountCodeIsUsed = false;
    public PurchaseMenu(Menu parent){
        super("Purchase Menu",parent);
        subMenus.put(1,getReceiverInformationMenu());
        subMenus.put(2,getDiscountCodeMenu());
        subMenus.put(3,getPaymentMenu());
    }

    public Menu getReceiverInformationMenu(){
        return new Menu("Receiver Information",this) {
            @Override
            public void show() {
                fancyTitle ();
            }

            @Override
            public void execute() {
                String input = "";
                System.out.println("Enter Your Address :");
                input = scanner.nextLine();
                if (input.equals(".."))
                    return;
                information += input;
                System.out.println("Enter Your Postal Code :");
                input = scanner.nextLine();
                if (input.equals(".."))
                    return;
                information += "\\" + input;
                System.out.println("Enter Additional Information :");
                input = scanner.nextLine();
                if (input.equals(".."))
                    return;
                information += "\\" + input;
                System.out.println("When Would You Like To Receive Your Purchase ? \n 1.8-12 AM \n 2.4-8 PM");
                while ((!(input = scanner.nextLine()).equals("1")  && !input.equals("2")) && !input.equals("..")) {
                    System.out.println("What the Fuck? 1 or 2 or \"..\" to Back");
                }
                if (input.equals(".."))
                    return;
                information += "\\" + input;
            }
        };
    }

    public Menu getDiscountCodeMenu(){
        return new Menu("discount Code",this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                if(discountCodeIsUsed){
                    System.out.println("You have already used discount code.");
                    return;
                }
                System.out.println("Enter discount code:");
                String code = getDiscountCode();
                if (code.equals(BACK_BUTTON))
                    return;
               // CartController.getInstance().manageDiscountCode(DiscountCodeController.getInstance().findDiscountCodeByCode(code));
                discountCodeIsUsed = true;
            }
        };
    }

    public Menu getPaymentMenu(){
        return new Menu("Payment",this) {
            @Override
            public void show() {
                //super.show();
            }

            @Override
            public void execute() {
                try {
                  //  CartController.getInstance().purchase();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        };
    }

    public String getDiscountCode(){
        Customer customer= null;// (Customer) PersonController.getInstance().getLoggedInPerson();

        boolean check;
        String input;
        do {
            input = scanner.nextLine ( );
            if(input.equals(".."))
                return input;
            check = customer.isThereDiscountCodeByCode(input);
            if (!check){
                System.out.println("you do not have such discount code:");
            }

        } while (!check);
        return input;
    }
}
