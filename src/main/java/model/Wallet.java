package model;

import controller.WalletController;

public class Wallet {
    private double balance;
    private String bankId;
    private double blocked;

    public Wallet(String bankId) {
        this.balance = 0;
        this.bankId = bankId;
        this.blocked = WalletController.MIN_BALANCE;
    }

    public double getBalance() {
        return balance;
    }

    public double getTotalBalance() {
        return balance + blocked;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void increaseBalance(double amount) {
        balance += amount;
    }

    public void decreaseBalance(double amount) {
        balance -= amount;
    }

    public void setBlocked(double blocked) {
        this.blocked = blocked;
    }

    public double getBlocked() {
        return blocked;
    }

    public void increaseBlocked(double amount) {
        blocked += amount;
    }

    public void decreaseBlocked(double amount) {
        blocked -= amount;
    }

}
