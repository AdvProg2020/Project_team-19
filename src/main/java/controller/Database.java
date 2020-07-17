package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import model.Category;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {

    public static HashMap<String, String> address = new HashMap<>();

    public static void initializeAddress() {
        String databaseAddress = System.getProperty("user.dir") + File.separator + "database";
        address.put("customers", databaseAddress + File.separator + "persons" + File.separator + "customers");
        address.put("managers", databaseAddress + File.separator + "persons" + File.separator + "managers");
        address.put("salespersons", databaseAddress + File.separator + "persons" + File.separator + "salespersons");
        address.put("products", databaseAddress + File.separator + "products");
        address.put("discount_codes", databaseAddress + File.separator + "discount_codes");
        address.put("root_categories", databaseAddress + File.separator + "root_categories.json");
        address.put("product_requests", databaseAddress + File.separator + "requests" + File.separator + "product_requests");
        address.put("discount_requests", databaseAddress + File.separator + "requests" + File.separator + "discount_requests");
        address.put("salesperson_requests", databaseAddress + File.separator + "requests" + File.separator + "salesperson_requests");
        address.put("auction_requests", databaseAddress + File.separator + "requests" + File.separator + "auction_requests");
        address.put("min_balance", databaseAddress + File.separator + "min_balance.json");
        address.put("wage", databaseAddress + File.separator + "wage.json");
        address.put("shop_bankId", databaseAddress + File.separator + "shop_bankId.json");
    }

    public static <T> Object read(Type typeOfT, String address) { //todo oooooooooooooooo
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
        Database.write(object, address);
    }

    public static ArrayList<Category> handleJsonArray(String address) {
        ArrayList<Category> patterns = new ArrayList<>();
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        try {
            BufferedReader br = new BufferedReader(new FileReader(address));
            JsonElement jsonElement = jsonParser.parse(br);
            Type arrayListType = new TypeToken<ArrayList<Category>>(){}.getType();
            ArrayList<Category> temp = gson.fromJson(jsonElement, arrayListType);
            if (temp != null)
                return temp;
            return new ArrayList <> (  );

        } catch (IOException e) {
            e.printStackTrace();
        }
        return patterns;

    }

    public static String createPath(String keyPath, String name) {
        return address.get(keyPath) + File.separator + name + ".json";
    }

    public static File[] returnListOfFiles(String folderAddress) {
        File folder = new File(folderAddress);
        return folder.listFiles();
    }

    public static boolean deleteFile(String fileAddress) throws IOException {
        File file = new File(fileAddress);
        if (!file.createNewFile()) {
            return file.delete();
        } else {
            return false;
        }
    }

    public static void createDatabase() {
        createFolder(System.getProperty("user.dir") + File.separator + "database");
        createFolder(System.getProperty("user.dir") + File.separator + "database" + File.separator + "persons");
        createFolder(System.getProperty("user.dir") + File.separator + "database" + File.separator + "persons" + File.separator + "salespersons");
        createFolder(System.getProperty("user.dir") + File.separator + "database" + File.separator + "persons" + File.separator + "customers");
        createFolder(System.getProperty("user.dir") + File.separator + "database" + File.separator + "persons" + File.separator + "managers");
        createFolder(System.getProperty("user.dir") + File.separator + "database" + File.separator + "requests");
        createFolder(System.getProperty("user.dir") + File.separator + "database" + File.separator + "requests" + File.separator + "salesperson_requests");
        createFolder(System.getProperty("user.dir") + File.separator + "database" + File.separator + "requests" + File.separator + "product_requests");
        createFolder(System.getProperty("user.dir") + File.separator + "database" + File.separator + "requests" + File.separator + "discount_requests");
        createFolder(System.getProperty("user.dir") + File.separator + "database" + File.separator + "requests" + File.separator + "auction_requests");
        createFolder(System.getProperty("user.dir") + File.separator + "database" + File.separator + "discount_codes");
        createFolder(System.getProperty("user.dir") + File.separator + "database" + File.separator + "products");
        createFile(System.getProperty("user.dir") + File.separator + "database" + File.separator + "root_categories.json");
        createFile(System.getProperty("user.dir") + File.separator + "database" + File.separator + "min_balance.json");
        createFile(System.getProperty("user.dir") + File.separator + "database" + File.separator + "wage.json");
        createFile(System.getProperty("user.dir") + File.separator + "database" + File.separator + "shop_bankId.json");
    }

    public static void createFolder(String path) {
        File file = new File(path);
        file.mkdir();
    }

    public static void createFile(String path) {
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
