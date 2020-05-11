package view;


import static controller.ProductController.searchProduct;

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
                System.out.println("Enter Product ID :");
                //momkene beshe behtaresh kard
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) {
                    this.parentMenu.show();
                    this.parentMenu.execute();
                }
                if(searchProduct(input) != null) {
                    //view product
                } else
                    System.out.println("Could not find any matches");
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
