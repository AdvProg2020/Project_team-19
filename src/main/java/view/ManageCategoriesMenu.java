package view;


import controller.CategoryController;
import model.Category;

import java.util.ArrayList;
import java.util.HashSet;

import static controller.CategoryController.*;

public class ManageCategoriesMenu extends Menu { //TODO ghatan check she
    public ManageCategoriesMenu(Menu parent) {
        super("Manage Categories Menu", parent);
        subMenus.put(1, getAddMenu());
        subMenus.put(2, getEditMenu());
        subMenus.put(3, getRemoveMenu());
    }

    public Menu getAddMenu() {
        return new Menu("Add Category", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
            }

            @Override
            public void execute() {
                HashSet<String> addProperties = new HashSet<>();
                String input;
                System.out.println("Enter category name you want to add");
                while (CategoryController.getInstance().getCategoryByName(input = scanner.nextLine(), rootCategories) != null && !input.equals(BACK_BUTTON))
                    System.out.println("This category already exists.");
                if (input.equals(BACK_BUTTON))
                    return;
                String input2;
                System.out.println("Enter parent category name");
                input2 = getValidCategoryName();
                if (input2.equals(BACK_BUTTON))
                    return;
                String field;
                do {
                    System.out.println("Enter field or \"..\" to continue:");
                    field = scanner.nextLine();
                    addProperties.add(field);
                }while (field.equals(BACK_BUTTON));
                Category parent = CategoryController.getInstance().getCategoryByName(input2, rootCategories);

                CategoryController.getInstance().addCategoryForMenu(input,parent,addProperties);
            }
        };
    }

    public Menu getEditMenu() {
        return new Menu("Edit Category", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
                System.out.println("1.Edit name:\n" +
                        "2.Edit Parent:\n");
            }

            @Override
            public void execute() {
                boolean isRoot = false;
                String name = null;
                Category parent = null;
                HashSet<String> addProperties = new HashSet<>();
                HashSet<String> removeProperties = new HashSet<>();
                String input = getValidCategoryName();
                if (input.equals(BACK_BUTTON))
                    return;
                Category category = CategoryController.getInstance().getCategoryByName(input, rootCategories);
                if(category.getIsLeaf()){
                    System.out.println("3. Add Property:");
                    System.out.println("4. Remove Property:");
                }
                do {
                    System.out.println("Which field do you want to edit?");
                    input = getValidMenuNumber(category.getIsLeaf()?3:2);
                    switch (Integer.parseInt(input)) {
                        case 1:
                            System.out.println("Enter product name:");
                            name = scanner.nextLine();
                            break;
                        case 2:
                            String parentName = getValidCategoryName();
                            if(parentName.equalsIgnoreCase("root")){
                                isRoot = true;
                                continue;
                            }
                            parent = CategoryController.getInstance().getCategoryByName(parentName,rootCategories);
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

                } while (!input.equals(BACK_BUTTON));
                CategoryController.getInstance().editCategory(name,category,parent,addProperties,removeProperties,isRoot);
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
                System.out.println("Enter category name you want to remove");
                input = getValidCategoryName();
                if (input.equals(BACK_BUTTON))
                    return;
                Category category = CategoryController.getInstance().getCategoryByName(input, rootCategories);
                if(!CategoryController.getInstance().isCategoryEmpty(category)) {
                    System.out.println("There are products in tis category's subcategories.Please empty them at first.");
                    return;
                }
                CategoryController.getInstance().removeCategory(category.getParent(), category);
            }
        };
    }

}
