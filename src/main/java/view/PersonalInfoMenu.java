package view;

public class PersonalInfoMenu extends Menu {
    public PersonalInfoMenu(Menu parent){
        super("Personal Information",parent);
    }

    public Menu getViewMenu(){
        return new Menu("View",this) {
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

    public Menu getEditMenu(){
        return new Menu("Edit",this) {
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
