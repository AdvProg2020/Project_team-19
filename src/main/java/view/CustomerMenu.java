package view;

public class CustomerMenu extends Menu {
    public CustomerMenu ( Menu parent ) {
        super ( "Customer Menu" , parent );
        submenus.put(1,new PersonalInfoMenu(this));
        submenus.put(2,new CartMenu(this));
        submenus.put(3,new PurchaseMenu(this));
        submenus.put(4,new CustomerOrdersMenu(this));
        submenus.put(5,getViewDiscountCodesMenu());
        submenus.put(6,getViewBalanceMenu());
    }

    public Menu getViewBalanceMenu(){
        return new Menu("View Balance",this) {
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

    public Menu getViewDiscountCodesMenu(){
        return new Menu("View Discount Codes",this) {
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

    public void show() {

    }

    public void execute() {

    }
}
