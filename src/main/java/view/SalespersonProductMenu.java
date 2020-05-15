package view;

import controller.PersonController;
import model.Product;
import model.Salesperson;
import model.SellLog;

public class SalespersonProductMenu extends Menu {
    public SalespersonProductMenu(Menu parent){
        super("Manage Products",parent);
        subMenus.put(1,getViewProductMenu());
        subMenus.put(2,getAddProductMenu());
        subMenus.put(3,getEditProductMenu());
        subMenus.put(4,getRemoveProductMenu());
        subMenus.put(5,getViewBuyersMenu());
    }


    public Menu getViewProductMenu(){
        return new Menu("View Product",this) {
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

    public Menu getAddProductMenu(){
        return new Menu("Add Product",this) {
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

    public Menu getEditProductMenu(){
        return new Menu("Edit Product",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");

            }

            @Override
            public void execute() {
                System.out.println("Enter product id :");
                String input = getValidProductId();
                if (input.equals(BACK_BUTTON))
                    return;
                Product product = Product.getProductById(input);
                System.out.println();
            }
        };
    }

    public Menu getRemoveProductMenu(){
        return new Menu("Remove Product",this) {
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

    public Menu getViewBuyersMenu(){
        return new Menu("View Buyers",this) {
            @Override
            public void show() {
                System.out.println ( BACK_HELP );
                String productID;
                while (true) {
                    System.out.print ( "Enter product ID to see customers who bought it : " );
                    productID = getValidProductId ();
                    if (productID.equals ( BACK_BUTTON ))
                        break;
                    Salesperson salesperson = (Salesperson) PersonController.getInstance ().getLoggedInPerson ();
                    for (SellLog sellLog : salesperson.getSellLogs ( )) {
                        if (sellLog.getProduct ().getID ().equals ( productID ))
                            System.out.println ( sellLog.getBuyer ().getUsername () );
                    }
                }
            }

            @Override
            public void execute() {
                //byd khli bshe chun tu show back capibility drim
            }
        };
    }


}