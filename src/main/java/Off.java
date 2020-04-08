import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Pattern;

public class Off {
    UUID offID;
    ArrayList<Merch> offMerchList;
    int[] offState; //or enum?
    String startTime; //we can use "new java.util.Date()" that gives the exact time
    String endTime;
    double offPercentage;

}
