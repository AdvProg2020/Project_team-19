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

public class Server {
    private static Server single_instance = null;
    private static int PORT = 4444;
    private static BlockingQueue<Connection> requests;
    private HashMap<String,String> authTokens;


    private Server() {
        try {
            requests = new LinkedBlockingQueue<>();
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        commands = new HashMap<>();
        commands.put(PacketType.LOGIN, new HandleLogin());
        commands.put(PacketType.REGISTER,new RegisterHandler());
        commands.put(PacketType.ADD_DISCOUNT_REQUEST,new AddDiscountHandler());
        commands.put(PacketType.REMOVE_DISCOUNT_REQUEST,new RemoveDiscountHandler());
        commands.put(PacketType.EDIT_DISCOUNT_REQUEST,new EditDiscountHandler());
        commands.put(PacketType.ADD_PRODUCT_FROM_STOCK,new AddStockProductHandler());
        commands.put(PacketType.ADD_NEW_PRODUCT,new AddNewProductHandler());
        commands.put(PacketType.ACCEPT_REQUEST,new AcceptRequestHandler());
        commands.put(PacketType.DECLINE_REQUEST,new DeclineRequestHandler());
        commands.put(PacketType.INCREASE_COUNT_CART,new IncreaseProductFromCart());
        commands.put(PacketType.DECREASE_COUNT_CART,new DecreaseProductFromCart());
        commands.put(PacketType.ADD_CATEGORY,new AddCategoryHandler());
        commands.put(PacketType.EDIT_CATEGORY,new EditCategoryHandler());
        commands.put(PacketType.REMOVE_CATEGORY,new RemoveCategoryHandler());
        commands.put(PacketType.GET_PERSON,new GetPersonHandler());
        commands.put(PacketType.IS_FIRST_MANAGER_REGISTERED,new IsManagerRegistered());
        commands.put(PacketType.LOG_OUT,new LogOutHandler());
        commands.put(PacketType.GET_PERSON_TYPE,new GetPersonType());
        commands.put(PacketType.GET_BANK_TOKEN, new GetBankToken());
        commands.put(PacketType.GET_BANK_BALANCE, new GetBankBalance());
        commands.put(PacketType.GET_TRANSACTION, new GetTransaction());
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
                        if (!checkToken(request.getToken()))
                            continue;
                    }
                    requests.put(new Connection(request, socket, dataOutputStream));
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Object read(java.lang.reflect.Type typeOfT, String string) { //todo oooooooooooooooo
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
                ArrayList<String> strings = connection.getRequest().getJson();
                PersonController.getInstance().login(strings.get(0), strings.get(1));
                String token = getToken(strings.get(0), strings.get(1));
                authTokens.put(token, strings.get(1));
                connection.getDataOutputStream().writeUTF(token);
            } catch (Exception e) {
                try {
                    System.out.println(e.getMessage());
                    connection.getDataOutputStream().writeUTF(e.getMessage());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    class AddDiscountHandler implements Handler{

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) read(Salesperson.class,strings.get(0));
            ArrayList<Product> products = (ArrayList) Server.read(new TypeToken<ArrayList<String>>(){}.getType(),strings.get(1));
            LocalDateTime start =  DiscountCodeController.getInstance().changeStringTDataTime(strings.get(2));
            LocalDateTime end = DiscountCodeController.getInstance().changeStringTDataTime(strings.get(3));
            RequestController.getInstance().addDiscountRequest(products,start,end,Double.parseDouble(strings.get(4)),salesperson);
        }
    }

    class RemoveDiscountHandler implements Handler{

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) read(Salesperson.class,strings.get(0));
            Discount discount = (Discount) read(Salesperson.class,strings.get(1));
            RequestController.getInstance().deleteDiscountRequest(discount,salesperson);
        }
    }

    class EditDiscountHandler implements Handler{

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) read(Salesperson.class,strings.get(0));
            Discount discount = (Discount) read(Salesperson.class,strings.get(1));
            ArrayList<String> products = (ArrayList) Server.read(new TypeToken<ArrayList<String>>(){}.getType(),strings.get(2));
            LocalDateTime start = (LocalDateTime) DiscountCodeController.getInstance().changeStringTDataTime(strings.get(3));
            LocalDateTime end = (LocalDateTime) DiscountCodeController.getInstance().changeStringTDataTime(strings.get(4));
            RequestController.getInstance().editDiscountRequest(discount,products,start,end,Double.parseDouble(strings.get(5)),salesperson);
        }
    }

    class AddNewProductHandler implements Handler{

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) read(Salesperson.class,strings.get(0));
            Product product = (Product) read(Salesperson.class,strings.get(1));
            HashMap<String, String> properties = (HashMap<String, String>) Server.read(new TypeToken<HashMap<String, String>>() {}.getType(),connection.getRequest().getJson().get(2));
            RequestController.getInstance().addProductRequest(Double.parseDouble(strings.get(3)),Integer.parseInt(strings.get(4)),salesperson,strings.get(5),strings.get(6),strings.get(7),properties,strings.get(8),strings.get(9));
        }
    }

    class AddStockProductHandler implements Handler{

        @Override
        public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) read(Salesperson.class,strings.get(0));
            Product product = (Product) read(Salesperson.class,strings.get(1));
            RequestController.getInstance().addProductRequest(Double.parseDouble(strings.get(2)),Integer.parseInt(strings.get(3)),salesperson,product);
        }
    }

    class RemoveProductForSellerHandler implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) read(Salesperson.class,strings.get(0));
            RequestController.getInstance().deleteProductRequest(strings.get(1),salesperson);
        }
    }

    class AcceptRequestHandler implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            model.Request request = (model.Request) read(model.Request.class,connection.getRequest().getJson().get(0));
            RequestController.getInstance().acceptRequest(request);
        }
    }

    class DeclineRequestHandler implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            model.Request request = (model.Request) read(model.Request.class,connection.getRequest().getJson().get(0));
            RequestController.getInstance().declineRequest(request);
        }
    }

    class DecreaseProductFromCart implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) read(Salesperson.class,strings.get(0));
            Product product = (Product) read(Salesperson.class,strings.get(1));
            CartController.getInstance().setProductCount(product,-1,salesperson);
        }
    }

    class IncreaseProductFromCart implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Salesperson salesperson = (Salesperson) read(Salesperson.class,strings.get(0));
            Product product = (Product) read(Salesperson.class,strings.get(1));
            CartController.getInstance().setProductCount(product,+1,salesperson);
        }
    }

    class AddCategoryHandler implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Category category = (Category) read(Category.class,strings.get(0));
            HashSet<String> properties = (HashSet<String>) Server.read(new TypeToken<HashSet<String>>() {}.getType(),connection.getRequest().getJson().get(2));
            CategoryController.getInstance().addCategory(strings.get(2),category,properties);
        }
    }

    class EditCategoryHandler implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Category category = (Category) read(Category.class,strings.get(0));
            Category parentCategory = (Category) read(Category.class,strings.get(1));
            HashSet<String> properties = (HashSet<String>) Server.read(new TypeToken<HashSet<String>>() {}.getType(),connection.getRequest().getJson().get(2));
            CategoryController.getInstance().editCategory(strings.get(3),category,parentCategory,properties,(boolean)read(boolean.class,strings.get(4)));
        }
    }

    class RemoveCategoryHandler implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            ArrayList<String> strings = connection.getRequest().getJson();
            Category category = (Category) read(Category.class,strings.get(0));
            Category parentCategory = (Category) read(Category.class,strings.get(1));
            CategoryController.getInstance().removeCategory(parentCategory,category);
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

    class RegisterHandler implements Handler{

        @Override
        public void handle(Connection connection) {
            HashMap<String,String> info = (HashMap<String, String>) Server.read(new TypeToken<HashMap<String, String>>() {}.getType(),connection.getRequest().getJson().get(0));
            RegisterController.getInstance().register(info);
            String response = "";
            if (!(RegisterController.getInstance().isFirstManagerRegistered() && info.get("type").equals("manager"))) {
                 response = BankAPI.getBankResponse("create_account " +
                        info.get("first name") + " " +
                        info.get("last name") + " " +
                        info.get("username") + " " +
                        info.get("password") + " " +
                        info.get("password"));
            }
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

    class LogOutHandler implements Handler{

        @Override
        public void handle(Connection connection) {
            authTokens.remove(connection.getToken());
        }
    }

    public static void main(String[] args) {
        Database.createDatabase ();
        Database.initializeAddress ( );
        ProductController.getInstance ().initializeProducts ();
        CategoryController.getInstance().initializeRootCategories();
        PersonController.getInstance ().initializePersons ();
        ProductController.getInstance ().initializeStock ();
        RequestController.getInstance ().initializeRequests ();
        DiscountCodeController.getInstance().initializeDiscountCodes();
        Thread listen = getInstance().listenRequest();
        listen.start();
        Thread read = getInstance().readRequest();
        read.start();
    }

    public String getToken(String username, String password) throws Exception {
        if (!PersonController.getInstance().isTherePersonByUsername(username))
            throw new Exception ( "You Don't Exist. Go Make Yourself." );
        PersonController.getInstance().checkPassword(username, password);
        return UUID.randomUUID().toString();
    }

    public static boolean checkToken(String token){
        return Server.getInstance().authTokens.containsKey(token);
    }
}

class Connection {
    private Request request;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private String token;

    public Connection(Request request, Socket socket, DataOutputStream dataOutputStream) {
        this.request = request;
        this.socket = socket;
        this.dataOutputStream = dataOutputStream;
    }

    public Request getRequest() {
        return request;
    }

    public Socket getSocket() {
        return socket;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
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


