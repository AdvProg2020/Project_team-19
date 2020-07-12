package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.*;
import model.Person;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    private static Server single_instance = null;
    private static int PORT = 4444;
    private static BlockingQueue<Connection> requests;

    public enum Type{
        LOGIN
    }

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
    }

    public static Server getInstance() {
        if (single_instance == null)
            single_instance = new Server();

        return single_instance;
    }

    private ServerSocket serverSocket;
    private HashMap<PacketType, Handler> commands;

    public static String detectCommand(String request) {
        return request.split(" ")[0];
    }

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
                        requests.put(new Connection((Request) read(Request.class, string), socket, dataOutputStream));
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
                String[] info = connection.getRequest().getJson().split(" ");
                PersonController.getInstance().login(info[0],info[1]);
                connection.getDataOutputStream().writeUTF("Logged in successfully.");
            } catch (Exception e) {
                try {
                    connection.getDataOutputStream().writeUTF(e.getMessage());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    class RegisterHandler implements Handler{

        @Override
        synchronized public void handle(Connection connection) {
            HashMap<String,String> info = (HashMap<String, String>) Server.read(new TypeToken<HashMap<String, String>>() {}.getType(),connection.getRequest().getJson());
            RegisterController.getInstance().register(info);
            connection.SendMessage("Registered successfully.");
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

