package view;

public class CartMenu extends Menu {
    public CartMenu(Menu parent){
        super("Cart",parent);
        subMenus.put(1,getShowCartMenu());
        subMenus.put(2,getViewProductMenu());
        subMenus.put(3,getDecreaseProductMenu());
        subMenus.put(4,getIncreaseProductMenu());
        subMenus.put(5, getShowTotalPriceMenu());
        subMenus.put(6,new PurchaseMenu(this));
    }

    public Menu getShowCartMenu(){
        return new Menu("Show Cart",this) {
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

    public Menu getIncreaseProductMenu(){
        return new Menu("Increase Product",this) {
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

    public Menu getDecreaseProductMenu(){
        return new Menu("Decrease Product",this) {
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

    public Menu getShowTotalPriceMenu(){
        return new Menu("Show Total Price",this) {
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
