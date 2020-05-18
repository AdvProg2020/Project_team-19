package view;

import controller.ProductController;
import model.Category;
import model.Product;
import model.Salesperson;
import model.wagu.Block;
import model.wagu.Board;
import model.wagu.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static controller.CategoryController.rootCategories;

public class ProductMenu extends Menu {

    public ProductMenu ( Menu parent ) {
        super ("Product Menu" , parent);
        //TODO set current products -> all products
        subMenus.put(1, getCategoryMenu(this));
        subMenus.put(2, new ViewProductMenu(this));
        subMenus.put(3, getCompareTwoProductsMenu());
        subMenus.put(4, new FilteringMenu(this));
        subMenus.put(5, getSearchMenu());

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
                compareTwoProducts(ProductController.getInstance().getProductById(id1),ProductController.getInstance().getProductById(id2));
            }
        };
    }

    public static void compareTwoProducts(Product product1,Product product2){
        ArrayList<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        HashSet<String> sameProperties = new HashSet<>();
        for (String s : product1.getProperties().keySet()) {
            if (product2.getProperties().containsKey(s))
                sameProperties.add(s);
        }
        List<String> headersList = new ArrayList<>();
        headersList.add("ID");headersList.add("Name");headersList.add("Brand");headersList.add("Category");headersList.add("Least Price");
        headersList.addAll(sameProperties);
        List<List<String>> rowsList = new ArrayList<>();
        for (Product product : products) {
            List<String> row = new ArrayList<>(sameProperties.size() + 5);
            row.add(product.getID());
            row.add(product.getName());
            row.add(product.getBrand());
            row.add(product.getCategory().getName());
            row.add(product.getLeastPrice() + "$");
            for (String sameProperty : sameProperties) {
                row.add(product.getProperties().get(sameProperty));
            }
            rowsList.add(row);
        }
        Board board = new Board(75);
        Table table = new Table(board, 75, headersList, rowsList);
        List<Integer> colAlignList = new ArrayList<>();
        for (int i = 0; i < sameProperties.size() + 5; i++)
            colAlignList.add(Block.DATA_CENTER);
        table.setColAlignsList(colAlignList);
        Block tableBlock = table.tableToBlocks();
        board.setInitialBlock(tableBlock);
        board.build();
        String tableString = board.getPreview();
        System.out.println(tableString);
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
        System.out.print(category.getName());
        if (!category.getProductList().isEmpty()) {
            System.out.print(" : ");
            for (Product product : category.getProductList()) {
                System.out.print(product + " | ");
            }
        }
        System.out.println();
        viewCategoryRecursively(category, 0);
    }

    public static void viewAllCategories() {
        for (Category category : rootCategories) {
            viewCategory(category);
        }
    }

}
