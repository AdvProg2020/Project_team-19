package view;

public class DiscountMenu extends Menu {
    public DiscountMenu(Menu parent) {
        super("Discount Menu", parent);
        subMenus.put(2, getHelpMenu(this));
        //ye tor bayad handel konim age ro ye product ya haraj kilik kone
    }

    @Override
    public void show() {
        System.out.println(this.getName() + " :");
        System.out.println("these are haraj ha:");
        //show  haraj ha:|
        System.out.println("enter haraj you want to see:");
    }
}
