package view;

import controller.PersonController;
import model.Product;
import model.Salesperson;
import model.SellLog;

public class SalespersonMenu extends Menu {

    Salesperson salesperson = (Salesperson) PersonController.getInstance ().getLoggedInPerson ();

    public SalespersonMenu ( Menu parent ) {
        super ( "Salesperson Menu" , parent );
        subMenus.put ( 1 , new PersonalInfoMenu ( this ) );
        subMenus.put ( 2 , getShowCompanyInfo ( ) );
        subMenus.put ( 3 , new SalespersonProductMenu ( this ) );
        subMenus.put ( 4 , new SalespersonDiscountsMenu ( this ) );
        subMenus.put ( 5 , getViewSalesHistory ( ) );
        subMenus.put ( 6 , getCategoryMenu(this) );
        subMenus.put ( 7 , getShowBalanceMenu () );
        subMenus.put ( 8 , getLogoutMenu ( ) );

    }

    @Override
    public void execute () { //ToDo add this to customer and salesperson and manager
        Menu nextMenu;
        int chosenMenu = Integer.parseInt ( getValidMenuNumber ( subMenus.size ( ) + 1 ) );
        if ( chosenMenu == subMenus.size ( ) + 1 ) {
            nextMenu = this.parentMenu.parentMenu;
        } else
            nextMenu = subMenus.get ( chosenMenu );
        nextMenu.run ( );
    }

    public Menu getShowCompanyInfo () {
        return new Menu ( "Show Company Information" , this ) {
            @Override
            public void show () {
                System.out.println (
                        "Company : " + salesperson.getPersonInfo ( ).get ( "company" ) + "\n" +
                                "Dar Surate Vjud Sayere Moshakhsat :" + salesperson.getPersonInfo ( ).get ( "dar surate vjud sayere moshakhsat" ) );
                super.show ( );
            }

            @Override
            public void execute () {
                super.execute ( );
            }
        };
    }

    public Menu getShowBalanceMenu () {
        return new Menu ( "Show Balance" , this ) {
            @Override
            public void show () {
                System.out.println ( "Your balance is : " + salesperson.getCredit ( ) );
                super.show ( );
            }

            @Override
            public void execute () {
                super.execute ( );
            }
        };
    }

    public Menu getViewSalesHistory(){
        return new Menu("View Sales History",this) {
            @Override
            public void show() {
                for (SellLog sellLog : salesperson.getSellLogs ( )) {
                    System.out.println ( sellLog.getEverythingString () );
                    System.out.println ( LINE );
                }
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }


}
