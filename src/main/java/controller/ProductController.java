package controller;

import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

import static model.Product.stock;
import static controller.Database.address;

public class ProductController {
    private static ArrayList<Product> allProducts = new ArrayList<Product>();
    private static ArrayList<Comment> allComments = new ArrayList<Comment>();

    public static void initializeProducts() throws FileNotFoundException {
        for (File file : Database.returnListOfFiles(address.get("product"))) {
            allProducts.add((Product) Database.read(Product.class, file.getAbsolutePath()));
        }
    }

    public static void initializeComments() throws FileNotFoundException {
        for (File file : Database.returnListOfFiles(address.get("comment"))) {
            allComments.add((Comment) Database.read(Product.class, file.getAbsolutePath()));
        }
    }

    public static ArrayList<Product> filterByField(String fieldName, String property, Category category) {
        return category.getProductList().stream().filter(product -> {
            return product.getProperties().get(fieldName).equals(property);
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<Salesperson> getSellersNameByPriceFiltering(double lowPrice, double highPrice, Product product) {
        return stock.get(product).stream().filter(salesperson -> {
            return salesperson.getProductPrice(product) >= lowPrice && salesperson.getProductPrice(product) <= highPrice;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<Product> filterACategoryByPrice(double lowPrice, double highPrice, Category category) {
        return category.getProductList().stream().filter(product -> {
            for (Salesperson salesperson : stock.get(product)) {
                if (salesperson.getProductPrice(product) >= lowPrice && salesperson.getProductPrice(product) <= highPrice)
                    return true;
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<Product> filterByExisting(Category category) {
        return category.getProductList().stream().filter(Product::isAvailable).collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<OwnedProduct> getProductsOfProduct(Product product) {
        ArrayList<OwnedProduct> productList = new ArrayList<>();

        for (Salesperson salesperson : stock.get(product)) {
            productList.add(new OwnedProduct(product, salesperson, salesperson.getProductPrice(product)));
        }

        return productList;
    }

    public static ArrayList<OwnedProduct> sortProductByPrice(Product product) {
        ArrayList<OwnedProduct> sortedProducts = new ArrayList<>();
        sortedProducts = getProductsOfProduct(product);

        sortedProducts.sort(new SortByPrice());
        return sortedProducts;
    }
}

class OwnedProduct {
    private Product product;
    private Salesperson salesperson;
    double price;

    public OwnedProduct(Product product, Salesperson salesperson, double price) {
        this.price = price;
        this.product = product;
        this.salesperson = salesperson;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "OwnedProduct{" +
                "product=" + product +
                ", price=" + price +
                '}';
    }
}

class SortByPrice implements Comparator<OwnedProduct> {

    @Override
    public int compare(OwnedProduct product1, OwnedProduct product2) {
        return (int) (product1.getPrice() - product2.getPrice());
    }
}