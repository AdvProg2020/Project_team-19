package model;

import java.util.ArrayList;

public class SellLog {
    private String logID;
    private String date;
    private double deliveredAmount;
    private double discountAmount;//by discount
    private ArrayList< Product > tradedProductList;
    private String buyerName;
    private boolean transmitted;
}
