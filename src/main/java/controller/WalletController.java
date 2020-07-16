package controller;

import bank.BankAPI;
import bank.BankServer;

public class WalletController {
    private BankServer bankServer;
    private static WalletController single_instance = null;
    private double MIN_BALANCE;

    private WalletController() {
        bankServer = BankServer.getInstance();
    }

    public static WalletController getInstance() {
        if (single_instance == null)
            single_instance = new WalletController();

        return single_instance;
    }
}
