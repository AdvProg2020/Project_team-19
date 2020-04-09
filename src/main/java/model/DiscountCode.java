package model;

import java.util.ArrayList;

public class DiscountCode {
    public static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<DiscountCode>();
    private String code;
    private String startTime;
    private String endTime;
    private double discountPercentage;
    private double maxDiscount; //maximum amount
    private int useCounter;
    private ArrayList<Customer> customerList;

    public DiscountCode(String code, String startTime, String endTime, double discountPercentage, double maxDiscount, int useCounter, ArrayList<Customer> customerList) {
        this.code = code;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPercentage = discountPercentage;
        this.maxDiscount = maxDiscount;
        this.useCounter = useCounter;
        this.customerList = customerList;
    }

}
