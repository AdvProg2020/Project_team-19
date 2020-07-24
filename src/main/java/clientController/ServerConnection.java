package clientController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.*;
import server.PacketType;
import server.Request;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import static server.PacketType.*;

public class ServerConnection {
    public static Socket socket;
    public static DataOutputStream dataOutputStream;
    public static DataInputStream dataInputStream;
    public static String token = "";
    private static Cart tempCart = new Cart();

    public static void run() {
        try {
            socket = new Socket("localhost", 4444);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String auctionPurchase(String auctionId) {
        ArrayList<String> info = new ArrayList<>();
        info.add(auctionId);
        return sendMessage(AUCTION_PURCHASE, info, token);
    }

    public static String sendAuctionMessage(String auctionId, String text) {
        ArrayList<String> info = new ArrayList<>();
        info.add(auctionId);
        info.add(text);
        return sendMessage(SEND_AUCTION_MESSAGE, info, token);
    }

    public static String getOfferPriceForAuction(String auctionId, double amount) {
        ArrayList<String> info = new ArrayList<>();
        info.add(auctionId);
        info.add(String.valueOf(amount));
        return sendMessage(OFFER_PRICE_FOR_AUCTION, info, token);
    }

    public static String addToCart(Salesperson seller, Product product) {
        ArrayList<String> info = new ArrayList<>();
        if (token.length()!=0) {
            info.add(seller.getUsername());
            info.add(product.getID());
            return sendMessage(ADD_TO_CART, info, token);
        }else {
            tempCart.addProduct(product,seller);
            return "successful";
        }
    }

    public static Cart getCart(){
        if (token.length()!=0){
            String response = sendMessage(GET_CART,null,token);
            return (Cart) getObj(Cart.class,response);
        }else {
            return tempCart;
        }
    }

    public static ArrayList<Product> getCategoryProductList(String categoryName, boolean inDiscount) {
        ArrayList<String> info = new ArrayList<>();
        info.add(categoryName);
        String response = sendMessage(inDiscount ? GET_IN_DISCOUNT_CATEGORY_PRODUCTS : GET_CATEGORY_PRODUCTS, info, "");
        return (ArrayList<Product>) getObj(new TypeToken<ArrayList<Product>>(){}.getType(), response);
    }

    public static ArrayList<Category> getNodeCategories() {
        String response = sendMessage(GET_NODE_CATEGORIES, null, "");
        return (ArrayList<Category>) getObj(new TypeToken<ArrayList<Category>>(){}.getType(), response);
    }

    public static String getWalletPurchase() {
        return sendMessage(WALLET_PURCHASE, null, token);
    }

    public static String getBankPurchase(String username , String password) {
        ArrayList<String> info = new ArrayList<>();
        info.add(username);
        info.add(password);
        return sendMessage(BANK_PURCHASE, info, token);
    }

    public static String getBankId() {
        return sendMessage(GET_BANK_ID, null, token);
    }

    public static String getWalletBalance() {
        return sendMessage(GET_WALLET_BALANCE, null, token);
    }

    public static Wallet getWallet() {
        String response = sendMessage(GET_WALLET, null, token);
        return (Wallet) getObj(Wallet.class, response);
    }

    public static String sendLogout() {
        String response = sendMessage(LOG_OUT, null, token);
        token = "";
        return response;
    }

    public static HashMap<String, String> getAllPersonInfo() {
        String response = sendMessage(GET_ALL_PERSON_INFO, null, "");
        return (HashMap<String, String>) getObj(new TypeToken<HashMap<String, String>>(){}.getType(), response);
    }

    public static HashMap<String, String> getPersonInfoByToken() {
        String response = sendMessage(GET_PERSON_INFO_BY_TOKEN, null, token);
        return (HashMap<String, String>) getObj(new TypeToken<HashMap<String, String>>(){}.getType(), response);
    }

    public static String increaseTotalSeen(String productId) {
        ArrayList<String> info = new ArrayList<>();
        info.add(productId);
        return sendMessage(INCREASE_SEEN, info, "");
    }

    public static String increaseTotalScore(String productId, int score) {
        ArrayList<String> info = new ArrayList<>();
        info.add(productId);
        info.add(String.valueOf(score));
        return sendMessage(INCREASE_SCORE, info, "");
    }

    public static ArrayList<Salesperson> getProductSellersForProductMenu(String productId) {
        ArrayList<String> info = new ArrayList<>();
        info.add(productId);
        String response = sendMessage(GET_SELLERS_OF_PRODUCTS, info, "");
        return (ArrayList<Salesperson>) getObj(new TypeToken<ArrayList<Salesperson>>(){}.getType(), response);
    }

    public static ArrayList<Product> getSimilarProducts(String productId) {
        ArrayList<String> info = new ArrayList<>();
        info.add(productId);
        String response = sendMessage(GET_SIMILAR_PRODUCTS, info, "");
        return (ArrayList<Product>) getObj(new TypeToken<ArrayList<Product>>(){}.getType(), response);
    }

    public static String getPersonType(String username) {
        ArrayList<String> info = new ArrayList<>();
        info.add(username);
        return sendMessage(GET_PERSON_TYPE, info, "");
    }

    public static <T> T getPersonByToken(Class<T> personType) {
        String response = sendMessage(GET_PERSON_BY_TOKEN, null, token);
        return (T) getObj(personType, response);
    }

    public static String getPersonTypeByToken() {
        return sendMessage(GET_TYPE_BY_TOKEN, null, token);
    }

    public static String isProductAvailable(String productId) {
        ArrayList<String> info = new ArrayList<>();
        info.add(productId);
        return sendMessage(IS_PRODUCT_AVAILABLE, info, "");
    }

    public static String getAveragePrice(String productId) {
        ArrayList<String> info = new ArrayList<>();
        info.add(productId);
        return sendMessage(GET_AVERAGE_PRICE, info, "");
    }

    public static String isProductBought(String productId) {
        ArrayList<String> info = new ArrayList<>();
        info.add(productId);
        return sendMessage(IS_PRODUCT_BOUGHT, info, token);
    }

    public static String sendAddComment(String productId, String comment, String title) {
        ArrayList<String> info = new ArrayList<>();
        info.add(productId);
        info.add(comment);
        info.add(title);
        return sendMessage(ADD_COMMENT, info, token);
    }

    public static String deleteProductForManager(String productId) {
        ArrayList<String> info = new ArrayList<>();
        info.add(productId);
        return sendMessage(DELETE_PRODUCT_MANAGER, info, "");
    }

    public static String deleteProductRequest(String productId) {
        ArrayList<String> info = new ArrayList<>();
        info.add(productId);
        return sendMessage(DELETE_PRODUCT_REQUEST, info, token);
    }

    public static ArrayList<Discount> getAllDiscountsOfSeller() {
        String response = sendMessage(GET_ALL_DISCOUNTS_OF_SELLER, null, token);
        return (ArrayList<Discount>) getObj(new TypeToken<ArrayList<Discount>>(){}.getType(), response);
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
        return sendMessage(REGISTER, info, "");
    }

    public static String sendLoginRequest(String username, String password) {
        ArrayList<String> info = new ArrayList<>();
        info.add(username);
        info.add(password); //todo n
        return sendMessage(LOGIN, info, "");
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
        return sendMessage(DECREASE_WALLET_BALANCE, info, token);
    }

    public static ArrayList<Product> getAllProducts() {
        String response = sendMessage(GET_ALL_PRODUCTS, null, "");
        return (ArrayList<Product>) getObj(new TypeToken<ArrayList<Product>>(){}.getType(), response);
    }

    public static ArrayList<Product> getAllInDiscountProducts() {
        String response = sendMessage(GET_ALL_PRODUCTS_IN_DISCOUNT, null, "");
        return (ArrayList<Product>) getObj(new TypeToken<ArrayList<Product>>(){}.getType(), response);    }

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

    public static String addAuctionRequest(String productId, String endTime) {
        ArrayList<String> info = new ArrayList<>();
        info.add(productId);
        info.add(endTime);
        return sendMessage(ADD_AUCTION_REQUEST, info, token);
    }

//    public static HashMap<Product> getOfferedProducts() {
//        String response = sendMessage(GET_SELLER_PRODUCTS, null, token);
//        return (ArrayList<Product>) getObj(new TypeToken<ArrayList<Product>>(){}.getType(), response);
//    }

    public static ArrayList<Product> getAllSellerProducts() {
        String response = sendMessage(GET_SELLER_PRODUCTS, null, token);
        return (ArrayList<Product>) getObj(new TypeToken<ArrayList<Product>>(){}.getType(), response);
    }

    public static ArrayList<Product> getVerifiedSellerProducts() {
        String response = sendMessage(GET_VERIFIED_PRODUCTS, null, token);
        return (ArrayList<Product>) getObj(new TypeToken<ArrayList<Product>>(){}.getType(), response);
    }

    public static ArrayList<Category> getRootCategories() {
        String response = sendMessage(GET_ROOT_CATEGORIES, null, "");
        return (ArrayList<Category>) getObj(new TypeToken<ArrayList<Category>>(){}.getType(), response);
    }

    public static String removeCategory(Category category) {
        ArrayList<String> info = new ArrayList<>();
        info.add(category.getParent().getName());
        info.add(category.getName());
        return sendMessage(REMOVE_CATEGORY, info, "");
    }

    public static ArrayList<Auction> getAllAuctions() {
        String response = sendMessage(GET_ALL_AUCTIONS, null, "");
        return (ArrayList<Auction>) getObj(new TypeToken<ArrayList<Auction>>(){}.getType(), response);
    }

    public static ArrayList<Product> getAvailableProductsForAuction() {
        String response = sendMessage(GET_AVAILABLE_AUCTION_PRODUCTS, null, token);
        return (ArrayList<Product>) getObj(new TypeToken<ArrayList<Product>>(){}.getType(), response);
    }

    public static ArrayList<SalespersonRequest> getSalespersonRequests () {
        ArrayList<String> info = new ArrayList<>();
        info.add("salesperson");
        String response = sendMessage(GET_REQUESTS_OF_TYPE, info, "");
        return (ArrayList<SalespersonRequest>) getObj(new TypeToken<ArrayList<SalespersonRequest>>(){}.getType(), response);
    }

    public static ArrayList<ProductRequest> getProductRequests () {
        ArrayList<String> info = new ArrayList<>();
        info.add("product");
        String response = sendMessage(GET_REQUESTS_OF_TYPE, info, "");
        return (ArrayList<ProductRequest>) getObj(new TypeToken<ArrayList<ProductRequest>>(){}.getType(), response);
    }

    public static ArrayList<DiscountRequest> getDiscountRequests () {
        ArrayList<String> info = new ArrayList<>();
        info.add("discount");
        String response = sendMessage(GET_REQUESTS_OF_TYPE, info, "");
        return (ArrayList<DiscountRequest>) getObj(new TypeToken<ArrayList<DiscountRequest>>(){}.getType(), response);
    }

    public static ArrayList<AuctionRequest> getAuctionRequests () {
        ArrayList<String> info = new ArrayList<>();
        info.add("auction");
        String response = sendMessage(GET_REQUESTS_OF_TYPE, info, "");
        return (ArrayList<AuctionRequest>) getObj(new TypeToken<ArrayList<AuctionRequest>>(){}.getType(), response);
    }

    public static String addCategory(ArrayList<String> info) {
        return sendMessage(ADD_CATEGORY, info, "");
    }

    public static String editCategory(ArrayList<String> info) {
        return sendMessage(EDIT_CATEGORY, info, "");
    }

    public static ArrayList<String> getParentCategories() {
        String response = sendMessage(GET_PARENT_CATEGORIES, null, "");
        return (ArrayList<String>) getObj(new TypeToken<ArrayList<String>>(){}.getType(), response);
    }

    public static String changeInfo (ArrayList<String> info) {
        return sendMessage ( CHANGE_INFO , info , token );
    }

    public static void supportChatSend ( ArrayList<String> info) {
        try {
            dataOutputStream.writeUTF(toJson(new Request(SUPPORT_CHAT_SEND, info, token)));
        } catch (IOException ioException) {
            ioException.printStackTrace ( );
        }
    }

    public static void supportChatOpen (ArrayList<String> info) {
        try {
            dataOutputStream.writeUTF(toJson(new Request(SUPPORT_CHAT_OPEN, info, token)));
        } catch (IOException ioException) {
            ioException.printStackTrace ( );
        }
    }

    public static String removeUser ( ArrayList<String> info ) {
        return sendMessage ( REMOVE_USER , info, token );
    }

    public static boolean isOnline ( ArrayList<String> info ) {
        return sendMessage ( IS_ONLINE , info , "" ).equals ( "successful" );
    }

    public static ArrayList<String> getAllOnlineSupports () {
        return (ArrayList<String>) getObj(new TypeToken<ArrayList<String>>(){}.getType(), sendMessage ( GET_ALL_ONLINE_SUPPORTS , null , "" ));
    }

    public static ArrayList<String> getAllClientsWithChats () {
        return (ArrayList<String>) getObj(new TypeToken<ArrayList<String>>(){}.getType(), sendMessage ( GET_ALL_CLIENTS_WITH_CHATS , null , token ));
    }

    public static void exit() {
        try {
            dataOutputStream.writeUTF(toJson(new Request(EXIT, null, "")));
        } catch (IOException ioException) {
            ioException.printStackTrace ( );
        }
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
