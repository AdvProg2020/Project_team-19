package bank;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class handles initiating connection to bankAPI ,sending requests to Bank server
 * and also responses from Bank server.
 */
public class BankAPI {
    public static final int PORT = 2222;
    public static final String IP = "localhost";

    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;

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

    /**
     * This method is used to send message with value
     *
     * @param msg to Bank server.
     * @throws IOException when OUT data stream been interrupted.
     */
    public static String sendMessageCreateAccount(String msg) throws IOException {
        try {
            msg = msg.replace("create_account ", "");
            Request request = new Request(PacketType.CREATE_ACCOUNT, msg);
            outputStream.writeUTF(BankServer.write(request));
            outputStream.flush();
            return inputStream.readUTF();
        } catch (IOException e) {
            throw new IOException("Exception while sending message:");
        }
    }

    public static String sendMessageGetToken(String msg) throws IOException {
        try {
            msg = msg.replace("get_token ", "");
            Request request = new Request(PacketType.TOKEN, msg);
            outputStream.writeUTF(BankServer.write(request));
            outputStream.flush();
            return inputStream.readUTF();
        } catch (IOException e) {
            throw new IOException("Exception while sending message:");
        }
    }

    public static String sendMessageGetBalance(String msg) throws IOException {
        try {
            msg = msg.replace("get_balance ", "");
            Request request = new Request(PacketType.BALANCE, msg);
            outputStream.writeUTF(BankServer.write(request));
            outputStream.flush();
            return inputStream.readUTF();
        } catch (IOException e) {
            throw new IOException("Exception while sending message:");
        }
    }

    public static String sendMessageGetTransaction(String msg) throws IOException {
        try {
            msg = msg.replace("get_transaction ", "");
            Request request = new Request(PacketType.TRANSACTION, msg);
            outputStream.writeUTF(BankServer.write(request));
            outputStream.flush();
            return inputStream.readUTF();
        } catch (IOException e) {
            throw new IOException("Exception while sending message:");
        }
    }

    public static String sendMessagePay(String msg) throws IOException {
        try {
            msg = msg.replace("pay ", "");
            Request request = new Request(PacketType.PAY, msg);
            outputStream.writeUTF(BankServer.write(request));
            outputStream.flush();
            return inputStream.readUTF();
        } catch (IOException e) {
            throw new IOException("Exception while sending message:");
        }
    }

    public static String sendMessageCreateReceipt(String msg) throws IOException {
        try {
            msg = msg.replace("create_receipt ", "");
            Request request = new Request(PacketType.CREATE_RECEIPT, msg);
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
            StartListeningOnInput();
            Scanner scanner = new Scanner(System.in);
//            while (true) {
//                SendMessage(scanner.nextLine());
//            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }


}

