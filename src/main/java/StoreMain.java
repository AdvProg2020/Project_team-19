import controller.*;
import view.MainMenu;
import view.Menu;

import java.util.Scanner;

public class StoreMain {
    public static void main ( String[] args ) {
        Database.initializeAddress ( );
        PersonController.getInstance ().initializePersons ();
        ProductController.getInstance ().initializeProducts ();
        RequestController.getInstance ().initializeRequests ();
        CategoryController.getInstance ().initializeRootCategories ();
        ProductController.getInstance ().initializeStock ();
        MainMenu mainMenu = new MainMenu ( null );
        Menu.setScanner ( new Scanner ( System.in ) );
        mainMenu.run();
    }
}
