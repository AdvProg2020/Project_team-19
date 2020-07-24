package model;

import controller.ProductController;

import java.util.*;

import static controller.CategoryController.*;

public class Category {

    private boolean isLeaf;
    private String name;
    private transient Category parent;
    private ArrayList<Category> children;
    private HashSet<String> propertyFields;
    private ArrayList<String> productList;

    public Category( String name, Category parent,HashSet<String> properties) {
        this.isLeaf = true;
        if (parent == null) {
            rootCategories.add(this);
        }
        this.name = name;

        this.parent = parent;

        if (parent != null) {
            parent.children.add(this);
            parent.setLeaf(false);
        }

        this.propertyFields = new HashSet<>(properties);
        this.children = new ArrayList<>();
        this.productList = new ArrayList<>();
    }



    public void setPropertyFields(HashSet<String> propertyFields) {
        this.propertyFields = propertyFields;
    }

    public void setProductList(ArrayList<Product> productList) {
        for (Product product : productList) {
            this.productList.add(product.getID());
        }
    }

    public HashSet<String> getPropertyFields() {
        return propertyFields;
    }

    public void addProduct(Product product) {
        this.productList.add(product.getID());
    }

    public void addProperty(String property) {
        this.propertyFields.add(property);
    }

    public void removeProperty(String property) {
        this.propertyFields.remove(property);
    }

    public void removeProduct(Product product) {
        this.productList.remove(product.getID());
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
        ArrayList<Product> products = new ArrayList<>();
        for (String s : productList) {
            products.add(ProductController.getInstance().getProductById(s));
        }
        return products;
    }

    public String getName() {
        return name;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public ArrayList<Category> getChildren() {
        return children;
    }

    public Category getParent () {
        return parent;
    }

    public void setParent ( Category parent ) {
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
