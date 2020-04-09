package model;

import java.util.ArrayList;

public class DiscountCode {
    String code;
    String startTime;
    String endTime;
    double discountPercentage;
    double maxDiscount; //is this percentage or amount?
    int useCounter;
    ArrayList<Person> customerList;
}
