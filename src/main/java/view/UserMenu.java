package view;

import controller.PersonController;
import view.CustomerMenu;
import model.Manager;
import model.Person;
import model.Salesperson;

public class UserMenu extends Menu {
    public UserMenu (Menu parent) {
        super ( "User Menu" , parent );
    }

    @Override
    public void show () {

    }

    @Override
    public void execute () {
        Person loggedInPerson = PersonController.getLoggedInPerson ();
        if ( loggedInPerson == null ) {
            LoginMenu loginMenu = new LoginMenu ( this );
            loginMenu.run ();
        }
        else {
            if (loggedInPerson instanceof Manager ) {
                ManagerMenu managerMenu = new ManagerMenu ( this );
            } else if (loggedInPerson instanceof Salesperson ) {
                SalespersonMenu salespersonMenu = new SalespersonMenu ( this );
            } else {
                CustomerMenu customerMenu = new CustomerMenu ( this );
            }
        }
    }
}
