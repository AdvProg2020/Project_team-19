package view;

import java.util.HashMap;
import controller.*;

public class LoginMenu extends Menu {
    private HashMap<String,String> personInfo ;
    public LoginMenu ( Menu parent ) {
        super ( "Login Menu" , parent );
        this.subMenus.put(1,getRegisterMenu());
        this.subMenus.put(2,getLoginMenu());
        personInfo = new HashMap<String, String>();
    }

    private Menu getRegisterMenu() {
        return new Menu("Register", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                System.out.println("Username must contain more than 4 character and include digit or alphabet");
                System.out.println("Enter username");
                String username = scanner.nextLine();
                    personInfo.put("username", username);
                    System.out.println("Enter type");
                    personInfo.put("type", scanner.nextLine());
                    System.out.println("Enter first name");
                    System.out.println("Enter password");
                    personInfo.put("password", scanner.nextLine());
                    personInfo.put("firstName", scanner.nextLine());
                    System.out.println("Enter last name");
                    personInfo.put("lastName", scanner.nextLine());
                    System.out.println("Enter email");
                    personInfo.put("email", scanner.nextLine());
                    System.out.println("Enter phone number");
                    personInfo.put("phoneNumber", scanner.nextLine());
                }
        };
    }

    private Menu getLoginMenu(){
        return new Menu("Login",this) {
            @Override
            public void show() {
                //to do
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }


}
