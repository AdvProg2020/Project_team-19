package view;

import controller.ProductController;
import model.OwnedProduct;
import model.Product;

import java.util.regex.Pattern;

public class ProductMenu extends Menu {

    public ProductMenu ( Menu parent ) {
        super ("Product Menu" , parent);
        subMenus.put(1, new SearchMenu(this));
        subMenus.put(2,new ViewProductMenu(this));
        subMenus.put(3, getHelpMenu(this));
        subMenus.put(4,getCompareTwoProductsMenu());
    }


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



    public Menu getCompareTwoProductsMenu(){
        return new Menu("compare two products",this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                System.out.println("please enter first product's id:");
                String id1 = getValidProductId();
                System.out.println("please enter second product's id:");
                String id2 = getValidProductId();
                compareTwoProducts(ProductController.searchProduct(id1),ProductController.searchProduct(id2));
            }
        };
    }

    public static void compareTwoProducts(Product product1,Product product2){
        String firstRowFormat = "|%-36s|%-38s|";
        String threePartRowFormat = "|%-30s|%-30s|%-30s|";
        System.out.println(String.format("%s", "----------------------------------------------------------------------------"));
        System.out.println(String.format(firstRowFormat,product1.getName(),product2.getName()));
        System.out.println(String.format("%s", "----------------------------------------------------------------------------"));
        System.out.println(String.format(threePartRowFormat,"average score",product1.getAverageScore(),product2.getAverageScore()));
        System.out.println(String.format("%s", "----------------------------------------------------------------------------"));
        System.out.println(String.format(threePartRowFormat,"current available price",product1.getLeastPrice(),product2.getLeastPrice()));
        System.out.println(String.format("%s", "----------------------------------------------------------------------------"));
        System.out.println(String.format(threePartRowFormat,"sellers' average price",product1.getAverageScore(),product2.getAveragePrice()));
            for (String s : product1.getProperties().keySet()) {
                if(product2.getProperties().containsKey(s)){
                    System.out.println(String.format("%s", "----------------------------------------------------------------------------"));
                    System.out.println(String.format(threePartRowFormat,s,product1.getProperties().get(s),product2.getProperties().get(s)));
                }
            }
    }

}
