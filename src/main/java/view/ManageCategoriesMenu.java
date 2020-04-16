package view;

public class ManageCategoriesMenu extends Menu {
    public ManageCategoriesMenu(Menu parent) {
        super("Manage Categories Menu", parent);
        subMenus.put(1, getAddMenu());
        subMenus.put(2, getEditMenu());
        subMenus.put(3, getRemoveMenu());
    }

    public Menu getAddMenu() {
        return new Menu("Add Category", this) {
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

    public Menu getEditMenu() {
        return new Menu("Edit Category", this) {
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

    public Menu getRemoveMenu() {
        return new Menu("Remove category", this) {
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
