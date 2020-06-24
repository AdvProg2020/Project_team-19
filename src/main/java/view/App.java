package view;

import controller.*;
import fxmlController.AllCategoriesMenu;
import fxmlController.ProductsInCategory;
import fxmlController.ProductMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.*;

import static view.Menu.mainMenu;
import static view.Menu.userMenu;

public class App extends Application {

    public static Scene currentScene;
    public static Stage currentStage;
    public static Scene firstScene; //ToDo feilan injow mizarimesh
    private double xOffset,yOffset;

    public static void main ( String[] args ) {
        launch ( args );
    }

    @Override
    public void start ( Stage primaryStage ) throws IOException {
//        initializer();
//        PersonController.getInstance().login("solale");
//        //initialLogIn();
//        Parent parent = FXMLLoader.load(App.class.getResource("/fxml/AllCategoriesMenu.fxml"));
//
//        App.currentStage = primaryStage ;
//        currentScene = new Scene ( parent, 800, 500 );
//        primaryStage.setTitle ( "Bruh" );
//        primaryStage.setScene( currentScene );
//        primaryStage.setResizable ( false ); //felan
//        primaryStage.show();
//        //mainProducts(primaryStage);

        App.currentStage = primaryStage ;

        AnchorPane root = getFXMLLoader ("mainMenu").load ();
        root.setOnMouseClicked ( event -> System.out.println ( event.getX () + " " + event.getY () ) );

        currentScene = new Scene ( root );
        primaryStage.setTitle ( "Bruh" );
        primaryStage.setScene( currentScene );
//        primaryStage.initStyle( StageStyle.UNDECORATED);
//        primaryStage.setResizable ( false ); //felan
//        root.setOnMousePressed( event -> {
//            xOffset = event.getSceneX();
//            yOffset = event.getSceneY();
//        } );
//        root.setOnMouseDragged( event -> {
//            primaryStage.setX(event.getScreenX() - xOffset);
//            primaryStage.setY(event.getScreenY() - yOffset);
//        } );

        primaryStage.show();

        mainRun ();
    }

    public void initialLogIn(){
        CartController.getInstance().addProduct(ProductController.allProducts.get(0),ProductController.getInstance().getProductsOfProduct(ProductController.allProducts.get(0)).get(0).getSalesperson());
        CartController.getInstance().addProduct(ProductController.allProducts.get(1),ProductController.getInstance().getProductsOfProduct(ProductController.allProducts.get(1)).get(0).getSalesperson());
    }



    private void mainProducts(Stage primaryStage) throws IOException{
        initializer();
        HashSet<String> p = new HashSet<>();
        p.add("color");
        p.add("size");
        Category category = new Category("labaniat", null, p);
//        Category category1 = new Category("dodols", null, new HashSet<>());
//        Product product = new Product("name", "brand", category.getName(), new HashMap<>());
        HashMap<String ,String> property = new HashMap<>();
        property.put("color", "white");
        property.put("size", "big");
        Product product2 = new Product("panir","lighvan", category.getName(), property);
//        Product product1 = new Product("dol", "dolhub", category1.getName(), new HashMap<>());
//        category.addProduct(product);
      category.addProduct(product2);
//        category1.addProduct(product1);
       HashMap<String, String> info = new HashMap<>();
        info.put("username", "jalalii");
        info.put("color", "yellow");
       info.put("dick", "golden");
        Salesperson salesperson = new Salesperson(info);
       //ProductMenu.customer = new Customer(info);
//        Salesperson seller2 = new Salesperson(info);
//        Salesperson seller3 = new Salesperson(info);
        salesperson.addToOfferedProducts(product2, 4, 100);
//        seller2.addToOfferedProducts(product, 5, 120);
//        seller3.addToOfferedProducts(product, 7, 1000);
//        seller3.addToOfferedProducts(product2, 6 , 10);
//        seller3.addToOfferedProducts(product1, 4, 12);
//        ArrayList<Salesperson> sellers = new ArrayList<>();
//        product.setProperties(info);
//        product2.setProperties(info);
//        sellers.add(salesperson);
//        sellers.add(seller2);
//        sellers.add(seller3);
        ArrayList<Salesperson> sellers2 = new ArrayList<>();
        sellers2.add(salesperson);
//        ArrayList<Salesperson> sellers3 = new ArrayList<>();
//        sellers3.add(seller3);
//        ProductController.stock.put(product, sellers);
        ProductController.stock.put(product2, sellers2);
//        ProductController.stock.put(product1, sellers3);
//        product.setAverageScore(3.5);
        product2.setAverageScore(3.5);
        Database.saveToFile(salesperson, Database.createPath("salespersons", salesperson.getUsername()));
        Database.saveToFile(CategoryController.rootCategories, Database.address.get("root_categories"));
        Parent parent = FXMLLoader.load(App.class.getResource("/fxml/mainProductsMenu.fxml"));
        App.currentStage = primaryStage ;
        currentScene = new Scene ( parent, 800, 500 );
        primaryStage.setTitle ( "Bruh" );
        primaryStage.setScene( currentScene );
        primaryStage.setResizable ( false ); //felan
        primaryStage.show();
    }

    private void mainRun () {
        initializer ();
        App.manageDiscountCodeTimer ();
        App.manageDiscountTimer ();
        mainMenu = new MainMenu ( null );
        userMenu =  new UserMenu(mainMenu);
    }

    public static void manageDiscountCodeTimer(){
        Timer timer = new Timer();
        TimerTask task = new DiscountCodeTimer ();
        timer.schedule(task,60000);
    }

    public static void manageDiscountTimer(){
        Timer timer = new Timer();
        TimerTask task = new DiscountTimer ();
        timer.schedule(task,60000);
    }

    private void allProducts(Stage primaryStage) throws IOException {
        initializer();
        Category category = new Category("labaniat", null, new HashSet<>());
        Product product = new Product("name", "brand", category.getName(), new HashMap<>());
        Product product2 = new Product("panir","lighvan", category.getName(), new HashMap<>());
        category.addProduct(product);
        category.addProduct(product2);
        HashMap<String, String> info = new HashMap<>();
        info.put("username", "jalal");
        info.put("color", "yellow");
        info.put("dick", "golden");
        Salesperson salesperson = new Salesperson(info);
        Salesperson seller2 = new Salesperson(info);
        Salesperson seller3 = new Salesperson(info);
        salesperson.addToOfferedProducts(product, 4, 100);
        seller2.addToOfferedProducts(product, 5, 120);
        seller3.addToOfferedProducts(product, 7, 1000);
        seller3.addToOfferedProducts(product2, 6 , 10);
        ArrayList<Salesperson> sellers = new ArrayList<>();
        product.setProperties(info);
        product2.setProperties(info);
        sellers.add(salesperson);
        sellers.add(seller2);
        sellers.add(seller3);
        ArrayList<Salesperson> sellers2 = new ArrayList<>();
        sellers2.add(seller3);
        ProductController.stock.put(product, sellers);
        ProductController.stock.put(product2, sellers2);
        product.setAverageScore(3.5);
        product2.setAverageScore(2);
        ProductsInCategory apm = new ProductsInCategory(category);
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/productsInCategory.fxml"));
        loader.setController(apm);
        Parent parent1 = loader.load();

        App.currentStage = primaryStage ;
        currentScene = new Scene ( parent1, 800, 500 );
        primaryStage.setTitle ( "Bruh" );
        primaryStage.setScene( currentScene );
        primaryStage.setResizable ( false ); //felan
        primaryStage.show();
    }

    public void productMenu(Stage primaryStage) throws IOException{
        initializer();
        Product product = new Product("name", "brand", "panir", new HashMap<>());
        HashMap<String, String> info = new HashMap<>();
        info.put("username", "jalal");
        info.put("color", "yellow");
        info.put("dick", "golden");
        Salesperson salesperson = new Salesperson(info);
        Salesperson seller2 = new Salesperson(info);
        Salesperson seller3 = new Salesperson(info);
        salesperson.addToOfferedProducts(product, 4, 100);
        seller2.addToOfferedProducts(product, 5, 120);
        seller3.addToOfferedProducts(product, 7, 1000);
        ArrayList<Salesperson> sellers = new ArrayList<>();
        product.setProperties(info);
        sellers.add(salesperson);
        sellers.add(seller2);
        sellers.add(seller3);
        ProductController.stock.put(product, sellers);
        ProductMenu pM = new ProductMenu(product);
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/singleProduct.fxml"));

        loader.setController(pM);
        Parent parent1 = loader.load();

        App.currentStage = primaryStage ;
        currentScene = new Scene ( parent1, 800, 500 );
        primaryStage.setTitle ( "Bruh" );
        primaryStage.setScene( currentScene );
        primaryStage.setResizable ( false ); //felan
        primaryStage.show();
    }

    public static void initializer(){
        Database.createDatabase ();
        Database.initializeAddress ( );
        ProductController.getInstance ().initializeProducts ();
        CategoryController.getInstance().initializeRootCategories();
        PersonController.getInstance ().initializePersons ();
        ProductController.getInstance ().initializeStock ();
        RequestController.getInstance ().initializeRequests ();
    }



    public static FXMLLoader getFXMLLoader ( String fxml ) {
        return new FXMLLoader ( App.class.getResource ( "/fxml/" + fxml + ".fxml"));
    }

    public static void error (String message) {
        Alert alert = new Alert ( Alert.AlertType.ERROR );
        alert.setContentText ( message );
        alert.showAndWait ();
    }

    public static void setRoot(String fxml) {
        try {
            currentScene.setRoot ( getFXMLLoader ( fxml ).load () );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }
    public static void setRoot(FXMLLoader fxmlLoader) {
        try {
            currentScene.setRoot ( fxmlLoader.load () );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }

    public static void goBack () {
        Person person = PersonController.getInstance ().getLoggedInPerson ();
        if (person instanceof Manager ) {
            App.setRoot ( "managerMenu" );
        } else if (person instanceof Salesperson ) {
            App.setRoot ( "salespersonMenu" );
        } else {
            App.setRoot ( "customerMenu" );
        }
    }

}
