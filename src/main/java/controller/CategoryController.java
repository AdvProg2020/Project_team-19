package controller;

import model.Category;

import java.util.ArrayList;

import static model.Category.rootCategories;

public class CategoryController {

    public static void addCategory(Category parent, Category category) {
        parent.AddSubCategory(category);
    }

    public static void removeCategory(Category parent, Category category) {
        parent.removeSubCategory(category);
    }

    public static void editCategoryName(Category category, String name) {
        category.setName(name);
    }

    public static void editCategoryProperties(Category category, ArrayList<String> addProperties, ArrayList<String> removeProperties) {
        //add and remove must not contain same properties. This should be handled. Which one should be first?
        for (String property : addProperties) {
            category.addProperty(property);
        }
        for (String property : removeProperties) {
            category.removeProperty(property);
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
