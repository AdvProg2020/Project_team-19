package server;

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
                        requests.remove(request);
                        getInstance().commands.get(request.getRequest().getRequestType()).handle(request);
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
            try {
                String string = dataInputStream.readUTF();
                System.out.println(string);
                dataOutputStream.writeUTF("connected successful");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
                while (true) {
                    try {
                        String string = dataInputStream.readUTF();
                        System.out.println(string);
                        Request request = (Request) read(Request.class, string);

                        requests.put(new Connection(request, socket, dataOutputStream));
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }

    }

    public static <T> Object read(java.lang.reflect.Type typeOfT, String string) { //todo oooooooooooooooo
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
        synchronized public void handle(Connection connection) {
            try {
                String[] info = connection.getRequest().getJson().remove(0).split(" ");
                PersonController.getInstance().login(info[0],info[1]);
                String token = getToken(info[0],info[1]);
                authTokens.put(token,info[0]);
                connection.getDataOutputStream().writeUTF(token);
            } catch (Exception e) {
                try {
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

    class RegisterHandler implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            HashMap<String,String> info = (HashMap<String, String>) Server.read(new TypeToken<HashMap<String, String>>() {}.getType(),connection.getRequest().getJson().get(0));
            RegisterController.getInstance().register(info);
            connection.SendMessage("Registered successfully.");
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

    public static void main(String[] args) throws InterruptedException {
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

    public  void SendMessage(String msg)  {
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


