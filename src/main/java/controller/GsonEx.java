package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GsonEx {

        private static ArrayList < String > allUserNames;
        private static String address;

        public static < T > ArrayList < T > read ( Type typeOfT ) throws FileNotFoundException {
            GsonBuilder builder = new GsonBuilder( ).setPrettyPrinting ( );
            Gson gson = builder.create ( );
            BufferedReader bufferedReader = new BufferedReader ( new FileReader ( address ) );
            return gson.fromJson ( bufferedReader , typeOfT );
        }

        public static < T > void write ( T obj , Type typeOfT) throws IOException {
            GsonBuilder builder = new GsonBuilder ( );
            Gson gson = builder.create ( );
            FileWriter writer = new FileWriter ( address );
            ArrayList <T> arrayList = read (typeOfT);
            arrayList.add ( obj );
            writer.write ( gson.toJson ( arrayList ) );
            writer.close ( );
        }


        public static ArrayList < String > getAllUserNames () {


            return allUserNames;
        }
    public static String handleJsonObject(JsonReader reader,String wantedFiledName) throws IOException {
        reader.beginObject();
        String fieldName=null;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();
             if (token.equals(JsonToken.END_OBJECT)) {
                reader.endObject();
                reader.peek();
            } else {
                if (token.equals(JsonToken.NAME)) {
                    fieldName = reader.nextName();
                    reader.peek();
                }

                if (wantedFiledName.equals(fieldName)) {
                    reader.peek();
                    return reader.nextString();
                }else {
                    reader.skipValue();
                }

            }
        }
        return null;
    }

    public static  ArrayList<String> handleJsonArray(String address, String filedName) throws IOException {
        JsonReader reader = new JsonReader(new FileReader(address));
        ArrayList<String> arrayList= new ArrayList<String>();
        reader.beginArray();
        while (true) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.END_ARRAY)) {
                reader.endArray();
                break;
            } else if (token.equals(JsonToken.BEGIN_OBJECT)) {
                arrayList.add(handleJsonObject(reader,filedName));
            } else if (token.equals(JsonToken.END_OBJECT)) {
                reader.endObject();
            } else {
                reader.skipValue();
            }
        }
        return arrayList;
    }

}

