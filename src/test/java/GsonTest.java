import com.google.gson.reflect.TypeToken;
import controller.Database;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class GsonTest {
    public static void main(String[] args) throws IOException {
        ArrayList <Test> tests = new ArrayList<Test>();
        tests.add(new Test("yalda", 1));
        tests.add(new Test("solale",2));
        tests.add(new Test("alireza",3));
        Database.setAddress(System.getProperty("user.dir") + "text2.json");
        Type collectionType = new TypeToken<Collection<Test>>() {}.getType();
        Database.write(tests, collectionType);
        System.out.println(Database.handleJsonArray("password"));
//        ArrayList<Test> allTests = new ArrayList<Test>();
//        allTests.add(new Test("hello", "1"));
//        allTests.add(new Test("salam", "2"));
//        Database.setAddress(System.getProperty("user.dir") + "text.json");
//
//        Type collectionType = new TypeToken<Collection<Test>>() {}.getType();
//
//        Database.write(allTests, collectionType);
//        System.out.println(allTests = Database.read(collectionType));
//        allTests.add(new Test("goodbye", "1"));
//        Database.write(allTests, new TypeToken<ArrayList<Test>>() {}.getType());
//        System.out.println(Database.read(collectionType));
    }

    static class Test {
        public String name;
        public int password;

        public Test(String name, int password) {
            this.name = name;
            this.password = password;
        }
    }
}