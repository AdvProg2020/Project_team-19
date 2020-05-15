import controller.Database;
import controller.PersonController;
import controller.ProductController;
import controller.RequestController;
import view.MainMenu;
import view.Menu;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class StoreMain {
    public static void main ( String[] args ) throws FileNotFoundException {
        Database.initializeAddress ( );
        PersonController.initializePersons ();
        ProductController.initializeProducts ();
        RequestController.initializeRequests ();
        MainMenu mainMenu = new MainMenu ( null );
        Menu.setScanner ( new Scanner ( System.in ) );
        mainMenu.run();
    }
}
