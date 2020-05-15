package view;

import model.Product;

class SalespersonProductMenu extends Menu {
    public SalespersonProductMenu(Menu parent){
        super("Manage Products",parent);
        subMenus.put(1,getViewProductMenu());
        //subMenus.put(2, )
        subMenus.put(3,getEditProductMenu());
        subMenus.put(4,getRemoveProductMenu());
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
}
