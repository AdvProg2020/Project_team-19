package view;

import controller.ProductController;
import model.OwnedProduct;
import model.Product;

public class ProductMenu extends Menu {

    public ProductMenu ( Menu parent ) {
        super ("Product Menu" , parent);
        subMenus.put(1, new SearchMenu(this));
        subMenus.put(2, getHelpMenu(this));
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
    }

        public void viwProduct(String productId){
        Product product = Product.getProductById(productId);
        System.out.println(String.format("%s", "------------------------------------------------------------------------------------------"));
        System.out.println(String.format("%s %20s %20s %20s %25s","|","product ID","|","product Name","|"));
        System.out.println(String.format("%s", "------------------------------------------------------------------------------------------"));
        System.out.println(String.format("%s %20s %20s %20s %25s","|",product.getID(),"|",product.getName(),"|"));
        System.out.println(String.format("%s", "------------------------------------------------------------------------------------------"));
        System.out.println(String.format("%s %20s %20s %20s %25s","|","property","|","value","|"));
        for (String s : product.getProperties().keySet()) {
        System.out.println(String.format("%s", "------------------------------------------------------------------------------------------"));
        System.out.println(String.format("%s %20s %20s %20s %25s","|",s , "|",product.getProperties().get(s) ,"|"));
        }
        System.out.println(String.format("%s", "------------------------------------------------------------------------------------------"));
        System.out.println();
        System.out.println("all salesperson");
        for (OwnedProduct ownedProduct : ProductController.getProductsOfProduct(product)) {
            System.out.println(String.format("%s", "------------------------------------------------------------------------------------------"));
            if(ownedProduct.getSalesperson().isInDiscount(product))
            {
                System.out.println(String.format("%s %20s %s %20s %s %20s %5s","|",ownedProduct.getSellerName() , "|",ownedProduct.getPrice(),"|",ownedProduct.getSalesperson().getDiscountPrice(product),"|"));

            }else
                System.out.println(String.format("%s %20s %5s %20s %s %20s %5s","|",ownedProduct.getSellerName() , "|",ownedProduct.getPrice(),"|"," ","|"));
        }
        System.out.println(String.format("%s", "------------------------------------------------------------------------------------------"));
    }

}
