package view;

public class MainMenu extends Menu {
    public MainMenu(Menu parent){
        super("Main Menu", parent);
        submenus.put(1, new ProductMenu (this));
        submenus.put(2, new DiscountMenu ( this));
        submenus.put(3, new LoginMenu ( this));
        submenus.put(4, getHelpMenu(this));
    }

    // mishe show ro override kard baraye namayesh chizaei ka mikhaim
    //show all discounts
    //show all products or whatever
}
