package view;

import controller.PersonController;
import model.Manager;
import model.Person;
import model.Salesperson;
import view.*;

import static controller.PersonController.getLoggedInPerson;

public class UserMenu extends Menu {
    public UserMenu (Menu parent) {
        super ( "User Menu" , parent );

    }

    @Override
    public void show () {

    }

    @Override
    public void execute () {
        if ( getLoggedInPerson () == null ) {
            LoginMenu loginMenu = new LoginMenu ( this );
            loginMenu.run ();
        }
        else {
            if (getLoggedInPerson () instanceof Manager ) {
                ManagerMenu managerMenu = new ManagerMenu ( this );
                managerMenu.run();
            } else if (getLoggedInPerson () instanceof Salesperson ) {
                SalespersonMenu salespersonMenu = new SalespersonMenu ( this );
                salespersonMenu.run();
            } else {
                CustomerMenu customerMenu = new CustomerMenu ( this );
                customerMenu.run ();
            }
        }
    }
}
