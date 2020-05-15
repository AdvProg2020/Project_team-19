package controller;

import model.Category;
import model.Product;

import java.util.*;

import static controller.Database.*;

public class CategoryController {
    private static CategoryController single_instance = null;
    public static LinkedList<Category> rootCategories = new LinkedList<>();

    private CategoryController() {
        rootCategories = new LinkedList<>();
    }

    public static CategoryController getInstance() {
        if (single_instance == null)
            single_instance = new CategoryController();

        return single_instance;
    }

    public void initializeRootCategories() {
        rootCategories.add((Category) read(LinkedList.class, address.get("root_categories")));
    }

    public Category getCategoryByName(String categoryName, LinkedList<Category> categories) {
        for (Category category : categories) {
            if (category.getName().equals(categoryName))
                return category;
            if (category.getChildren().size() > 0) {
                return getCategoryByName(categoryName, category.getChildren());
            }
        }
        return null;
    }

    public void removeCategory(Category parent, Category category) {
        if (parent == null)
            rootCategories.remove(category);
        else
            parent.removeSubCategory(category);
        saveToFile(rootCategories, address.get("root_categories"));
    }


    public boolean isCategoryEmpty(Category category) {
        for (Category child : category.getChildren()) {
            if (child.getIsLeaf())
                return child.getProductList() == null;
            return isCategoryEmpty(child);
        }
        return true;
    }

    public void addCategory(Category category, Category parent) {
        if (parent.getIsLeaf()) {
            for (Product product : parent.getProductList()) {
                category.addProduct(product);
            }
            for (Product product : parent.getProductList()) {
                product.setCategory(category.getName());
            }
            parent.getProductList().clear();
            saveToFile(rootCategories, address.get("root_categories"));
        }
    }

    public void addCategoryForMenu(String name, Category parent, HashSet<String> properties) {
        Category category = new Category(name, parent, properties);
        addCategory(category, parent);
    }

    public void editCategory(String name, Category category, Category parent, HashSet<String> addProperties, HashSet<String> removeProperties, boolean root) {

        if (name != null)
            category.setName(name);
        if (addProperties.size() > 0)
            for (String addProperty : addProperties) {
                category.addProperty(addProperty);
            }

        if (removeProperties.size() > 0)
            for (String removeProperty : removeProperties) {
                category.removeProperty(removeProperty);
            }

        if (parent != null)
            changeParent(category, parent);

        if (parent == null && root) {
            rootCategories.add(category);
            saveToFile(rootCategories, address.get("root_categories"));
        }
    }

    public void changeParent(Category category, Category newParent) {
        addCategory(category, newParent);
        removeCategory(category.getParent(), category);
        category.setParent(newParent);
    }

}
