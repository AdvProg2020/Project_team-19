package view;

public class MainMenu extends Menu {
    public MainMenu(Menu parent){
        super("Main Menu", parent);
        subMenus.put ( 1 , new ProductMenu (this) );
        subMenus.put ( 2 , new DiscountMenu ( this ) );
        subMenus.put ( 3 , new LoginMenu ( this ) );
    }

}
