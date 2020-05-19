import controller.*;
import view.MainMenu;
import view.Menu;
import view.UserMenu;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import static view.Menu.mainMenu;
import static view.Menu.userMenu;

public class StoreMain {
    public static void main ( String[] args ) {
        initializer ();
        StoreMain.manageDiscountCodeTimer ();
        StoreMain.manageDiscountTimer ();
        mainMenu = new MainMenu ( null );
        userMenu =  new UserMenu(mainMenu);
        Menu.setScanner ( new Scanner ( System.in ) );
        mainMenu.run();
    }

    public static void manageDiscountCodeTimer(){
        Timer timer = new Timer();
        TimerTask task = new DiscountCodeTimer();
        timer.schedule(task,60000);
    }

    public static void manageDiscountTimer(){
        Timer timer = new Timer();
        TimerTask task = new DiscountTimer();
        timer.schedule(task,60000);
    }

    public static void initializer(){
        Database.createDatabase ();
        Database.initializeAddress ( );
        ProductController.getInstance ().initializeProducts ();
        CategoryController.getInstance().initializeRootCategories();
        PersonController.getInstance ().initializePersons ();
        ProductController.getInstance ().initializeStock ();
        RequestController.getInstance ().initializeRequests ();
    }
}
