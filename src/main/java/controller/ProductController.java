package controller;

import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProductController {
    private static ArrayList<Product> allProducts = new ArrayList<Product>();
    private static ArrayList<Comment> allComments = new ArrayList<Comment>();

    public static void initializeProducts() throws FileNotFoundException {
        for (File file : Database.returnListOfFiles(Database.address.get("product"))) {
            allProducts.add((Product) Database.read(Product.class, file.getAbsolutePath()));
        }
    }

    public static void initializeComments() throws FileNotFoundException {
        for (File file : Database.returnListOfFiles(Database.address.get("comment"))) {
            allComments.add((Comment) Database.read(Product.class, file.getAbsolutePath()));
        }
    }


    public static ArrayList filterBy(String fieldName, String property, Category category) {
        return category.getProductList().stream().filter(product -> {
            return product.getProperties().get(fieldName).equals(property);
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList getSellersNameByPriceFiltering(double lowPrice, double highPrice, Product product) {
        return Product.stock.get(product).stream().filter(salesperson -> {
            return salesperson.getProductPrice(product) >= lowPrice && salesperson.getProductPrice(product) <= highPrice;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList filterACategoryByPrice(double lowPrice, double highPrice, Category category) {
        return category.getProductList().stream().filter(product -> {
            for (Salesperson salesperson : Product.stock.get(product)) {
                if (salesperson.getProductPrice(product) >= lowPrice && salesperson.getProductPrice(product) <= highPrice)
                    return true;
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

}
