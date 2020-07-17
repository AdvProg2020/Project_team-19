package controller;

import bank.BankAPI;

import static controller.Database.*;
public class WalletController {
    private static WalletController single_instance = null;
    public static double MIN_BALANCE;
    public static double WAGE;
    public static String SHOP_BANK_ID;

    private WalletController() {

    }

    public static WalletController getInstance() {
        if (single_instance == null)
            single_instance = new WalletController();

        return single_instance;
    }

    public void setMIN_BALANCE(double MIN_BALANCE) {
        WalletController.MIN_BALANCE = MIN_BALANCE;
        saveToFile(MIN_BALANCE, address.get("min_balance"));
    }

    public void setWAGE(double WAGE) {
        WalletController.WAGE = WAGE;
        saveToFile(WAGE, address.get("wage"));
    }

    public void setSHOP_BANK_ID(String SHOP_BANK_ID) {
        WalletController.SHOP_BANK_ID = SHOP_BANK_ID;
        saveToFile(SHOP_BANK_ID, address.get("shop_bankId"));
    }

    public double getMIN_BALANCE() {
        return MIN_BALANCE;
    }

    public double getWAGE() {
        return WAGE;
    }

    public String getSHOP_BANK_ID() {
        return SHOP_BANK_ID;
    }

    public String getWalletIncreaseBalanceRespond(double amount, String token, String sourceId) {
        String msg = "create_receipt " +
                token + " " +
                "move " +
                amount + " " +
                sourceId + " " +
                SHOP_BANK_ID + " " +
                "hey.";

        return BankAPI.getBankResponse(msg);
    }

    public String getBankIncreaseBalance(double amount, String token, String destId) {
        String msg = "create_receipt " +
                token + " " +
                "deposit " +
                amount + " " +
                "-1 " +
                destId + " " +
                "hey.";
        return BankAPI.getBankResponse(msg);
    }

    public String getPayResponse(String receiptId) {
        String msg = "pay " + receiptId;
        return BankAPI.getBankResponse(msg);
    }

    public String getWalletDecreaseBalanceRespond(double amount, String token, String destId) {
        String msg = "create_receipt " +
                token + " " +
                "move " +
                amount + " " +
                SHOP_BANK_ID + " " +
                destId + " " +
                "hey.";

        return BankAPI.getBankResponse(msg);
    }

    public void setWalletForCustomer() {

    }
}
