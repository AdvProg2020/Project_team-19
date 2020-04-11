package view;

public class LoginMenu extends Menu {
    public LoginMenu ( Menu parent ) {
        super ( "Login Menu" , parent );
        this.subMenus.put(1,getRegisterMenu());
        this.subMenus.put(2,getLogInMenu());
    }

    private Menu getRegisterMenu(){
        return new Menu("Register",this) {
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

    private Menu getLogInMenu(){
        return new Menu("Log in",this) {
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
