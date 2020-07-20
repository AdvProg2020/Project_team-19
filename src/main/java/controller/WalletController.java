package controller;

import bank.BankAPI;
import model.Customer;
import model.Person;
import model.Salesperson;

import static controller.Database.*;
public class WalletController {
    private static WalletController single_instance = null;
    public static double MIN_BALANCE;
    public static double WAGE;
    public static String SHOP_BANK_ID;
    public static String SHOP_BANK_USERNAME;
    public static String SHOP_BANK_PASSWORD;

    private WalletController() {

    }

    public static WalletController getInstance() {
        if (single_instance == null)
            single_instance = new WalletController();

        return single_instance;
    }

    public void initializer() {
        MIN_BALANCE = (Double) Database.read(Double.class, address.get("min_balance"));
        WAGE = (Double) read(Double.class, address.get("wage"));
        SHOP_BANK_ID = (String) read(String.class, address.get("shop_bankId"));
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

    public void setShopBankUsername(String shopBankUsername) {
        SHOP_BANK_USERNAME = shopBankUsername;
        saveToFile(SHOP_BANK_USERNAME, address.get("shop_username"));
    }

    public void setShopBankPassword(String shopBankPassword) {
        SHOP_BANK_PASSWORD = shopBankPassword;
        saveToFile(SHOP_BANK_PASSWORD, address.get("shop_password"));
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

    public String increaseShopBalance(double amount) {
        String msg = "get_token " + SHOP_BANK_USERNAME + " " + SHOP_BANK_PASSWORD;
        String token = BankAPI.getBankResponse(msg);
        String receiptId = getBankIncreaseBalance(amount, token, SHOP_BANK_ID);
        return getPayResponse(receiptId);
    }

    public String moveFromCustomerToBank(double amount, String username, String password, String sourceId) {
        String msg = "get_token " + username + " " + password;
        String token = BankAPI.getBankResponse(msg);
        String receiptId = getWalletIncreaseBalanceRespond(amount, token, sourceId);
        return getPayResponse(receiptId);
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

    public void increaseWalletBalance(Person person, double amount) {
        if (person instanceof Salesperson) {
            ((Salesperson) person).getWallet().increaseBalance(amount);
            saveToFile(person, createPath("salespersons", person.getUsername()));
        } else if (person instanceof Customer) {
            ((Customer) person).getWallet().increaseBalance(amount);
            saveToFile(person, createPath("customers", person.getUsername()));
        }
    }

    public boolean canDecreaseWalletBalance(Person person, double amount) {
        if (person instanceof Salesperson) {
            return ((Salesperson)person).getWallet().getBalance() - amount >= WalletController.MIN_BALANCE;
        } else if (person instanceof  Customer) {
            return ((Customer)person).getWallet().getBalance() - amount >= WalletController.MIN_BALANCE;
        }
        return false;
    }

    public void decreaseWalletBalance(Person person, double amount) {
        System.out.println(address);
        if (person instanceof Salesperson) {
            ((Salesperson) person).getWallet().decreaseBalance(amount);
            saveToFile(person, createPath("salespersons", person.getUsername()));
        } else if (person instanceof Customer) {
            ((Customer) person).getWallet().decreaseBalance(amount);
            saveToFile(person, createPath("customers", person.getUsername()));
        }
    }

    public void setWalletForCustomer() {

    }
}
