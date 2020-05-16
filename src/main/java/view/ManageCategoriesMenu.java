package view;


import controller.CategoryController;
import model.Category;

import java.util.HashSet;

import static controller.CategoryController.*;

public class ManageCategoriesMenu extends Menu {
    public ManageCategoriesMenu(Menu parent) {
        super("Manage Categories", parent);
        subMenus.put(1, getCategoryMenu(this));
        subMenus.put(2, getAddMenu());
        subMenus.put(3, getEditMenu());
        subMenus.put(4, getRemoveMenu());
    }

    public Menu getAddMenu() {
        return new Menu("Add Category", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
            }

            @Override
            public void execute() {
                Category parent;
                HashSet<String> addProperties = new HashSet<>();
                String input;
                System.out.println("Enter category name to add :");
                while (CategoryController.getInstance().getCategoryByName(input = scanner.nextLine(), rootCategories) != null
                && !input.equals(BACK_BUTTON)) {
                    System.out.println("Already exists. Try another :");
                }
                if (input.equals(BACK_BUTTON))
                    return;
                String input2;
                System.out.println("Enter Parent Category Name or Enter \"root\" If It Does Not Have Any:");
                do {
                input2 = getValidCategoryName();
                if (input2.equals(BACK_BUTTON))
                    return;
                if(!CategoryController.getInstance().getCategoryByName(input2,rootCategories).getProductList().isEmpty()){
                    System.out.println("This category has products. choose a parent category.");}
                }while (!CategoryController.getInstance().getCategoryByName(input2,rootCategories).getProductList().isEmpty());
                String field;
                while (true) {
                    System.out.println ( "Enter field or \"..\" to continue:" );
                    field = scanner.nextLine ( );
                    if (field.equals(BACK_BUTTON))
                        break;
                    addProperties.add ( field );
                }

                if(input2.equalsIgnoreCase("root"))
                    parent = null;
                else
                    parent = CategoryController.getInstance().getCategoryByName(input2, rootCategories);

                CategoryController.getInstance().addCategory(input,parent,addProperties);
                System.out.println("Successful.");
            }
        };
    }

    public Menu getEditMenu() {
        return new Menu("Edit Category", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
            }

            @Override
            public void execute() {
                boolean isRoot = false;
                String name = null;
                Category parent = null;
                HashSet<String> addProperties = new HashSet<>();
                HashSet<String> removeProperties = new HashSet<>();
                System.out.println("Please the name of category you want to edit: ");
                String input = getValidCategoryName();
                if (input.equals(BACK_BUTTON))
                    return;
                Category category = CategoryController.getInstance().getCategoryByName(input, rootCategories);
                System.out.println("1. Edit name:\n" +
                        "2. Edit Parent:");
                if(category.isLeaf ()){
                    System.out.println("3. Add Property:");
                    System.out.println("4. Remove Property:");
                    System.out.println("5. Confirm Edit.");
                }else
                    System.out.println("3. Confirm Edit.");
                do {
                    System.out.println("Which field do you want to edit?");
                    input = getValidMenuNumber(category.isLeaf ()?3:5);
                    switch (Integer.parseInt(input)) {
                        case 1:
                            System.out.println("Enter new category name:");
                            name = scanner.nextLine();
                            break;
                        case 2:
                            System.out.println("Enter new parent category name:");
                            String parentName = getValidCategoryName();
                            if(parentName.equalsIgnoreCase("root")){
                                isRoot = true;
                                continue;
                            }
                            parent = CategoryController.getInstance().getCategoryByName(parentName,rootCategories);
                            if(!parent.getProductList().isEmpty()) {
                                System.out.println("This category has products. choose a parent category.");
                                parent = null;
                            }
                            break;
                        case 3:
                            System.out.println("Enter field:");
                            String field = scanner.nextLine();
                            addProperties.add(field);
                            break;
                        case 4:
                            System.out.println("Enter field:");
                            field = scanner.nextLine();
                            if(!category.getPropertyFields().contains(field)){
                                System.out.println("This category does not have such property.");
                                continue;
                            }
                            removeProperties.add(field);
                            break;
                    }

                } while (!(input.equals(category.isLeaf () ?"3":"5")));
                CategoryController.getInstance().editCategory(name,category,parent,addProperties,removeProperties,isRoot);
                System.out.println("Successful.");
            }
        };
    }

    public Menu getRemoveMenu() {
        return new Menu("Remove Category", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
            }

            @Override
            public void execute() {
                String input;
                System.out.println("Enter category name you want to remove :");
                input = getValidCategoryName();
                if (input.equals(BACK_BUTTON))
                    return;
                Category category = CategoryController.getInstance().getCategoryByName(input, rootCategories);
                if(!CategoryController.getInstance().isCategoryEmpty(category)) {
                    System.out.println("There are products in this category's subcategories.Please empty them at first.");
                    return;
                }
                CategoryController.getInstance().removeCategory(category.getParent(), category);
                System.out.println("Successful.");
            }
        };
    }

}
