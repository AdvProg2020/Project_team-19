package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Customer extends Person {
    private ArrayList<BuyLog> buyLogs;
    private LinkedList < Product > cart;
    private double credit;

    public void addToCart ( Product product ) {
        cart.add ( product );
    }

    public void removeFromCart ( Product product ) {
        cart.remove ( product );
    }

    public boolean checkCredit () {
        // Comparing cart price with credit
        return false;
    }

    public void setCredit ( double credit ) {
        this.credit = credit;
    }

    public double getCredit () {
        return credit;
    }

    public void getLog () {

    }

    public void buy () {

    }

    public void updateHistory () {

    }

}
