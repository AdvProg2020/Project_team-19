import view.MainMenu;
import view.Menu;

import java.util.Scanner;

public class StoreMain {
    public static void main ( String[] args ) {
        MainMenu mainMenu = new MainMenu ( null );
        Menu.setScanner ( new Scanner ( System.in ) );
        mainMenu.run();
    }
}
