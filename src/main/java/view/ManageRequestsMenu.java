package view;

public class ManageRequestsMenu extends Menu {
    public ManageRequestsMenu(Menu parent){
        super("Manage Requests",parent);
    }

    public Menu getAcceptMenu(){
        return new Menu("Accept",this) {
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

    public Menu getDeclineMenu(){
        return new Menu("Decline",this) {
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
