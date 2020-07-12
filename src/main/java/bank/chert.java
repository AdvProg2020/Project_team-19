package bank;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class chert {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 2222);
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        while (true) {
            String s = new Scanner(System.in).nextLine();
            if (s.startsWith("create_account")) {
                s = s.replace("create_account ", "");
                Request request = new Request(PacketType.CREATE_ACCOUNT, s);
                dataOutputStream.writeUTF(BankServer.write(request));
                dataOutputStream.flush();
                System.out.println(dataInputStream.readUTF());
            }
        }
    }
}
