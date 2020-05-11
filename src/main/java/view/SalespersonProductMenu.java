package view;

public class SalespersonProductMenu extends Menu {
    public SalespersonProductMenu(Menu parent){
        super("Manage Products",parent);
        subMenus.put(1,new ManageProduct(this));
        subMenus.put(2,getViewSalesHistory());
        subMenus.put(3,getAddProductMenu());
        subMenus.put(4,getShowCategoriesMenu());
    }

    public Menu getViewSalesHistory(){
        return new Menu("View Sales History",this) {
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

    public Menu getShowCategoriesMenu(){
        return new Menu("Show Category",this) {
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

class ManageProduct extends Menu{
    public ManageProduct(Menu parent){
        super("Manage Products",parent);
        subMenus.put(1,getViewProductMenu());
        subMenus.put(2,getEditProductMenu());
        subMenus.put(3,getRemoveProductMenu());
        subMenus.put(4,getViewBuyersMenu());
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

    public Menu getViewBuyersMenu(){
        return new Menu("View Buyers",this) {
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
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
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
}
