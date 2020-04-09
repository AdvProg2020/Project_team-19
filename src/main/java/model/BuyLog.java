package model;

import java.util.ArrayList;
import java.util.UUID;

public class BuyLog {
    private String logID;
    private String date;
    private double paymentAmount;
    private double discountCodeAmount;//by discount code
    private ArrayList < Product > tradedProductList;
    private String sellerName;
    private boolean reachedBuyer;
}
