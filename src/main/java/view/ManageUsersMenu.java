package view;

public class ManageUsersMenu extends Menu {
    public ManageUsersMenu(Menu parent){
        super("Manage Users",parent);
        submenus.put(1,getViewUserMenu());
        submenus.put(2,getDeleteUserMenu());
        submenus.put(3,getCreateManagerProfileMenu());
    }

    public Menu getViewUserMenu(){
        return new Menu("View User",this) {
            @Override
            public void show() {
                System.out.println("Enter username or back to return");

            }

            @Override
            public void execute() {

            }
        };
    }

    public Menu getDeleteUserMenu(){
        return new Menu("Delete User",this) {
            @Override
            public void show() {
                System.out.println("Enter username or back to return");
            }

            @Override
            public void execute() {

            }
        };
    }

    public Menu getCreateManagerProfileMenu(){
        return new Menu("Create Manager Profile",this) {
            @Override
            public void show() {
                System.out.println();
            }

            @Override
            public void execute() {

            }
        };
    }

}
