package view;

public class CustomerMenu extends Menu {
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
}
