package controller;


import model.Customer;
import model.DiscountCode;
import model.Person;

import java.io.File;
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

    public void initializeDiscountCodes() {
        for (File file : Database.returnListOfFiles(Database.address.get("discount_codes"))) {
            allDiscountCodes.add((DiscountCode) Database.read(DiscountCode.class, file.getAbsolutePath()));
        }
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
            if (customer.isThereDiscountCodeByCode(discountCode.getCode())) {
                customer.removeDiscountCode(discountCode);
            }
        }
    }

    public void checkDiscountCodeEndTime(){
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.getEndTime().isAfter(LocalDateTime.now())){
                removeDiscountCode(discountCode);
            }
        }
    }

    public DiscountCode addNewDiscountCode(LocalDateTime start,LocalDateTime end,double percentage,double max,int useCounter,ArrayList<Person> people){ //ToDo ridin
        DiscountCode discountCode = new DiscountCode(start,end,percentage,max,useCounter);
        if(people!=null)
            for (Person person : people) {
                if(((Customer) person).getDiscountCodes().containsKey(discountCode)){
                    ((Customer) person).getDiscountCodes().put(discountCode,(((Customer) person).getDiscountCodes().get(discountCode)+useCounter));
                }else
                    ((Customer) person).getDiscountCodes().put(discountCode,useCounter);
                Database.saveToFile(person,Database.createPath("customers",person.getUsername()));
            }
        allDiscountCodes.add(discountCode);
        Database.saveToFile(discountCode, Database.createPath("discount_codes",discountCode.getCode()));
        return discountCode;
    }

    public DiscountCode addNewDiscountCodeGraphics(LocalDateTime start,LocalDateTime end,double percentage,double max,int useCounter,ArrayList<Customer> customers){
        DiscountCode discountCode = new DiscountCode(start,end,percentage,max,useCounter);
        if(customers!=null)
            for (Customer customer : customers) {
                customer.addDiscountCode ( discountCode , useCounter );
                Database.saveToFile(customer ,Database.createPath("customers",customer.getUsername()));
            }
        allDiscountCodes.add(discountCode);
        Database.saveToFile(discountCode, Database.createPath("discount_codes",discountCode.getCode()));
        return discountCode;
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
            case 6:
                addToCustomer(discountCode,(Customer)PersonController.getInstance().getPersonByUsername(newValue));
            case 7:
                removeFromCustomer(discountCode,(Customer)PersonController.getInstance().getPersonByUsername(newValue));

        }
    }

    public void addToCustomer(DiscountCode discountCode,Customer customer){
        customer.addDiscountCode(discountCode,discountCode.getUseCounter());
    }

    public void removeFromCustomer(DiscountCode discountCode,Customer customer){
        customer.getDiscountCodes().remove(discountCode);
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

}
