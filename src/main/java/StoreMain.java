import controller.Database;
import controller.PersonController;
import controller.ProductController;
import controller.RequestController;
import view.MainMenu;
import view.Menu;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class StoreMain {
    public static void main ( String[] args ) {
        Database.initializeAddress ( );
        PersonController.getInstance ().initializePersons ();
        ProductController.getInstance ().initializeProducts ();
        RequestController.getInstance ().initializeRequests ();
        MainMenu mainMenu = new MainMenu ( null );
        Menu.setScanner ( new Scanner ( System.in ) );
        mainMenu.run();
    }
}
