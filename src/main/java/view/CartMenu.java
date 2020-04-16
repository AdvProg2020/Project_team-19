package view;

public class CartMenu extends Menu {
    public CartMenu(Menu parent){
        super("Cart",parent);
        submenus.put(1,getShowCartMenu());
        submenus.put(2,getViewProductMenu());
        submenus.put(3,getDecreaseProductMenu());
        submenus.put(4,getIncreaseProductMenu());
        submenus.put(5, getShowTotalPriceMenu());
        submenus.put(6,new PurchaseMenu(this));
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
