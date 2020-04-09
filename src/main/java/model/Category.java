package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Category {

    private boolean isRoot;
    private String name;
    private Category parent;
    private Set<Category> children;
    private HashSet<String> properties;
    private ArrayList< Product > productList;

    public Category(boolean isRoot, String name, Category parent) {
        this.isRoot = isRoot;
        this.name = name;
        this.parent = parent;
        this.children = new HashSet<Category>();
        this.productList = new ArrayList<Product>();
    }

    public static boolean checkValidCategory(String address){
        //search each category in its level recursively
        return false;
    }

    public HashSet<String> getProperties() {
        return properties;
    }

    public void addProduct(Product product){
        this.productList.add(product);
    }

    public void addProperty(String property){
        this.properties.add(property);
    }

    public void removeProperty(String property){
        this.properties.remove(property);
    }

    public void removeProduct(Product product){
        this.productList.remove(product);
    }

    public void AddSubCategory(Category category){ }


}
