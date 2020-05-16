package controller;


import model.Customer;
import model.DiscountCode;
import model.Person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DiscountCodeController {
    private static DiscountCodeController single_instance = null;
    private static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();

    private DiscountCodeController() {
    }

    public static DiscountCodeController getInstance() {
        if (single_instance == null)
            single_instance = new DiscountCodeController();

        return single_instance;
    }

    public ArrayList<DiscountCode> getAllDiscountCodes() {
        return allDiscountCodes;
    }

    public DiscountCode findDiscountCodeByCode(String code) {
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.getCode().equals(code))
                return discountCode;
        }
        return null;
    }

    public void removeDiscountCode(DiscountCode discountCode) {
        for (Person person : PersonController.getInstance().filterByRoll(Customer.class)) {
            Customer customer = (Customer) person;
            if (customer.findDiscountCodeByCode(discountCode.getCode()) != null) {
                customer.removeDiscountCode(discountCode);
            }
        }
    }

    public void editDiscountCode(DiscountCode discountCode, int field, String newValue) {
        switch (field) {
            case 1:
                discountCode.setStartTime(changeStringTDataTime(newValue));
                break;
            case 2:
                discountCode.setEndTime(changeStringTDataTime(newValue));
                break;
            case 3:
                discountCode.setDiscountPercentage(Double.parseDouble(newValue));
                break;
            case 4:
                discountCode.setMaxDiscount(Double.parseDouble(newValue));
                break;
            case 5:
                discountCode.setUseCounter(Integer.parseInt(newValue));
        }
    }

    public String changeDateTimeToString(LocalDateTime time) {
        LocalDateTime datetime1 = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return datetime1.format(format);
    }

    public LocalDateTime changeStringTDataTime(String string) {
        if (string != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(string, formatter);
        } else
            return null;
    }

    public boolean isThereDiscountCodeByCode(String code) {
        return findDiscountCodeByCode(code) != null;
    }

    public void checkDiscountCodeEndTime(){
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.getEndTime().isAfter(LocalDateTime.now())){
                removeDiscountCode(discountCode);
            }
        }
    }
}
