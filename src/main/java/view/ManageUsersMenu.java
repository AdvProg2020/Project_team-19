package view;

import controller.PersonController;
import controller.ProductController;
import model.Manager;
import model.Person;
import model.Salesperson;

import java.io.IOException;
import java.util.HashMap;

import static view.LoginMenu.*;

public class ManageUsersMenu extends Menu {

    private HashMap < String, String > personInfo = new HashMap <> (  );

    public ManageUsersMenu(Menu parent){
        super("Manage Users",parent);
        subMenus.put(1,getViewAllUsersMenu());
        subMenus.put(2,getViewUserMenu());
        subMenus.put(3,getDeleteUserMenu());
        subMenus.put(4,getCreateManagerProfileMenu());
    }

    public Menu getViewAllUsersMenu(){
        return new Menu("View All Users",this) {
            @Override
            public void show() {
                System.out.println ( PersonController.getInstance ().getEveryone () );
            }

            @Override
            public void execute() {
            }
        };
    }

    public Menu getViewUserMenu(){
        return new Menu("View A Specific User",this) {
            @Override
            public void show() {
                System.out.println("Enter username or back to return");
            }

            @Override
            public void execute() {
                String input;
                while (true) {
                    input = scanner.nextLine ();
                    if (input.equals ( BACK_BUTTON ))
                        break;
                    else if ( !PersonController.getInstance ().isTherePersonByUsername ( input ) )
                        System.out.println ( "This username doesn't exist." );
                    else {
                        System.out.println ( PersonController.getInstance ().getPersonByUsername ( input ).getPersonalInfo () );
                        System.out.println ( "You just spied on someone successfully." );
                    }

                }
            }
        };
    }

    public Menu getDeleteUserMenu(){
        return new Menu("Delete User",this) {
            @Override
            public void show() {
                System.out.println("Enter username or back to return");
            }

            @Override
            public void execute() {
                String input;
                while (true) {
                    input = scanner.nextLine ();
                    if (input.equals ( BACK_BUTTON ))
                        break;
                    else if ( !PersonController.getInstance ().isTherePersonByUsername ( input ) )
                        System.out.println ( "This username doesn't exist." );
                    else {
                        Person person = PersonController.getInstance ().getPersonByUsername ( input );
                        try {
                            if (person instanceof Salesperson ) {
                                CartController.getInstance ( ).removeSeller ( (Salesperson) person );
                                ProductController.getInstance ().removeSellerInStock ( (Salesperson) person );
                            }
                            PersonController.getInstance ().removePersonFromAllPersons ( person );
                            System.out.println ( "Removed successfully." );
                        } catch (IOException e) {
                            e.printStackTrace ( );
                            System.out.println ( "Couldn't remove." );
                        }
                    }
                }
            }
        };
    }

    public Menu getCreateManagerProfileMenu(){
        return new Menu("Create Manager Profile",this) {
            @Override
            public void show() {
                System.out.println ( BACK_HELP );
            }

            @Override
            public void execute() {
                String username;
                while (true) {
                    System.out.print ( "Enter Username : " );
                    username = scanner.nextLine ();
                    if (username.equals ( BACK_BUTTON ))
                        return;
                    try {
                        LoginMenu.usernameErrorHandler ( username , State.REGISTER );
                        personInfo.put ( "username" , username );
                        personInfo.put ( "type" , "manager" );
                        String input;
                        for (int i = 0; i < 5; i++) {
                            System.out.print ( "Enter " + informationArray[i] + " : " );
                            input = getValidInput ( patternArray[i] , i );
                            if (input.equals ( BACK_BUTTON )) {
                                personInfo.clear ();
                                return;
                            }
                            personInfo.put ( informationArray[i] , input );
                        }
                        new Manager(personInfo);
                        System.out.println ( "Manager Created." );
                        break;
                    } catch (Exception e) {
                        System.out.println ( e.getMessage () );
                    }

                }
            }
        };
    }

}
