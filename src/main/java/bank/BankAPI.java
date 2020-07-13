package bank;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import static bank.PacketType.*;

/**
 * This class handles initiating connection to bankAPI ,sending requests to Bank server
 * and also responses from Bank server.
 */
public class BankAPI {
    public static final int PORT = 2222;
    public static final String IP = "localhost";

    private static ArrayList<PacketType> types;
    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;

    static {
        types = new ArrayList<>();
        types.add(CREATE_ACCOUNT);
        types.add(TOKEN);
        types.add(CREATE_RECEIPT);
        types.add(TRANSACTION);
        types.add(PAY);
        types.add(BALANCE);
        types.add(EXIT);
    }
    /**
     * This method is used to add initiating socket and IN/OUT data stream .
     *
     * @throws IOException when IP/PORT hasn't been set up properly.
     */
    public static void ConnectToBankServer() throws IOException {
        try {
            Socket socket = new Socket(IP, PORT);
            outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            //System.out.println(inputStream.readUTF());
        } catch (IOException e) {
            throw new IOException("Exception while initiating connection:");
        }
    }

    /**
     * This method is used to start a Thread ,listening on IN data stream.
     */
    public static void StartListeningOnInput() {
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println(inputStream.readUTF());
                } catch (IOException e) {
                    System.out.println("disconnected");
                    System.exit(0);
                }
            }
        }).start();
    }

    private static PacketType getType(String label) {
        for (PacketType type : types) {
            if (type.label.equals(label))
                return type;
        }
        return null;
    }

    /**
     * This method is used to send message with value
     *
     * @param msg to Bank server.
     * @throws IOException when OUT data stream been interrupted.
     */

    public static String sendMessage(String msg) throws IOException {
        try {
            String type = msg.split("\\s+")[0];
            msg = msg.replace(type + " ", "");
            Request request = new Request(getType(type), msg);
            outputStream.writeUTF(BankServer.write(request));
            outputStream.flush();
            return inputStream.readUTF();
        } catch (IOException e) {
            throw new IOException("Exception while sending message:");
        }
    }

    /**
     * This method is used to illustrate an example of using methods of this class.
     */
    public static void main(String[] args) {
        try {
            ConnectToBankServer();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println(sendMessage(scanner.nextLine()));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }


}

