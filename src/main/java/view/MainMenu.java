package view;

public class MainMenu extends Menu {
    public MainMenu(Menu parent){
        super("Main Menu", parent);
        subMenus.put(1, new ProductMenu (this));
        subMenus.put(2, new DiscountMenu ( this));
        subMenus.put(3, new LoginMenu ( this));
        subMenus.put(4, getHelpMenu(this));
    }

    // mishe show ro override kard baraye namayesh chizaei ka mikhaim
    //show all discounts
    //show all products or whatever
}
