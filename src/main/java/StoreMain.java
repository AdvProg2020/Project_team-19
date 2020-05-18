import controller.*;
import view.MainMenu;
import view.Menu;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class StoreMain {
    public static void main ( String[] args ) {
        Database.createDatabase();
        Database.initializeAddress ( );
        CategoryController.getInstance().initializeRootCategories();
        PersonController.getInstance ().initializePersons ();
        ProductController.getInstance ().initializeProducts ();
        RequestController.getInstance ().initializeRequests ();
        ProductController.getInstance ().initializeStock ();
        MainMenu mainMenu = new MainMenu ( null );
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
}
