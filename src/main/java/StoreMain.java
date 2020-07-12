import controller.*;
import model.Customer;
import model.Manager;
import model.Person;
import server.PacketType;
import server.Request;
import server.Server;
import view.MainMenu;
import view.Menu;
import view.UserMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import static view.Menu.mainMenu;
import static view.Menu.userMenu;

public class StoreMain {
    public static void main ( String[] args ) {
        initializer ();
        System.out.println(Database.address);
        StoreMain.manageDiscountCodeTimer ();
        StoreMain.manageDiscountTimer ();
        mainMenu = new MainMenu ( null );
        userMenu =  new UserMenu(mainMenu);
        Menu.setScanner ( new Scanner ( System.in ) );
        connectServe();
        mainMenu.run();
    }

    public static void connectServe(){
        try {
            Socket socket = new Socket("127.0.0.1",4444);
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF("hello");
            System.out.println(inputStream.readUTF());
            HashMap<String,String> info = new HashMap<>();
            info.put("username","ali");
            info.put("password","1234");
            info.put("first name","ali");
            info.put("last name","ali");
            info.put("email","ali");
            info.put("phone number","ali");
            info.put("type","manager");
            info.put("dar surate vjud sayere moshakhsat","");
            info.put("profile","");
            Request request = new Request(PacketType.LOGIN, "ali 1234");
            Scanner scanner = new Scanner(System.in);
               outputStream.writeUTF(Server.write(request));
                System.out.println(inputStream.readUTF());

        } catch (IOException e) {
            e.printStackTrace();
        }
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
        DiscountCodeController.getInstance().initializeDiscountCodes();
    }
}
