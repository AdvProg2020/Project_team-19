package model;


import controller.Database;
import controller.ProductController;
import model.wagu.Block;
import model.wagu.Board;
import model.wagu.Table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Salesperson extends Person {
    private ArrayList<SellLog> sellLogs;
    private HashMap<Product, ProductState> offeredProducts;
    private ArrayList<Discount> discounts;
    private double credit;


    public Salesperson(HashMap<String, String> personInfo) {
        super(personInfo);
        sellLogs = new ArrayList<>();
        offeredProducts = new HashMap<>();
        discounts = new ArrayList<>();
        Database.saveToFile(this, Database.createPath("salespersons", personInfo.get("username")));
    }


    public void addSellLogAndPurchase(SellLog sellLog){
        offeredProducts.get(sellLog.getProduct()).setAmount(offeredProducts.get(sellLog.getProduct()).getAmount()-sellLog.getCount());
        setCredit(credit+sellLog.getDeliveredAmount());
        sellLogs.add(sellLog);
    }

    public double getDiscountPrice(Product product){
        return offeredProducts.get(product).getDiscount().getPriceAfterDiscount(offeredProducts.get(product).getPrice());
    }

    public void setProductState(Product product, ProductState.State state) {
        offeredProducts.get(product).setState(state);
    }

    public boolean isInDiscount(Product product){
        return offeredProducts.get(product).isInDiscount();
    }

    public boolean hasProduct(Product offeredProduct) {
        return offeredProducts.containsKey(offeredProduct);
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getCredit () {
        return credit;
    }

    public ArrayList < SellLog > getSellLogs () {
        return sellLogs;
    }

    public Discount getDiscountWithIdSpecificSalesperson (String id) {
        for (Discount discount : discounts) {
            if (discount.getDiscountID ().equals ( id ))
                return discount;
        }
        return null;
    }

    public void setProductDiscountState( Product product, Discount discount) {
        offeredProducts.get(product).setDiscount(discount);
    }

    public void addToOfferedProducts(Product offeredProduct, int amount, double price) {
        offeredProducts.put(offeredProduct, new ProductState( amount, price));
    }

    public void removeFromDiscounts(Discount discount) {
        for (Product product : discount.getProducts()) {
            offeredProducts.get(product).removeFromDiscount();
        }
        discounts.remove(discount);
    }

    public void setProductsDiscountState(Discount discount,boolean state){
        for (Product product : discount.getProducts()) {
            offeredProducts.get(product).setInDiscount(state);
        }
    }

    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }

    public void addToDiscounts(Discount discount) { //TODO check here
        discounts.add(discount);
    }

    public void removeFromOfferedProducts(Product offeredProduct) {
        if (offeredProducts.get(offeredProduct).isInDiscount()) {
            offeredProducts.get(offeredProduct).getDiscount().removeProduct(offeredProduct);
        }
        offeredProducts.remove(offeredProduct);
    }

    public HashMap<Product, ProductState> getOfferedProducts() {
        return offeredProducts;
    }

    public double getProductPrice(Product product) {
        return offeredProducts.get(product).getPrice();
    }

    public void setProductPrice(Product product, double price) {
        offeredProducts.get(product).setPrice(price);
    }

    public void setProductAmount(Product product, int amount) {
        offeredProducts.get(product).setAmount(amount);
    }

    public void editProduct(Product product, double price, int amount) {
        setProductPrice(product, price);
        setProductAmount(product, amount);
    }

    public double discountAmount(Product product){
       // return offeredProducts.get(product).discount
        return 1;
    }

    public int getProductAmount(Product product) {
        return offeredProducts.get(product).getAmount();
    }

    public String getAllProducts() {
        List <String> headersList = Arrays.asList("Product Name", "Product State");
        List < List <String>> rowsList = new ArrayList <> (  );
        offeredProducts.forEach ( (key,value) -> {
            List <String> row = new ArrayList <> ( 2 );
            row.add ( key.toString () );
            row.add ( value.getProductState ().label );
            rowsList.add ( row );
        } );
        if (offeredProducts.size () == 0)
            return "You Currently Have No Products";
        Board board = new Board (75);
        Table table = new Table (board, 75, headersList, rowsList);
        List<Integer> colAlignList = Arrays.asList(
                Block.DATA_CENTER,
                Block.DATA_CENTER);
        table.setColAlignsList(colAlignList);
        Block tableBlock = table.tableToBlocks();
        board.setInitialBlock(tableBlock);
        board.build();
        return board.getPreview();
    }
}

class ProductState {
    public enum State {
        BUILD_IN_PROGRESS ("Build In Progress"),
        EDIT_IN_PROGRESS ("Edit In Progress"),
        VERIFIED ("Verified");

        public final String label;

        State (String label) {
            this.label = label;
        }
    }

    private Discount discount;
    private boolean inDiscount;
    private int amount;
    private double price;
    private State productState;

    public ProductState(  int amount, double price) {
        this.inDiscount = false;
        this.amount = amount;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public State getProductState() {
        return productState;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
        this.inDiscount = true;
    }

    public void removeFromDiscount(){
        discount = null;
        inDiscount = false;
    }

    public void setInDiscount(boolean inDiscount) {
        this.inDiscount = inDiscount;
    }

    public boolean isInDiscount() {
        return inDiscount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setState(State productState) {
        this.productState = productState;
    }

}

