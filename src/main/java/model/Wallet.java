package model;

import controller.WalletController;

public class Wallet {
    private double balance;
    private String bankId;

    public Wallet(double balance, String bankId) {
        this.balance = balance;
        this.bankId = bankId;
    }

    public double getBalance() {
        return balance;
    }

    public String getBankId() {
        return bankId;
    }

    public void increaseBalance(double amount) {
        balance += amount;
    }

    public void decreaseBalance(double amount) {
        balance -= amount;
    }
}
