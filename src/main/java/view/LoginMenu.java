package view;

import java.util.ArrayList;

public class LoginMenu extends Menu {
    public LoginMenu(Menu parent) {
        super("Login Menu", parent);
        submenus.put(1, getCreateAccountMenu());
        submenus.put(2, getLoginMenu());
        submenus.put(3, getHelpMenu(this));
    }

    //1 = create account   2 = login   3 = help

    private Menu getCreateAccountMenu() {
        return new Menu("Create New Account", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
                System.out.println("enter required info or back to return");
                // next prints happens  in execute when info is valid
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) {
                    this.parentMenu.show();
                    this.parentMenu.execute();
                }
                // if ( controller username is unique ) {
                // controller (getPersonInfoForCreatingAccount(splitInput[2].equals("customer")));
                //} else print invalid username
            }
        };
    }

    private Menu getLoginMenu() {
        return new Menu("Login", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
                System.out.println("enter password or back");  // imagine username exists
                // next prints happens in execute when info is not valid
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) {
                    this.parentMenu.show();
                    this.parentMenu.execute();
                }
                // check username exists then check correctness of password
                //if both valid, controller do other works
            }
        };
    }

    private ArrayList getPersonInfoForCreatingAccount(boolean isSalesperson) {
        ArrayList<String> personInfo = new ArrayList<String>();
        String input;
        int key = isSalesperson ? 6 : 5;
        for (int i = 0; i < key; i++) {   //1.password, 2.fname, 3.lname, 4.email, 5.telephone, salesperson : 6.company name
            System.out.format("enter %d:\n", i + 1);
            personInfo.add(scanner.nextLine());
        }
        return personInfo;
    }
}
