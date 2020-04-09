package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Salesperson extends Person {
    private HashMap < Product, productState > offeredProducts;
    private ArrayList < Discount > discountProducts;

    class productState {
        boolean inDiscount;
        int amount;

        public productState(boolean inDiscount, int amount) {
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
        offeredProducts.put ( offeredProduct , new productState(false,amount));
    }

    public void removeFromOfferedProducts ( Product offeredProduct , int amount ) {
        offeredProducts.remove ( offeredProduct );
    }

    public HashMap < Product, productState > getOfferedProducts () {
        return offeredProducts;
    }
}
