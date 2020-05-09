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

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public double getMaxDiscount() {
        return maxDiscount;
    }

    public int getUseCounter() {
        return useCounter;
    }

    public static DiscountCode findDiscountCodeByCode(String code){
        for (DiscountCode discountCode : allDiscountCodes) {
            if(discountCode.code.equals(code))
                return discountCode;
        }
        return null;
    }

    public static boolean isThereDiscountCodeByCode(String code){
        return findDiscountCodeByCode(code)!=null;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString () {
        return "Discount code : " + code + "\n" +
                "Start time : " + startTime + "\n" +
                "End time : " + endTime + "\n" +
                "Discount Percentage : " + discountPercentage + "\n" +
                "Max Discount : " + maxDiscount + "\n" +
                "Use Counter : " + useCounter;
    }
}
