package bank;

public class Account {
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
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
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
