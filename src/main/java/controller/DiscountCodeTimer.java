package controller;

import java.util.TimerTask;

public class DiscountCodeTimer extends TimerTask {
    @Override
    public void run() {
        DiscountCodeController.getInstance().checkDiscountCodeEndTime();
    }
}
