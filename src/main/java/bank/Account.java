package bank;

import org.apache.commons.lang3.RandomStringUtils;

public class Account {
    private String id;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private double balance;

    public Account(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.balance = 0;
        this.id = RandomStringUtils.random(8, false, true);
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void increaseBalance(double amount) {
        this.balance += amount;
    }

    public void decreaseBalance(double amount) {
        this.balance -= amount;
    }
}
