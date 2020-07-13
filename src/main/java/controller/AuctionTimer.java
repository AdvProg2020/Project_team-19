package controller;

import java.util.TimerTask;

public class AuctionTimer extends TimerTask {
    @Override
    public void run() {
        ProductController.getInstance().checkAuctionTime();
    }
}
