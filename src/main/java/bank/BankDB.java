package bank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;

public class BankDB {
    public static HashMap<String, String> address = new HashMap<>();

    public static void initializeAddress() {
        String databaseAddress = System.getProperty("user.dir") + File.separator + "bankDB";
        address.put("accounts", databaseAddress + File.separator + "accounts");
        address.put("receipts", databaseAddress + File.separator + "receipts");
    }

    public static Object read(Type typeOfT, String address) {
        try {
            GsonBuilder builder = new GsonBuilder().setLenient().enableComplexMapKeySerialization();
            Gson gson = builder.create();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(address));
            Object obj = gson.fromJson(bufferedReader, typeOfT);
            bufferedReader.close();
            return obj;
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> void write(T obj, String address) {
        try {
            GsonBuilder builder = new GsonBuilder().enableComplexMapKeySerialization();
            Gson gson = builder.create();
            FileWriter writer = new FileWriter(address);
            writer.write(gson.toJson(obj));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static <T> void saveToFile(T object, String address) {
        write(object, address);
    }

    public static String createPath(String keyPath, String name) {
        return address.get(keyPath) + File.separator + name + ".json";
    }

    public static File[] returnListOfFiles(String folderAddress) {
        File folder = new File(folderAddress);
        return folder.listFiles();
    }

    public static void createDatabase() {
        createFolder(System.getProperty("user.dir") + File.separator + "bankDB");
        createFolder(System.getProperty("user.dir") + File.separator + "bankDB" + File.separator + "accounts");
        createFolder(System.getProperty("user.dir") + File.separator + "bankDB" + File.separator + "receipts");
    }

    public static void createFolder(String path) {
        File file = new File(path);
        file.mkdir();
    }
}