package view;

import controller.PersonController;
import controller.RegisterController;
import model.Manager;
import model.Person;

import java.io.IOException;
import java.util.HashMap;

import static view.LoginMenu.*;

public class ManageUsersMenu extends Menu {

    private HashMap < String, String > personInfo;

    public ManageUsersMenu(Menu parent){
        super("Manage Users",parent);
        subMenus.put(1,getViewUserMenu());
        subMenus.put(2,getDeleteUserMenu());
        subMenus.put(3,getCreateManagerProfileMenu());
    }

    public Menu getViewUserMenu(){
        return new Menu("View User",this) {
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
                    else if ( !PersonController.isTherePersonByUsername ( input ) )
                        System.out.println ( "This username doesn't exist." );
                    else {
                        System.out.println ( PersonController.getPersonByUsername ( input ).getPersonalInfo () );
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
                    else if ( !PersonController.isTherePersonByUsername ( input ) )
                        System.out.println ( "This username doesn't exist." );
                    else {
                        Person person = PersonController.getPersonByUsername ( input );
                        try {
                            PersonController.removePersonFromAllPersons ( person );
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
                    System.out.print ( "Enter username : " );
                    username = scanner.nextLine ();
                    if (username.equals ( BACK_BUTTON ))
                        return;
                    try {
                        LoginMenu.registerUsernameErrorHandler ( username );
                        personInfo.put ( "username" , username );
                        personInfo.put ( "type" , "manager" );
                        String input;
                        for (int i = 0; i < 5; i++) {
                            System.out.println ( "Enter " + informationArray[i] );
                            input = getValidInput ( patternArray[i] , i );
                            if (input.equals ( BACK_BUTTON )) {
                                personInfo.clear ();
                                BACK_PRESSED = true;
                                return;
                            }
                            personInfo.put ( informationArray[i] , input );
                        }
                        new Manager(personInfo);
                        System.out.println ( "Manager Created." );
                    } catch (Exception e) {
                        System.out.println ( e.getMessage () );
                    }

                }
            }
        };
    }

}
