package clientController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.PacketType;
import server.Request;
import server.Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerConnection {
    public static Socket socket;
    public static DataOutputStream dataOutputStream;
    public static DataInputStream dataInputStream;
    public static String token;

    public static void run() {
        try {
            socket = new Socket("localhost", 4444);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPersonType(String username) {
        ArrayList<String> info = new ArrayList<>();
        info.add(username);
        return sendMessage(PacketType.GET_PERSON_TYPE, info, "");
    }

    public static String sendGetPerson(String username) {
        ArrayList<String> info = new ArrayList<>();
        info.add(username);
        return sendMessage(PacketType.GET_PERSON, info, "");
    }

    public static String getIsFirstManagerRegistered() {
        return sendMessage(PacketType.IS_FIRST_MANAGER_REGISTERED, null, "");
    }

    public static String sendRegisterRequest(HashMap< String, String > personInfo){
        ArrayList<String> info = new ArrayList<>();
        info.add(toJson(personInfo));
        return sendMessage(PacketType.REGISTER, info, "");
    }

    public static String sendLoginRequest(String username, String password) {
        ArrayList<String> info = new ArrayList<>();
        info.add(username);
        info.add(password);
        return sendMessage(PacketType.LOGIN, info, "");
    }

    public static String getBankToken(String username, String password) {
        ArrayList<String> info = new ArrayList<>();
        info.add(username);
        info.add(password);
        return sendMessage(PacketType.GET_BANK_TOKEN, info, "");
    }

    public static String getBankBalance(String token) {
        ArrayList<String> info = new ArrayList<>();
        info.add(token);
        return sendMessage(PacketType.GET_BANK_BALANCE, info, "");
    }

    public static String getTransaction(String token, String type) {
        ArrayList<String> info = new ArrayList<>();
        info.add(token);
        info.add(type);
        return sendMessage(PacketType.GET_TRANSACTION, info, "");
    }

    private static String sendMessage(PacketType packetType, ArrayList<String> info, String token) {
        Request request = new Request(packetType, info, token);
        try {
            dataOutputStream.writeUTF(toJson(request));
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static Object getObj(java.lang.reflect.Type typeOfT, String string) {
        GsonBuilder builder = new GsonBuilder().setLenient().enableComplexMapKeySerialization();
        Gson gson = builder.create();
        return gson.fromJson(string, typeOfT);
    }

    public static <T> String toJson(T obj) {
        GsonBuilder builder = new GsonBuilder().enableComplexMapKeySerialization();
        Gson gson = builder.create();
        return gson.toJson(obj);
    }
}
