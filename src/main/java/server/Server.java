package server;

import bank.BankAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.*;
import model.*;
import view.App;
import view.LoginMenu;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static server.PacketType.*;

public class Server {
    private static Server single_instance = null;
    private static int PORT = 4444;
    private static BlockingQueue<Connection> requests;
    private HashMap<String, String> authTokens = new HashMap<>();


    private Server() {
        try {
            requests = new LinkedBlockingQueue<>();
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        commands = new HashMap<>();
        commands.put(LOGIN, new HandleLogin());
        commands.put(REGISTER,new RegisterHandler());
        commands.put(ADD_DISCOUNT_REQUEST,new AddDiscountHandler());
        commands.put(REMOVE_DISCOUNT_REQUEST,new RemoveDiscountHandler());
        commands.put(EDIT_DISCOUNT_REQUEST,new EditDiscountHandler());
        commands.put(ADD_PRODUCT_FROM_STOCK,new AddStockProductHandler());
        commands.put(ADD_NEW_PRODUCT,new AddNewProductHandler());
        commands.put(ACCEPT_REQUEST,new AcceptRequestHandler());
        commands.put(DECLINE_REQUEST,new DeclineRequestHandler());
        commands.put(INCREASE_COUNT_CART,new IncreaseProductFromCart());
        commands.put(DECREASE_COUNT_CART,new DecreaseProductFromCart());
        commands.put(ADD_CATEGORY,new AddCategoryHandler());
        commands.put(EDIT_CATEGORY,new EditCategoryHandler());
        commands.put(REMOVE_CATEGORY,new RemoveCategoryHandler());
        commands.put(GET_PERSON,new GetPersonHandler());
        commands.put(IS_FIRST_MANAGER_REGISTERED,new IsManagerRegistered());
        commands.put(LOG_OUT,new LogOutHandler());
        commands.put(GET_PERSON_TYPE,new GetPersonType());
        commands.put(GET_BANK_TOKEN, new GetBankToken());
        commands.put(GET_BANK_BALANCE, new GetBankBalance());
        commands.put(GET_TRANSACTION, new GetTransaction());
        commands.put(INCREASE_BANK_BALANCE, new IncreaseBankBalance());
        commands.put(INCREASE_WALLET_BALANCE, new IncreaseWalletBalance());
        commands.put(DECREASE_WALLET_BALANCE, new DecreaseWalletBalance());
        commands.put(GET_ALL_PRODUCTS,new GetAllProductHandler());
        commands.put(GET_ALL_PRODUCTS_IN_DISCOUNT, new GetAllProductsInDiscount());
        commands.put(EDIT_PRODUCT_REQUEST,new EditProductRequest());
        commands.put(GET_CATEGORY_BY_NAME,new GetCategoryByName());
        commands.put(REMOVE_PRODUCT_FOR_SELLER,new RemoveProductForSellerHandler());
        commands.put(GET_PARENT_CATEGORIES,new GetParentCategories());
        //commands.put(GET_ALL_REQUESTS,new GetAllRequests());
        commands.put(GET_SELLER_PRODUCTS,new GetSellerProducts());
        commands.put(GET_PRODUCT_BY_ID,new GetProductById());
        commands.put(ADD_AUCTION_REQUEST,new AddAuctionRequest());
        commands.put(GET_AVAILABLE_AUCTION_PRODUCTS,new GetProductsForAuction());
        commands.put(GET_ALL_AUCTIONS,new GetAllAuctions());
        commands.put(GET_ROOT_CATEGORIES,new GetRootCategories());
        commands.put(GET_SIMILAR_PRODUCTS,new GetSimilarProducts());
        commands.put(GET_SELLERS_OF_PRODUCTS,new GetSellerOfProduct());
        commands.put(GET_VERIFIED_PRODUCTS,new GetVerifiedProducts());
        commands.put(INCREASE_SEEN,new IncreaseProductSeen());
        commands.put(INCREASE_SCORE,new IncreaseScore());
        commands.put(GET_ALL_DISCOUNTS_OF_SELLER,new GetSellerDiscounts());
        commands.put(DELETE_PRODUCT_MANAGER, new DeleteProductForManager());
        commands.put(DELETE_PRODUCT_REQUEST, new DeleteProductRequest());
        commands.put(ADD_COMMENT, new AddCommentHandler());
        commands.put(IS_PRODUCT_AVAILABLE, new IsProductAvailable());
        commands.put(GET_TYPE_BY_TOKEN, new GetTypeByToken());
        commands.put(IS_PRODUCT_BOUGHT, new IsProductBought());
        commands.put(GET_AVERAGE_PRICE, new GetAveragePrice());
        commands.put(GET_PERSON_BY_TOKEN, new GetPersonByToken());
        commands.put(GET_BANK_ID, new GetBankId());
        commands.put(GET_WALLET_BALANCE, new GetWalletBalance());
        commands.put(GET_REQUESTS_OF_TYPE, new GetRequestsOfType());
        commands.put(GET_PERSON_INFO_BY_TOKEN, new GetPersonInfoByToken());
        commands.put(WALLET_PURCHASE, new WalletPurchaseHandler());
        commands.put(BANK_PURCHASE, new BankPurchaseHandler());
        commands.put(GET_CATEGORY_PRODUCTS, new GetProductsOfCategory());
        commands.put(GET_IN_DISCOUNT_CATEGORY_PRODUCTS, new GetInDiscountProductsOfCategory());
        commands.put(GET_NODE_CATEGORIES, new GetNodeCategories());
        commands.put(ADD_TO_CART, new AddToCartHandler());
        commands.put(GET_CART,new GetCartHandler());
    }

    public static Server getInstance() {
        if (single_instance == null)
            single_instance = new Server();

        return single_instance;
    }

    private ServerSocket serverSocket;
    private HashMap<PacketType, Handler> commands;

    public Thread listenRequest() {
        return new Thread(() -> {
            while (true) {
                Socket socket;
                try {
                    socket = serverSocket.accept();
                    new HandleClient(socket, new DataOutputStream(socket.getOutputStream()), new DataInputStream(socket.getInputStream())).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Thread readRequest() {
        return new Thread(() -> {
            while (true) {
                try {
                    Connection request = requests.take();
                    getInstance().commands.get(request.getRequest().getRequestType()).handle(request);
                    requests.remove(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static class HandleClient extends Thread {
        Socket socket;
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;


        public HandleClient(Socket socket, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
            this.socket = socket;
            this.dataOutputStream = dataOutputStream;
            this.dataInputStream = dataInputStream;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String string = dataInputStream.readUTF();
                    System.out.println(string);
                    Request request = (Request) read(Request.class, string);
                    if (request.getToken().length() > 0){
                        if (!checkToken(request.getToken())) {
                            dataOutputStream.writeUTF("invalid token.");
                            continue;
                        }
                    }
                    requests.put(new Connection(request, dataOutputStream));
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Object read(java.lang.reflect.Type typeOfT, String string) {
        GsonBuilder builder = new GsonBuilder().setLenient().enableComplexMapKeySerialization();
        Gson gson = builder.create();
        return gson.fromJson(string, typeOfT);
    }

    public static <T> String write(T obj) {
        GsonBuilder builder = new GsonBuilder().enableComplexMapKeySerialization();
        Gson gson = builder.create();
        return gson.toJson(obj);
    }

    class HandleLogin implements Handler {
        @Override
        public void handle(Connection connection) {
            try {
                Cart cart = null;
                ArrayList<String> strings = connection.getRequest().getJson();
                if (PersonController.getInstance().getPersonByUsername(strings.get(0)).getType().equalsIgnoreCase("customer")) {
                    cart = (Cart) read(Cart.class, strings.get(2));
                }
                PersonController.getInstance().login(strings.get(0), strings.get(1),cart);
                String token = UUID.randomUUID().toString();;
                authTokens.put(token, strings.get(0));
                connection.SendMessage(token);
            } catch (Exception e) {
                connection.SendMessage(e.getMessage());
            }
        }
    }

    class AddDiscountHandler implements Handler{

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(strings.get(0));
            ArrayList<String> productStrings = (ArrayList) Server.read(new TypeToken<ArrayList<String>>(){}.getType(),strings.get(1));
            ArrayList<Product> products = new ArrayList<>();
            for (String productId : productStrings) {
                products.add(ProductController.getInstance().getProductById(productId));
            }
            LocalDateTime start =  DiscountCodeController.getInstance().changeStringTDataTime(strings.get(2));
            LocalDateTime end = DiscountCodeController.getInstance().changeStringTDataTime(strings.get(3));
            RequestController.getInstance().addDiscountRequest(products,start,end,Double.parseDouble(strings.get(4)),salesperson);
            connection.SendMessage("successful");
        }
    }

    class RemoveDiscountHandler implements Handler{

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(strings.get(0));
            Discount discount = DiscountController.getInstance().getDiscountByIdFromAll(strings.get(1));
            RequestController.getInstance().deleteDiscountRequest(discount,salesperson);
            connection.SendMessage("successful");
        }
    }

    class EditDiscountHandler implements Handler{

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(strings.get(0));
            Discount discount = DiscountController.getInstance().getDiscountByIdFromAll(strings.get(1));
            ArrayList<String> products = (ArrayList) Server.read(new TypeToken<ArrayList<String>>(){}.getType(),strings.get(2));
            LocalDateTime start = (LocalDateTime) DiscountCodeController.getInstance().changeStringTDataTime(strings.get(3));
            LocalDateTime end = (LocalDateTime) DiscountCodeController.getInstance().changeStringTDataTime(strings.get(4));
            RequestController.getInstance().editDiscountRequest(discount,products,start,end,Double.parseDouble(strings.get(5)),salesperson);
            connection.SendMessage("successful");
        }
    }

    class AddNewProductHandler implements Handler{

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            String username = authTokens.get(connection.getRequest().getToken());
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(username);
            HashMap<String, String> properties = (HashMap<String, String>) Server.read(new TypeToken<HashMap<String, String>>() {}.getType(),connection.getRequest().getJson().get(5));
            RequestController.getInstance().addProductRequest(Double.parseDouble(strings.get(0)),Integer.parseInt(strings.get(1)),salesperson,strings.get(2),strings.get(3),strings.get(4),properties,strings.get(6),strings.get(7));
            connection.SendMessage("successful");
        }
    }

    class AddStockProductHandler implements Handler{

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            Product product = ProductController.getInstance().getProductById(strings.get(0));
            RequestController.getInstance().addProductRequest(Double.parseDouble(strings.get(1)),Integer.parseInt(strings.get(2)),salesperson,product);
            connection.SendMessage("add prod from stock" + product.getID());
        }
    }

    class GetCategoryByName implements Handler{

        @Override
        public void handle(Connection connection) {
            connection.SendMessage(write(CategoryController.getInstance().getCategoryByName(connection.getRequest().getJson().get(0),CategoryController.rootCategories)));
        }
    }

    class EditProductRequest implements Handler{

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            HashMap<String, String> properties = (HashMap<String, String>) Server.read(new TypeToken<HashMap<String, String>>() {}.getType(),connection.getRequest().getJson().get(6));
            RequestController.getInstance().editProductRequest(strings.get(0),strings.get(1),salesperson,strings.get(2),strings.get(3),strings.get(4),strings.get(5),properties,strings.get(7),strings.get(8));
            connection.SendMessage("successful");
        }
    }

    class RemoveProductForSellerHandler implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(strings.get(0));
            RequestController.getInstance().deleteProductRequest(strings.get(1),salesperson);
            connection.SendMessage("successful");
        }
    }

    class AcceptRequestHandler implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            model.Request request = RequestController.getInstance().getRequestById(connection.getRequest().getJson().get(0));
            RequestController.getInstance().acceptRequest(request);
            connection.SendMessage(request.getRequestId() + "accepted");
        }
    }

    class DeclineRequestHandler implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            model.Request request = RequestController.getInstance().getRequestById(connection.getRequest().getJson().get(0));
            RequestController.getInstance().declineRequest(request);
            connection.SendMessage(request.getRequestId() + "declined");
        }
    }

    class DecreaseProductFromCart implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            Customer customer = (Customer) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(strings.get(0));
            Product product = ProductController.getInstance().getProductById(strings.get(1));
            CartController.getInstance().setProductCount(product,-1,salesperson,customer);
            connection.SendMessage("successful");
        }
    }

    class GetCartHandler implements Handler{

        @Override
        public void handle(Connection connection) {
            Customer customer = (Customer) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            connection.SendMessage(write(customer.getCart()));
        }
    }

    class PurchaseHandler implements Handler{

        @Override
        public void handle(Connection connection) {
//            try {
//
//                CartController.getInstance().purchase(true);
//                App.setRoot("pay");
//            } catch (CartController.NoLoggedInPersonException | CartController.AccountIsNotCustomerException e) {
//                connection.SendMessage(e.getMessage());
//            }
        }
    }

    class IncreaseProductFromCart implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            Customer customer = (Customer) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(strings.get(0));
            Product product = ProductController.getInstance().getProductById(strings.get(1));
            CartController.getInstance().setProductCount(product,+1,salesperson,customer);
            connection.SendMessage("successful");
        }
    }

    class AddCategoryHandler implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Category category = CategoryController.getInstance().getCategoryByName(strings.get(1),CategoryController.rootCategories);
            HashSet<String> properties = (HashSet<String>) Server.read(new TypeToken<HashSet<String>>() {}.getType(),connection.getRequest().getJson().get(2));
            CategoryController.getInstance().addCategory(strings.get(0),category,properties);
            connection.SendMessage("successful");
        }
    }

    class EditCategoryHandler implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Category category = CategoryController.getInstance().getCategoryByName(strings.get(1),CategoryController.rootCategories);
            HashSet<String> properties = (HashSet<String>) Server.read(new TypeToken<HashSet<String>>() {}.getType(),connection.getRequest().getJson().get(3));
            if (!strings.get(2).equals("root")) {
                Category parentCategory = CategoryController.getInstance().getCategoryByName(strings.get(2),CategoryController.rootCategories);
                CategoryController.getInstance().editCategory(strings.get(0), category, parentCategory, properties, false);
            }else {
                CategoryController.getInstance().editCategory(strings.get(0), category, null, properties, true);
            }
            connection.SendMessage("successful");
        }
    }

    class RemoveCategoryHandler implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Category category = CategoryController.getInstance().getCategoryByName(strings.get(1),CategoryController.rootCategories);
            Category parentCategory = CategoryController.getInstance().getCategoryByName(strings.get(0),CategoryController.rootCategories);
            CategoryController.getInstance().removeCategory(parentCategory,category);
            connection.SendMessage("successful");
        }
    }

    class GetPersonHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            if (!PersonController.getInstance().isTherePersonByUsername(strings.get(0))){
                try {
                    connection.getDataOutputStream().writeUTF("invalid username.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    String person = write(PersonController.getInstance().getPersonByUsername(strings.get(0)));
                    connection.getDataOutputStream().writeUTF(person);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class RegisterHandler implements Handler{

        @Override
        public void handle(Connection connection) {
            HashMap<String,String> info = (HashMap<String, String>) Server.read(new TypeToken<HashMap<String, String>>() {}.getType(),connection.getRequest().getJson().get(0));
            System.out.println(info);
            String response = "";
            if ((info.get("type").equalsIgnoreCase("customer")) || (!RegisterController.getInstance().isFirstManagerRegistered() && info.get("type").equalsIgnoreCase("manager"))) {
                response = BankAPI.getBankResponse("create_account " +
                        info.get("first name") + " " +
                        info.get("last name") + " " +
                        info.get("username") + " " +
                        info.get("password") + " " +
                        info.get("password"));
            }
            if (!RegisterController.getInstance().isFirstManagerRegistered() && info.get("type").equalsIgnoreCase("manager")) {
                WalletController.getInstance().setMIN_BALANCE(Double.parseDouble(info.get(LoginMenu.PersonInfo.MIN_BALANCE.label)));
                WalletController.getInstance().setWAGE(Double.parseDouble(info.get(LoginMenu.PersonInfo.WAGE.label)));
                if (response.matches("\\d+")) {
                    WalletController.getInstance().setSHOP_BANK_ID(response);
                    WalletController.getInstance().setShopBankUsername(info.get("username"));
                    WalletController.getInstance().setShopBankPassword(info.get("password"));
                }
                else
                    connection.SendMessage("error during making shop account : " + response);
            }
            RegisterController.getInstance().register(info, response);
            connection.SendMessage("Registered successfully.\nYour bank response : " + response);
        }
    }

    class IsManagerRegistered implements Handler{

        @Override
        public void handle(Connection connection) {
            connection.SendMessage(write(RegisterController.getInstance().isFirstManagerRegistered()));
        }
    }

    class GetPersonType implements Handler{

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            connection.SendMessage(PersonController.getInstance().getPersonByUsername(strings.get(0)).getType());
        }
    }

    static class GetBankToken implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> info = connection.getRequest().getJson();
            String username = info.get(0);
            String password = info.get(1);
            String msg = "get_token " + username + " " + password;
            String response = BankAPI.getBankResponse(msg);
            connection.SendMessage(response);
        }
    }

    static class GetBankBalance implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> info = connection.getRequest().getJson();
            String token = info.get(0);
            String msg = "get_balance " + token;
            String response = BankAPI.getBankResponse(msg);
            connection.SendMessage(response);
        }
    }

    static class GetTransaction implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> info = connection.getRequest().getJson();
            String token = info.get(0);
            String type = info.get(1);
            String msg = "get_transaction " + token + " " + type;
            String response = BankAPI.getBankResponse(msg);
            connection.SendMessage(response);
        }
    }

    class IncreaseBankBalance implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> info = connection.getRequest().getJson();
            String bankToken = info.get(0);
            String amount = info.get(1);
            String bankId = getBankId(connection.getRequest().getToken());
            String bankReceiptRp = WalletController.getInstance()
                    .getBankIncreaseBalance(Double.parseDouble(amount), bankToken, bankId);
            if (bankReceiptRp.matches("\\d+")) { //it means it is a receipt id
                bankReceiptRp = WalletController.getInstance().getPayResponse(bankReceiptRp);
            }
            connection.SendMessage(bankReceiptRp);
        }
    }

    class IncreaseWalletBalance implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> info = connection.getRequest().getJson();
            String bankToken = info.get(0);
            String amount = info.get(1);
            String bankId = getBankId(connection.getRequest().getToken());
            String bankReceiptRp = WalletController.getInstance()
                    .getWalletIncreaseBalanceRespond(Double.parseDouble(amount), bankToken, bankId);
            if (bankReceiptRp.matches("\\d+")) { //it means it is a receipt id
                bankReceiptRp = WalletController.getInstance().getPayResponse(bankReceiptRp);
                if (bankReceiptRp.equalsIgnoreCase("successfully paid!")) {
                    String username = authTokens.get(connection.getRequest().getToken());
                    Person person = PersonController.getInstance().getPersonByUsername(username);
                    WalletController.getInstance().increaseWalletBalance(person, Double.parseDouble(amount));
                }
            }
            connection.SendMessage(bankReceiptRp);
        }
    }

    class DecreaseWalletBalance implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> info = connection.getRequest().getJson();
            String bankToken = info.get(0);
            String amount = info.get(1);
            String bankId = getBankId(connection.getRequest().getToken());
            String bankReceiptRp = WalletController.getInstance()
                    .getWalletDecreaseBalanceRespond(Double.parseDouble(amount), bankToken, bankId);

            String username = authTokens.get(connection.getRequest().getToken());
            Person person = PersonController.getInstance().getPersonByUsername(username);

            if (bankReceiptRp.matches("\\d+")) { //it means it is a receipt id
                if (person instanceof Salesperson || person instanceof Customer) {
                    if (WalletController.getInstance().canDecreaseWalletBalance(person, Double.parseDouble(amount))) {
                        bankReceiptRp = WalletController.getInstance().getPayResponse(bankReceiptRp);
                        if (bankReceiptRp.equalsIgnoreCase("successfully paid!")) {
                            WalletController.getInstance().decreaseWalletBalance(person, Double.parseDouble(amount));
                        }
                    } else {
                        bankReceiptRp = "not enough";
                    }
                }
            }
            connection.SendMessage(bankReceiptRp);
        }
    }

    class GetBankId implements Handler {

        @Override
        public void handle(Connection connection) {
            String token = connection.getRequest().getToken();
            connection.SendMessage(getBankId(token));
        }
    }

    class GetWalletBalance implements Handler {

        @Override
        public void handle(Connection connection) {
            String username = authTokens.get(connection.getRequest().getToken());
            Person person = PersonController.getInstance().getPersonByUsername(username);
            String balance = "";
            if (person.getType().equalsIgnoreCase("customer")) {
                balance = String.valueOf(((Customer) person).getWallet().getBalance());
            } else if (person.getType().equalsIgnoreCase("salesperson")) {
                balance = String.valueOf(((Salesperson)person).getWallet().getBalance());
            }
            connection.SendMessage(balance);
        }
    }

    private String getBankId(String shopToken) {
        String username = authTokens.get(shopToken);
        Person person = PersonController.getInstance().getPersonByUsername(username);
        String bankId = "";
        if (person.getType().equalsIgnoreCase("salesperson")) {
            bankId = ((Salesperson) person).getWallet().getBankId();
        } else if (person.getType().equalsIgnoreCase("customer")) {
            bankId = ((Customer) person).getWallet().getBankId();
        }
        return bankId;
    }

    class LogOutHandler implements Handler{

        @Override
        public void handle(Connection connection) {
            String username = authTokens.get(connection.getRequest().getToken());
            authTokens.remove(connection.getRequest().getToken());
            connection.SendMessage(username + " logout");
        }
    }

    static class GetAllProductHandler implements Handler{

        @Override
        public void handle(Connection connection) {
            connection.SendMessage(write(ProductController.getAllProducts()));
        }
    }

    static class GetAllProductsInDiscount implements Handler {

        @Override
        public void handle(Connection connection) {
            connection.SendMessage(write(ProductController.getAllProductsInDiscount()));
        }
    }

//    class GetAllRequests implements Handler{
//
//        @Override
//        public void handle(Connection connection) {
//            connection.SendMessage(write(RequestController.getAllRequests()));
//        }
//    }

    class GetRequestsOfType implements Handler {

        @Override
        public void handle(Connection connection) {
            String type = connection.getRequest().getJson().get(0);
            switch (type) {
                case "salesperson":
                    connection.SendMessage(write(RequestController.getInstance().filterByType(SalespersonRequest.class)));
                    break;
                case "product":
                    connection.SendMessage(write(RequestController.getInstance().filterByType(ProductRequest.class)));
                    break;
                case "discount":
                    connection.SendMessage(write(RequestController.getInstance().filterByType(DiscountRequest.class)));
                    break;
                case "auction":
                    connection.SendMessage(write(RequestController.getInstance().filterByType(AuctionRequest.class)));
                    break;
                case "support":
                    connection.SendMessage(write(RequestController.getInstance().filterByType(SupportRequest.class)));
                    break;
            }
        }
    }

    class GetParentCategories implements Handler{

        @Override
        public void handle(Connection connection) {
            ArrayList<String> categories = new ArrayList<>();
            CategoryController.getInstance().getParentCategories(categories,CategoryController.rootCategories);
            connection.SendMessage(write(categories));
        }
    }

    class GetProductById implements Handler{

        @Override
        public void handle(Connection connection) {
            connection.SendMessage(write(ProductController.getInstance().getProductById(connection.getRequest().getJson().get(0))));
        }
    }

    class GetSellerProducts implements Handler{

        @Override
        public void handle(Connection connection) {
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            connection.SendMessage(write(salesperson.getOfferedProducts().keySet()));
        }
    }

    class AddAuctionRequest implements Handler{

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            String username = authTokens.get(connection.getRequest().getToken());
            RequestController.getInstance().addAuctionRequest(username,strings.get(0),strings.get(1));
            connection.SendMessage("successful");
        }
    }

    class GetProductsForAuction implements Handler{

        @Override
        public void handle(Connection connection) {
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            connection.SendMessage(write(AuctionController.getInstance().getSellerAuctionProducts(salesperson)));
        }
    }

    class GetAllAuctions implements Handler{

        @Override
        public void handle(Connection connection) {
            connection.SendMessage(write(AuctionController.getInstance().getAllAuctions()));
        }
    }

    class GetRootCategories implements Handler{

        @Override
        public void handle(Connection connection) {
            connection.SendMessage(write(CategoryController.rootCategories));
        }
    }

    class GetSimilarProducts implements Handler{

        @Override
        public void handle(Connection connection) {
            Product product = ProductController.getInstance().getProductById(connection.getRequest().getJson().get(0));
            connection.SendMessage(write(ProductController.getInstance().getSimilarProducts(product)));
        }
    }

    class GetSellerOfProduct implements Handler{

        @Override
        public void handle(Connection connection) {
            Product product = ProductController.getInstance().getProductById(connection.getRequest().getJson().get(0));
            connection.SendMessage(write(ProductController.getInstance().getVerifiedSellersOfProduct(product)));
        }
    }

    class GetPersonInfoByToken implements Handler {

        @Override
        public void handle(Connection connection) {
            String username = authTokens.get(connection.getRequest().getToken());
            Person person = PersonController.getInstance().getPersonByUsername(username);
            connection.SendMessage(write(person.getPersonInfo()));
        }
    }

    class GetVerifiedProducts implements Handler{

        @Override
        public void handle(Connection connection) {
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            connection.SendMessage(write(ProductController.getInstance().getSellerVerifiedProducts(salesperson)));
        }
    }

    class IncreaseProductSeen implements Handler{

        @Override
        public void handle(Connection connection) {
            Product product = ProductController.getInstance().getProductById(connection.getRequest().getJson().get(0));
            product.increaseSeen();
            connection.SendMessage("successful");
        }
    }

    class IncreaseScore implements Handler{

        @Override
        public void handle(Connection connection) {
            Product product = ProductController.getInstance().getProductById(connection.getRequest().getJson().get(0));
            product.increaseTotalScore(Integer.parseInt(connection.getRequest().getJson().get(1)));
            connection.SendMessage("successful");
        }
    }

    class GetSellerDiscounts implements Handler{

        @Override
        public void handle(Connection connection) {
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            connection.SendMessage(write(salesperson.getDiscounts()));
        }
    }

    class DeleteProductForManager implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> info = connection.getRequest().getJson();
            String productId = info.get(0);
            Product product = ProductController.getInstance().getProductById(productId);
            ProductController.getInstance().removeProductForManager(product);
            connection.SendMessage(  " send delete prod (manager) : " + productId);
        }
    }

    class DeleteProductRequest implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> info = connection.getRequest().getJson();
            String productId = info.get(0);
            String token = connection.getRequest().getToken();
            String username = authTokens.get(token);
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(username);
            RequestController.getInstance().deleteProductRequest(productId, salesperson);
            connection.SendMessage(username + " send delete prod request : " + productId);
        }
    }

    class AddCommentHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> info = connection.getRequest().getJson();
            String productId = info.get(0);
            String comment = info.get(1);
            String title = info.get(2);
            Product product = ProductController.getInstance().getProductById(productId);
            String username = authTokens.get(connection.getRequest().getToken());
            Person person = PersonController.getInstance().getPersonByUsername(username);
            if (!person.getType().equals("customer")) {
                connection.SendMessage("you are not a customer :(");
                return;
            }
            ProductController.getInstance().addComment(product, (Customer) person, comment, title);
            connection.SendMessage("your comment added");
        }
    }

    class IsProductAvailable implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> info = connection.getRequest().getJson();
            String productId = info.get(0);
            Product product = ProductController.getInstance().getProductById(productId);
            if (ProductController.getInstance().isProductAvailable(product)) {
                connection.SendMessage("true");
            } else {
                connection.SendMessage("false");
            }
        }
    }

    class GetTypeByToken implements Handler {

        @Override
        public void handle(Connection connection) {
            String token = connection.getRequest().getToken();
            String username = authTokens.get(token);
            Person person = PersonController.getInstance().getPersonByUsername(username);
            if (person != null)
                connection.SendMessage(person.getType());
            else
                connection.SendMessage("null");
        }
    }

    class IsProductBought implements Handler {

        @Override
        public void handle(Connection connection) {
            String token = connection.getRequest().getToken();
            String username = authTokens.get(token);
            Person person = PersonController.getInstance().getPersonByUsername(username);

            String productId = connection.getRequest().getJson().get(0);
            Product product = ProductController.getInstance().getProductById(productId);
            if (person != null && person.getType().equals("customer") && ((Customer) person).isProductBought(product)) {
                connection.SendMessage("true");
            } else {
                connection.SendMessage("false");
            }
        }
    }

    class GetAveragePrice implements Handler {

        @Override
        public void handle(Connection connection) {
            String productId = connection.getRequest().getJson().get(0);
            Product product = ProductController.getInstance().getProductById(productId);
            connection.SendMessage(String.valueOf(product.getAveragePrice()));
        }
    }

    class GetPersonByToken implements Handler {

        @Override
        public void handle(Connection connection) {
            String username = authTokens.get(connection.getRequest().getToken());
            Person person = PersonController.getInstance().getPersonByUsername(username);
            if (person != null) {
                connection.SendMessage(write(person));
            } else
                connection.SendMessage("null");
        }
    }

    class WalletPurchaseHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            String username = authTokens.get(connection.getRequest().getToken());
            Person person = PersonController.getInstance().getPersonByUsername(username);
            if (person instanceof Customer) {
                try {
                    CartController.getInstance().purchaseWallet((Customer)person);
                    connection.SendMessage("successful");
                } catch (CartController.NotEnoughCreditMoney notEnoughCreditMoney) {
                    connection.SendMessage(notEnoughCreditMoney.getMessage());
                }
            } else
                connection.SendMessage("you should be a customer");
        }
    }

    class BankPurchaseHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            String username = authTokens.get(connection.getRequest().getToken());
            Person person = PersonController.getInstance().getPersonByUsername(username);
            String bankUsername = connection.getRequest().getJson().get(0);
            String bankPassword = connection.getRequest().getJson().get(1);
            if (person instanceof Customer) {
                try {
                    CartController.getInstance().purchaseBank((Customer) person, bankUsername, bankPassword);
                    connection.SendMessage("successful");
                } catch (Exception e) {
                    connection.SendMessage(e.getMessage());
                }
            } else
                connection.SendMessage("you should be a customer");
        }
    }

    static class GetProductsOfCategory implements Handler {

        @Override
        public void handle(Connection connection) {
            String categoryName = connection.getRequest().getJson().get(0);
            Category category = CategoryController.getInstance().getCategoryByName(categoryName, CategoryController.rootCategories);
            connection.SendMessage(write(category.getProductList()));
        }
    }

    static class GetInDiscountProductsOfCategory implements Handler {

        @Override
        public void handle(Connection connection) {
            String categoryName = connection.getRequest().getJson().get(0);
            Category category = CategoryController.getInstance().getCategoryByName(categoryName, CategoryController.rootCategories);
            connection.SendMessage(write(CategoryController.getInstance().getInDiscountsProductsOfCategory(category)));
        }
    }

    static class GetNodeCategories implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<Category> leafCategories = new ArrayList<>();
            CategoryController.getInstance().getNodeCategories(leafCategories, CategoryController.rootCategories);
            connection.SendMessage(write(leafCategories));
        }
    }

    class AddToCartHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            Customer customer = null;
            String sellerName = connection.getRequest().getJson().get(0);
            String productId = connection.getRequest().getJson().get(1);
            if (connection.getRequest().getToken().length()!=0)
                 customer = (Customer) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(sellerName);
            Product product = ProductController.getInstance().getProductById(productId);
            CartController.getInstance().addProduct(product,salesperson,customer);
        }
    }

    public static void main(String[] args) {
        mainRun();
        Thread listen = getInstance().listenRequest();
        listen.start();
        Thread read = getInstance().readRequest();
        read.start();
    }

    public static boolean checkToken(String token){
        return Server.getInstance().authTokens.containsKey(token);
    }

    public static void initializer() {
        Database.createDatabase();
        Database.initializeAddress();
        ProductController.getInstance().initializeProducts();
        CategoryController.getInstance().initializeRootCategories();
        PersonController.getInstance().initializePersons();
        ProductController.getInstance().initializeStock();
        RequestController.getInstance().initializeRequests();
        WalletController.getInstance().initializer();
    }

    private static void mainRun() {
        initializer();
        manageDiscountCodeTimer();
        manageDiscountTimer();
        manageAuctionTimer();
    }

    public static void manageDiscountCodeTimer() {
        Timer timer = new Timer();
        TimerTask task = new DiscountCodeTimer();
        timer.schedule(task, new Date(), 60000);
    }

    public static void manageDiscountTimer() {
        Timer timer = new Timer();
        TimerTask task = new DiscountTimer();
        timer.schedule(task, new Date(), 60000);
    }

    public static void manageAuctionTimer() {
        Timer timer = new Timer();
        TimerTask timerTask = new AuctionTimer();
        timer.schedule(timerTask, new Date(), 60000);
    }
}

class Connection {
    private Request request;
    private DataOutputStream dataOutputStream;

    public Connection(Request request, DataOutputStream dataOutputStream) {
        this.request = request;
        this.dataOutputStream = dataOutputStream;
    }

    public Request getRequest() {
        return request;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }


    public void SendMessage(String msg)  {
        try {
            dataOutputStream.writeUTF(msg);
        } catch (IOException e) {
            try {
                dataOutputStream.writeUTF(e.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

interface Handler {
    void handle(Connection connection);
}