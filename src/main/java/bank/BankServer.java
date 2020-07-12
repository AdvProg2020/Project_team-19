package bank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static bank.PacketType.*;

public class BankServer {
    private static BankServer single_instance = null;
    public static int port;
    private static BlockingQueue<Connection> requests;
    private static HashMap<PacketType, Handler> commands;
    private static HashMap<String, String> tokens;

    private BankServer() {
        try {
            initializer();
            createLists();
            ServerSocket serverSocket = new ServerSocket(2222);
            port = serverSocket.getLocalPort();
            System.out.println(port);
            new ServerImpl(serverSocket).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createLists() {
        tokens = new HashMap<>();
        requests = new LinkedBlockingQueue<>();
        commands = new HashMap<>();
        commands.put(BALANCE, new HandleGetBalance());
        commands.put(CREATE_ACCOUNT, new HandleCreateAccount());
        commands.put(CREATE_RECEIPT, new HandleCreateReceipt());
        commands.put(TOKEN, new HandleGetToken());
        commands.put(TRANSACTION, new HandleTransaction());
        commands.put(PAY, new HandlePay());
        //commands.put(EXIT, new HandleExit());
    }

    public static BankServer getInstance() {
        if (single_instance == null)
            single_instance = new BankServer();

        return single_instance;
    }

    private static class ServerImpl extends Thread {
        private final ServerSocket server;

        public ServerImpl(ServerSocket server) {
            this.server = server;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Bank is Waiting for Client...");
                    Socket socket = server.accept();
                    System.out.println("A client Connected to bank");
                    DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                    new ClientHandler(socket, dataInputStream, dataOutputStream).start();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket socket;
        private final DataOutputStream dataOutputStream;
        private final DataInputStream dataInputStream;

        public ClientHandler(Socket clientSocket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
            this.socket = clientSocket;
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
        }

        private void handleClient() {
            String input;
            while (true) {
                try {
                    input = dataInputStream.readUTF();
                    System.out.println(input);
                    if (input.equals(EXIT.label)) {
                        socket.close();
                        return;
                    }
                    requests.put(new Connection((Request) getObj(Request.class, input), dataOutputStream));
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void run() {
            handleClient();
        }
    }

    public Thread readRequest() {
        return new Thread(() -> {
            while (true) {
                try {
                    Connection request = requests.take();
                    commands.get(request.getRequest().getRequestType()).handle(request);
                    requests.remove(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    static class HandleGetBalance implements Handler {

        @Override
        public void handle(Connection connection) {
            String token = connection.getRequest().getRequestString();

            if (!tokens.containsKey(token)) {
                try {
                    connection.getDataOutputStream().writeUTF("invalid_token");
                    connection.getDataOutputStream().flush();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
            String username = tokens.get(token);
            String balance = String.valueOf(BankController.getInstance().getBalance(username));
            try {
                connection.getDataOutputStream().writeUTF(balance);
                connection.getDataOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class HandleCreateAccount implements Handler {

        @Override
        public void handle(Connection connection) {
            String[] info = connection.getRequest().getRequestString().split("\\s+");
            try {
                //first_name  last_name  username  password  repeatedPass
                BankController.getInstance().createAccount(info[0], info[1], info[2], info[3], info[4]);
                connection.getDataOutputStream().writeUTF("successfully created" + Arrays.toString(info));
                connection.getDataOutputStream().flush();
            } catch (BankController.BankException e) {
                try {
                    connection.getDataOutputStream().writeUTF(e.getMessage());
                    connection.getDataOutputStream().flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class HandleCreateReceipt implements Handler {

        @Override
        public void handle(Connection connection) {
            String[] info = connection.getRequest().getRequestString().split("\\s+");
            String token = info[0];
            if (!tokens.containsKey(token)) {
                try {
                    connection.getDataOutputStream().writeUTF("invalid_token");
                    connection.getDataOutputStream().flush();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }

            try {
                String receiptId = BankController.getInstance().createReceipt(info[1], info[2], info[3], info[4], info[5]);
                connection.getDataOutputStream().writeUTF(receiptId);
                connection.getDataOutputStream().flush();

            } catch (BankController.BankException e) {
                try {
                    connection.getDataOutputStream().writeUTF(e.getMessage());
                    connection.getDataOutputStream().flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    class HandleGetToken implements Handler {

        @Override
        public void handle(Connection connection) {
            String[] info = connection.getRequest().getRequestString().split("\\s+");
            try {
                String token = BankController.getInstance().getToken(info[0], info[1]);
                String username = info[0];

                deleteOldToken(username);
                tokens.put(token, username);
                checkTimer(token);

                connection.getDataOutputStream().writeUTF(token);
                connection.getDataOutputStream().flush();

            } catch (BankController.BankException e) {
                try {
                    connection.getDataOutputStream().writeUTF(e.getMessage());
                    connection.getDataOutputStream().flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class HandleTransaction implements Handler {

        @Override
        public void handle(Connection connection) {
            String[] info = connection.getRequest().getRequestString().split("\\s+");
            String token = info[0];
            String type = info[1];

            if (!tokens.containsKey(token)) {
                try {
                    connection.getDataOutputStream().writeUTF("invalid_token");
                    connection.getDataOutputStream().flush();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }

            try {
                String username = tokens.get(token);
                String transactions = BankController.getInstance().getTransaction(type, username);
                try {
                    connection.getDataOutputStream().writeUTF(transactions);
                    connection.getDataOutputStream().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (BankController.BankException e) {
                try {
                    connection.getDataOutputStream().writeUTF(e.getMessage());
                    connection.getDataOutputStream().flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    static class HandlePay implements Handler {

        @Override
        public void handle(Connection connection) {
            String receiptId = connection.getRequest().getRequestString();
            try {
                BankController.getInstance().pay(receiptId);
                try {
                    connection.getDataOutputStream().writeUTF("successfully paid!");
                    connection.getDataOutputStream().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (BankController.BankException e) {
                try {
                    connection.getDataOutputStream().writeUTF(e.getMessage());
                    connection.getDataOutputStream().flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        BankServer.getInstance();
        BankServer.getInstance().readRequest().start();
    }

    public static Object getObj(Type typeOfT, String string) {
        GsonBuilder builder = new GsonBuilder().setLenient().enableComplexMapKeySerialization();
        Gson gson = builder.create();
        return gson.fromJson(string, typeOfT);
    }

    public static <T> String write(T obj) {
        GsonBuilder builder = new GsonBuilder().enableComplexMapKeySerialization();
        Gson gson = builder.create();
        return gson.toJson(obj);
    }

    public void checkTimer(String token) {
        new Thread(() -> {
            final int[] second = {0};
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    second[0]++;
                    if (second[0] == 90) {  //3600
                        timer.cancel();
                        tokens.remove(token);
                    }
                }
            };
            timer.schedule(timerTask, new Date(), 1000);
        }).start();
    }

    public void deleteOldToken(String username) {
        for (String token : tokens.keySet()) {
            if (tokens.get(token).equals(username)) {
                tokens.remove(token);
                return;
            }
        }
    }

    public static void initializer() {
        BankDB.createDatabase();
        BankDB.initializeAddress();
        BankController.getInstance().initializeAccounts();
        BankController.getInstance().initializeReceipts();
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
}

interface Handler {
    void handle(Connection connection);
}