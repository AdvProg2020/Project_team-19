package server;

import bank.BankAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.*;
import model.*;

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
    private static int PORT;
    private static BlockingQueue<Connection> requests;
    private HashMap<String, String> authTokens = new HashMap<>();
    public static ArrayList<FileRequestInfo> fileRequestInfo = new ArrayList<>();
    public static HashMap<String, HashMap<String,Chat>> clientChatsHashmap = new HashMap <> (  );
    public static HashMap<String,HashMap<String,Connection>> auctionHashmap = new HashMap <> (  );



    private Server() {
        try {
            requests = new LinkedBlockingQueue<>();
            serverSocket = new ServerSocket(4444);
            System.out.println("Server Started To Listen ON PORT: " + PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        commands = new HashMap<>();
        commands.put(LOGIN, new HandleLogin());
        commands.put(REGISTER, new RegisterHandler());
        commands.put(ADD_DISCOUNT_REQUEST, new AddDiscountHandler());
        commands.put(REMOVE_DISCOUNT_REQUEST, new RemoveDiscountHandler());
        commands.put(EDIT_DISCOUNT_REQUEST, new EditDiscountHandler());
        commands.put(ADD_PRODUCT_FROM_STOCK, new AddStockProductHandler());
        commands.put(ADD_NEW_PRODUCT, new AddNewProductHandler());
        commands.put(ACCEPT_REQUEST, new AcceptRequestHandler());
        commands.put(DECLINE_REQUEST, new DeclineRequestHandler());
        commands.put(INCREASE_COUNT_CART, new IncreaseProductFromCart());
        commands.put(DECREASE_COUNT_CART, new DecreaseProductFromCart());
        commands.put(ADD_CATEGORY, new AddCategoryHandler());
        commands.put(EDIT_CATEGORY, new EditCategoryHandler());
        commands.put(REMOVE_CATEGORY, new RemoveCategoryHandler());
        commands.put(GET_PERSON, new GetPersonHandler());
        commands.put(IS_FIRST_MANAGER_REGISTERED, new IsManagerRegistered());
        commands.put(LOG_OUT, new LogOutHandler());
        commands.put(GET_PERSON_TYPE, new GetPersonType());
        commands.put(GET_BANK_TOKEN, new GetBankToken());
        commands.put(GET_BANK_BALANCE, new GetBankBalance());
        commands.put(GET_TRANSACTION, new GetTransaction());
        commands.put(INCREASE_BANK_BALANCE, new IncreaseBankBalance());
        commands.put(INCREASE_WALLET_BALANCE, new IncreaseWalletBalance());
        commands.put(DECREASE_WALLET_BALANCE, new DecreaseWalletBalance());
        commands.put(GET_ALL_PRODUCTS, new GetAllProductHandler());
        commands.put(GET_ALL_PRODUCTS_IN_DISCOUNT, new GetAllProductsInDiscount());
        commands.put(EDIT_PRODUCT_REQUEST, new EditProductRequest());
        commands.put(GET_CATEGORY_BY_NAME, new GetCategoryByName());
        commands.put(REMOVE_PRODUCT_FOR_SELLER, new RemoveProductForSellerHandler());
        commands.put(GET_PARENT_CATEGORIES, new GetParentCategories());
        //commands.put(GET_ALL_REQUESTS,new GetAllRequests());
        commands.put(GET_SELLER_PRODUCTS, new GetSellerProducts());
        commands.put(GET_PRODUCT_BY_ID, new GetProductById());
        commands.put(ADD_AUCTION_REQUEST, new AddAuctionRequest());
        commands.put(GET_AVAILABLE_AUCTION_PRODUCTS, new GetProductsForAuction());
        commands.put(GET_ALL_AUCTIONS, new GetAllAuctions());
        commands.put(GET_ROOT_CATEGORIES, new GetRootCategories());
        commands.put(GET_SIMILAR_PRODUCTS, new GetSimilarProducts());
        commands.put(GET_SELLERS_OF_PRODUCTS, new GetSellerOfProduct());
        commands.put(GET_VERIFIED_PRODUCTS, new GetVerifiedProducts());
        commands.put(INCREASE_SEEN, new IncreaseProductSeen());
        commands.put(INCREASE_SCORE, new IncreaseScore());
        commands.put(GET_ALL_DISCOUNTS_OF_SELLER, new GetSellerDiscounts());
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
        commands.put(GET_WALLET, new GetWallet());
        commands.put(GET_REQUESTS_OF_TYPE, new GetRequestsOfType());
        commands.put(GET_PERSON_INFO_BY_TOKEN, new GetPersonInfoByToken());
        commands.put(WALLET_PURCHASE, new WalletPurchaseHandler());
        commands.put(BANK_PURCHASE, new BankPurchaseHandler());
        commands.put(GET_CATEGORY_PRODUCTS, new GetProductsOfCategory());
        commands.put(GET_IN_DISCOUNT_CATEGORY_PRODUCTS, new GetInDiscountProductsOfCategory());
        commands.put(GET_NODE_CATEGORIES, new GetNodeCategories());
        commands.put(ADD_TO_CART, new AddToCartHandler());
        commands.put(GET_CART, new GetCartHandler());
        commands.put(OFFER_PRICE_FOR_AUCTION, new OfferPriceForAuctionHandler());
        commands.put(SEND_AUCTION_MESSAGE, new SendAuctionMessage());
        commands.put(AUCTION_PURCHASE, new AuctionPurchase());
        commands.put(GET_COUNT_IN_CART, new GetContInCart());
        commands.put(SET_COUNT_IN_CART, new SetCountInCart());
        commands.put(ADD_FILE_REQUEST, new AddFileHandler());
        commands.put(ASK_FOR_FILE_REQUEST, new CheckSellerFileRequest());
        commands.put(BUY_FILE_REQUEST, new BuyFileRequestHandler());
        commands.put(GET_ALL_FILES, new GetAllFiles());
        commands.put(GET_FILE_PATH, new GetFilePath());
        commands.put(FILE_WALLET_PURCHASE, new FileWalletPurchase());
        commands.put(FILE_BANK_PURCHASE, new FileBankPurchase());
        commands.put (SUPPORT_CHAT_OPEN , new SupportChatOpen () );
        commands.put (SUPPORT_CHAT_SEND , new SupportChatSend () );
        commands.put (CHANGE_INFO,new ChangeInfo() );
        commands.put (GET_ALL_PERSON_INFO, new GetAllPersonInfo() );
        commands.put (REMOVE_USER, new RemoveUser() );
        commands.put (IS_ONLINE, new IsOnline() );
        commands.put (GET_ALL_ONLINE_SUPPORTS, new GetAllOnlineSupports() );
        commands.put (SUPPORT_CHAT_BACK, new SupportChatBack() );
        commands.put (GET_ALL_CLIENTS_WITH_CHATS, new GetAllClientsWithChats() );
    }

    public static Server getInstance() {
        if (single_instance == null)
            single_instance = new Server();

        return single_instance;
    }

    public String getTokenByUsername(String username) {
        for (Map.Entry < String, String > stringStringEntry : authTokens.entrySet ( )) {
            if (stringStringEntry.getValue ().equals ( username ))
                return stringStringEntry.getKey ();
        }
        return "";
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
                    if (request.getRequestType () == EXIT) {
                        dataOutputStream.close ();
                        dataInputStream.close ();
                        socket.close ( );
                        return;
                    }
                    if (request.getToken().length() > 0){
                        if (!checkToken(request.getToken())) {
                            dataOutputStream.writeUTF("invalid token.");
                            continue;
                        }
                    }
                    Connection connection = new Connection ( request, dataOutputStream );
                    requests.put(connection);
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
                Person person = PersonController.getInstance().getPersonByUsername(strings.get(0));
                if (person != null && person.getType().equalsIgnoreCase("customer")) {
                    cart = (Cart) read(Cart.class, strings.get(2));
                }
                PersonController.getInstance().login(strings.get(0), strings.get(1), cart);
                String token = UUID.randomUUID().toString();

                authTokens.put(token, strings.get(0));
                connection.sendMessage (token);
            } catch (Exception e) {
                e.printStackTrace();
                connection.sendMessage (e.getMessage());
            }
        }
    }

    class AddDiscountHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(strings.get(0));
            ArrayList<String> productStrings = (ArrayList) Server.read(new TypeToken<ArrayList<String>>() {}.getType(), strings.get(1));
            ArrayList<Product> products = new ArrayList<>();
            for (String productId : productStrings) {
                products.add(ProductController.getInstance().getProductById(productId));
            }
            LocalDateTime start = DiscountCodeController.getInstance().changeStringTDataTime(strings.get(2));
            LocalDateTime end = DiscountCodeController.getInstance().changeStringTDataTime(strings.get(3));
            RequestController.getInstance().addDiscountRequest(products, start, end, Double.parseDouble(strings.get(4)), salesperson);
            connection.sendMessage ("successful");
        }
    }

    class RemoveDiscountHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(strings.get(0));
            Discount discount = DiscountController.getInstance().getDiscountByIdFromAll(strings.get(1));
            RequestController.getInstance().deleteDiscountRequest(discount, salesperson);
            connection.sendMessage ("successful");
        }
    }

    class EditDiscountHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(strings.get(0));
            Discount discount = DiscountController.getInstance().getDiscountByIdFromAll(strings.get(1));
            ArrayList<String> products = (ArrayList) Server.read(new TypeToken<ArrayList<String>>() {
            }.getType(), strings.get(2));
            LocalDateTime start = (LocalDateTime) DiscountCodeController.getInstance().changeStringTDataTime(strings.get(3));
            LocalDateTime end = (LocalDateTime) DiscountCodeController.getInstance().changeStringTDataTime(strings.get(4));
            RequestController.getInstance().editDiscountRequest(discount, products, start, end, Double.parseDouble(strings.get(5)), salesperson);
            connection.sendMessage ("successful");
        }
    }

    class AddNewProductHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            String username = authTokens.get(connection.getRequest().getToken());
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(username);
            HashMap<String, String> properties = (HashMap<String, String>) Server.read(new TypeToken<HashMap<String, String>>() {
            }.getType(), connection.getRequest().getJson().get(5));
            RequestController.getInstance().addProductRequest(Double.parseDouble(strings.get(0)), Integer.parseInt(strings.get(1)), salesperson, strings.get(2), strings.get(3), strings.get(4), properties, strings.get(6), strings.get(7));
            connection.sendMessage ("successful");
        }
    }

    class AddFileHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> info = connection.getRequest().getJson();
            String username = authTokens.get(connection.getRequest().getToken());
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(username);
            String fileName = info.get(0);
            String description = info.get(1);
            String price = info.get(2);
            String path = info.get(3);
            RequestController.getInstance().addFileRequest(salesperson.getUsername(), fileName, description, Double.parseDouble(price), path);
            connection.sendMessage ("successful");
        }
    }


    class AddStockProductHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            Product product = ProductController.getInstance().getProductById(strings.get(0));
            RequestController.getInstance().addProductRequest(Double.parseDouble(strings.get(1)), Integer.parseInt(strings.get(2)), salesperson, product);
            connection.sendMessage ("add prod from stock" + product.getID());
        }
    }

    class GetCategoryByName implements Handler {

        @Override
        public void handle(Connection connection) {
            connection.sendMessage (write(CategoryController.getInstance().getCategoryByName(connection.getRequest().getJson().get(0), CategoryController.rootCategories)));
        }
    }

    class EditProductRequest implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Product product = ProductController.getInstance().getProductById(strings.get(0));
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            HashMap<String, String> properties = (HashMap<String, String>) Server.read(new TypeToken<HashMap<String, String>>() {
            }.getType(), connection.getRequest().getJson().get(3));
            RequestController.getInstance().editProductRequest(strings.get(1), strings.get(2), salesperson, product.getID(), properties);
            connection.sendMessage ("successful");
        }
    }

    class RemoveProductForSellerHandler implements Handler {

        @Override
        synchronized public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(strings.get(0));
            RequestController.getInstance().deleteProductRequest(strings.get(1), salesperson);
            connection.sendMessage ("successful");
        }
    }

    class AcceptRequestHandler implements Handler {

        @Override
        synchronized public void handle(Connection connection) {
            model.Request request = RequestController.getInstance().getRequestById(connection.getRequest().getJson().get(0));
            RequestController.getInstance().acceptRequest(request);
            connection.sendMessage (request.getRequestId() + "accepted");
        }
    }

    class DeclineRequestHandler implements Handler {

        @Override
        synchronized public void handle(Connection connection) {
            model.Request request = RequestController.getInstance().getRequestById(connection.getRequest().getJson().get(0));
            RequestController.getInstance().declineRequest(request);
            connection.sendMessage (request.getRequestId() + "declined");
        }
    }

    class DecreaseProductFromCart implements Handler {

        @Override
        synchronized public void handle(Connection connection) {
            Customer customer = (Customer) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(strings.get(0));
            Product product = ProductController.getInstance().getProductById(strings.get(1));
            CartController.getInstance().setProductCount(product.getID(), -1, salesperson.getUsername(), customer);
            connection.sendMessage ("successful");
        }
    }

    class GetCartHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            Customer customer = (Customer) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            connection.sendMessage (write(customer.getCart()));
        }
    }

    class GetContInCart implements Handler {

        @Override
        public void handle(Connection connection) {
            Customer customer = (Customer) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            Product product = ProductController.getInstance().getProductById(connection.getRequest().getJson().get(0));
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(connection.getRequest().getJson().get(1));
            connection.sendMessage (String.valueOf(CartController.getInstance().getCart(customer).getProducts().get(product.getID()).get(salesperson.getUsername()).getCount()));
        }
    }

    class SetCountInCart implements Handler {

        @Override
        public void handle(Connection connection) {
            Customer customer = (Customer) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            Product product = ProductController.getInstance().getProductById(connection.getRequest().getJson().get(0));
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(connection.getRequest().getJson().get(1));
            CartController.getInstance().setProductCount(product.getID(), Integer.parseInt(connection.getRequest().getJson().get(2)), salesperson.getUsername(), customer);
            connection.sendMessage ("successful.");
        }
    }

    class PurchaseHandler implements Handler {

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

    class IncreaseProductFromCart implements Handler {

        @Override
        synchronized public void handle(Connection connection) {
            Customer customer = (Customer) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(strings.get(0));
            Product product = ProductController.getInstance().getProductById(strings.get(1));
            CartController.getInstance().setProductCount(product.getID(), +1, salesperson.getUsername(), customer);
            connection.sendMessage ("successful");
        }
    }

    class AddCategoryHandler implements Handler {

        @Override
        synchronized public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Category category = CategoryController.getInstance().getCategoryByName(strings.get(1), CategoryController.rootCategories);
            HashSet<String> properties = (HashSet<String>) Server.read(new TypeToken<HashSet<String>>() {
            }.getType(), connection.getRequest().getJson().get(2));
            CategoryController.getInstance().addCategory(strings.get(0), category, properties);
            connection.sendMessage ("successful");
        }
    }

    class EditCategoryHandler implements Handler {

        @Override
        synchronized public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Category category = CategoryController.getInstance().getCategoryByName(strings.get(1), CategoryController.rootCategories);
            HashSet<String> properties = (HashSet<String>) Server.read(new TypeToken<HashSet<String>>() {
            }.getType(), connection.getRequest().getJson().get(3));
            if (!strings.get(2).equals("root")) {
                Category parentCategory = CategoryController.getInstance().getCategoryByName(strings.get(2), CategoryController.rootCategories);
                CategoryController.getInstance().editCategory(strings.get(0), category, parentCategory, properties, false);
            } else {
                CategoryController.getInstance().editCategory(strings.get(0), category, null, properties, true);
            }
            connection.sendMessage ("successful");
        }
    }

    class RemoveCategoryHandler implements Handler {

        @Override
        synchronized public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Category category = CategoryController.getInstance().getCategoryByName(strings.get(1), CategoryController.rootCategories);
            Category parentCategory = CategoryController.getInstance().getCategoryByName(strings.get(0), CategoryController.rootCategories);
            CategoryController.getInstance().removeCategory(parentCategory, category);
            connection.sendMessage ("successful");
        }
    }

    class GetPersonHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            if (!PersonController.getInstance().isTherePersonByUsername(strings.get(0))) {
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

    static class RegisterHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            HashMap<String, String> info = (HashMap<String, String>) Server.read(new TypeToken<HashMap<String, String>>() {
            }.getType(), connection.getRequest().getJson().get(0));
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
                WalletController.getInstance().setMIN_BALANCE(Double.parseDouble(info.get("min_balance")));
                WalletController.getInstance().setWAGE(Double.parseDouble(info.get("wage")));
                if (response.matches("\\d+")) {
                    WalletController.getInstance().setSHOP_BANK_ID(response);
                    WalletController.getInstance().setShopBankUsername(info.get("username"));
                    WalletController.getInstance().setShopBankPassword(info.get("password"));
                } else
                    connection.sendMessage ("error during making shop account : " + response);
            }
            RegisterController.getInstance().register(info, response);
            connection.sendMessage ("Registered successfully.\nYour bank response : " + response);
        }
    }

    class IsManagerRegistered implements Handler {

        @Override
        public void handle(Connection connection) {
            connection.sendMessage (write(RegisterController.getInstance().isFirstManagerRegistered()));
        }
    }

    class GetPersonType implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            connection.sendMessage (PersonController.getInstance().getPersonByUsername(strings.get(0)).getType());
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
            connection.sendMessage (response);
        }
    }

    static class GetBankBalance implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> info = connection.getRequest().getJson();
            String token = info.get(0);
            String msg = "get_balance " + token;
            String response = BankAPI.getBankResponse(msg);
            connection.sendMessage (response);
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
            connection.sendMessage (response);
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
            connection.sendMessage (bankReceiptRp);
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
                    .getBankDecreaseBalanceRespond(Double.parseDouble(amount), bankToken, bankId);
            if (bankReceiptRp.matches("\\d+")) { //it means it is a receipt id
                bankReceiptRp = WalletController.getInstance().getPayResponse(bankReceiptRp);
                if (bankReceiptRp.equalsIgnoreCase("successfully paid!")) {
                    String username = authTokens.get(connection.getRequest().getToken());
                    Person person = PersonController.getInstance().getPersonByUsername(username);
                    WalletController.getInstance().increaseWalletBalance(person, Double.parseDouble(amount));
                }
            }
            connection.sendMessage (bankReceiptRp);
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
            connection.sendMessage (bankReceiptRp);
        }
    }

    class GetBankId implements Handler {

        @Override
        public void handle(Connection connection) {
            String token = connection.getRequest().getToken();
            connection.sendMessage (getBankId(token));
        }
    }

    class GetWallet implements Handler {

        @Override
        public void handle(Connection connection) {
            String username = authTokens.get(connection.getRequest().getToken());
            Person person = PersonController.getInstance().getPersonByUsername(username);
            String wallet = "";
            if (person.getType().equalsIgnoreCase("customer")) {
                wallet = write(((Customer) person).getWallet());
            } else if (person.getType().equalsIgnoreCase("salesperson")) {
                wallet = write(((Salesperson) person).getWallet());
            }
            connection.sendMessage (wallet);
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
                balance = String.valueOf(((Salesperson) person).getWallet().getBalance());
            }
            connection.sendMessage (balance);
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
            String token = connection.getRequest().getToken();
            if (!token.isEmpty ()) {
                String username = authTokens.get ( token );
                authTokens.remove ( token );
                connection.sendMessage ( username + " logout" );
            } else {
                connection.sendMessage ( "offline" );
            }
        }
    }
    static class GetAllProductHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            connection.sendMessage (write(ProductController.getAllProducts()));
        }
    }

    static class GetAllProductsInDiscount implements Handler {

        @Override
        public void handle(Connection connection) {
            connection.sendMessage (write(ProductController.getAllProductsInDiscount()));
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
                    connection.sendMessage (write(RequestController.getInstance().filterByType(SalespersonRequest.class)));
                    break;
                case "product":
                    connection.sendMessage (write(RequestController.getInstance().filterByType(ProductRequest.class)));
                    break;
                case "discount":
                    connection.sendMessage (write(RequestController.getInstance().filterByType(DiscountRequest.class)));
                    break;
                case "auction":
                    connection.sendMessage (write(RequestController.getInstance().filterByType(AuctionRequest.class)));
                    break;
                case "file":
                    connection.sendMessage (write(RequestController.getInstance().filterByType(FileRequest.class)));
                    break;
            }
        }
    }

    class GetParentCategories implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> categories = new ArrayList<>();
            CategoryController.getInstance().getParentCategories(categories, CategoryController.rootCategories);
            connection.sendMessage (write(categories));
        }
    }

    class GetProductById implements Handler {

        @Override
        public void handle(Connection connection) {
            connection.sendMessage (write(ProductController.getInstance().getProductById(connection.getRequest().getJson().get(0))));
        }
    }

    class GetSellerProducts implements Handler {

        @Override
        public void handle(Connection connection) {
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            connection.sendMessage (write(salesperson.getOfferedProducts().keySet()));
        }
    }

    class AddAuctionRequest implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            String username = authTokens.get(connection.getRequest().getToken());
            RequestController.getInstance().addAuctionRequest(username, strings.get(0), strings.get(1));
            connection.sendMessage ("successful");
        }
    }

    class GetProductsForAuction implements Handler {

        @Override
        public void handle(Connection connection) {
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            connection.sendMessage (write(AuctionController.getInstance().getSellerAvailableProductsForAuction(salesperson)));
        }
    }

    class GetAllAuctions implements Handler {

        @Override
        public void handle(Connection connection) {
            connection.sendMessage (write(AuctionController.getInstance().getAllAuctions()));
        }
    }

    class GetRootCategories implements Handler {

        @Override
        public void handle(Connection connection) {
            connection.sendMessage (write(CategoryController.rootCategories));
        }
    }

    class GetSimilarProducts implements Handler {

        @Override
        public void handle(Connection connection) {
            Product product = ProductController.getInstance().getProductById(connection.getRequest().getJson().get(0));
            connection.sendMessage (write(ProductController.getInstance().getSimilarProducts(product)));
        }
    }

    class GetSellerOfProduct implements Handler {

        @Override
        public void handle(Connection connection) {
            Product product = ProductController.getInstance().getProductById(connection.getRequest().getJson().get(0));
            connection.sendMessage (write(ProductController.getInstance().getVerifiedSellersOfProduct(product)));
        }
    }

    class GetPersonInfoByToken implements Handler {

        @Override
        public void handle(Connection connection) {
            String username = authTokens.get(connection.getRequest().getToken());
            Person person = PersonController.getInstance().getPersonByUsername(username);
            connection.sendMessage (write(person.getPersonInfo()));
        }
    }

    class GetVerifiedProducts implements Handler {

        @Override
        public void handle(Connection connection) {
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            connection.sendMessage (write(ProductController.getInstance().getSellerVerifiedProducts(salesperson)));
        }
    }

    class IncreaseProductSeen implements Handler {

        @Override
        public void handle(Connection connection) {
            Product product = ProductController.getInstance().getProductById(connection.getRequest().getJson().get(0));
            product.increaseSeen();
            connection.sendMessage ("successful");
        }
    }

    class IncreaseScore implements Handler {

        @Override
        public void handle(Connection connection) {
            Product product = ProductController.getInstance().getProductById(connection.getRequest().getJson().get(0));
            product.increaseTotalScore(Integer.parseInt(connection.getRequest().getJson().get(1)));
            connection.sendMessage ("successful");
        }
    }

    class GetSellerDiscounts implements Handler {

        @Override
        public void handle(Connection connection) {
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            connection.sendMessage (write(salesperson.getDiscounts()));
        }
    }

    class DeleteProductForManager implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> info = connection.getRequest().getJson();
            String productId = info.get(0);
            Product product = ProductController.getInstance().getProductById(productId);
            ProductController.getInstance().removeProductForManager(product);
            connection.sendMessage (" send delete prod (manager) : " + productId);
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
            connection.sendMessage (username + " send delete prod request : " + productId);
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
                connection.sendMessage ("you are not a customer :(");
                return;
            }
            ProductController.getInstance().addComment(product, (Customer) person, comment, title);
            connection.sendMessage ("your comment added");
        }
    }

    class IsProductAvailable implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> info = connection.getRequest().getJson();
            String productId = info.get(0);
            Product product = ProductController.getInstance().getProductById(productId);
            if (ProductController.getInstance().isProductAvailable(product)) {
                connection.sendMessage ("true");
            } else {
                connection.sendMessage ("false");
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
                connection.sendMessage (person.getType());
            else
                connection.sendMessage ("null");
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
                connection.sendMessage ("true");
            } else {
                connection.sendMessage ("false");
            }
        }
    }

    class GetAveragePrice implements Handler {

        @Override
        public void handle(Connection connection) {
            String productId = connection.getRequest().getJson().get(0);
            Product product = ProductController.getInstance().getProductById(productId);
            connection.sendMessage (String.valueOf(product.getAveragePrice()));
        }
    }

    class GetPersonByToken implements Handler {

        @Override
        public void handle(Connection connection) {
            String username = authTokens.get(connection.getRequest().getToken());
            Person person = PersonController.getInstance().getPersonByUsername(username);
            if (person != null) {
                connection.sendMessage (write(person));
            } else
                connection.sendMessage ("null");
        }
    }

    class WalletPurchaseHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            String username = authTokens.get(connection.getRequest().getToken());
            Person person = PersonController.getInstance().getPersonByUsername(username);
            if (person instanceof Customer) {
                try {
                    CartController.getInstance().purchaseWallet((Customer) person);
                    connection.sendMessage ("successful");
                } catch (CartController.NotEnoughCreditMoney notEnoughCreditMoney) {
                    connection.sendMessage (notEnoughCreditMoney.getMessage());
                }
            } else
                connection.sendMessage ("you should be a customer");
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
                    connection.sendMessage ("successful");
                } catch (Exception e) {
                    connection.sendMessage (e.getMessage());
                }
            } else
                connection.sendMessage ("you should be a customer");
        }
    }

    static class GetProductsOfCategory implements Handler {

        @Override
        public void handle(Connection connection) {
            String categoryName = connection.getRequest().getJson().get(0);
            Category category = CategoryController.getInstance().getCategoryByName(categoryName, CategoryController.rootCategories);
            connection.sendMessage (write(category.getProductList()));
        }
    }

    static class GetInDiscountProductsOfCategory implements Handler {

        @Override
        public void handle(Connection connection) {
            String categoryName = connection.getRequest().getJson().get(0);
            Category category = CategoryController.getInstance().getCategoryByName(categoryName, CategoryController.rootCategories);
            connection.sendMessage (write(CategoryController.getInstance().getInDiscountsProductsOfCategory(category)));
        }
    }

    static class GetNodeCategories implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<Category> leafCategories = new ArrayList<>();
            CategoryController.getInstance().getNodeCategories(leafCategories, CategoryController.rootCategories);
            connection.sendMessage (write(leafCategories));
        }
    }

    class AddToCartHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            Customer customer = null;
            String sellerName = connection.getRequest().getJson().get(0);
            String productId = connection.getRequest().getJson().get(1);
            if (connection.getRequest().getToken().length() != 0)
                customer = (Customer) PersonController.getInstance().getPersonByUsername(authTokens.get(connection.getRequest().getToken()));
            Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(sellerName);
            Product product = ProductController.getInstance().getProductById(productId);
            CartController.getInstance().addProduct(product, salesperson, customer);
        }
    }

    class OfferPriceForAuctionHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            String username = authTokens.get(connection.getRequest().getToken());
            String auctionId = connection.getRequest().getJson().get(0);
            String amount = connection.getRequest().getJson().get(1);

            Customer customer = (Customer) PersonController.getInstance().getPersonByUsername(username);
            Auction auction = AuctionController.getInstance().getAuctionById(auctionId);

            if (WalletController.getInstance().canDecreaseWalletBalance(customer, Double.parseDouble(amount))) {
                AuctionController.getInstance().addBuyer(auction, customer, Double.parseDouble(amount));

                if (auctionHashmap.get ( auctionId ) == null) {
                    auctionHashmap.put ( auctionId , new HashMap <String,Connection> ( ) {{
                        put ( username , connection );
                    }} );
                }
                else
                    auctionHashmap.get ( auctionId ).put ( username , connection );

                connection.sendMessage ("successful");
            } else {
                connection.sendMessage ("not enough money in your wallet");
            }
        }
    }

    class AuctionPurchase implements Handler {

        @Override
        public void handle(Connection connection) {
            Auction auction = AuctionController.getInstance().getAuctionById(connection.getRequest().getJson().get(0));
            String username = authTokens.get(connection.getRequest().getToken());
            if (AuctionController.getInstance().getHighestOfferPriceCustomer(auction) != null && AuctionController.getInstance().getHighestOfferPriceCustomer(auction).getUsername().equals(username)) {
                connection.sendMessage ("Yaaay! you won the auction :)");
            } else {
                connection.sendMessage (username + " won thw auction :(");
            }
            AuctionController.getInstance().purchase(auction);
        }
    }

    class SendAuctionMessage implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> info = connection.getRequest ().getJson ();
            String message = authTokens.get ( connection.getRequest ().getToken () ) + " : " + info.get ( 0 );
            String auctionID = info.get ( 1 );
            Auction auction = AuctionController.getInstance ().getAuctionById ( auctionID );
            auction.messages = auction.messages.concat ( message );
            System.out.println ( message );
            auctionHashmap.get ( auctionID ).forEach ( (k,v) -> v.sendMessage ( message ) );
            connection.sendMessage ( message );
        }
    }

    class SupportChatOpen implements Handler {

        @Override
        public void handle ( Connection connection ) {
            Chat chat;

            if (connection.getRequest ().getJson ().get ( 0 ).equals ( "customer" )) {
                String clientUsername = authTokens.get ( connection.getRequest ().getToken () );
                String supportUsername = connection.getRequest ().getJson ().get ( 1 );

                HashMap < String, Chat > supportHashmap = clientChatsHashmap.computeIfAbsent ( clientUsername , k -> new HashMap <> ( ) );

                chat = supportHashmap.computeIfAbsent ( supportUsername , k -> new Chat ( supportUsername , connection ) );

            } else {
                String clientUsername = connection.getRequest ().getJson ().get ( 1 );
                String supportUsername = authTokens.get ( connection.getRequest ().getToken () );

                HashMap < String, Chat > supportHashmap = clientChatsHashmap.get ( clientUsername );

                chat = supportHashmap.get ( supportUsername );
                chat.setSupportConnection ( connection );

            }
            connection.sendMessage ( "/open/" + chat.messages );

        }
    }

    class SupportChatSend implements Handler {

        @Override
        public void handle ( Connection connection ) {
            Chat chat;
            String message;
            String client;
            String support;
            if (connection.getRequest ().getJson ().get ( 0 ).equals ( "customer" )) {
                client = authTokens.get ( connection.getRequest ().getToken () );
                message = client + " : " + connection.getRequest ().getJson ().get ( 1 );
                support = connection.getRequest ().getJson ().get ( 2 );
                HashMap<String,Chat> supportHashmap = clientChatsHashmap.get ( client );
                chat = supportHashmap.get ( support );
                if (chat.supportConnection != null)
                    chat.supportConnection.sendMessage ( message );
            } else {
                support = authTokens.get ( connection.getRequest ().getToken () );
                message = support + " : " + connection.getRequest ().getJson ().get ( 1 );
                client = connection.getRequest ().getJson ().get ( 2 );
                HashMap<String,Chat> supportHashmap = clientChatsHashmap.get ( client );
                chat = supportHashmap.get ( support );
                chat.clientConnection.sendMessage ( message );
            }
            chat.messages = chat.messages.concat ( message );
            connection.sendMessage ( message );
        }
    }

    class ChangeInfo implements Handler {

        @Override
        public void handle ( Connection connection ) {
            String field = connection.getRequest ().getJson ().get ( 0 );
            String newValue = connection.getRequest ().getJson ().get ( 1 );
            if (field.equalsIgnoreCase("wage")) {
                WalletController.getInstance().setWAGE(Double.parseDouble(newValue));
            }
            if (field.equalsIgnoreCase("min_balance")) {
                WalletController.getInstance().setMIN_BALANCE(Double.parseDouble(newValue));
            }
            PersonController.getInstance ().getPersonByUsername ( authTokens.get ( connection.getRequest ().getToken () ) ).setField ( field , newValue );
            connection.sendMessage ( "successful" );
        }
    }

    class GetAllPersonInfo implements Handler {

        @Override
        public void handle ( Connection connection ) {
            HashMap<String,String> response = new HashMap <> (  );
            for (Person person : PersonController.allPersons) {
                response.put ( person.getUsername () , person.getType () );
            }
            connection.sendMessage (write(response));
        }
    }

    class RemoveUser implements Handler {

        @Override
        public void handle ( Connection connection ) {
            Person person = PersonController.getInstance ().getPersonByUsername ( connection.getRequest ().getJson ().get ( 0 ) );
            try {
                if (person == PersonController.getInstance ().getPersonByUsername ( authTokens.get ( connection.getRequest ().getToken () ) ))
                    throw new Exception ( "Don't Commit Suicide" );
                if (person instanceof Salesperson) {
                    CartController.getInstance ( ).removeSeller ( (Salesperson) person );
                    ProductController.getInstance ().removeSellerInStock ( (Salesperson) person );
                }
                PersonController.getInstance ().removePersonFromAllPersons ( person );
                connection.sendMessage ( "Removed Successfully" );
            } catch (Exception exception) {
                connection.sendMessage ( exception.getMessage () );
            }
        }
    }

    class IsOnline implements Handler {

        @Override
        public void handle ( Connection connection ) {
            if ( authTokens.containsValue ( connection.getRequest ().getJson ().get ( 0 ) ) )
                connection.sendMessage ( "successful" );
            else
                connection.sendMessage ( "unsuccessful" );
        }
    }

    class GetAllOnlineSupports implements Handler {

        @Override
        public void handle ( Connection connection ) {
            ArrayList<String> allOnlineSupports = new ArrayList <> ();
            for (Map.Entry < String, String > stringStringEntry : authTokens.entrySet ( )) {
                String username = stringStringEntry.getValue ();
                Person person = PersonController.getInstance ().getPersonByUsername ( username );
                if (person instanceof Support)
                    allOnlineSupports.add ( person.getUsername () );
            }
            connection.sendMessage ( write ( allOnlineSupports ) );
        }
    }

    class GetAllClientsWithChats implements Handler {

        @Override
        public void handle ( Connection connection ) {
            ArrayList<String> allClientsWithChats = new ArrayList <> ();
            String supportUsername = authTokens.get ( connection.getRequest ().getToken () );
            for (Map.Entry < String, HashMap < String, Chat > > stringHashMapEntry : clientChatsHashmap.entrySet ( )) {
                if (stringHashMapEntry.getValue ().containsKey ( supportUsername ))
                    allClientsWithChats.add ( stringHashMapEntry.getKey () );
            }
//            clientChatsHashmap.get ( supportUsername ).forEach ( (k,v) -> allClientsWithChats.add ( k ) );
            connection.sendMessage ( write ( allClientsWithChats ) );
        }
    }

    class SupportChatBack implements Handler {

        @Override
        public void handle ( Connection connection ) {
            connection.sendMessage ( "/back/" );
        }
    }

    class CheckSellerFileRequest implements Handler {
        @Override
        public void handle(Connection connection) {
            String username = authTokens.get(connection.getRequest().getToken());
            FileRequestInfo info = findRequestForSeller(username);
            if (info == null) {
                connection.sendMessage ("no request");
            } else {
                connection.sendMessage (info.port + " " + info.fileId);
            }
        }
    }

    public static FileRequestInfo findRequestForSeller(String username) {
        FileRequestInfo temp = null;
        for (FileRequestInfo requestInfo : fileRequestInfo) {
            if (requestInfo.sellerName.equals(username)) {
                temp = requestInfo;
                break;
            }
        }
        if (temp != null)
            fileRequestInfo.remove ( temp );
        return temp;
    }

    class BuyFileRequestHandler implements Handler {

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            FileRequestInfo info = new FileRequestInfo(strings.get(0), strings.get(1), strings.get(2));
            fileRequestInfo.add(info);
            connection.sendMessage ("successful");
        }
    }

    class FileWalletPurchase implements Handler {

        @Override
        public void handle(Connection connection) {
            String username = authTokens.get(connection.getRequest().getToken());
            Person person = PersonController.getInstance().getPersonByUsername(username);
            String fileId = connection.getRequest().getJson().get(0);
            FileProduct fileProduct = ProductController.getInstance().getFileById(fileId);

            if (person instanceof Customer) {
                if (WalletController.getInstance().canDecreaseWalletBalance(person, fileProduct.getPrice())) {
                    Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(fileProduct.getSellerId());
                    ProductController.getInstance().purchaseFileWallet((Customer)person, fileProduct, salesperson);
                    connection.sendMessage ("successful");
                } else {
                    connection.sendMessage ("not enough money in your wallet.");
                }
            } else {
                connection.sendMessage ("login with customer account.");
            }
        }
    }

    class FileBankPurchase implements Handler {

        @Override
        public void handle(Connection connection) {
            String username = authTokens.get(connection.getRequest().getToken());
            Person person = PersonController.getInstance().getPersonByUsername(username);
            String fileId = connection.getRequest().getJson().get(0);
            FileProduct fileProduct = ProductController.getInstance().getFileById(fileId);

            String bankUsername = connection.getRequest().getJson().get(1);
            String bankPass = connection.getRequest().getJson().get(2);

            if (person instanceof Customer) {
                if (WalletController.getInstance().moveFromCustomerToBank(fileProduct.getPrice(), bankUsername, bankPass , ((Customer)person).getWallet().getBankId()).equals("successfully paid!")) {
                    Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(fileProduct.getSellerId());
                    ProductController.getInstance().purchaseFileBank((Customer)person, fileProduct, salesperson);
                    connection.sendMessage ("successful");
                } else {
                    connection.sendMessage ("not enough money in tour account");
                }
            } else {
                connection.sendMessage ("login with customer account.");
            }

        }
    }

    static class GetAllFiles implements Handler {

        @Override
        public void handle(Connection connection) {
            connection.sendMessage (write(ProductController.allFiles));
        }
    }

    static class GetFilePath implements Handler {

        @Override
        public void handle(Connection connection) {
            String fileId = connection.getRequest().getJson().get(0);
            FileProduct fileProduct = ProductController.getInstance().getFileById(fileId);
            connection.sendMessage (fileProduct.getAddress());
        }
    }

    public static void main(String[] args) {
        mainRun();
        Thread listen = getInstance().listenRequest();
        listen.start();
        Thread read = getInstance().readRequest();
        read.start();
    }

    public static boolean checkToken(String token) {
        return Server.getInstance().authTokens.containsKey(token);
    }

    public static void initializer() {
        Database.createDatabase();
        Database.initializeAddress();
        ProductController.getInstance().initializeProducts();
        ProductController.getInstance().initializeFiles();
        CategoryController.getInstance().initializeRootCategories();
        PersonController.getInstance().initializePersons();
        AuctionController.getInstance().initializeAuctions();
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

    static class FileRequestInfo {
        String sellerName;
        String port;
        String fileId;

        public FileRequestInfo(String sellerName, String fileId, String port) {
            this.port = port;
            this.fileId = fileId;
            this.sellerName = sellerName;
        }
    }
}

class Connection {
    private final Request request;
    private final DataOutputStream dataOutputStream;

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


    public void sendMessage ( String msg) {
        try {
            dataOutputStream.writeUTF(msg);
            //dataOutputStream.flush ();
        } catch (IOException e) {
            try {
                dataOutputStream.writeUTF(e.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

class Chat {
    Connection clientConnection;
    Connection supportConnection;
    String supportUsername;
    String messages;

    public Chat ( String supportUsername , Connection clientConnection ) {
        this.clientConnection = clientConnection;
        this.supportUsername = supportUsername;
        messages = "";
    }

    public void setSupportConnection ( Connection supportConnection ) {
        this.supportConnection = supportConnection;
    }
}

interface Handler {
    void handle(Connection connection);
}