package bank;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class chert2 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 2222);
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        while (true) {
            String s = new Scanner(System.in).nextLine();
            if (s.startsWith("get_token")) {
                s = s.replace("get_token ", "");
                Request request = new Request(PacketType.TOKEN, s);
                dataOutputStream.writeUTF(BankServer.write(request));
                dataOutputStream.flush();
                System.out.println(dataInputStream.readUTF());
            } else if (s.startsWith("get_balance")) {
                s = s.replace("get_balance ", "");
                Request request = new Request(PacketType.BALANCE, s);
                dataOutputStream.writeUTF(BankServer.write(request));
                dataOutputStream.flush();
                System.out.println(dataInputStream.readUTF());
            } else if (s.startsWith("create_receipt")) {
                s = s.replace("create_receipt ", "");
                Request request = new Request(PacketType.CREATE_RECEIPT, s);
                dataOutputStream.writeUTF(BankServer.write(request));
                dataOutputStream.flush();
                System.out.println(dataInputStream.readUTF());
            } else if (s.startsWith("pay")) {
                s = s.replace("pay ", "");
                Request request = new Request(PacketType.PAY, s);
                dataOutputStream.writeUTF(BankServer.write(request));
                dataOutputStream.flush();
                System.out.println(dataInputStream.readUTF());
            } else if (s.startsWith("get_transaction")) {
                s = s.replace("get_transaction ", "");
                Request request = new Request(PacketType.TRANSACTION, s);
                dataOutputStream.writeUTF(BankServer.write(request));
                dataOutputStream.flush();
                System.out.println(dataInputStream.readUTF());
            } else if (s.equals("exit")) {
                dataOutputStream.writeUTF(s);
                dataOutputStream.flush();
                break;
            }
        }
    }
}
