package view;

import controller.ProductController;
import model.OwnedProduct;
import model.Product;

import java.util.ArrayList;

import static controller.ProductController.*;
import static view.ViewProductMenu.showProductDigest;

public class SortingMenu extends Menu {
    private ArrayList<Product> products = new ArrayList<>();
    private SortType sortType;
    enum SortType {
        AVERAGE_SCORE, VIEW, NAME, BRAND
    }


    public SortingMenu(Menu parentMenu) {
        super("Sort Menu", parentMenu);
        subMenus.put(1, getSortByPrice());
        subMenus.put(2, getSortByAverageScore());
        subMenus.put(3, getSortByViewMenu());
        subMenus.put(4, getSortByNameMenu());
        subMenus.put(5, getSortByBrandMenu());
        subMenus.put(6, getShowCurrentSortMenu());
        subMenus.put(7, getDisableSortMenu());
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    private Menu getSortByPrice(){
        return new Menu("Sort By Price", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
            }

            @Override
            public void execute() {
                for (Product product : currentProducts) {
                    System.out.println(LINE);
                    System.out.println("Name : " + product.getName() + ", Brand : " + product.getBrand());
                    showProductsOfProduct(ProductController.getInstance().sortProductByPrice(product));
                    System.out.println();
                }
            }
        };
    }

    private void showProductsOfProduct(ArrayList<OwnedProduct> products) {
        for (OwnedProduct product : products) {
            showOwnedProduct(product);
            System.out.println();
        }
    }

    private void showOwnedProduct(OwnedProduct product) {
        System.out.println(LINE);
        System.out.println(String.format("|%-26s%-18s%-11s|", product.getSellerName(), product.getInDiscount() ? "In Discount" : "No Discount", product.getPriceForShow()));
        System.out.println(LINE);
    }

    private Menu getSortByAverageScore() {
        return new Menu("Sort By Average Score", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
            }

            @Override
            public void execute() {
                sortType = SortType.AVERAGE_SCORE;
                ProductController.getInstance().sortProduct(products, true, false, false);
                showProducts();
            }
        };
    }

    private void showProducts() {
        for (Product product : products) {
            showProductDigest(product);
            System.out.println();
        }
    }

    private Menu getSortByViewMenu() {
        return new Menu("Sort By View", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void execute() {
                sortType = SortType.VIEW;
                ProductController.getInstance().sortProduct(products, false, false, false);
                showProducts();
            }
        };
    }

    private Menu getSortByNameMenu() {
        return new Menu("Sort By Name", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void execute() {
                sortType = SortType.NAME;
                ProductController.getInstance().sortProduct(products, false, true, false);
                showProducts();
            }
        };
    }

    public Menu getSortByBrandMenu() {
        return new Menu("Sort By Brand", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void execute() {
                sortType = SortType.BRAND;
                ProductController.getInstance().sortProduct(products, false, false, true);
                showProducts();
            }
        };
    }

    private Menu getShowCurrentSortMenu() {
        return new Menu("Current Sort", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
            }

            @Override
            public void execute() {
                System.out.println(sortType);
            }
        };
    }

    private Menu getDisableSortMenu() {
        return new Menu("Disable Current Sort", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
            }

            @Override
            public void execute() {
                products = currentProducts;
                ProductController.getInstance().sortProduct(products, false, false, false);
                showProducts();
            }
        };
    }
}
