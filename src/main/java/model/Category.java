package model;

import java.util.*;

public class Category {

    private boolean isRoot;
    private String name;
    private Category parent;
    private Set<Category> children;
    private HashSet<String> propertyFields;
    private ArrayList<Product> productList;
    private static Category current;
    private static Category tempCurrent;
    private static LinkedHashSet<Category> rootCategories = new LinkedHashSet<Category>();



    public Category(boolean isRoot, String name, Category parent) {
        this.isRoot = isRoot;

        if (isRoot)
            rootCategories.add ( this );
        this.name = name;

        this.parent = parent;

        if (!isRoot)
            parent.children.add ( this );

        this.children = new LinkedHashSet<>();
        this.productList = new ArrayList<>();
    }

    public void setPropertyFields(HashSet<String> propertyFields) {
        this.propertyFields = propertyFields;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }


    public static boolean childExists (String name) {
        return findCategoryByName(name)!=null;
    }

    public static Category findCategoryByName(String name){
        for (Category child : current.children) {
            if (child.name.equals ( name )) {
                return child;
            }
        }
        return null;
    }

    public HashSet<String> getPropertyFields() {
        return propertyFields;
    }

    public void addProduct(Product product){
        this.productList.add(product);
    }

    public void addProperty(String property){
        this.propertyFields.add(property);
    }

    public void removeProperty(String property){
        this.propertyFields.remove(property);
    }

    public void removeProduct(Product product){
        this.productList.remove(product);
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public void AddSubCategory( Category category){
        this.children.add ( category );
    }

    public void removeSubCategory(Category category) {
        this.children.remove ( category );
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", propertyFields=" + propertyFields +
                ", productList=" + productList +
                '}';
    }
}
