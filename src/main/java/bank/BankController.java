package bank;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import static bank.BankDB.*;

public class BankController {
    private static BankController single_instance = null;
    public ArrayList<Account> bankAccounts = new ArrayList<>();
    public ArrayList<Receipt> receipts = new ArrayList<>();

    public void initializeAccounts() {
        for (File account : BankDB.returnListOfFiles(BankDB.address.get("accounts"))) {
            bankAccounts.add((Account) BankDB.read(Account.class, account.getAbsolutePath()));
        }
    }

    public void initializeReceipts() {
        for (File receipt : BankDB.returnListOfFiles(BankDB.address.get("receipts"))) {
            receipts.add((Receipt) BankDB.read(Receipt.class, receipt.getAbsolutePath()));
        }
    }

    private BankController() {
    }

    public static BankController getInstance() {
        if (single_instance == null)
            single_instance = new BankController();

        return single_instance;
    }

    public boolean isPasswordsCorrect(String username, String password) {
        return getAccountByUserName(username).getPassword().equals(password);
    }

    public Account getAccountByUserName(String username) {
        for (Account account : bankAccounts) {
            if (account.getUsername().equals(username))
                return account;
        }
        return null;
    }

    public Account getAccountById(String id) {
        for (Account account : bankAccounts) {
            if (account.getId().equals(id))
                return account;
        }
        return null;
    }

    public Receipt getReceiptById(String receiptId) {
        for (Receipt receipt : receipts) {
            if (receipt.getReceiptId().equals(receiptId))
                return receipt;
        }
        return null;
    }

    public boolean isThereAccountByUsername(String username) {
        return getAccountByUserName(username) != null;
    }

    public boolean isThereAccountById(String id) {
        return getAccountById(id) != null;
    }

    public boolean isThereReceiptById(String receiptId) {
        return getReceiptById(receiptId) != null;
    }

    public boolean isReceiptForAccount(String username, String receiptId) {
        Receipt receipt = getReceiptById(receiptId);
        Account account = getAccountByUserName(username);
        return receipt.getSourceId().equals(account.getId()) || receipt.getDestId().equals(account.getId());
    }

    public ArrayList<Receipt> getReceiptsWithSourceId(String sourceId) {
        ArrayList<Receipt> srcReceipts = new ArrayList<>();
        for (Receipt receipt : receipts) {
            if (receipt.getSourceId().equals(sourceId))
                srcReceipts.add(receipt);
        }
        return srcReceipts;
    }

    public ArrayList<Receipt> getReceiptsWithDestId(String destId) {
        ArrayList<Receipt> destReceipts = new ArrayList<>();
        for (Receipt receipt : receipts) {
            if (receipt.getDestId().equals(destId))
                destReceipts.add(receipt);
        }
        return destReceipts;
    }

    public String createAccount(String firstName, String lastName, String username, String password, String repeatedPass) throws BankException {
        if (isThereAccountByUsername(username))
            throw new BankException("account_exist");
        if (!repeatedPass.equals(password))
            throw new BankException("wrong_2nd_password");

        Account account = new Account(firstName, lastName, username, password);
        saveToFile(account, createPath("accounts", account.getUsername()));
        bankAccounts.add(account);
        return account.getId();
    }

    public String getToken(String username, String password) throws BankException {
        if (!isThereAccountByUsername(username))
            throw new BankException("no_account_exist");
        if (!isPasswordsCorrect(username, password))
            throw new BankException("wrong_password");

        return UUID.randomUUID().toString().substring(0, 8);
    }

    public String createReceipt(String receiptType, String money, String sourceId, String destId, String description) throws BankException {
        if (!receiptType.matches("deposit|move|withdraw"))
            throw new BankException("invalid_type");
        if (!money.matches("^\\d*(\\.\\d+)?$"))
            throw new BankException("invalid_number");
        if (Integer.parseInt(money) == 0)
            throw new BankException("money_must_be_greater_than_zero");
        if (receiptType.equals("deposit") && !sourceId.equals("-1"))
            throw new BankException("invalid_account_id");
        if (receiptType.equals("withdraw") && !destId.equals("-1"))
            throw new BankException("invalid_account_id");
        if (!sourceId.equals("-1") && !isThereAccountById(sourceId))
            throw new BankException("invalid_sourceId");
        if (!destId.equals("-1") && !isThereAccountById(destId))
            throw new BankException("invalid_destId");
        if (sourceId.equals(destId))
            throw new BankException("equal_source_and_dest_account");


        String receiptId = RandomStringUtils.random(4, false, true);
        double moneyAmount = Double.parseDouble(money);
        Receipt receipt = new Receipt(receiptId, receiptType, description, moneyAmount, sourceId, destId);
        receipts.add(receipt);
        saveToFile(receipt, createPath("receipts", receiptId));
        return receiptId;
    }

    public String getTransaction(String type, String username) throws BankException {
        if (!type.matches("[+\\-*]") && !isThereReceiptById(type))
            throw new BankException("invalid_receipt_id");

        if (!type.matches("[+\\-*]")) {
            if (!isReceiptForAccount(username, type))
                throw new BankException("not_your_receipt");
        }

        String transaction;
        Account account = getAccountByUserName(username);
        switch (type) {
            case "+":
                transaction = destTransactions(account.getId());
                break;
            case "-":
                transaction = sourceTransactions(account.getId());
                break;
            case "*":
                transaction = allTransactions(account.getId());
                break;
            default:
                Receipt receipt = getReceiptById(type);
                transaction = receipt.toString();
                break;
        }
        return transaction;
    }

    public String destTransactions(String destId) {
        String destTransactions = "";
        int index = 0;
        int size = getReceiptsWithDestId(destId).size();
        for (Receipt receipt : getReceiptsWithDestId(destId)) {
            destTransactions += receipt.toString();
            if (index != size - 1)
                destTransactions += "*";
            index++;
        }
        return destTransactions;
    }

    public String sourceTransactions(String sourceId) {
        String sourceTransactions = "";
        int index = 0;
        int size = getReceiptsWithSourceId(sourceId).size();
        for (Receipt receipt : getReceiptsWithSourceId(sourceId)) {
            sourceTransactions += receipt.toString();
            if (index != size - 1)
                sourceTransactions += "*";
            index++;
        }
        return sourceTransactions;
    }

    public String allTransactions(String username) {
        return sourceTransactions(username) + "*" + destTransactions(username);
    }

    public double getBalance(String username) {
        return getAccountByUserName(username).getBalance();
    }

    public void pay(String receiptId) throws BankException {
        if (!isThereReceiptById(receiptId))
            throw new BankException("invalid_receipt_id");
        Receipt receipt = getReceiptById(receiptId);
        if (receipt.isPaid())
            throw new BankException("receipt_is_payed_before");
        if (!receipt.getSourceId().equals("-1")) {
            Account source = getAccountById(receipt.getSourceId());
            if (receipt.getMoney() >= source.getBalance())
                throw new BankException("source_account_does_not_have_enough_money");
        }

        receipt.pay();
        receipt.setPaid(true);
        saveToFile(receipt, createPath("receipts", receiptId));
    }

    public void move(Account source, Account dest, double money) {
        source.decreaseBalance(money);
        dest.increaseBalance(money);

        saveToFile(source, createPath("accounts", source.getUsername()));
        saveToFile(dest, createPath("accounts", dest.getUsername()));
    }

    public void deposit(Account dest, double money) {
        dest.increaseBalance(money);
        saveToFile(dest, createPath("accounts", dest.getUsername()));
    }

    public void withdraw(Account source, double money) {
        source.decreaseBalance(money);
        saveToFile(source, createPath("accounts", source.getUsername()));
    }

    static class BankException extends Exception {
        public BankException(String msg) {
            super(msg);
        }
    }

}
