package view;

public class CustomerOrdersMenu extends Menu {
    public CustomerOrdersMenu(Menu parent){
        super("View Orders",parent);
        subMenus.put(1, getShowOrderMenu ());
        subMenus.put(2,getRateProductMenu());
    }

    public Menu getShowOrderMenu (){
        return new Menu("Show Order",this) {
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

    public Menu getRateProductMenu(){
        return new Menu("Rate Product",this) {
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

    @Override
    public void show () {
        System.out.println ( "show order [orderId]" );
        System.out.println ( "rate [productId] [1-5]" );
    }

    @Override
    public void execute () {

    }
}
