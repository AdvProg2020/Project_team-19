package view;

import controller.CategoryController;
import controller.ProductController;
import model.Category;
import model.OwnedProduct;
import model.Product;

import java.util.regex.Pattern;

import static controller.CategoryController.rootCategories;

public class ProductMenu extends Menu {

    public ProductMenu ( Menu parent ) {
        super ("Product Menu" , parent);
        subMenus.put(1, getSearchMenu());
        //TODO set current products -> all products
        subMenus.put(2, getHelpMenu(this));
        subMenus.put(3, getCompareTwoProductsMenu());
        subMenus.put(4, getCategoryMenu());
        subMenus.put(5, new FilteringMenu(this));
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

    public Menu getCategoryMenu() {
        return new Menu("Category Menu", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " : \n All Categories:");
                viewAllCategories();
            }

            @Override
            public void execute() {
                boolean check;
                String input;
                do {
                    System.out.println("Enter A Category Name to See It! :");
                    input = scanner.nextLine();
                    check = (CategoryController.getInstance().getCategoryByName(input, rootCategories) != null);
                } while (!check);
                Category category = CategoryController.getInstance().getCategoryByName(input, rootCategories);
                assert category != null;
                viewCategory(category);
            }
        };
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
        if (category.getIsLeaf())
            return;

        for (Category child : category.getChildren()) {
            for (int i = 0; i < root; i++) {
                if (i % 2 == 0)
                    System.out.print("\u2502");
                else
                    System.out.print("    ");
            }
            if (child.getIsLeaf())
                System.out.printf("\u2514\u2014\u2014\u2014%s\n", child.getName() + " : " + child.getProductList());
            else
                System.out.printf("\u2514\u2014\u2014\u2014%s\n", child.getName());
            viewCategoryRecursively(child, root + 2);
        }
    }

    public static void viewCategory(Category category) {
        System.out.println(category.getName() + " :");
        viewCategoryRecursively(category, 0);
    }

    public static void viewAllCategories() {
        for (Category category : rootCategories) {
            viewCategory(category);
        }
    }

}
