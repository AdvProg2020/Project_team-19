package model;

import java.util.ArrayList;

public class Discount {
    private static ArrayList<Discount> allDiscounts;//showing all discounts in view
    public enum  DiscountState{
        BUILD_IN_PROGRESS, EDIT_IN_PROGRESS, VERIFIED
    }

    private String discountID;
    private ArrayList< Product > discountProductList;
    private String startTime; //we can use "new java.util.Date()" that gives the exact time
    private String endTime;
    private double discountPercentage;

}
