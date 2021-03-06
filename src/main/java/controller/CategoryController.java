package controller;

import model.Category;
import model.Product;

import java.util.*;

import static controller.Database.*;

public class CategoryController {
    private static CategoryController single_instance = null;
    public static ArrayList<Category> rootCategories = new ArrayList<>();

    private CategoryController() {
    }

    public static CategoryController getInstance() {
        if (single_instance == null)
            single_instance = new CategoryController();

        return single_instance;
    }

    public void initializeRootCategories() {
        rootCategories = Database.handleJsonArray(address.get("root_categories"));
        for (Category rootCategory : rootCategories) {
            rootCategory.setParent(null);
            setParents(rootCategory);
        }
    }

    public ArrayList<Product> getInDiscountsProductsOfCategory(Category category) {
        ArrayList<Product> products = new ArrayList<>();
        for (Product product : category.getProductList()) {
            if (product.isInDiscountInTotal())
                products.add(product);
        }
        return products;
    }

    public void setParents(Category category){
        for (Category child : category.getChildren()) {
            child.setParent(category);
            if (!child.getChildren().isEmpty())
                setParents(child);
        }
    }

    public void getNodeCategories(ArrayList<Category> leafCategories, ArrayList<Category> categories) {
        for (Category category : categories) {
            if (category.isLeaf())
                leafCategories.add(category);
            else if (category.getChildren().size() != 0)
                getNodeCategories(leafCategories, category.getChildren());
        }
    }

    public void getParentCategories(ArrayList<String> leafCategories, ArrayList<Category> categories) {
        for (Category category : categories) {
            if (category.getProductList ().size () == 0)
                leafCategories.add ( category.getName ( ) );
            if (category.getChildren ().size () > 0)
                getParentCategories ( leafCategories , category.getChildren ( ) );
        }
    }

    public Category getCategoryByName(String categoryName, ArrayList<Category> categories) {
        for (Category category : categories) {
            if (category.getName().equals(categoryName)) {
                return category;
            }
            if (category.getChildren().size() > 0) {
                Category category1 = getCategoryByName(categoryName, category.getChildren());
                if( category1!= null)
                return category1;
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
        if (category.isLeaf())
            return category.getProductList().isEmpty();
        for (Category child : category.getChildren()) {
            if (child.isLeaf())
                return child.getProductList().isEmpty();
            return isCategoryEmpty(child);
        }
        return true;
    }


    public void addCategory(String name, Category parent, HashSet<String> properties) {
        new Category(name, parent, properties);
        saveToFile(rootCategories, address.get("root_categories"));
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
        System.out.println(parent);
        if (parent != null)
            changeParent(category, parent);

        if (parent == null && root) {
            changeParent(category,null);
            rootCategories.add(category);
        }
        saveToFile(rootCategories, address.get("root_categories"));
    }

    public void editCategory(String name, Category category, Category parent, HashSet<String> properties, boolean root) {

        if (name != null)
            category.setName(name);
        if (parent != null)
            changeParent(category, parent);

        category.setPropertyFields(properties);

        if (parent == null && root) {
            changeParent(category,null);
            rootCategories.add(category);
        }
        saveToFile(rootCategories, address.get("root_categories"));
    }

    public void changeParent(Category category, Category newParent) {
        removeCategory(category.getParent(), category);
        category.setParent(newParent);
        if(newParent!=null) {
            newParent.getChildren().add(category);
            newParent.setLeaf(false);
        }
        saveToFile(rootCategories, address.get("root_categories"));
    }

}
