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
        Request request = new Request(PacketType.GET_PERSON_TYPE, info, "");
        String requestString = toJson(request);
        try {
            dataOutputStream.writeUTF(requestString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String sendGetPerson(String username) {
        ArrayList<String> info = new ArrayList<>();
        info.add(username);
        Request request = new Request(PacketType.GET_PERSON, info, "");
        String requestString = toJson(request);
        try {
            dataOutputStream.writeUTF(requestString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getIsFirstManagerRegistered() {
        Request request = new Request(PacketType.IS_FIRST_MANAGER_REGISTERED, null, "");
        String requestString = toJson(request);
        try {
            dataOutputStream.writeUTF(requestString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String sendRegisterRequest(HashMap< String, String > personInfo){
        ArrayList<String> info = new ArrayList<>();
        info.add(toJson(personInfo));
        Request request = new Request(PacketType.REGISTER, info,"");
        try {
            dataOutputStream.writeUTF(toJson(request));
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String sendLoginRequest(String username, String password) {
        ArrayList<String> info = new ArrayList<>();
        info.add(username);
        info.add(password);
        Request request = new Request(PacketType.LOGIN, info, "");
        String requestString = toJson(request);
        try {
            dataOutputStream.writeUTF(requestString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //username wrong / password wrong / token
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
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
