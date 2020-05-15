package view;

import controller.PersonController;
import model.Salesperson;
import model.SellLog;

import static controller.PersonController.getLoggedInPerson;

public class SalespersonMenu extends Menu {
    public SalespersonMenu ( Menu parent ) {
        super ( "Salesperson Menu" , parent );
        subMenus.put ( 1 , new PersonalInfoMenu ( this ) );
        subMenus.put ( 2 , getShowCompanyInfo ( ) );
        subMenus.put ( 3 , new SalespersonProductMenu ( this ) );
        subMenus.put ( 4 , new SalesPersonDiscountsMenu ( this ) );
        subMenus.put ( 5 , getViewSalesHistory ( ) );
        subMenus.put ( 6 , getShowCategoriesMenu ( ) );
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
                Salesperson thisGuy = (Salesperson) getLoggedInPerson ( );
                System.out.println (
                        "Company : " + thisGuy.getPersonInfo ( ).get ( "company" ) + "\n" +
                                "Dar Surate Vjud Sayere Moshakhsat :\n" + thisGuy.getPersonInfo ( ).get ( "dar surate vjud sayere moshakhsat" ) );
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
                System.out.println ( "Your balance is : " + ((Salesperson) getLoggedInPerson ( )).getCredit ( ) );
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
                Salesperson salesperson = (Salesperson) getLoggedInPerson ( );
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

    public Menu getShowCategoriesMenu(){
        return new Menu("Show Category",this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }


}
