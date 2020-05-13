package view;

import controller.PersonController;

import static view.LoginMenu.getValidInput;
import static view.LoginMenu.patternArray;

public class PersonalInfoMenu extends Menu {
    public PersonalInfoMenu(Menu parent) {
        super ( "Personal Information" , parent );
        this.subMenus.put ( 1 , getViewMenu () );
        this.subMenus.put ( 2 , getEditMenu () );
    }

    public Menu getViewMenu(){
        return new Menu("View",this) {
            @Override
            public void show() {
                System.out.println (
                        PersonController.getLoggedInPerson ().getPersonalInfo ()
                );
                System.out.println ( "\u2014\u2014\u2014\u2014\u2014\u2014" );
                super.show ();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    public Menu getEditMenu(){
        return new Menu("Edit",this) {
            @Override
            public void show() {
                System.out.println ( "1. Password" );
                System.out.println ( "2. First Name" );
                System.out.println ( "3. Last Name" );
                System.out.println ( "4. Email" );
                System.out.println ( "5. Phone Number" );
                System.out.println ( "Which field do you want to edit?" );
            }

            @Override
            public void execute() { //ToDO mitune bre tu menu, submenus bzrim
                int chosenMenu = Integer.parseInt ( getValidMenuNumber ( 5 ) );
                System.out.println ( "Enter desired value : " );
                String desiredValue = getValidInput ( patternArray[chosenMenu-1] , chosenMenu-1 );
                PersonController.getLoggedInPerson ().setField
                        ( LoginMenu.informationArray[chosenMenu-1] , desiredValue );
            }
        };
    }
}
