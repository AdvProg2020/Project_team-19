package Model;

import java.util.ArrayList;
import java.util.UUID;

public class Log {
    UUID logID;
    String date;
    double paymentAmount;
    double discountAmount;
    ArrayList < Product > tradedProductList;
    String buyerName;
    String sellerName;
    boolean reachedBuyer;
}
