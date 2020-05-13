package view;

public class SalespersonMenu extends Menu {
    public SalespersonMenu ( Menu parent ) {
        super ( "Salesperson Menu" , parent );
        subMenus.put(1,new PersonalInfoMenu(this));
        subMenus.put(2,getShowCompanyInfo());
        subMenus.put(3,new SalespersonProductMenu(this));
    }

    @Override
    public void execute () { //ToDo add this to customer and salesperson and manager
        Menu nextMenu;
        int chosenMenu = Integer.parseInt(getValidMenuNumber ( subMenus.size () + 1 ));
        if (chosenMenu == subMenus.size() + 1) {
            nextMenu = this.parentMenu.parentMenu;
        } else
            nextMenu = subMenus.get(chosenMenu);
        nextMenu.run ();
    }

    public Menu getShowCompanyInfo(){
        return new Menu("Show Campany Information",this) {
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

    public Menu getShowBalanceMenu(){
        return new Menu("Show Balance",this) {
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
