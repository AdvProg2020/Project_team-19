package view;

import controller.ProductController;
import model.Product;

public class ManagerMenu extends Menu {
    public ManagerMenu ( Menu parent ) {
        super ( "Manager Menu" , parent );
        subMenus.put ( 1 , new PersonalInfoMenu ( this ) );
        subMenus.put ( 2 , new ManageUsersMenu ( this ) );
        subMenus.put ( 3 , getManageProductsMenu ( ) );
        subMenus.put ( 4 , new ManageDiscountCodeMenu ( this ) );
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
        return new Menu ( "Manage All Products" , this ) {
            @Override
            public void show () {

            }

            @Override
            public void execute () {
                String id;
                System.out.println("Enter product id you want to remove :");
                id = getValidProductId();
                if (id.equals(BACK_BUTTON))
                    return;
                if (assertDeletion().equals("Y")) {
                    Product product = ProductController.getInstance().searchProduct(id);
                    ProductController.getInstance().removeProductForManager(product);
                    System.out.println("Your chosen product has been removed from all sellers.");
                }

            }
        };
    }


}
