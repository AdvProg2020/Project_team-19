package view;

public class SalesPersonDiscountsMenu extends Menu {
    public SalesPersonDiscountsMenu(Menu parent){
        super("Discounts Menu",parent);
        subMenus.put(1,getAddDiscountMenu());
        subMenus.put(2,getViewDiscountMenu());
        subMenus.put(3,getEditDiscountMenu());
    }

    public Menu getAddDiscountMenu(){
        return new Menu("Add Discount",this) {
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

    public Menu getViewDiscountMenu(){
        return new Menu("View Discount",this) {
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

    public Menu getEditDiscountMenu(){
        return new Menu("Edit Discount",this) {
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
