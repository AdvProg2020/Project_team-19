package clientController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Category;
import model.Person;
import model.Product;
import model.Salesperson;
import server.PacketType;
import server.Request;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static server.PacketType.*;

public class ServerConnection {
    public static Socket socket;
    public static DataOutputStream dataOutputStream;
    public static DataInputStream dataInputStream;
    public static String token;

    public static void run() {
        try {
            socket = new Socket("localhost", 4444);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String increaseTotalSeen(String productId) { //todo server
        ArrayList<String> info = new ArrayList<>();
        info.add(productId);
        return sendMessage(, info, "");
    }

    public static String increaseTotalScore(String productId) { //todo server
        ArrayList<String> info = new ArrayList<>();
        info.add(productId);
        return sendMessage(, info, "");
    }

    public static ArrayList<Salesperson> getProductSellers(String productId) {
        ArrayList<String> info = new ArrayList<>();
        info.add(productId);
        String response = sendMessage(, info, "");
        return (ArrayList<Salesperson>) getObj(new TypeToken<ArrayList<Salesperson>>(){}.getType(), response);
    }

    public static ArrayList<Product> getSimilarProducts(String productId) { //todo server
        ArrayList<String> info = new ArrayList<>();
        info.add(productId);
        String response = sendMessage(, info, "");
        return (ArrayList<Product>) getObj(new TypeToken<ArrayList<Product>>(){}.getType(), response);
    }

    public static String getPersonType(String username) {
        ArrayList<String> info = new ArrayList<>();
        info.add(username);
        return sendMessage(GET_PERSON_TYPE, info, "");
    }

    public static <T> T getPersonByToken(Class<T> personType) { //todo server
        String response = sendMessage(, null, token);
        return (T) getObj(personType, response);
    }

    public static String getPersonByUsername(String username) {
        ArrayList<String> info = new ArrayList<>();
        info.add(username);
        return sendMessage(PacketType.GET_PERSON, info, "");
    }

    public static String getIsFirstManagerRegistered() {
        return sendMessage(PacketType.IS_FIRST_MANAGER_REGISTERED, null, "");
    }

    public static String sendRegisterRequest(HashMap< String, String > personInfo){
        ArrayList<String> info = new ArrayList<>();
        info.add(toJson(personInfo));
        return sendMessage(PacketType.REGISTER, info, "");
    }

    public static String sendLoginRequest(String username, String password) {
        ArrayList<String> info = new ArrayList<>();
        info.add(username);
        info.add(password);
        return sendMessage(PacketType.LOGIN, info, "");
    }

    public static String getBankToken(String username, String password) {
        ArrayList<String> info = new ArrayList<>();
        info.add(username);
        info.add(password);
        return sendMessage(PacketType.GET_BANK_TOKEN, info, "");
    }

    public static String getBankBalance(String token) {
        ArrayList<String> info = new ArrayList<>();
        info.add(token);
        return sendMessage(PacketType.GET_BANK_BALANCE, info, "");
    }

    public static String getTransaction(String token, String type) {
        ArrayList<String> info = new ArrayList<>();
        info.add(token);
        info.add(type);
        return sendMessage(PacketType.GET_TRANSACTION, info, "");
    }

    public static String getIncreaseBankBalance(String bankToken, String amount) {
        ArrayList<String> info = new ArrayList<>();
        info.add(bankToken);
        info.add(amount);
        return sendMessage(PacketType.INCREASE_BANK_BALANCE, info, token);
    }

    public static String getIncreaseWalletBalance(String bankToken, String amount) {
        ArrayList<String> info = new ArrayList<>();
        info.add(bankToken);
        info.add(amount);
        return sendMessage(PacketType.INCREASE_WALLET_BALANCE, info, token);
    }

    public static String getDecreaseWalletBalance(String bankToken, String amount) {
        ArrayList<String> info = new ArrayList<>();
        info.add(bankToken);
        info.add(amount);
        return sendMessage(PacketType.DECREASE_WALLET_BALANCE, info, token);
    }

    public static ArrayList<Product> getAllProducts() {
        String response = sendMessage(GET_ALL_PRODUCTS, null, "");
        return (ArrayList<Product>) getObj(new TypeToken<ArrayList<Product>>(){}.getType(), response);
    }

    public static String addNewProduct(ArrayList<String> info) {
        return sendMessage(ADD_NEW_PRODUCT, info, token);
    }

    public static String addProductRequestFromStock(ArrayList<String> info) {
        return sendMessage(ADD_PRODUCT_FROM_STOCK, info, token);
    }

    public static String editProductRequest(ArrayList<String> info) {
        return sendMessage(EDIT_PRODUCT_REQUEST, info, token);
    }

    public static Category getCategoryByName(String categoryName) {
        ArrayList<String> info = new ArrayList<>();
        info.add(categoryName);
        return (Category) getObj(Category.class, sendMessage(GET_CATEGORY_BY_NAME, info, ""));
    }

    public static String acceptRequest(String requestId) {
        ArrayList<String> info = new ArrayList<>();
        info.add(requestId);
        return sendMessage(ACCEPT_REQUEST, info, "");
    }

    public static String declineRequest(String requestId) {
        ArrayList<String> info = new ArrayList<>();
        info.add(requestId);
        return sendMessage(DECLINE_REQUEST, info, "");
    }

    public static Product getProductById(String id) {
        ArrayList<String> info = new ArrayList<>();
        info.add(id);
        String response = sendMessage(GET_PRODUCT_BY_ID, info, "");
        return (Product) getObj(Product.class, response);
    }

    public static String addAuctionRequest(String sellerName, String productId, String endTime) {
        ArrayList<String> info = new ArrayList<>();
        info.add(sellerName);
        info.add(productId);
        info.add(endTime);
        return sendMessage(ADD_AUCTION_REQUEST, info, "");
    }

//    public static HashMap<Product> getOfferedProducts() {
//        String response = sendMessage(GET_SELLER_PRODUCTS, null, token);
//        return (ArrayList<Product>) getObj(new TypeToken<ArrayList<Product>>(){}.getType(), response);
//    }

    public static ArrayList<Product> getAllSellerProducts() {  //todo server
        String response = sendMessage(GET_SELLER_PRODUCTS, null, token);
        return (ArrayList<Product>) getObj(new TypeToken<ArrayList<Product>>(){}.getType(), response);
    }

    public static ArrayList<Product> getVerifiedSellerProducts() { //todo server
        String response = sendMessage(, null, token);
        return (ArrayList<Product>) getObj(new TypeToken<ArrayList<Product>>(){}.getType(), response);
    }

    public static ArrayList<Category> getRootCategories() {
        String response = sendMessage(GET_ROOT_CATEGORIES, null, "");
        return (ArrayList<Category>) getObj(new TypeToken<ArrayList<Category>>(){}.getType(), response);
    }

    public static String removeCategory(Category category) {  //todo server
        ArrayList<String> info = new ArrayList<>();
        info.add(category.getName());
        return sendMessage(REMOVE_CATEGORY, info, "");
    }

    public static HashMap<Salesperson, ArrayList<Product>> getAllAuctions() {
        String response = sendMessage(GET_ALL_AUCTIONS, null, "");
        return (HashMap<Salesperson, ArrayList<Product>>) getObj(new TypeToken<HashMap<Salesperson, ArrayList<Product>>>(){}.getType(), response);
    }

    public static ArrayList<Product> getAvailableProductsForAuction() {
        String response = sendMessage(GET_AVAILABLE_AUCTION_PRODUCTS, null, token);
        return (ArrayList<Product>) getObj(new TypeToken<ArrayList<Product>>(){}.getType(), response);
    }

    public static ArrayList<model.Request> getAllRequests() {
        String response = sendMessage(GET_ALL_REQUESTS, null, "");
        return (ArrayList<model.Request>) getObj(new TypeToken<ArrayList<model.Request>>(){}.getType(), response);
    }

    public static String addCategory(ArrayList<String> info) {  //todo server
        return sendMessage(ADD_CATEGORY, info, "");
    }

    public static String editCategory(ArrayList<String> info) {  //todo server
        return sendMessage(EDIT_CATEGORY, info, "");
    }

    public static ArrayList<String> getParentCategories() {
        String response = sendMessage(GET_PARENT_CATEGORIES, null, "");
        return (ArrayList<String>) getObj(new TypeToken<ArrayList<String>>(){}.getType(), response);
    }

    private static String sendMessage(PacketType packetType, ArrayList<String> info, String token) {
        Request request = new Request(packetType, info, token);
        try {
            dataOutputStream.writeUTF(toJson(request));
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static Object getObj(java.lang.reflect.Type typeOfT, String string) {
        GsonBuilder builder = new GsonBuilder().setLenient().enableComplexMapKeySerialization();
        Gson gson = builder.create();
        return gson.fromJson(string, typeOfT);
    }

    public static <T> String toJson(T obj) {
        GsonBuilder builder = new GsonBuilder().enableComplexMapKeySerialization();
        Gson gson = builder.create();
        return gson.toJson(obj);
    }
}
