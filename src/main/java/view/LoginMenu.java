package view;

import controller.PersonController;
import controller.RegisterController;

import java.util.HashMap;
import java.util.regex.Pattern;

import static view.LoginMenu.PersonInfo.*;

public class LoginMenu extends Menu {

    private HashMap < String, String > personInfo;

    public enum PersonInfo {
        USERNAME ( "username" ),
        PASSWORD ( "password" ),
        FIRST_NAME ( "first name" ),
        LAST_NAME ( "last name" ),
        EMAIL ( "email" ),
        PHONE ( "phone number" ),
        COMPANY ( "company" ),
        SAYERE_MOSHAKHASAT ( "dar surate vjud sayere moshakhsat" ),
        PROFILE ("profile");

        public final String label;

        PersonInfo ( String label ) {
            this.label = label;
        }
    }

    public enum State {
        REGISTER, LOGIN
    }

    public static final Pattern typePattern = Pattern.compile ( "Customer|Salesperson|Manager" , Pattern.CASE_INSENSITIVE );
    // username : more than 3 chars
    public static final Pattern usernamePattern = Pattern.compile ( "\\w{3,}" );
    // password : more than 8 chars, hatman ydune char o ydune capital o ydune adad
    public static final Pattern passwordPattern = Pattern.compile ( "(?=\\w*[0-9])(?=\\w*[a-z])(?=\\w*[A-Z])\\w{8,}" );
    // name : can have spaces
    public static final Pattern namePattern = Pattern.compile ( "([a-zA-Z0-9]+ )*[a-zA-Z0-9]+" );
    // email : blank@blank.blank
    public static final Pattern emailPattern = Pattern.compile ( "\\w+@\\w+\\.\\w+" );
    // phone : only number
    public static final Pattern phonePattern = Pattern.compile ( "[0-9]+" );

    private static String usernameInstance;

    private static String typeInstance;

    public static final Pattern[] patternArray = {passwordPattern , namePattern , namePattern , emailPattern , phonePattern , namePattern , Pattern.compile ( ".*" )};

    public static final String[] informationArray = {PASSWORD.label , FIRST_NAME.label , LAST_NAME.label , EMAIL.label , PHONE.label , COMPANY.label , SAYERE_MOSHAKHASAT.label};

    public static String[] helpArray;


    public LoginMenu ( Menu parent ) {
        super ( "Login Menu" , parent );
        this.subMenus.put ( 1 , getRegisterMenu ( ) );
        this.subMenus.put ( 2 , getLoginMenu ( ) );
        personInfo = new HashMap <> ( );
    }

    @Override
    public void show () {
        eachUserShowMenu ();
    }

    @Override
    public void execute () {
        eachUserExecuteMenu ();
    }

    private Menu getRegisterMenu () {
        return new Menu ( "Register" , this ) {
            @Override
            public void show () {
                fancyTitle ();
            }

            @Override
            public void execute () {

                System.out.println ( BACK_HELP );
                System.out.print ( "Enter Username : " );
                String username = getValidUsername ( State.REGISTER );
                if ( username.equals ( BACK_BUTTON ) )
                    return;
                usernameInstance = username;
                setHelpArray ();
                personInfo.put ( "username" , username );

                System.out.print ( "Enter Type : " );
                String type = getValidType ( );
                if ( type.equals ( BACK_BUTTON ) )
                    return;
                typeInstance = type;
                personInfo.put ( "type" , type );

                boolean backPressed = getPasswordTillEnd ( );

                if ( !backPressed ) {
                    RegisterController.getInstance ( ).register ( personInfo );
                    System.out.println ( "Nice Job. Now Login." );
                }
            }
        };
    }

    private Menu getLoginMenu () {
        return new Menu ( "Login" , this ) {
            @Override
            public void show () {
                fancyTitle ();
            }

            @Override
            public void execute () {
                System.out.println ( BACK_HELP );
                System.out.print ( "Enter Username : " );
                String username = getValidUsername ( State.LOGIN );
                if ( username.equals ( BACK_BUTTON ) )
                    return;
                System.out.print ( "Enter Password : " );
                String password = getValidPassword (username);
                if ( password.equals ( BACK_BUTTON ) )
                    return;
                PersonController.getInstance ().login ( username );
                usernameInstance = username;
                setHelpArray ();
                System.out.println ( "Yaaay! It was successful! Welcome to our app " + username + " :D" );
            }
        };
    }

    private static boolean checkRegex ( Pattern regex , String input ) {
        return regex.matcher ( input ).matches ( );
    }

    public static String getValidInput ( Pattern regex , int arrayIndex ) {
        boolean check;
        String input;
        do {
            if ( regex.equals ( phonePattern ) )
                System.out.print ( "+" );
            input = scanner.nextLine ( );
            if ( input.equals ( BACK_BUTTON ) )
                return BACK_BUTTON;
            check = checkRegex ( regex , input );
            if ( !check ) {
                System.out.println ( "Invalid input\n" +
                        "[Help : \n" + helpArray[arrayIndex] + "]" );
                System.out.print ( "Enter " + informationArray[arrayIndex] + " : " );
            }
        } while ( !check );

        return input;
    }

    private static String getValidUsername ( State state ) {
        String username;
        while ( true ) {
            username = scanner.nextLine ( );
            if ( username.equals ( BACK_BUTTON ) )
                break;
            try {
                usernameErrorHandler ( username , state );
                break;
            } catch (Exception e) {
                System.out.println ( e.getMessage ( ) );
                System.out.print ( "Enter Username : " );
            }
        }
        return username;
    }

    private static String getValidType () {
        String type;
        while ( true ) {
            type = scanner.nextLine ( );
            if ( type.equals ( BACK_BUTTON ) )
                break;
            try {
                typeErrorHandler ( type );
                break;
            } catch (Exception e) {
                System.out.println ( e.getMessage ( ) );
                System.out.print ( "Enter Type : " );
            }
        }
        return type;
    }

    private static String getValidPassword ( String username) {
        String password;
        while (true) {
            password = scanner.nextLine ( );
            if ( password.equals ( BACK_BUTTON ) )
                break;
            try {
                PersonController.getInstance ( ).checkPassword ( password , username );
                break;
            } catch (Exception e) {
                System.out.println ( e.getMessage ( ) );
                System.out.print ( "Enter Password : " );
            }
        }
        return password;
    }

    public boolean getPasswordTillEnd () {
        String input;
        for (int i = 0; i < 5; i++) {
            System.out.print ( "Enter " + informationArray[i] + " : " );
            input = getValidInput ( patternArray[i] , i );
            if ( input.equals ( BACK_BUTTON ) ) {
                personInfo.clear ( );
                return true;
            }
            personInfo.put ( informationArray[i] , input );
        }
        if ( typeInstance.equalsIgnoreCase ( "salesperson" ) ) {
            System.out.print ( "Enter " + informationArray[5] + " : " );
            input = getValidInput ( patternArray[5] , 5 );
            personInfo.put ( informationArray[5] , input );
            System.out.print ( "Enter " + SAYERE_MOSHAKHASAT.label + " : " );
            input = scanner.nextLine ( );
            personInfo.put ( SAYERE_MOSHAKHASAT.label , input );
        }
        return false;
    }

    public static void typeErrorHandler ( String type ) throws Exception {
        if ( !typePattern.matcher ( type ).matches ( ) )
            throw new Exception ( "This type isn't valid." );
        if ( type.equalsIgnoreCase ( "manager" ) && RegisterController.getInstance ( ).isFirstManagerRegistered ( ) )
            throw new Exception ( "You can't add a manager. Contact one of the existing managers." );
    }


    public static void usernameErrorHandler ( String username , State state ) throws Exception {
        if ( !usernamePattern.matcher ( username ).matches ( ) )
            throw new Exception ( "Username Should Contain More Than 3 Characters And Not Contain Spaces." );
        if ( state.equals ( State.REGISTER ) && PersonController.getInstance ( ).isTherePersonByUsername ( username ) )
            throw new Exception ( "This Dude Already Exists." );
        if ( state.equals ( State.LOGIN ) && !PersonController.getInstance ( ).isTherePersonByUsername ( username ) )
            throw new Exception ( "You Don't Exist. Go Make Yourself." );

    }

    private void setHelpArray () {
        helpArray = new String[]{
                "Your password should be more than 8 characters and should contain at least 1 small letter, 1 capital letter, and 1 number." ,
                "Pardon me " + usernameInstance + " but that definitely isn't how your first name is written." ,
                usernameInstance + " is that seriously your last name?" ,
                "Enter a valid email address " + usernameInstance + ". It should be like this : blah@blah.blah" ,
                "Enter a valid phone number " + usernameInstance + ". You shouldn't put +, I already put that for you" ,
                "Enter a valid company name. It can only contain alphanumerics." ,
                "That's not sayere moshakhsat."};
    }

    public void setPersonInfoProperty ( String key , String value ) {
        personInfo.put ( key , value );
    }

    public HashMap < String, String > getPersonInfo () {
        return personInfo;
    }
}