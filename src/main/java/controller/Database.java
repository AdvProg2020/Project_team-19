package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import model.*;

public class Database {
    public static ArrayList<Person> allPeople = new ArrayList<Person>();
    public static HashMap<String,String> address = new HashMap<String, String>();

    public static <T> Object read(Type typeOfT, String address) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(address));
        return gson.fromJson(bufferedReader, typeOfT);
    }

    public static <T> void write(T obj, Type typeOfT,String address) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter(address);
        writer.write(gson.toJson(obj));
        writer.close();
    }

    public static ArrayList<Person> getAllPeople() {
        return allPeople;
    }

    public static <T> void saveToFile(T object,String address) throws IOException {
        Database.write(object,object.getClass(),address);
    }

    public static String handleJsonObject(JsonReader reader, String wantedFieldName) throws IOException {
        reader.beginObject();
        String fieldName = null;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.END_OBJECT)) {
                reader.endObject();
            } else {
                if (token.equals(JsonToken.NAME)) {
                    fieldName = reader.nextName();
                }
                if (wantedFieldName.equals(fieldName)) {
                    reader.peek();
                    return reader.nextString();
                } else
                    reader.skipValue();
            }
        }
        return null;
    }

    public static ArrayList<String> handleJsonArray(String filedName,String address) throws IOException {
        JsonReader reader = new JsonReader(new FileReader(address));
        ArrayList<String> arrayList = new ArrayList<String>();
        reader.beginArray();
        while (true) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.END_ARRAY)) {
                reader.endArray();
                break;
            } else if (token.equals(JsonToken.BEGIN_OBJECT)) {
                arrayList.add(handleJsonObject(reader, filedName));
            } else if (token.equals(JsonToken.END_OBJECT)) {
                reader.endObject();
            } else {
                reader.skipValue();
            }
        }
        return arrayList;
    }

    public static String createPath (String keyPath, String name) {
        return address.get(keyPath) + "\\" + name;
    }

    public static File[] returnListOfFiles(String folderAddress){
        File folder = new File(folderAddress);
        return folder.listFiles();
    }

    public static void deleteFile(String fileAddress){
        File file = new File("filename.txt");
        file.delete();
    }

    public static class FileDoesNotExistsException extends Exception{
        String message;
    }

}
