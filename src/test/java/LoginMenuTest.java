import controller.PersonController;
import controller.RegisterController;
import org.junit.Test;
import view.LoginMenu;

import static view.LoginMenu.PersonInfo.*;

public class LoginMenuTest {

    @Test
    public void registerTest () {
//        StoreMain.initializer ();
//        LoginMenu loginMenu = new LoginMenu ( null );
//        try {
//            LoginMenu.usernameErrorHandler ( "Alireza" , LoginMenu.State.REGISTER );
//        } catch (Exception e) {
//            e.printStackTrace ( );
//        }
//        loginMenu.setPersonInfoProperty ( USERNAME.label , "Alireza" );
//        loginMenu.setPersonInfoProperty ( "type" , "customer" );
//        loginMenu.setPersonInfoProperty ( PASSWORD.label , "Aa123456" );
//        loginMenu.setPersonInfoProperty ( FIRST_NAME.label , "Alireza" );
//        loginMenu.setPersonInfoProperty ( LAST_NAME.label , "Honarvar" );
//        loginMenu.setPersonInfoProperty ( EMAIL.label , "honarvar45@gmail.com" );
//        loginMenu.setPersonInfoProperty ( PHONE.label , "989174877257" );
//        RegisterController.getInstance ( ).register ( loginMenu.getPersonInfo () );
    }

    @Test
    public void loginTest () {
        PersonController.getInstance ().login ( "Alireza" );
    }
}
