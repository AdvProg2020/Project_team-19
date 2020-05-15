package view;

import controller.PersonController;
import controller.RegisterController;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu extends Menu {

    private HashMap < String, String > personInfo;

    public enum PersonType {
        CUSTOMER ( "Customer" ),
        SALESPERSON ( "Salesperson" ),
        MANAGER ( "Manager" );

        public final String label;

        PersonType ( String label ) {
            this.label = label;
        }
    }

    private static final Pattern typePattern = Pattern.compile ( "Customer|Salesperson|Manager" , Pattern.CASE_INSENSITIVE );
    // username : more than 3 chars
    private static final Pattern usernamePattern = Pattern.compile ( "\\w{3,}" );
    // password : more than 8 chars, hatman ydune char o ydune capital o ydune adad
    private static final Pattern passwordPattern = Pattern.compile ( "(?=\\w*[0-9])(?=\\w*[a-z])(?=\\w*[A-Z])\\w{8,}" );
    // name : can have spaces
    private static final Pattern namePattern = Pattern.compile ( "([a-zA-Z0-9]+ )*[a-zA-Z0-9]+" );
    // email : blank@blank.blank
    private static final Pattern emailPattern = Pattern.compile ( "\\w+@\\w+\\.\\w+" );
    // phone : only number
    private static final Pattern phonePattern = Pattern.compile ( "[0-9]+" );

    private static String usernameInstance;

    private static String typeInstance;

    private static final String CREATE_ACCOUNT_HELP = "Enter type and username in the order shown below :" + "\n" + "create account [type] [username]";
    private static final String LOGIN_HELP = "You can login with your username by typing :" + "\n" + "login [username]";

    private static final Pattern[] patternArray = {passwordPattern , namePattern , namePattern , emailPattern , phonePattern};

    private static final String[] informationArray = {"password" , "first name" , "last name" , "email" , "phone number"};

    private static final String[] helpArray = {
            "Your password should be more than 8 characters and should contain at least 1 small letter, 1 capital letter, and 1 number." ,
            "Pardon me " + usernameInstance + " but that definitely isn't how your first name is written." ,
            usernameInstance + " is that seriously your last name?" ,
            "Enter a valid email address " + usernameInstance + ". It should be like this : blah@blah.blah" ,
            "Enter a valid phone number " + usernameInstance + ". You shouldn't put +, I already put that for you >:("};


    public LoginMenu ( Menu parent ) {
        super ( "Login Menu" , parent );
        this.subMenus.put ( 1 , getRegisterMenu ( ) );
        this.subMenus.put ( 2 , getLoginMenu ( ) );
        this.subMenus.put ( 3 , getHelpMenu ( this ) );
        personInfo = new HashMap <> ( );
    }

    @Override
    public void execute () { //ToDo add this to customer and salesperson and manager
        Menu nextMenu;
        int chosenMenu = Integer.parseInt(scanner.nextLine());
        if (chosenMenu == subMenus.size() + 1) {
            nextMenu = this.parentMenu.parentMenu;
        } else
            nextMenu = subMenus.get(chosenMenu);
        nextMenu.run ();
    }

    private Menu getRegisterMenu () {
        return new Menu ( "Register" , this ) {
            @Override
            public void show () {
                System.out.println ( this.getName () + " :" );
            }

            @Override
            public void execute () {

                getTypeAndUsernameForRegister ( );
                if (!BACK_PRESSED)
                    getPasswordTillEnd ( );


                //ToDo write on Database
                if (!BACK_PRESSED) {
                    RegisterController.getInstance().register ( personInfo );

                    System.out.println ( "A verification code has been sent to your email." );
                    System.out.println ( "Just kidding lmao" );
                    System.out.println ( "You can log in whenever you wanted to" );
                }
                BACK_PRESSED = false;
            }
        };
    }

    private Menu getLoginMenu () {
        return new Menu ( "Login" , this ) {
            @Override
            public void show () {
                System.out.println ( this.getName () + " :" );
            }

            @Override
            public void execute () {
                getUsernameForLogin();
            }
        };
    }

    private boolean checkRegex ( Pattern regex , String input ) {
        return regex.matcher ( input ).matches ( );
    }

    private String getValidInput ( Pattern regex, int arrayIndex) {
        boolean check;
        String input;
        do {
            if ( regex.equals ( phonePattern ) )
                System.out.print ( "+" );
            input = scanner.nextLine ( );
            if (input.equals ( BACK_BUTTON ))
                return BACK_BUTTON;
            check = checkRegex ( regex , input );
            if ( !check )
                System.out.println ( "Invalid input\n" +
                                        "[Help : \n" + helpArray[arrayIndex]);
        } while ( !check );

        return input;
    }

    private void getTypeAndUsernameForRegister () {

        Pattern createAccountPattern = Pattern.compile ( "create account (\\w+) (\\w+)" );
        boolean check;
        String input;
        Matcher inputsMatcher;
        do {
            System.out.println ( CREATE_ACCOUNT_HELP );
            System.out.println ( BACK_HELP );
            input = scanner.nextLine ( );
            if (input.equals ( BACK_BUTTON )) {
                BACK_PRESSED = true;
                break;
            }
            inputsMatcher = createAccountPattern.matcher ( input );
            check = registerHandler ( inputsMatcher );
        } while ( !check );
    }

    private void getUsernameForLogin() {
        Pattern loginPattern = Pattern.compile ( "login (\\w+)" );
        boolean check;
        Matcher inputsMatcher;
        String input;
        String username = null;
        String password;
        do {
            System.out.println ( LOGIN_HELP );
            System.out.println ( BACK_HELP );
            input = scanner.nextLine ( );
            if (input.equals ( BACK_BUTTON )) {
                BACK_PRESSED = true;
                break;
            }
            System.out.println ( "Enter password" );
            password = scanner.nextLine ( );
            if (input.equals ( BACK_BUTTON )) { //ToDo Tamiz nis. Tamiz this.
                BACK_PRESSED = true;
                break;
            }
            inputsMatcher = loginPattern.matcher ( input );
            check = loginHandler ( inputsMatcher , password );
        } while ( !check );

        usernameInstance = username;
        typeInstance = PersonController.getInstance().getTypeFromList(username);

    }

    private boolean registerHandler (Matcher inputsMatcher) {
        boolean check = inputsMatcher.matches ();
        String type;
        String username;
        if ( !check )
            System.out.println ( "Wtf are you doing? Don't mess with me I'm just an app xD\n" +
                                    "Write your command according to the rules!");
        else {
            try {
                type = inputsMatcher.group ( 1 );
                username = inputsMatcher.group ( 2 );
                if ( !typePattern.matcher ( type ).matches ( ) )
                    throw new Exception ("This type isn't valid.");
                registerUsernameErrorHandler ( username );
                System.out.println ( "Yaaay! It was successful! Now enter your information to complete the registration process." );
                usernameInstance = username;
                personInfo.put ( "type" , type );
                personInfo.put ( "username" , username );
                return true;
            } catch (Exception e) {
                System.out.println ( e.getMessage () );
            }
        }
        return false;
    }

    private boolean loginHandler(Matcher inputsMatcher, String password){
        boolean check = inputsMatcher.matches ();
        String username;
        if ( !check ) {
            System.out.println ( "You probably didn't write login correctly, or maybe you wrote something\n" +
                    "totally meaningless for username lol" );
            return false;
        }
        else {
            try {
                username = inputsMatcher.group ( 1 );
                PersonController.getInstance().login ( username , password );
                System.out.println ( "Yaaay! It was successful! Welcome to our app " + username + " :D" );
                return true;
            } catch (Exception e) {
                System.out.println ( e.getMessage () );
                return false;
            }
        }
    }

    private void getPasswordTillEnd () {
        String input;
        //ToDo Test This. az password ta akhare.
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
    }


    private void registerUsernameErrorHandler(String username) throws Exception{
        if ( !usernamePattern.matcher ( username ).matches ( ) )
            throw new Exception ( "Username should contain more than 3 characters." );
        if ( PersonController.getInstance().isTherePersonByUsername ( username ) )
            throw new Exception ( "This dude already exists." );
    }

}