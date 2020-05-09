package model;

import java.util.ArrayList;
import java.util.HashMap;

public class DiscountCode {
    public static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<DiscountCode>();
    private String code;
    private String startTime;
    private String endTime;
    private double discountPercentage;
    private double maxDiscount; //maximum amount
    private int useCounter;

    public DiscountCode(String code, String startTime, String endTime, double discountPercentage, double maxDiscount, int useCounter, ArrayList<Customer> customerList) {
        this.code = code;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPercentage = discountPercentage;
        this.maxDiscount = maxDiscount;
        this.useCounter = useCounter;
    }

    public static ArrayList<DiscountCode> getAllDiscountCodes() {
        return allDiscountCodes;
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

    public void setCode(String code) {
        this.code = code;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setMaxDiscount(double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public void setUseCounter(int useCounter) {
        this.useCounter = useCounter;
    }
}
