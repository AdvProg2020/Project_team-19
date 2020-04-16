package view;

public class SalespersonMenu extends Menu {
    public SalespersonMenu ( Menu parent ) {
        super ( "Salesperson Menu" , parent );
        submenus.put(1,new PersonalInfoMenu(this));
        submenus.put(2,getShowCompanyInfo());
        submenus.put(3,new SalespersonProductMenu(this));
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
