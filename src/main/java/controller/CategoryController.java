package controller;

import model.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CategoryController {
    private static CategoryController single_instance = null;
    private LinkedList<Category> rootCategories;

    private CategoryController(){
        rootCategories = new LinkedList<>();
    }

    public static CategoryController getInstance() {
        if (single_instance == null)
            single_instance = new CategoryController();

        return single_instance;
    }

    public void addCategory ( String categoryAddress, Category category ){

    }

    public Category findCategoryByAddress(String address){
        List<String> separatedCategories = Arrays.asList ( address.split ( "/" ) );
        Category tempCurrent;
        boolean levelCheck = false;
        for (Category rootCategory : rootCategories) {
            if (rootCategory.getName().equals ( separatedCategories.get ( 0 ) )) {
                tempCurrent = rootCategory;
                while (!separatedCategories.isEmpty ()) {
                    separatedCategories.remove ( 0 ); //error
                    levelCheck = rootCategory.childExists ( separatedCategories.get ( 0 ) );
                    if (!levelCheck)
                        return null;
                }
                return tempCurrent;
            }
        }
        return null;
    }


    public void removeCategory ( Category parent, Category category ) {
        parent.removeSubCategory ( category );
    }

    public void editCategoryName (Category category, String name) {
        category.setName ( name );
    }

    public void editCategoryProperties ( Category category, ArrayList<String> addProperties, ArrayList<String> removeProperties ) {
        for (String property : addProperties) {
            category.addProperty ( property );
        }
        for (String property : removeProperties) {
            category.removeProperty ( property );
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
