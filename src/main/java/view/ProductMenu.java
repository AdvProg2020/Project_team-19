package view;

public class ProductMenu extends Menu {

    public ProductMenu ( Menu parent ) {
        super ("Product Menu" , parent);
        submenus.put(1, new SearchMenu(this));
        submenus.put(2, getHelpMenu(this));
        //ye tor bayad handel konim age ro ye product ya haraj kilik kone
    }
    // 1 = search   2 = help


    @Override
    public void show() {
        System.out.println(this.getName() +" :");
        //show all products
        //show all categories
    }

    @Override
    public void execute() {
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("back")) {
            this.parentMenu.show();
            this.parentMenu.execute();
        }
        //fek konam execute nadashte bashe dige
    }

}
