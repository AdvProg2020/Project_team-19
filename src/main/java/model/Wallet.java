package model;

import bank.BankAPI;

import java.util.UUID;

public class Wallet {
    private double balance;
    private BankInfo bankInfo;

    public Wallet(Person person,String bankAccountId) {
        bankInfo = new BankInfo(person.getUsername(),person.getPassword());
        bankInfo.setBankAccountId(bankAccountId);
    }

    public double getBalance() { return balance; }

    public void setBalance(double balance) { this.balance = balance; }

    private static class BankInfo{
        private String username;
        private String password;
        private UUID token;
        private String bankAccountId;

        public BankInfo(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public UUID getToken() {
            return token;
        }

        public void setToken(UUID token) {
            this.token = token;
        }

        public void setBankAccountId(String bankAccountId) {
            this.bankAccountId = bankAccountId;
        }

        public String getBankAccountId() {
            return bankAccountId;
        }
    }
}
