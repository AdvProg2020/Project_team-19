package controller;

import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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
}
