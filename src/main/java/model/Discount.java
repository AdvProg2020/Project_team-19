package model;

import java.util.ArrayList;

public class Discount {
    String discountID;
    ArrayList< Product > discountProductList;
    int[] discountState; //or enum?
    String startTime; //we can use "new java.util.Date()" that gives the exact time
    String endTime;
    double discountPercentage;

}
