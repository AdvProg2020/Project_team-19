package view;

public class ManagerDiscountCodeMenu extends Menu {
    public ManagerDiscountCodeMenu(Menu parent){
        super("Discount Menu",parent);
        submenus.put(1,getCreateDiscountCode());
        submenus.put(2,getViewDiscountCode());
        submenus.put(3,getEditDiscountCode());
        submenus.put(4,getDeleteDiscountCode());

    }

    public Menu getCreateDiscountCode(){
        return new Menu("Create Discount Code",this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {

            }
        };
    }

    public Menu getViewDiscountCode(){
        return new Menu("View Discount Code",this) {
            @Override
            public void show() {
                System.out.println("Enter code or back to return");
            }

            @Override
            public void execute() {

            }
        };
    }

    public Menu getEditDiscountCode(){
        return new Menu("Edit Discount Code",this) {
            @Override
            public void show() {
                System.out.println("Enter code or back to return");

            }

            @Override
            public void execute() {

            }
        };
    }

    public Menu getDeleteDiscountCode(){
        return new Menu("Delete Discount Code",this) {
            @Override
            public void show() {
                System.out.println("Enter code or back to return");

            }

            @Override
            public void execute() {

            }
        };
    }

}
