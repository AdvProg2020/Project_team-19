package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Salesperson extends Person {
    private HashMap < Product, Integer > offeredProducts;
    private ArrayList < Discount > discountProducts;

    class productState {
        boolean inDiscount;
        int amount;
        // move outside functions to here
    }

    public void changeProductAmount ( Product offeredProduct , int amount ) { //amount can be negative
        offeredProducts.put ( offeredProduct , offeredProducts.get ( offeredProduct ) + amount );
    }

    public boolean findProduct ( Product offeredProduct ) {
        return offeredProducts.containsKey ( offeredProduct );
    }

    public void addToOfferedProducts ( Product offeredProduct , int amount ) {
        offeredProducts.put ( offeredProduct , amount );
    }

    public void removeFromOfferedProducts ( Product offeredProduct , int amount ) {
        offeredProducts.remove ( offeredProduct );
    }

    public HashMap < Product, Integer > getOfferedProducts () {
        return offeredProducts;
    }
}
