package model;

import controller.PersonController;
import view.*;

public class UserMenu extends Menu {
    public UserMenu (Menu parent) {
        super ( "User Menu" , parent );
        Person loggedInPerson = PersonController.getLoggedInPerson ();
        if ( loggedInPerson == null ) {
            LoginMenu loginMenu = new LoginMenu ( this );
            loginMenu.show ();
            loginMenu.execute ();
        }
        else {
            if (loggedInPerson instanceof Manager) {
                ManagerMenu managerMenu = new ManagerMenu ( this );
//                managerMenu.run();
            } else if (loggedInPerson instanceof Salesperson) {
                SalespersonMenu salespersonMenu = new SalespersonMenu ( this );
//                salespersonMenu.run();
            } else {
                CustomerMenu customerMenu = new CustomerMenu ( this );

            }
        }
    }
}
