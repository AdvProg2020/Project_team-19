package fxmlController;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class LogForTable {
    private final String date;
    private final ArrayList < String > products;
    private final double cost;

    public LogForTable ( LocalDateTime date , ArrayList < String > products , double cost) {
        this.date = date.toString ();
        this.products = products;
        this.cost = cost;
    }

    public String getDate () {
        return date;
    }

    public String getProducts () {
        return products.toString ().substring ( 1, products.toString ().length ()-1 );
    }

    public double getCost () {
        return cost;
    }
}
