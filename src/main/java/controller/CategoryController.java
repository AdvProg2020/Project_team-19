package controller;

import model.Category;

import java.util.ArrayList;

public class CategoryController {

    public void addCategory ( Category parent, Category category ) {
        parent.AddSubCategory ( category );
    }

    public void removeCategory ( Category parent, Category category ) {
        parent.removeSubCategory ( category );
    }

    public void editCategoryName (Category category, String name) {
        category.setName ( name );
    }

    public void editCategoryProperties ( Category category, ArrayList<String> addProperties, ArrayList<String> removeProperties ) {
        //add and remove must not contain same properties. This should be handled. Which one should be first?
        for (String property : addProperties) {
            category.addProperty ( property );
        }
        for (String property : removeProperties) {
            category.removeProperty ( property );
        }
    }
}
