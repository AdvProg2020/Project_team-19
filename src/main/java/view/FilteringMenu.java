package view;

import controller.ProductController;
import model.OwnedProduct;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;

import static controller.ProductController.currentProducts;

public class FilteringMenu extends Menu {
    protected ArrayList<Product> products = new ArrayList<>();
    protected HashMap<FilterName, ArrayList<String>> filters = new HashMap<>();

    public enum FilterName {
        NAME, BRAND, CATEGORY, AVAILABLE, FIELD ,PRICE
    }

    public FilteringMenu(Menu parent) {
        super("Filter Menu", parent);
        subMenus.put(1, getFilterByName());
        subMenus.put(2, getFilterByBrand());
        subMenus.put(3, getFilterByCategory());
        subMenus.put(4, getFilterByExisting());
        subMenus.put(5, getFilterByField());
        subMenus.put(6, getFilterByPrice());
        subMenus.put(7, getShowCurrentFilters());
        subMenus.put(8, getDisableFilter());
    }

    private Menu getFilterByName() {
        return new Menu("Filter By Name", this) {
            @Override
            public void show() {
                fancyTitle ();
            }

            @Override
            public void execute() {
                System.out.println("Enter Product Name :");
                String input = scanner.nextLine();
                if ((products = ProductController.getInstance().filterByName(input, products)) != null)
                    System.out.println(products);
                else
                    System.out.println("Nothing Found :(");

                ArrayList<String> inputs = new ArrayList<>();
                inputs.add(input);
                filters.put(FilterName.NAME, inputs);
            }
        };
    }

    private Menu getFilterByBrand() {
        return new Menu("Filter By Brand", this) {
            @Override
            public void show() {
                fancyTitle ();
            }

            @Override
            public void execute() {
                System.out.println("Enter Product Brand :");
                String input = scanner.nextLine();
                if ((products = ProductController.getInstance().filterByBrand(input, products)) != null)
                    System.out.println(products);
                else
                    System.out.println("Nothing Found :(");

                ArrayList<String> inputs = new ArrayList<>();
                inputs.add(input);
                filters.put(FilterName.BRAND, inputs);
            }
        };
    }

    private Menu getFilterByCategory() {
        return new Menu("Filter By Category", this) {
            @Override
            public void show() {
                fancyTitle ();
            }

            @Override
            public void execute() {
                System.out.println("Enter Category Name :");
                String input = scanner.nextLine();
                if ((products = ProductController.getInstance().filterByCategory(input, products)) != null)
                    System.out.println(products);
                else
                    System.out.println("Nothing Found :(");

                ArrayList<String> inputs = new ArrayList<>();
                inputs.add(input);
                filters.put(FilterName.CATEGORY, inputs);
            }
        };
    }

    private Menu getFilterByExisting() {
        return new Menu("Filter By Existing", this) {
            @Override
            public void show() {
                fancyTitle ();
            }

            @Override
            public void execute() {
                if ((products = ProductController.getInstance().filterByExisting(products)) != null)
                    System.out.println(products);
                else
                    System.out.println("Nothing Found :(");

                filters.put(FilterName.AVAILABLE, new ArrayList<>());
            }
        };
    }

    private Menu getFilterByPrice() {
        return new Menu("Filter By Price", this) {
            @Override
            public void show() {
                fancyTitle ();
            }

            @Override
            public void execute() {
                String input1, input2;
                ArrayList<OwnedProduct> products;
                System.out.println("Enter Low Price :");
                while (!(input1 = scanner.nextLine()).matches("\\d+")) {
                    System.out.println("Enter Valid Number -_-");
                }
                System.out.println("Enter High Price :");
                while (!(input2 = scanner.nextLine()).matches("\\d+")) {
                    System.out.println("Enter Valid Number -_-");
                }
                if ((products = ProductController.getInstance().filterByPrice(Double.parseDouble(input1), Double.parseDouble(input2))).size() > 0)
                    System.out.println(products);
                else
                    System.out.println("Nothing Found :(");
            }
        };
    }

    private Menu getFilterByField() {
        return new Menu("Filter By Field", this) {
            @Override
            public void show() {
                fancyTitle ();
            }

            @Override
            public void execute() {
                String input1, input2;
                System.out.println("Enter Field :");
                input1 = scanner.nextLine();
                System.out.println("Enter Property :");
                input2 = scanner.nextLine();
                if ((products = ProductController.getInstance().filterByField(input1, input2, products)) != null)
                    System.out.println(products);
                else
                    System.out.println("Nothing Found :(");

                ArrayList<String> inputs = new ArrayList<>();
                inputs.add(input1);
                inputs.add(input2);
                filters.put(FilterName.FIELD, inputs);
            }
        };
    }

    private Menu getDisableFilter() {
        return new Menu("Disable Filter", this) {
            @Override
            public void show() {
                fancyTitle ();
            }

            @Override
            public void execute() {
                String input;
                System.out.println("Enter Filter Number You Want To Disable :");
                while (!(input = scanner.nextLine()).matches("[12345]") && !input.equals(BACK_BUTTON)){
                    System.out.println("Enter a number between 1-5 or .. to go back");
                }
                if (input.equals(BACK_BUTTON))
                    return;

                filters.remove(FilterName.values()[Integer.parseInt(input)]);
                //BAYAD GHABLESH SET SHE PRODUCTS IN MNU BAR ASAS HAR MENU EE KE TOSHE
                filterByExistingFilters();
            }
        };
    }

    private Menu getShowCurrentFilters() {
        return new Menu("Current Filters", this) {
            @Override
            public void show() {
                fancyTitle ();
            }

            @Override
            public void execute() {
                int number = 1;
                for (FilterName filter : filters.keySet()) {
                    System.out.println(number++ + "." + filter);
                }
            }
        };
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    private void filterByExistingFilters() {
        products = currentProducts;
        for (FilterName filter : filters.keySet()) {
            products = filterByType(filter, products);
        }
    }

    private ArrayList<Product> filterByType(FilterName filterName, ArrayList<Product> products) {
        switch (filterName) {
            case NAME:
                return ProductController.getInstance().filterByName(filters.get(filterName).get(0), products);
            case BRAND:
                return ProductController.getInstance().filterByBrand(filters.get(filterName).get(0), products);
            case CATEGORY:
                return ProductController.getInstance().filterByCategory(filters.get(filterName).get(0), products);
            case FIELD:
                return ProductController.getInstance().filterByField(filters.get(filterName).get(0), filters.get(filterName).get(1), products);
            case AVAILABLE:
                return ProductController.getInstance().filterByExisting(products);
            default:
                return currentProducts;
        }
    }
}