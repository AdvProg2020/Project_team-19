package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import model.*;

public class Database {
    private static ArrayList<Person> allPeople = new ArrayList<Person>();
    public static ArrayList<Request> allRequests = new ArrayList<Request>();
    private static String address;

    public static <T> ArrayList<T> read(Type typeOfT) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(address));
        return gson.fromJson(bufferedReader, typeOfT);
    }

    public static <T> void write(T obj, Type typeOfT) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter(address);
        ArrayList<T> objList = read(typeOfT);
        if (objList == null)
            objList = new ArrayList<T>();
        objList.add(obj);
        for (T t : objList) {
            writer.write(gson.toJson(t));
        }
        writer.close();
    }

    public static ArrayList<Person> getAllPeople() {
        return allPeople;
    }

    public static ArrayList<Request> getAllRequest () {
        return allRequests;
    }

    public static void setAllRequests(ArrayList<Request> allRequests) {
        Database.allRequests = allRequests;
    }

    public static void removeFromAllRequest (Request request) {
        allRequests.remove(request);
    }

    public static void setAddress(String address) {
        Database.address = address;
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

    public static ArrayList<String> handleJsonArray(String filedName) throws IOException {
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


}
