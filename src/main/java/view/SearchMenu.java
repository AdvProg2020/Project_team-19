package view;

public class SearchMenu extends Menu {
    public SearchMenu(Menu parent) {
        super("Search Menu", parent);
        subMenus.put(1, getSearchMenu());
        subMenus.put(2, getSearchMenuByField());
    }

    private Menu getSearchMenu() {
        return new Menu("Search", this) {
            @Override
            public void show() {
                System.out.println("Search sth or enter back to return");
                //momkene beshe behtaresh kard
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) {
                    this.parentMenu.show();
                    this.parentMenu.execute();
                }
                //show all
            }
        };
    }

    private Menu getSearchMenuByField() {
        return new Menu("Search By Field", this) {
            @Override
            public void show() {
                System.out.println("search sth with filter or enter back to return");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) {
                    this.parentMenu.show();
                    this.parentMenu.execute();
                }
                //show products by sort field
            }
        };
    }
}
