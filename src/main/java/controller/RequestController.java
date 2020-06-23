package controller;

import model.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import static controller.Database.*;
import static controller.ProductController.stock;

public class RequestController {
    public static ArrayList<Request> allRequests = new ArrayList<>();
    private static RequestController single_instance = null;
    private String productID;
    private String discountID;

    private RequestController() {
    }

    public static RequestController getInstance() {
        if (single_instance == null)
            single_instance = new RequestController();

        return single_instance;
    }

    public enum FilterType {
        ALL, SALESPERSON, PRODUCT, DISCOUNT
    }

    public String getProductID() {
        return productID;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void initializeRequests() {
        for (File file : Database.returnListOfFiles(Database.address.get("discount_requests"))) {
            allRequests.add((DiscountRequest) read(DiscountRequest.class, file.getAbsolutePath()));
        }
        for (File file : Database.returnListOfFiles(address.get("product_requests"))) {
            allRequests.add((ProductRequest) read(ProductRequest.class, file.getAbsolutePath()));
        }
        for (File file : Database.returnListOfFiles(address.get("salesperson_requests"))) {
            allRequests.add((SalespersonRequest) read(SalespersonRequest.class, file.getAbsolutePath()));
        }
    }

    public void acceptRequest(Request request) {
        request.doThis();
        allRequests.remove(request);
        try {
            if (request instanceof DiscountRequest)
                deleteFile(createPath("discount_requests", request.getRequestId()));
            else if (request instanceof ProductRequest)
                deleteFile(createPath("product_requests", request.getRequestId()));
            else if (request instanceof SalespersonRequest)
                deleteFile(createPath("salesperson_requests", request.getRequestId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void declineRequest(Request request) {
        request.decline();
        allRequests.remove(request);
        try {
            if (request instanceof DiscountRequest)
                deleteFile(createPath("discount_requests", request.getRequestId()));
            else if (request instanceof ProductRequest)
                deleteFile(createPath("product_requests", request.getRequestId()));
            else if (request instanceof SalespersonRequest)
                deleteFile(createPath("salesperson_requests", request.getRequestId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSalesPerson(HashMap<String, String> personInfo) {
        Salesperson salesperson = new Salesperson(personInfo);
        PersonController.getInstance().addPerson(salesperson);
    }

    public ArrayList<Request> filterByState(Request.RequestState requestState) {
        return allRequests.stream().filter(request -> request.getRequestState().
                equals(requestState)).collect(Collectors.toCollection(ArrayList::new));
    }

    public <T> ArrayList<Request> getSpecificTypeOfRequests(Class<T> requestType) {
        ArrayList<Request> requests = allRequests;
        return requests.stream().filter(requestType::isInstance).collect(Collectors.toCollection(ArrayList::new));
    }

    public void deleteProductRequest(String productId, Salesperson salesperson) {
        new ProductRequest(salesperson, ProductController.getInstance().getProductById(productId));
    }

    public void addProductRequest(Double price, Integer amount, Salesperson salesperson
            , String category, String name, String brand, HashMap<String, String> properties) {
        Product product = new Product(name, brand, category, properties);
        saveTempProductInProgramLists(product, salesperson, amount, price);
        saveFileChangesForProductBeforeSendingRequest(product, salesperson);
        productID = product.getID();
        new ProductRequest(price, amount, salesperson, product);
    }

    private void saveTempProductInProgramLists(Product product, Salesperson salesperson, int amount, double price) {
        salesperson.addToOfferedProducts(product, amount, price);
        salesperson.setBuildInProgress(product);
        if (stock.containsKey(product))
            stock.get(product).add(salesperson);
        else {
            ArrayList<Salesperson> salespeople = new ArrayList<>();
            salespeople.add(salesperson);
            stock.put(product, salespeople);
        }
    }

    private void saveFileChangesForProductBeforeSendingRequest(Product product, Salesperson salesperson) {
        saveToFile(product, createPath("products", product.getID()));
        saveToFile(salesperson, createPath("salespersons", salesperson.getUsername()));
    }

    public void editProductRequest(String price, String amount, Salesperson salesperson, String productID
            , String category, String name, String brand, HashMap<String, String> properties) {

        Product product = ProductController.getInstance().getProductById(productID);
        salesperson.setEditInProgress(product);
        saveFileChangesForProductBeforeSendingRequest(product, salesperson);
        double pr = (price == null) ? salesperson.getProductPrice(product) : Double.parseDouble(price);
        int am = (amount == null) ? salesperson.getProductAmount(product) : Integer.parseInt(amount);
        new ProductRequest(pr, am, salesperson, category, name, brand, properties, product);
    }

    public void deleteDiscountRequest(Discount discount, Salesperson salesperson) {
        new DiscountRequest(discount, salesperson, Request.RequestState.DELETE);
    }

    public void addDiscountRequest(ArrayList<Product> add, LocalDateTime startTime, LocalDateTime endTime, Double discountPercentage, Salesperson salesperson) {
        Discount discount = new Discount(startTime, endTime, discountPercentage, add);

        discount.setDiscountState(Discount.DiscountState.BUILD_IN_PROGRESS);

        salesperson.addToDiscounts(discount);

        saveToFile(salesperson, createPath("salespersons", salesperson.getUsername()));

        discountID = discount.getDiscountID();
        new DiscountRequest(discount, salesperson, Request.RequestState.ADD);
    }

    public void editDiscountRequest(Discount discount, ArrayList<Product> add, ArrayList<Product> remove, LocalDateTime startTime,
                                    LocalDateTime endTime, Double discountPercentage, Salesperson salesperson) {

        discount.setDiscountState(Discount.DiscountState.EDIT_IN_PROGRESS);
        //serfan bedonim editInProgressE base
        saveToFile(salesperson, createPath("salespersons", salesperson.getUsername()));

        ArrayList<Product> products = discount.getProducts();
        if (startTime == null)
            startTime = discount.getStartTime();
        if (endTime == null)
            endTime = discount.getEndTime();
        if (discountPercentage == null)
            discountPercentage = discount.getDiscountPercentage();
        products.addAll(add);
        products.removeAll(remove);

        new DiscountRequest(discount, products, startTime, endTime, discountPercentage, salesperson);
    }


    public Request getRequestById(String requestId) {
        for (Request request : allRequests) {
            if (request.getRequestId().equals(requestId))
                return request;
        }
        return null;
    }

}
