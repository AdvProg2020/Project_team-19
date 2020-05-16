package controller;

import java.util.TimerTask;

public class DiscountTimer extends TimerTask {
    @Override
    public void run() {
        DiscountController.getInstance().checkDiscountTime();
    }
}