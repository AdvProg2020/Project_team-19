import controller.Database;
import controller.ProductController;
import model.Product;
import model.Salesperson;
import model.SalespersonRequest;
import org.junit.Test;
import model.wagu.*;

import java.util.*;

public class WaguTest {

    @Test
    public void tableTest() {
        List<String> headersList = Arrays.asList("Product ID", "Name", "Price Before Off", "Price After Off");
        List < List <String>> rowsList = new ArrayList <> (  );
        for (int i = 0; i < 3; i++) {
            List <String> row = new ArrayList <> ( 4 );
            row.add ( "1234" );
            row.add ( "Apple" );
            row.add ( "9532153" + " $" );
            row.add ( "4" + " $" );
            rowsList.add ( row );
        }
        Board board = new Board(75);
        Table table = new Table(board, 75, headersList, rowsList);
        List<Integer> colAlignList = Arrays.asList(
                Block.DATA_CENTER,
                Block.DATA_CENTER,
                Block.DATA_CENTER,
                Block.DATA_CENTER);
        table.setColAlignsList(colAlignList);
        Block tableBlock = table.tableToBlocks();
        board.setInitialBlock(tableBlock);
        board.build();
        String tableString = board.getPreview();
        System.out.println(tableString);
    }

    @Test
    public void digestTest() {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("color", "red");
        properties.put("size", "big");
        Product product = new Product("panir", "lighvan", "chiz", properties);
        List<String> headersList = Arrays.asList("Product ID", "Name", "Brand", "Average Score");
        List<List<String>> rowsList = new ArrayList<>();
        List<String> row = new ArrayList<>(4);
        row.add(product.getID());
        row.add(product.getName());
        row.add(product.getBrand());
        row.add(String.valueOf(product.getAverageScore()));
        rowsList.add(row);
        Board board = new Board(75);
        Table table = new Table(board, 75, headersList, rowsList);
        List<Integer> colAlignList = Arrays.asList(
                Block.DATA_CENTER,
                Block.DATA_CENTER,
                Block.DATA_CENTER,
                Block.DATA_CENTER);
        table.setColAlignsList(colAlignList);
        Block tableBlock = table.tableToBlocks();
        board.setInitialBlock(tableBlock);
        board.build();
        String tableString = board.getPreview();
        System.out.println(tableString);

    }

    @Test
    public void showProductSellers() {
        Database.initializeAddress();
        HashMap<String, String> properties = new HashMap<>();
        properties.put("color", "red");
        properties.put("size", "big");
        Product product = new Product("panir", "lighvan", "chiz", properties);
        HashMap<String, String> info = new HashMap<>();
        info.put("username", "seller");
        Salesperson se = new Salesperson(info, null);
        se.addToOfferedProducts(product, 2, 3000);
        Salesperson s2 = new Salesperson(info,null);
        s2.addToOfferedProducts(product, 5, 1000);
        ArrayList<Salesperson> sellers = new ArrayList<>();
        sellers.add(se);
        sellers.add(s2);
        List<String> headersList = Arrays.asList("Seller", "Price", "(In Discount)");
        List<List<String>> rowsList = new ArrayList<>();
        for (Salesperson seller : sellers) {
            List<String> row = new ArrayList<>(3);
            row.add(seller.getUsername());
            row.add(seller.getProductPrice(product)+ "$");
            row.add(String.valueOf(seller.isInDiscount(product) ? seller.getDiscountPrice(product) : "-"));
            rowsList.add(row);
        }
        Board board = new Board(75);
        Table table = new Table(board, 75, headersList, rowsList);
        List<Integer> colAlignList = Arrays.asList(
                Block.DATA_CENTER,
                Block.DATA_CENTER,
                Block.DATA_CENTER);
        table.setColAlignsList(colAlignList);
        Block tableBlock = table.tableToBlocks();
        board.setInitialBlock(tableBlock);
        board.build();
        String tableString = board.getPreview();
        System.out.println(tableString);
    }

    @Test
    public void compare() {
        Database.initializeAddress();
        HashMap<String, String> properties = new HashMap<>();
        properties.put("color", "red");
        properties.put("size", "big");
        Product product = new Product("panir", "lighvan", "chiz", properties);
        HashMap<String, String> info = new HashMap<>();
        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("color", "brown");
        properties1.put("sia", "small");
        Product product1 = new Product("shir", "mihan", "labaniat", properties1);
        info.put("username", "seller");
        Salesperson se = new Salesperson(info,null);
        se.addToOfferedProducts(product, 2, 3000);
        Salesperson s2 = new Salesperson(info,"");
        s2.addToOfferedProducts(product1, 5, 1000);
        s2.addToOfferedProducts(product, 2 , 500);
        ArrayList<Salesperson> seller1 = new ArrayList<>();
        seller1.add(se);
        seller1.add(s2);
        ArrayList<Salesperson> seller2 = new ArrayList<>();
        seller2.add(s2);
        ProductController.stock.put(product, seller1);
        ProductController.stock.put(product1, seller2);
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product1);
        HashSet<String> sameProperties = new HashSet<>();
        for (String s : product.getProperties().keySet()) {
            if (product1.getProperties().containsKey(s))
                sameProperties.add(s);
        }
        List<String> headersList = new ArrayList<>();
        headersList.add("ID");headersList.add("Name");headersList.add("Brand");headersList.add("Least Price");
        headersList.addAll(sameProperties);
        List<List<String>> rowsList = new ArrayList<>();
        for (Product product2 : products) {
            List<String> row = new ArrayList<>(sameProperties.size() + 4);
            row.add(product2.getID());
            row.add(product2.getName());
            row.add(product2.getBrand());
            row.add(product2.getLeastPrice() + "$");
            for (String sameProperty : sameProperties) {
                row.add(product2.getProperties().get(sameProperty));
            }
            rowsList.add(row);
        }
        Board board = new Board(75);
        Table table = new Table(board, 75, headersList, rowsList);
        List<Integer> colAlignList = new ArrayList<>();
        for (int i = 0; i < sameProperties.size() + 4; i++)
            colAlignList.add(Block.DATA_CENTER);
        table.setColAlignsList(colAlignList);
        Block tableBlock = table.tableToBlocks();
        board.setInitialBlock(tableBlock);
        board.build();
        String tableString = board.getPreview();
        System.out.println(tableString);

    }
}
