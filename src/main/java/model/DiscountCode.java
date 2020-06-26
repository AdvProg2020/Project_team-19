package model;

import controller.DiscountCodeController;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

public class DiscountCode {
    private String code;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double discountPercentage;
    private double maxDiscount; //maximum amount
    private int useCounter;

    public DiscountCode(LocalDateTime startTime, LocalDateTime endTime, double discountPercentage, double maxDiscount, int useCounter) {
        this.code = RandomStringUtils.random(8, true, true);
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPercentage = discountPercentage;
        this.maxDiscount = maxDiscount;
        this.useCounter = useCounter;
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
        for (DiscountCode discountCode : DiscountCodeController.getInstance ().getAllDiscountCodes ()) {
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

    public double getPriceAfterDiscountCode(double price) {
        if(price * (1 - discountPercentage / 100)<= maxDiscount)
            return price * (1 - discountPercentage / 100);
        return price - maxDiscount;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
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
