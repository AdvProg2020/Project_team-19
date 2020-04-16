package model;

import java.util.*;

public class Category {

    private boolean isRoot;
    private String name;
    private Category parent;
    private Set<Category> children;
    private HashSet<String> properties;
    private ArrayList< Product > productList;
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

        this.children = new LinkedHashSet<Category>();
        this.productList = new ArrayList<Product>();
    }

    public static boolean checkValidCategory(String address){
        List<String> seperatedCategories = Arrays.asList ( address.split ( "/" ) );
        boolean levelCheck = false;
        for (Category rootCategory : rootCategories) {
            if (rootCategory.name.equals ( seperatedCategories.get ( 0 ) )) {
                tempCurrent = rootCategory;
                while (!seperatedCategories.isEmpty ()) {
                    seperatedCategories.remove ( 0 ); //error
                    levelCheck = childExists ( seperatedCategories.get ( 0 ) );
                    if (!levelCheck)
                        return false;
                }
                break;
            }
        }
        current = tempCurrent;
        return true;
    }

    private static boolean childExists (String name) {
        for (Category child : current.children) {
            if (child.name.equals ( name )) {
                tempCurrent = child;
                return true;
            }
        }
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

    public void setName ( String name ) {
        this.name = name;
    }

    public void AddSubCategory( Category category){
        this.children.add ( category );
    }

    public void removeSubCategory(Category category) {
        this.children.remove ( category );
    }

}
