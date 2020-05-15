package model;

import java.util.*;

import static controller.CategoryController.*;

public class Category {

    private boolean isLeaf;
    private String name;
    private Category parent;
    private LinkedList<Category> children;
    private HashSet<String> propertyFields;
    private ArrayList<Product> productList;

    public Category( String name, Category parent,HashSet<String> properties) {
        this.isLeaf = true;
        if (parent == null) {
            rootCategories.add(this);
        }
        this.name = name;

        this.parent = parent;

        if (parent != null)
            parent.children.add(this);

        this.propertyFields = new HashSet<>(properties);
        this.children = new LinkedList<>();
        this.productList = new ArrayList<>();
    }

    public void setPropertyFields(HashSet<String> propertyFields) {
        this.propertyFields = propertyFields;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public HashSet<String> getPropertyFields() {
        return propertyFields;
    }

    public void addProduct(Product product) {
        this.productList.add(product);
    }

    public void addProperty(String property) {
        this.propertyFields.add(property);
    }

    public void removeProperty(String property) {
        this.propertyFields.remove(property);
    }

    public void removeProduct(Product product) {
        this.productList.remove(product);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void AddSubCategory(Category category) {
        this.children.add(category);
    }

    public void removeSubCategory(Category category) {
        this.children.remove(category);
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public String getName() {
        return name;
    }

    public boolean getIsLeaf() {
        return isLeaf;
    }

    public LinkedList<Category> getChildren() {
        return children;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
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
