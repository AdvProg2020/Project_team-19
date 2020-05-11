package view;

import model.UserMenu;

public class MainMenu extends Menu {
    public MainMenu(Menu parent){
        super("Main Menu", parent);
        subMenus.put(1, new ProductMenu (this));
        subMenus.put(2, new DiscountMenu ( this));
        subMenus.put(3, new UserMenu (this));
        subMenus.put(4, getHelpMenu(this));
    }

    // mishe show ro override kard baraye namayesh chizaei ka mikhaim
    //show all discounts
    //show all products or whatever

    //ToDo Alireza : Chizaii k inja mitune bshe
    // 1. recommended products (arraylist e)
    // 2. 1-5 haraj moving
    // 3. Category menu
    // 4. Quote
    // 5. Stupid Fact
    // 6. Contact us
    // 7. View Sellers
    // 8. View Manager
}
