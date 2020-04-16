package view;

public class ManagerMenu extends Menu {
    public ManagerMenu(Menu parent) {
        super("Manager Menu", parent);
        submenus.put(1, new PersonalInfoMenu(this));
        submenus.put(2, new ManageUsersMenu(this));
        submenus.put(3, getManageProductsMenu());
        submenus.put(4, new ManagerDiscountCodeMenu(this));
        submenus.put(5, new ManageRequestsMenu(this));
        submenus.put(6, new ManageCategoriesMenu(this));

    }

    public Menu getManageProductsMenu() {
        //To Do change it to class
        return new Menu("Mange All Products", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {

            }
        };
    }


}
