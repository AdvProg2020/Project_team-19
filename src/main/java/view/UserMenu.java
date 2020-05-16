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
        Person loggedInPerson = PersonController.getInstance().getLoggedInPerson ();
        if ( loggedInPerson == null ) {
            LoginMenu loginMenu = new LoginMenu ( this );
            loginMenu.run ();
            this.run ();
        }
        else {
            if (loggedInPerson instanceof Manager ) {
                ManagerMenu managerMenu = new ManagerMenu ( this );
                managerMenu.run();
                this.run ();
            } else if (loggedInPerson instanceof Salesperson ) {
                SalespersonMenu salespersonMenu = new SalespersonMenu ( this );
                salespersonMenu.run();
                this.run ();
            } else {
                CustomerMenu customerMenu = new CustomerMenu ( this );
                customerMenu.run ();
                this.run ();
            }
        }
    }
}
