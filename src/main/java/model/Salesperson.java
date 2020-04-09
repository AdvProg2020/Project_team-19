package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Salesperson extends Person {
    private ArrayList<SellLog> sellLogs;
    private String company;
    private HashMap < Product, ProductState> offeredProducts;
    private ArrayList < Discount > discountProducts;

    class ProductState {
        boolean inDiscount;
        int amount;

        public ProductState(boolean inDiscount, int amount) {
            this.inDiscount = inDiscount;
            this.amount = amount;
        }

        public void setInDiscount(boolean inDiscount) {
            this.inDiscount = inDiscount;
        }

        public void setAmount(int amount){
            this.amount+=amount;
        }
    }

    public void changeProductAmount ( Product offeredProduct , int amount ) { //amount can be negative
        offeredProducts.get(offeredProduct).setAmount(amount);
    }

    public void setProductDiscountState(Product product,boolean state){
        offeredProducts.get(product).setInDiscount(state);
    }

    public boolean findProduct ( Product offeredProduct ) {
        return offeredProducts.containsKey ( offeredProduct );
    }

    public void addToOfferedProducts ( Product offeredProduct , int amount ) {
        offeredProducts.put ( offeredProduct , new ProductState(false,amount));
    }

    public void removeFromOfferedProducts ( Product offeredProduct , int amount ) {
        offeredProducts.remove ( offeredProduct );
    }

    public HashMap < Product, ProductState> getOfferedProducts () {
        return offeredProducts;
    }
}
