package view;

import controller.PersonController;
import controller.RegisterController;

import java.util.HashMap;

public class LoginMenu extends Menu {
    private HashMap<String,String> personInfo ;

    public enum PersonType {
        CUSTOMER("Customer"),
        SALESPERSON("Salesperson"),
        MANAGER("Manager");

        public final String label;

        PersonType ( String label ) {
            this.label = label;
        }
    }

    private enum Option {
        USERNAME, PASSWORD, NAME, EMAIL, PHONE_NUM
    }

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

                //ToDo Test This
                System.out.println ( "Enter username" );
                String username = enterOption (Option.USERNAME);
                personInfo.put("username", username);

                //ToDo Test This
                System.out.println ( "Choose type" );
                PersonType typeChosen = enterType ();
                personInfo.put("type", typeChosen.label);

                //ToDo Test This
                System.out.println ( "Enter password" );
                String password = enterOption (Option.PASSWORD);
                personInfo.put("password", password);

                //ToDo Test This
                System.out.println("Enter first name");
                String firstName = enterOption (Option.NAME);
                personInfo.put("firstName", firstName);
                System.out.println("Enter last name");
                String lastName = enterOption (Option.NAME);
                personInfo.put("lastName", lastName);

                //ToDo Test This
                System.out.println("Enter email");
                String email = enterOption ( Option.EMAIL );
                personInfo.put("email", email);

                //ToDo Test This
                System.out.println("Enter phone number");
                String phoneNum = enterOption ( Option.PHONE_NUM );
                personInfo.put("phoneNumber", phoneNum);

                //ToDo write on Database
                if (typeChosen == PersonType.MANAGER) {
                    RegisterController.registerManager(personInfo);
                } else if (typeChosen == PersonType.SALESPERSON) {

                } else if (typeChosen == PersonType.CUSTOMER) {
                    RegisterController.registerCustomer(personInfo);
                }
            }
        };
    }

    private Menu getLoginMenu(){
        return new Menu("Login",this) {
            @Override
            public void show() {
                super.show ();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    private String enterOption (Option option) {
        String string = null;
        boolean check = false;
        //ToDo bray Username : tabe call kne, regex esh chk kne k
        // 1. space dre y n
        // 2. az qbl vjud dshte y n
        // 3. Username must contain more than 4 characters
        // 4. include digits and letters.

        do {
            try {
                string = scanner.nextLine ();
                switch (option) {
                    case USERNAME:
                        checkUsername(string);
                        break;
                    case PASSWORD:
                        checkPassword(string);
                        break;
                    case NAME:
                        checkName(string);
                        break;
                    case EMAIL:
                        checkEmail(string);
                        break;
                    case PHONE_NUM:
                        checkPhoneNum(string);
                        break;
                }
                check = true;
            } catch (Exception e) {
                System.out.println ( e.getMessage () );
            }
        } while (!check);

        return string;
    }

    private PersonType enterType () {
        PersonType typeChosen = null;
        do {
            System.out.println ( "Choose type" );
            System.out.println (
                            "1. Customer\n" +
                            "2. Salesperson\n" +
                            "3. Manager" );
            try {
                typeChosen = PersonController.chooseType ( );
            } catch (Exception e) {
                System.out.println ( e.getMessage () );
            }
        } while (typeChosen == null);

        return typeChosen;
    }

    private void checkUsername(String string) {

    }

    private void checkPassword(String string) {

    }

    private void checkName(String string) {

    }

    private void checkEmail(String string) {

    }

    private void checkPhoneNum(String string) {

    }

//    private void typeShower () {
//
//        ToDo : this is an arrow thing. Choose option with enter button and arrow keys.
//        System.out.println (
//                        "\u21921. Customer\n" +
//                        " 2. Salesperson\n" +
//                        " 3. Manager");
//
//
//    }
}



//class KeyListenerTutorial implements KeyListener {
//
//    @Override
//    public void keyTyped ( KeyEvent e ) {
//
//    }
//
//    @Override
//    public void keyPressed ( KeyEvent e ) {
////        if (e.getKeyCode () == KeyEvent.VK_UP)
//    }
//
//    @Override
//    public void keyReleased ( KeyEvent e ) {
//
//    }
//}