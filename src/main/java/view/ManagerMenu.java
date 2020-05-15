package view;

public class ManagerMenu extends Menu {
    public ManagerMenu ( Menu parent ) {
        super ( "Manager Menu" , parent );
        subMenus.put ( 1 , new PersonalInfoMenu ( this ) );
        subMenus.put ( 2 , new ManageUsersMenu ( this ) );
        subMenus.put ( 3 , getManageProductsMenu ( ) );
        subMenus.put ( 4 , new ManagerDiscountCodeMenu ( this ) );
        subMenus.put ( 5 , new ManageRequestsMenu ( this ) );
        subMenus.put ( 6 , new ManageCategoriesMenu ( this ) );
        subMenus.put ( 7 , getLogoutMenu ( ) );

    }

    @Override
    public void execute () { //ToDo add this to customer and salesperson and manager
        Menu nextMenu;
        int chosenMenu = Integer.parseInt ( getValidMenuNumber ( subMenus.size ( ) + 1 ) );
        if ( chosenMenu == subMenus.size ( ) + 1 ) {
            nextMenu = this.parentMenu.parentMenu;
        } else
            nextMenu = subMenus.get ( chosenMenu );
        nextMenu.run ( );
    }

    public Menu getManageProductsMenu () {
        //ToDo change it to class
        return new Menu ( "Manage All Products" , this ) {
            @Override
            public void show () {

            }

            @Override
            public void execute () {

            }
        };
    }


}
