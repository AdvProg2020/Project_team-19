package model;

public class Wallet {
    private double balance;

    public Wallet(double balance) {

    }

    public double getBalance() {
        return balance;
    }

    public void increaseBalance(double amount) {
        balance += amount;
    }

    public void decreaseBalance(double amount) {
        balance -= amount;
    }
}
