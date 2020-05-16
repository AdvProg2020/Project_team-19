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
                fancyTitle ();
            }

            @Override
            public void execute() {
                Category parent;
                HashSet<String> addProperties = new HashSet<>();
                String input;
                System.out.print("Enter Category Name To Add : ");
                while (CategoryController.getInstance().getCategoryByName(input = scanner.nextLine(), rootCategories) != null
                && !input.equals(BACK_BUTTON)) {
                    System.out.print("Already Exists. Try Another : ");
                }
                if (input.equals(BACK_BUTTON))
                    return;
                String input2;
                System.out.print("Enter Parent Category Name or Enter \"root\" If It Does Not Have Any : ");
                do {
                input2 = getValidCategoryName();
                if (input2.equals(BACK_BUTTON))
                    return;
                if(!CategoryController.getInstance().getCategoryByName(input2,rootCategories).getProductList().isEmpty()){
                    System.out.println("This Category Has Products. Choose A Parent Category.");}
                }while (!CategoryController.getInstance().getCategoryByName(input2,rootCategories).getProductList().isEmpty());
                String field;
                while (true) {
                    System.out.println ( "Enter Field Or \"..\" To Continue:" );
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
                fancyTitle ();
            }

            @Override
            public void execute() {
                boolean isRoot = false;
                String name = null;
                Category parent = null;
                HashSet<String> addProperties = new HashSet<>();
                HashSet<String> removeProperties = new HashSet<>();
                System.out.println("Please Enter The Name Of Category You Want To Edit: ");
                String input = getValidCategoryName();
                if (input.equals(BACK_BUTTON))
                    return;
                Category category = CategoryController.getInstance().getCategoryByName(input, rootCategories);
                System.out.println("1. Edit Name:\n" +
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
                            System.out.print("Enter New Category Name : ");
                            name = scanner.nextLine();
                            break;
                        case 2:
                            System.out.print("Enter New Parent Category Name : ");
                            String parentName = getValidCategoryName();
                            if(parentName.equalsIgnoreCase("root")){
                                isRoot = true;
                                continue;
                            }
                            parent = CategoryController.getInstance().getCategoryByName(parentName,rootCategories);
                            if(!parent.getProductList().isEmpty()) {
                                System.out.println("This Category Has Products. Choose A Parent Category.");
                                parent = null;
                            }
                            break;
                        case 3:
                            System.out.print("Enter Field : ");
                            String field = scanner.nextLine();
                            addProperties.add(field);
                            break;
                        case 4:
                            System.out.print("Enter Field : ");
                            field = scanner.nextLine();
                            if(!category.getPropertyFields().contains(field)){
                                System.out.println("This Category Does Not Have Such Property.");
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
                fancyTitle ();
            }

            @Override
            public void execute() {
                String input;
                System.out.println("Enter Category Name You Want To Remove :");
                input = getValidCategoryName();
                if (input.equals(BACK_BUTTON))
                    return;
                Category category = CategoryController.getInstance().getCategoryByName(input, rootCategories);
                if(!CategoryController.getInstance().isCategoryEmpty(category)) {
                    System.out.println("There Are Products In This Category's Subcategories.Please Empty Them At First.");
                    return;
                }
                CategoryController.getInstance().removeCategory(category.getParent(), category);
                System.out.println("Successful.");
            }
        };
    }

}
