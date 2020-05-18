package controller;

import model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

import static controller.Database.*;

public class ProductController {
    public static ArrayList<Product> allProducts = new ArrayList<>();
    public static ArrayList<Product> currentProducts = new ArrayList<>();
    public static HashMap<Product, ArrayList<Salesperson>> stock;

    private static ProductController single_instance = null;

    private ProductController() {
    }

    public static ProductController getInstance() {
        if (single_instance == null)
            single_instance = new ProductController();

        return single_instance;
    }

    public void initializeProducts() {
        for (File file : Database.returnListOfFiles(Database.address.get("products"))) {
            allProducts.add( (Product)Database.read(Product.class, file.getAbsolutePath()));
        }
    }

    public void initializeStock() {
        stock = new HashMap<>();
        for (Product product : allProducts) {
            stock.put(product,new ArrayList<>());
        }
        for (Person person : PersonController.getInstance().filterByRoll(Salesperson.class)) {
            Salesperson salesperson = (Salesperson) person;
            for (Product product : salesperson.getOfferedProducts().keySet()) {
                stock.get(product).add(salesperson);
            }
        }
    }

    public void setCurrentProducts(ArrayList<Product> currentProducts) {
        ProductController.currentProducts = currentProducts;
    }

    public boolean isThereProductById(String productID)  {
        return getProductById(productID)!=null;
    }

    public static class WrongProductIdException extends Exception{
        public String message="No product with such id";
    }

    public Product getProductById(String productID) {
        for (Product product : allProducts) {
            if (product.getID().equals(productID))
                return product;
        }
        return null;
    }

    public void addProduct(Product product, Salesperson salesperson) {
        if (!product.getCategory().getProductList().contains(product))
            product.getCategory().addProduct(product);
        changeStateToVerified(product, salesperson);
        saveChangesInFileAfterAddProduct(product, salesperson);
    }

    private void changeStateToVerified(Product product, Salesperson salesperson) {
        salesperson.setVerified(product);
    }

    private void saveChangesInFileAfterAddProduct(Product product, Salesperson salesperson) {
        Database.write(product, Database.createPath("products", product.getID()));
        saveToFile(salesperson, createPath("salespersons", salesperson.getUsername()));
        saveToFile(stock, address.get("stock"));
        saveToFile(CategoryController.rootCategories,address.get("root_categories"));
    }


    public void editProduct(Product product, Salesperson salesperson, int amount, double price,
                            String category, String name, String brand, HashMap<String, String>properties) {

        changeStateToVerified(product, salesperson);
        editProductForSeller(product, salesperson, price, amount);
        editProductInGeneral(product, category, name, brand, properties);
        changeFilesAfterEditProduct(product, salesperson);
    }

    private void editProductForSeller(Product product, Salesperson salesperson, double price, int amount) {
        salesperson.editProduct(product, price, amount);
    }

    private void editProductInGeneral(Product product, String category, String name, String brand, HashMap<String, String>properties) {
        product.getCategory().removeProduct(product);
        product.edit(category, name, brand, properties);
        product.getCategory().addProduct(product);
    }

    private void changeFilesAfterEditProduct(Product product, Salesperson salesperson) {
        saveToFile(product, createPath("products", product.getID()));
        saveToFile(salesperson, createPath("salespersons", salesperson.getUsername()));
        saveToFile(CategoryController.rootCategories,address.get("root_categories"));
        saveToFile(stock, address.get("stock"));
    }

    public void removeProduct(Product product, Salesperson salesperson)  {
        removeProductInProgramLists(product, salesperson);
        changeFilesAfterRemoveProduct(product, salesperson);
    }

    private void removeProductInProgramLists(Product product, Salesperson salesperson) {
        allProducts.remove(product);
        stock.get(product).remove(salesperson);
        salesperson.removeFromOfferedProducts(product);
        CartController.getInstance().removeProduct(product,salesperson);
    }

    private void changeFilesAfterRemoveProduct(Product product, Salesperson salesperson) {
        saveToFile(CategoryController.rootCategories,address.get("root_categories"));
        saveToFile(salesperson, createPath("salespersons", salesperson.getUsername()));
        saveToFile(stock, address.get("stock"));
        try {
            deleteFile(createPath("products", product.getID()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void declineProductRequestForAdd(Product product, Salesperson salesperson) {
        removeTempProductFromProgram(product, salesperson);
        changeFilesAfterRemoveProduct(product, salesperson);
    }

    private void removeTempProductFromProgram(Product product, Salesperson salesperson) {
        allProducts.remove(product);
        stock.get(product).remove(salesperson);
        salesperson.removeFromOfferedProducts(product);
    }

    public void declineRequestForEditAndRemove(Product product, Salesperson salesperson) {
        salesperson.setVerified(product);
        saveToFile(salesperson, Database.createPath("salespersons", salesperson.getUsername()));
    }

    public void removeProductForManager(Product product) {
        product.getCategory().removeProduct(product);
        for (Person salesperson : PersonController.getInstance().filterByRoll(Salesperson.class)) {
            if(((Salesperson)salesperson).hasProduct(product))
                removeProduct(product, (Salesperson) salesperson);

        }
        CartController.getInstance().removeProduct(product);
        saveToFile(CategoryController.rootCategories,address.get("root_categories"));
        saveToFile(stock, address.get("stock"));
        try {
            deleteFile(Database.createPath("products", product.getID()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isProductAvailable(Product product){
        for (Salesperson salesperson : stock.get(product)) {
            if(salesperson.getProductAmount(product)>0){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Product> filterByField(String fieldName, String property, ArrayList<Product> products) {
        return products.stream().filter(product -> {
            if (product.getProperties().containsKey(fieldName))
                return product.getProperties().get(fieldName).equals(property);
            else
                return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Product> filterByName(String productName, ArrayList<Product> products) {
        return products.stream().filter(product -> product.getName().equals(productName)).
                collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Product> filterByBrand(String brandName, ArrayList<Product> products) {
        return products.stream().filter(product -> product.getBrand().equals(brandName)).
                collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Product> filterByCategory(String categoryName, ArrayList<Product> products) {
        return products.stream().filter(product -> product.getCategory().getName().equals(categoryName)).
                collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<OwnedProduct> filterByPrice(double lowPrice, double highPrice) {
        ArrayList<OwnedProduct> products = new ArrayList<>();
        for (Product product : currentProducts) {
            products.addAll(filterOwnedProductByPrice(lowPrice, highPrice, product));
        }
        return products;
    }

    public ArrayList<Product> filterByPrice(double low, double high, ArrayList<Product> products) {
        return products.stream().filter(product -> product.getLeastPrice() >= low && product.getLeastPrice() <= high)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<OwnedProduct> filterOwnedProductByPrice(double lowPrice, double highPrice, Product product) {
        return getProductsOfProduct(product).stream().filter(ownedProduct -> ownedProduct.getPrice() <= highPrice &&
                ownedProduct.getPrice() >= lowPrice).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Product> filterACategoryByPrice(double lowPrice, double highPrice, Category category) {
        return category.getProductList().stream().filter(product -> {
            for (Salesperson salesperson : stock.get(product)) {
                if (salesperson.getProductPrice(product) >= lowPrice && salesperson.getProductPrice(product) <= highPrice)
                    return true;
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Product> filterByExisting(ArrayList<Product> products) {
        return products.stream().filter(this::isProductAvailable).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<OwnedProduct> getProductsOfProduct(Product product) {
        ArrayList<OwnedProduct> productList = new ArrayList<>();

        for (Salesperson salesperson : stock.get(product)) {
            productList.add(new OwnedProduct(product, salesperson, salesperson.getProductPrice(product)));
        }

        return productList;
    }

    public void removeSellerInStock(Salesperson salesperson){
        for (Product product : stock.keySet()) {
            stock.get(product).remove(salesperson);
        }
    }

    public boolean doesSellerHasProduct(Product product, Salesperson salesperson){
        for (OwnedProduct ownedProduct : getProductsOfProduct(product)) {
            if(ownedProduct.getSalesperson().equals(salesperson))
                return true;
        }
        return false;
    }

    public ArrayList<OwnedProduct> sortProductByPrice(Product product) {
        ArrayList<OwnedProduct> sortedProducts = getProductsOfProduct(product);

        sortedProducts.sort(new SortByPrice());
        return sortedProducts;
    }

    public void sortProduct(ArrayList<Product> products, boolean averageScore,
                            boolean name, boolean brand, boolean price) {
        SortProduct sortProduct = new SortProduct();
        sortProduct.setAverageScore(averageScore);
        sortProduct.setName(name);
        sortProduct.setBrand(brand);
        sortProduct.setPrice(price);
        products.sort(sortProduct);
    }
}

class SortByPrice implements Comparator<OwnedProduct> {

    @Override
    public int compare(OwnedProduct product1, OwnedProduct product2) {
        if (product1.getPriceForShow() != product2.getPriceForShow())
            return (int) (product1.getPriceForShow() - product2.getPriceForShow());
        else
            return product1.getSellerName().compareTo(product2.getSellerName());
    }
}

class SortProduct implements Comparator<Product> {

    private boolean averageScore = false;
    private boolean name = false;
    private boolean brand = false;
    private boolean price = false;

    public void setAverageScore(boolean averageScore) {
        this.averageScore = averageScore;
    }

    public void setName(boolean name) {
        this.name = name;
    }

    public void setBrand(boolean brand) {
        this.brand = brand;
    }

    public void setPrice(boolean price) {
        this.price = price;
    }

    @Override
    public int compare(Product product1, Product product2) {

        if (averageScore && product1.getAverageScore() != product2.getAverageScore())
            return (int) (product1.getAverageScore() - product2.getAverageScore());
        else if (name && !product1.getName().equals(product2.getName()))
            return product1.getName().compareTo(product2.getName());
        else if (brand && !product1.getBrand().equals(product2.getBrand()))
            return product1.getBrand().compareTo(product2.getBrand());
        else if (price && product1.getLeastPrice() != product2.getLeastPrice())
            return (int) (product1.getLeastPrice() - product2.getLeastPrice());
        else
            return product1.getSeen() - product2.getSeen();

    }
}