package view;

public class PurchaseMenu extends Menu {
    public PurchaseMenu(Menu parent){
        super("Purchase Menu",parent);
        submenus.put(1,getReceiverInformationMenu());
        submenus.put(2,getDiscountCodeMenu());
        submenus.put(3,getPaymentMenu());
    }

    public Menu getReceiverInformationMenu(){
        return new Menu("Receiver Information",this) {
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

    public Menu getDiscountCodeMenu(){
        return new Menu("discount Code",this) {
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

    public Menu getPaymentMenu(){
        return new Menu("Payment",this) {
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
