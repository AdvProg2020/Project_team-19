package view;

import controller.CategoryController;
import controller.ProductController;
import model.Category;
import model.Product;

import static controller.CategoryController.rootCategories;

public class ProductMenu extends Menu {

    public ProductMenu ( Menu parent ) {
        super ("Product Menu" , parent);
        //TODO set current products -> all products
        subMenus.put(1, getCategoryMenu());
        subMenus.put(2, new ViewProductMenu(this));
        subMenus.put(3, getCompareTwoProductsMenu());
        subMenus.put(4, new FilteringMenu(this));
        subMenus.put(5, getSearchMenu());
        subMenus.put(6, getHelpMenu(this));
    }

    public Menu getCategoryMenu() {
        return new Menu("Category Menu", this) {
            @Override
            public void show() {
                fancyTitle ();
                viewAllCategories();
            }

            @Override
            public void execute() {
                String input;
                input = getValidCategoryName();
                if(input.equals(BACK_BUTTON)){
                    return;
                }
                Category category = CategoryController.getInstance().getCategoryByName(input, rootCategories);
                assert category != null;
                viewCategory(category);
            }
        };
    }

    public Menu getCompareTwoProductsMenu(){
        return new Menu("Compare Two Products",this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                System.out.println("please enter first product's id:");
                String id1 = getValidProductId();
                if(id1.equals(BACK_BUTTON))
                    return;
                System.out.println("please enter second product's id:");
                String id2 = getValidProductId();
                if(id2.equals(BACK_BUTTON))
                    return;
                compareTwoProducts(ProductController.getInstance().searchProduct(id1),ProductController.getInstance().searchProduct(id2));
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

    public static void viewCategoryRecursively(Category category, int root) {
        if (category.isLeaf ())
            return;

        for (Category child : category.getChildren()) {
            for (int i = 0; i < root; i++) {
                if (i % 2 == 0)
                    System.out.print("\u2502");
                else
                    System.out.print("    ");
            }
            System.out.printf ( "\u2514\u2014\u2014\u2014%s" , child.getName ( ) );
            if (child.isLeaf () && child.getProductList ().size () != 0) {
                System.out.print (" : " );
                for (Product product : child.getProductList ( )) {
                    System.out.print ( product + " | " );
                }
            }
            System.out.println ( );
            viewCategoryRecursively(child, root + 2);
        }
    }

    public static void viewCategory(Category category) {
        System.out.println(category.getName());
        viewCategoryRecursively(category, 0);
    }

    public static void viewAllCategories() {
        for (Category category : rootCategories) {
            viewCategory(category);
        }
    }

}
